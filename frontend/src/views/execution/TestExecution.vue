<template>
  <div class="test-execution">
    <div class="filter-bar">
      <el-cascader v-model="filterStore.model" :options="modelOptions" placeholder="机型/构型" style="width: 200px" />
      <el-cascader v-model="filterStore.profession" :options="professionOptions" placeholder="专业" style="width: 200px" />
      
      <el-select
        v-model="selectedPlanId"
        placeholder="选择测试计划"
        style="width: 250px; margin-left: 20px"
        @change="loadExecutionTree"
        filterable
      >
        <el-option
          v-for="item in planOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-button type="primary" @click="fetchPlans" style="margin-left: 10px;">刷新计划</el-button>
    </div>

    <div class="execution-layout">
      <div class="tree-panel">
        <div class="panel-header">
          <span>测试执行导航</span>
          <div>
             <el-button link type="primary" size="small" @click="expandAll">展开</el-button>
             <el-button link type="primary" size="small" @click="loadExecutionTree">刷新</el-button>
          </div>
        </div>
        <el-tree
          ref="treeRef"
          :data="treeData"
          :props="defaultProps"
          default-expand-all
          highlight-current
          node-key="id"
          @node-click="handleNodeClick"
          v-loading="treeLoading"
        >
          <template #default="{ node, data }">
            <div class="custom-tree-node">
              <span>
                <el-icon v-if="data.type === 'step'"><Operation /></el-icon>
                <el-icon v-else-if="data.type === 'function'"><FolderOpened /></el-icon>
                <el-icon v-else-if="data.type === 'plan'"><DataBoard /></el-icon>
                {{ node.label }}
              </span>
              <el-tag 
                v-if="data.type === 'step' && data.status" 
                size="small" 
                :type="getStatusType(data.status)" 
                style="margin-left: 5px"
              >
                {{ getStatusText(data.status) }}
              </el-tag>
            </div>
          </template>
        </el-tree>
      </div>

      <div class="detail-panel" v-loading="detailLoading">
        
        <div v-if="currentNode?.type === 'step'" class="step-detail">
          <div class="detail-header">
            <div class="title-area">
                <h2>{{ currentNode.label }}</h2>
                <el-tag :type="getStatusType(currentNode.status)">{{ getStatusText(currentNode.status) }}</el-tag>
            </div>
            
            <div class="step-actions">
              <el-button type="primary" plain icon="VideoPlay" @click="handleExecuteCommand">执行指令</el-button>
              
              <el-divider direction="vertical" />
              
              <el-button type="success" @click="handleOperate('PASS')">通过</el-button>
              <el-button type="danger" @click="handleOperate('FAIL')">失败</el-button>
              <el-button type="warning" @click="handleOperate('SKIP')">跳过</el-button>
            </div>
          </div>

          <el-descriptions title="步骤详情" :column="1" border>
            <el-descriptions-item label="步骤描述">
              {{ currentNode.description || '暂无描述' }}
            </el-descriptions-item>
            <el-descriptions-item label="预期结果">
              {{ currentNode.expected || '暂无预期' }}
            </el-descriptions-item>
            <el-descriptions-item label="实际结果">
              <el-input v-model="currentNode.actualResult" type="textarea" :rows="2" placeholder="请输入实际结果记录" />
            </el-descriptions-item>
          </el-descriptions>

          <div class="log-section">
            <div class="section-title">
                <h3>执行日志</h3>
                <el-button size="small" @click="addManualLog">添加备注</el-button>
            </div>
            <el-timeline>
              <el-timeline-item
                v-for="(log, index) in currentNode.logs"
                :key="index"
                :timestamp="log.createTime"
                :type="log.type || 'primary'"
              >
                {{ log.content }}
              </el-timeline-item>
            </el-timeline>
          </div>
        </div>

        <div v-else-if="currentNode?.type === 'function'" class="function-detail">
             <el-empty description="功能节点操作区">
                 <template #image>
                     <el-icon :size="60"><FolderOpened /></el-icon>
                 </template>
                 <div class="function-actions">
                     <p>功能名称: {{ currentNode.label }}</p>
                     <el-button type="warning" icon="VideoPause" @click="handleBatchPause">批量暂停本功能下所有步骤</el-button>
                 </div>
             </el-empty>
        </div>

        <div v-else class="empty-state">
          <el-empty description="请点击左侧具体的测试步骤进行执行" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Operation, FolderOpened, DataBoard, } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 引入 API
