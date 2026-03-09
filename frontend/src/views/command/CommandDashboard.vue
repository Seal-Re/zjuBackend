<template>
  <div class="command-dashboard">
    <div class="control-panel">
      <span class="label">当前任务：</span>
      <el-select 
        v-model="selectedPlanId" 
        placeholder="请选择测试计划" 
        style="width: 300px"
        filterable
        @change="loadExecutionTree"
      >
        <el-option 
            v-for="p in planOptions" 
            :key="p.value" 
            :label="p.label" 
            :value="p.value" 
        />
      </el-select>
      
      <el-button type="primary" :icon="Refresh" circle style="margin-left: 10px" @click="loadExecutionTree" />
      <div class="right-actions">
          <el-tag type="info" v-if="currentTask">当前选中: {{ currentTask.name }}</el-tag>
      </div>
    </div>

    <div class="dashboard-layout">
        
        <div class="tree-sidebar" v-loading="treeLoading">
            <div class="sidebar-header">步骤导航</div>
            <el-tree
                ref="treeRef"
                :data="treeData"
                :props="defaultProps"
                node-key="id"
                highlight-current
                default-expand-all
                @node-click="handleNodeClick"
            >
                <template #default="{ data }">
                    <span class="custom-tree-node">
                        <el-icon v-if="data.type === 'plan'"><DataBoard /></el-icon>
                        <el-icon v-else-if="data.type === 'function'"><FolderOpened /></el-icon>
                        <el-icon v-else><Operation /></el-icon>
                        <span class="node-label">{{ data.label }}</span>
                        <span v-if="data.status" class="status-dot-mini" :class="getStatusClass(data.status)"></span>
                    </span>
                </template>
            </el-tree>
        </div>

        <div class="card-display-area">
            
            <div v-if="currentTask" class="task-card-wrapper">
                <div class="task-card">
                    <div class="card-header">
                        <span class="seq">{{ currentTask.type === 'step' ? 'Step' : 'Group' }}</span>
                        <span class="title">{{ currentTask.name }}</span>
                        <el-tag size="small" effect="plain" class="type-tag">
                            {{ currentTask.type === 'step' ? '测试步骤' : '功能组' }}
                        </el-tag>
                    </div>
                    
                    <div class="card-body">
                        <div class="info-row">
                            <span class="label">所属计划：</span>
                            <span class="value">{{ currentTask.planName }}</span>
                        </div>
                        
                        <div class="info-row" v-if="currentTask.description">
                            <span class="label">描述信息：</span>
                            <span class="value">{{ currentTask.description }}</span>
                        </div>

                        <div class="status-section">
                            <div class="status-indicator">
                                <span class="status-dot" :class="getStatusClass(currentTask.status)"></span>
                                <span class="status-text">{{ getStatusText(currentTask.status) }}</span>
                            </div>
                            
                            <div class="progress-container" v-if="currentTask.type === 'function'">
                                <el-progress 
                                    :percentage="currentTask.percentage" 
                                    :format="() => `${currentTask.stepCurrent}/${currentTask.stepTotal}`" 
                                />
                            </div>
                        </div>

                        <el-divider />

                        <div class="actions-area">
                            <div class="action-buttons">
                                <el-button 
                                    type="primary" 
                                    size="large" 
                                    circle 
                                    :disabled="currentTask.type !== 'step'"
                                    @click="handleStart"
                                >
                                    <el-icon :size="24"><VideoPlay /></el-icon>
                                </el-button>
                                <div class="action-label">发送指令</div>
                            </div>

                            <div class="action-buttons">
                                <el-button 
                                    type="warning" 
                                    size="large" 
                                    circle 
                                    @click="handlePause"
                                >
                                    <el-icon :size="24"><VideoPause /></el-icon>
                                </el-button>
                                <div class="action-label">暂停/中断</div>
                            </div>

                            <div class="action-buttons">
                                <el-button type="info" size="large" circle>
                                    <el-icon :size="24"><Upload /></el-icon>
                                </el-button>
                                <div class="action-label">上报日志</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <el-empty v-else description="请在左侧选择一个 测试步骤 或 功能组" />
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { 
    VideoPlay, VideoPause, Upload, Refresh, 
    DataBoard, FolderOpened, Operation 
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// API 引入
import { getTestPlans } from '@/api/planner'
import { 
    getExeFunctionsByPlanId,
    getExeStepsByFunction, 
    executeStepCommand, 
    pauseExeFunction 
} from '@/api/execution'

// --- 类型定义 ---
interface TaskNode {
    id: string
    label: string
    type: 'plan' | 'function' | 'step'
    children?: TaskNode[]
    
    // 业务数据
    exeFunctionId?: string
    exeStepId?: string
    status?: number // 0:未开始 1:进行中 2:完成 3:暂停/失败
    
    // 用于卡片展示的数据
    planName?: string
    description?: string
    stepCurrent?: number
    stepTotal?: number
    percentage?: number
}

// --- 状态变量 ---
const treeLoading = ref(false)
const selectedPlanId = ref('')
const planOptions = ref<any[]>([])
const treeData = ref<TaskNode[]>([])
const currentTask = ref<any>(null) // 当前选中的节点转换为卡片数据对象

const defaultProps = { children: 'children', label: 'label' }

const fetchPlans = async () => {
    console.log("【Debug】开始获取测试计划列表...")
    try {
        const res: any = await getTestPlans({})
        console.log("【Debug】getTestPlans 原始返回:", res)
        
        const list = Array.isArray(res) ? res : []
        // 仅展示已派发/执行中的计划，避免选择未派发计划导致“无执行结构”报错
        const runnable = list.filter((p: any) => [0, 1, 2, 3, 4, 6].includes(Number(p.status)))
        planOptions.value = runnable.map((p: any) => ({ 
            label: p.planName, 
            value: p.planId, 
            suiteId: p.suiteId 
        }))
    } catch (e) {
        console.error("【Debug】获取计划失败:", e)
    }
}

// 2. 加载左侧树 (核心修改区域)
const loadExecutionTree = async () => {
    console.log("【Debug】触发 loadExecutionTree, 当前选中PlanID:", selectedPlanId.value)
    
    if (!selectedPlanId.value) {
        console.warn("【Debug】未选择 PlanID，停止加载")
        return
    }
    
    treeLoading.value = true
    treeData.value = []
    currentTask.value = null

    try {
        const planInfo = planOptions.value.find(p => p.value === selectedPlanId.value)
        
        if (!planInfo) {
             console.error("【Debug】无法在选项中找到当前计划信息")
             return
        }

        // 根节点 (Plan)
        const root: TaskNode = {
            id: `plan-${planInfo.value}`,
            label: planInfo.label,
            type: 'plan',
            children: [],
            planName: planInfo.label
        }

        // 执行域正确链路：planId -> exeFunction -> exeStep
        const exeFunctionsRes: any = await getExeFunctionsByPlanId(selectedPlanId.value)
        const exeFunctions = Array.isArray(exeFunctionsRes)
            ? exeFunctionsRes
            : (exeFunctionsRes ? [exeFunctionsRes] : [])

        if (exeFunctions.length === 0) {
            ElMessage.warning("该计划暂无执行结构，请先派发计划")
        }

        for (const func of exeFunctions) {
            const exeFunctionId = func.exeFunctionId || func.id
            const funcNode: TaskNode = {
                id: `func-${exeFunctionId}`,
                exeFunctionId,
                label: func.functionName || func.funName || '未命名功能',
                type: 'function',
                status: 0, 
                children: [],
                planName: planInfo.label,
                description: `执行功能ID: ${exeFunctionId}`
            }

            // 后端接口 /exeStep/getinexe/{exeFunctionId}
            const stepsRes: any = await getExeStepsByFunction(exeFunctionId)

            const steps = Array.isArray(stepsRes) ? stepsRes : []
            // console.log(`【Debug】解析出的步骤数 (${func.funName}):`, steps.length)

            // 计算进度
            let finishedCount = 0
            
            funcNode.children = steps.map((step: any) => {
                const sStatus = step.exeStatus || 0
                if (sStatus === 2) finishedCount++

                return {
                    id: `step-${step.exeStepId}`,
                    exeStepId: step.exeStepId,
                    exeFunctionId,
                    label: step.stepName || '未命名步骤',
                    type: 'step',
                    status: sStatus,
                    planName: planInfo.label,
                    description: step.stepDescription || step.stepDesc || '暂无步骤描述'
                }
            })

            funcNode.stepTotal = steps.length
            funcNode.stepCurrent = finishedCount
            funcNode.percentage = steps.length > 0 ? Math.floor((finishedCount / steps.length) * 100) : 0
            
            if (steps.length > 0 && finishedCount === steps.length) funcNode.status = 2
            else if (finishedCount > 0) funcNode.status = 1

            root.children?.push(funcNode)
        }

        console.log("【Debug】最终生成的树数据:", [root])
        treeData.value = [root]
        
    } catch (e: any) {
        console.error("【Debug】加载执行结构发生异常:", e)
        const msg = String(e?.message || '')
        if (msg.includes('查询不到对应planId的ExeFunction')) {
            ElMessage.warning('当前计划暂无执行结构，请先在测试计划页执行“派发”')
            return
        }
        ElMessage.error('加载执行结构失败')
    } finally {
        treeLoading.value = false
    }
}

// 3. 点击树节点 -> 渲染右侧卡片
const handleNodeClick = (data: TaskNode) => {
    if (data.type === 'plan') {
        currentTask.value = null
        return
    }
    // 构造卡片所需数据
    currentTask.value = {
        id: data.id,
        name: data.label,
        type: data.type,
        planName: data.planName,
        status: data.status,
        description: data.description,
        // 只有 function 有进度
        stepCurrent: data.stepCurrent || 0,
        stepTotal: data.stepTotal || 0,
        percentage: data.percentage || 0,
        // 业务ID
        exeStepId: data.exeStepId,
        exeFunctionId: data.exeFunctionId
    }
}

// 4. 操作逻辑

// 开始/执行指令 (对应 /exeStep/do)
const handleStart = async () => {
    if (!currentTask.value || currentTask.value.type !== 'step') {
        ElMessage.warning('请选择一个具体的步骤来发送指令')
        return
    }

    try {
        const payload = {
            exeStepId: currentTask.value.exeStepId,
            deviceId: '',
            command: "AUTO_EXEC",
            url: ''
        }
        await executeStepCommand(payload)
        
        currentTask.value.status = 1 // 标记为进行中
        ElMessage.success('指令已发送')
        // 这里可以重新刷新树状态，或者手动更新本地状态
    } catch (e) {
        // demo
    }
}

// 暂停 (对应 /exeStep/pause)
const handlePause = async () => {
    if (!currentTask.value) return
    
    // 如果选中的是步骤，则暂停其所属的功能组
    const targetFuncId = currentTask.value.exeFunctionId
    if (!targetFuncId) return

    try {
        await ElMessageBox.confirm('确定要发送暂停指令吗？这将暂停该功能组下的所有步骤。', '警告', {
            type: 'warning'
        })
        await pauseExeFunction(targetFuncId)
        
        currentTask.value.status = 3 // 标记为暂停/异常
        ElMessage.success('暂停指令已发送')
    } catch (e) {
        // cancel
    }
}

// 辅助样式函数
const getStatusClass = (status: number) => {
    if (status === 2) return 'green' // 完成
    if (status === 1) return 'blue'  // 进行中
    if (status === 3) return 'orange' // 暂停
    return 'grey' // 未开始
}

const getStatusText = (status: number) => {
    const map: any = { 0: '未开始', 1: '进行中', 2: '已完工', 3: '已暂停' }
    return map[status] || '未知'
}

onMounted(() => {
    fetchPlans()
})
</script>

<style scoped lang="scss">
.command-dashboard {
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    background-color: #f0f2f5;
}

.control-panel {
    background: white;
    padding: 15px 20px;
    border-radius: 8px;
    margin-bottom: 15px;
    display: flex;
    align-items: center;
    box-shadow: 0 1px 4px rgba(0,0,0,0.05);

    .label {
        font-weight: bold;
        margin-right: 10px;
        color: #333;
    }

    .right-actions {
        margin-left: auto;
    }
}

.dashboard-layout {
    flex: 1;
    display: flex;
    gap: 20px;
    overflow: hidden;
}

// 左侧树样式
.tree-sidebar {
    width: 280px;
    background: white;
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    box-shadow: 0 1px 4px rgba(0,0,0,0.05);

    .sidebar-header {
        padding: 15px;
        font-weight: bold;
        border-bottom: 1px solid #eee;
        background-color: #fafafa;
        border-radius: 8px 8px 0 0;
    }

    .el-tree {
        flex: 1;
        padding: 10px;
        overflow-y: auto;
    }

    .custom-tree-node {
        display: flex;
        align-items: center;
        width: 100%;
        
        .node-label {
            margin-left: 5px;
            flex: 1;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .status-dot-mini {
            width: 6px;
            height: 6px;
            border-radius: 50%;
            margin-left: 5px;
            
            &.green { background: #67C23A; }
            &.blue { background: #409EFF; }
            &.orange { background: #E6A23C; }
            &.grey { background: #eee; }
        }
    }
}

// 右侧卡片区域
.card-display-area {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: flex-start;
    padding-top: 20px;
}

.task-card-wrapper {
    width: 100%;
    max-width: 600px; 
}

.task-card {
    background: white;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    transition: all 0.3s;

    &:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 24px rgba(0,0,0,0.12);
    }

    .card-header {
        background-color: #001529;
        color: white;
        padding: 15px 20px;
        display: flex;
        align-items: center;
        gap: 15px;

        .seq {
            background: rgba(255,255,255,0.2);
            width: 32px;
            height: 32px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
        }

        .title {
            font-size: 18px;
            font-weight: bold;
            flex: 1;
        }
    }

    .card-body {
        padding: 25px;

        .info-row {
            margin-bottom: 12px;
            display: flex;
            
            .label { color: #909399; width: 80px; }
            .value { color: #303133; font-weight: 500; }
        }

        .status-section {
            margin-top: 20px;
            background: #f8f9fa;
            padding: 15px;
            border-radius: 4px;

            .status-indicator {
                display: flex;
                align-items: center;
                gap: 8px;
                margin-bottom: 10px;
                
                .status-dot {
                    width: 12px;
                    height: 12px;
                    border-radius: 50%;
                    &.green { background: #67C23A; }
                    &.blue { background: #409EFF; }
                    &.orange { background: #E6A23C; }
                    &.grey { background: #909399; }
                }
                
                .status-text {
                    font-weight: bold;
                    font-size: 16px;
                }
            }
        }

        .actions-area {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;

            .action-buttons {
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 8px;

                .action-label {
                    font-size: 12px;
                    color: #606266;
                }
            }
        }
    }
}
</style>