import { getTestPlans } from '@/api/planner'
import { getTestSuiteDetail } from '@/api/designer' // 假设你有获取Suite详情的API
import { 
    getExeStepsByFunction, 
    operateStep, 
    executeStepCommand, 
    pauseExeFunction, 
    saveExecutionLog 
} from '@/api/execution'

import { useGlobalFilterStore } from '@/store/globalFilter'

// --- 类型定义 ---
interface TreeNode {
  id: string
  label: string
  type: 'plan' | 'function' | 'step'
  children?: TreeNode[]
  
  // 业务字段
  exeStepId?: string // 步骤真实ID
  exeFunctionId?: string // 功能真实ID
  
  status?: string // 后端可能返回字符串或数字，这里统一处理
  description?: string
  expected?: string
  actualResult?: string
  logs?: any[]
}

const filterStore = useGlobalFilterStore()

// Mock 选项
const modelOptions = [{ value: 'model1', label: 'C919' }]
const professionOptions = [{ value: 'prof1', label: '飞控系统' }]

// 状态变量
const treeLoading = ref(false)
const detailLoading = ref(false)
const selectedPlanId = ref('')
const planOptions = ref<any[]>([])
const treeData = ref<TreeNode[]>([])
const currentNode = ref<TreeNode | null>(null)
const treeRef = ref()

const defaultProps = { children: 'children', label: 'label' }

// 1. 获取测试计划列表
const fetchPlans = async () => {
  try {
    const list: any = await getTestPlans({})
    const arr = Array.isArray(list) ? list : []
    planOptions.value = arr.map((p: any) => ({ 
        label: p.planName, 
        value: p.planId
    }))
  } catch (e) {
    console.error("Fetch plans failed", e)
  }
}

// 2. 加载执行树 (Plan -> ExeFunction -> ExeSteps)
const loadExecutionTree = async () => {
  if (!selectedPlanId.value) return
  
  treeLoading.value = true
  treeData.value = []
  currentNode.value = null

  try {
    const selectedPlan = planOptions.value.find(p => p.value === selectedPlanId.value)
    if (!selectedPlan) return

    // 构建根节点：计划
    const planNode: TreeNode = {
      id: `plan-${selectedPlan.value}`,
      label: selectedPlan.label,
      type: 'plan',
      children: []
    }

    // 根据 planId 获取执行功能（ExeFunction）
    const exeFunc: any = await getExeFunctionsByPlanId(selectedPlan.value)
    if (!exeFunc || !exeFunc.exeFunctionId) {
      ElMessage.warning('当前计划下暂无可执行功能')
      treeData.value = [planNode]
      return
    }

    const funcNode: TreeNode = {
      id: `func-${exeFunc.exeFunctionId}`,
      exeFunctionId: exeFunc.exeFunctionId,
      label: exeFunc.functionName || '执行功能',
      type: 'function',
      children: []
    }

    // 核心对接：调用后端 /exeStep/getinexe/{exeFunctionId}
    const stepsRes: any = await getExeStepsByFunction(exeFunc.exeFunctionId)
    const steps = Array.isArray(stepsRes) ? stepsRes : []

    funcNode.children = steps.map((step: any) => ({
      id: `step-${step.exeStepId}`,
      exeStepId: step.exeStepId,
      label: step.stepDescription || `步骤 ${step.stepSeq || ''}` || '未命名步骤',
      type: 'step',
      status: step.exeStatus, // 数字状态，后续映射
      description: step.stepDescription,
      expected: step.criterionDesc,
      logs: [],
      actualResult: step.stepResult || ''
    }))

    planNode.children?.push(funcNode)

    treeData.value = [planNode]
  } catch (e) {
    console.error(e)
    ElMessage.error('加载执行树失败')
  } finally {
    treeLoading.value = false
  }
}

// 3. 节点点击
const handleNodeClick = (data: TreeNode) => {
  currentNode.value = data
  if (data.type === 'step') {
      // 可以在这里调用单独的日志接口刷新日志
  }
}

// 4. 执行指令 (对应后端 /do)
const handleExecuteCommand = async () => {
    if (!currentNode.value?.exeStepId) return
    
    detailLoading.value = true
    try {
        // 构造 ExeStepCommand 对象，对应后端 ExeStepCommand
        const commandPayload = {
            exeStepId: currentNode.value.exeStepId,
            deviceId: '',          // 设备管理尚未完备，这里先留空或传占位
            command: 'AUTO_EXEC',  // 示例指令内容
            url: ''                // 如需对接设备网关，可在此填入地址
        }
        
        await executeStepCommand(commandPayload)
        ElMessage.success('指令发送成功')
        addLocalLog('发送设备执行指令 V1')
    } catch (e) {
        ElMessage.error('指令发送失败')
    } finally {
        detailLoading.value = false
    }
}

// 5. 步骤操作 (对应后端 /stepOperate)
// result 表示前端语义：PASS/FAIL/SKIP，后端统一使用 doFinish 更新为完成状态
const handleOperate = async (result: 'PASS' | 'FAIL' | 'SKIP') => {
    if (!currentNode.value?.exeStepId) return

    try {
        await operateStep({
            exeStepId: currentNode.value.exeStepId,
            option: 'doFinish'
        })
        
        // 更新本地状态为「已完成」
        currentNode.value.status = 6
        const text = result === 'PASS' ? '通过' : result === 'FAIL' ? '失败' : '跳过'
        ElMessage.success(`操作成功: ${text}`)
        addLocalLog(`人工标记为: ${text}`, result === 'FAIL' ? 'danger' : 'success')
    } catch (e) {
        ElMessage.error('操作提交失败')
    }
}

// 6. 批量暂停 (对应后端 /pause/{exeFunctionId})
const handleBatchPause = async () => {
    if (!currentNode.value?.exeFunctionId) return
    
    try {
        await ElMessageBox.confirm('确定要暂停该功能下的所有步骤吗？', '提示', { type: 'warning' })
        await pauseExeFunction(currentNode.value.exeFunctionId)
        ElMessage.success('批量暂停成功')
        // 这里建议重新加载树或更新子节点状态
        loadExecutionTree() 
    } catch (e) {
        if (e !== 'cancel') ElMessage.error('批量暂停失败')
    }
}

// 7. 保存日志 (对应后端 /log/save)
const addManualLog = async () => {
    if (!currentNode.value) return
    
    try {
        const { value } = await ElMessageBox.prompt('请输入备注内容', '添加备注')
        if (value) {
            // 调用后端保存，对应 ExeLog
            await saveExecutionLog({
                stepId: currentNode.value.exeStepId,
                planId: selectedPlanId.value || '',
                content: value,
                createTime: new Date()
            })
            addLocalLog(`备注: ${value}`, 'info')
            ElMessage.success('日志保存成功')
        }
    } catch(e) {}
}

// 辅助：添加本地日志显示 (不调接口)
const addLocalLog = (content: string, type: string = 'primary') => {
    if (currentNode.value) {
        if (!currentNode.value.logs) currentNode.value.logs = []
        currentNode.value.logs.unshift({
            content,
            createTime: new Date().toLocaleString(),
            type
        })
    }
}

// 工具函数
const expandAll = () => { /* 展开逻辑 */ }

const getStatusText = (status: any) => {
    // 后端 exeStatus 使用 TestPlanStatusContants：0 未执行、2 执行中、3 暂停、6 已完成
    const map: any = { 
        0: '未执行',
        2: '执行中',
        3: '已暂停',
        6: '已完成'
    }
    return map[status] || status || '未执行'
}

const getStatusType = (status: any) => {
    const map: any = { 
        0: '',
        2: 'success',
        3: 'warning',
        6: 'success'
    }
    return map[status] || 'info'
}

onMounted(() => {
  fetchPlans()
})
</script>

<style scoped lang="scss">
.test-execution { height: 100%; display: flex; flex-direction: column; }
.filter-bar { padding: 10px; background: white; margin-bottom: 10px; border-radius: 4px; display: flex; align-items: center; gap: 10px; }
.execution-layout { flex: 1; display: flex; border: 1px solid #eee; background: white; overflow: hidden; }
.tree-panel { 
    width: 320px; border-right: 1px solid #eee; display: flex; flex-direction: column; 
    .panel-header { padding: 10px; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; align-items: center; background: #fafafa; font-weight: bold; }
    .el-tree { flex: 1; overflow-y: auto; padding: 10px; }
}
.detail-panel { flex: 1; padding: 20px; overflow-y: auto; background: #fff; }
.custom-tree-node { display: flex; align-items: center; font-size: 14px; .el-icon { margin-right: 5px; } }

.detail-header {
    display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #eee;
    .title-area { display: flex; align-items: center; gap: 10px; h2 { margin: 0; } }
}
.log-section {
    margin-top: 30px;
    .section-title { display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; }
}
.function-detail {
    display: flex; justify-content: center; align-items: center; height: 100%;
    .function-actions { text-align: center; margin-top: 20px; }
}
.empty-state { display: flex; justify-content: center; align-items: center; height: 100%; color: #909399; }
</style>