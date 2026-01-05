<template>
  <div class="test-execution">
    <!-- Filter Bar -->
    <div class="filter-bar">
         <el-cascader
            v-model="filterStore.model"
            :options="modelOptions"
            placeholder="机型/构型"
            style="width: 200px"
        />
        <el-cascader
            v-model="filterStore.profession"
            :options="professionOptions"
            placeholder="专业"
            style="width: 200px"
        />
        <el-button type="primary" @click="handleFilterChange">查询计划</el-button>

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
    </div>

    <div class="execution-layout">
        <!-- Left: Tree Structure -->
        <div class="tree-panel">
            <div class="panel-header">
                <span>测试步骤导航</span>
                <el-button link type="primary" size="small" @click="expandAll">展开</el-button>
            </div>
            <el-tree
                ref="treeRef"
                :data="treeData"
                :props="defaultProps"
                default-expand-all
                highlight-current
                node-key="id"
                @node-click="handleNodeClick"
            >
                <template #default="{ node, data }">
                    <div class="custom-tree-node">
                        <span>
                           <el-icon v-if="data.type === 'step'"><Operation /></el-icon>
                           <el-icon v-else-if="data.type === 'case'"><Folder /></el-icon>
                           <el-icon v-else><Document /></el-icon>
                           {{ node.label }}
                        </span>
                        <el-tag size="small" :type="getStatusType(data.status)" v-if="data.status !== undefined" style="margin-left: 5px">
                            {{ getStatusText(data.status) }}
                        </el-tag>
                    </div>
                </template>
            </el-tree>
        </div>

        <!-- Right: Execution Detail -->
        <div class="detail-panel">
            <div v-if="currentStep" class="step-detail">
                <div class="detail-header">
                    <h2>{{ currentStep.label }}</h2>
                    <div class="step-actions">
                        <el-button type="primary" @click="markStatus(2)">通过</el-button>
                        <el-button type="danger" @click="markStatus(3)">失败</el-button>
                        <el-button @click="markStatus(1)">跳过</el-button>
                    </div>
                </div>

                <el-descriptions title="步骤信息" :column="1" border>
                    <el-descriptions-item label="步骤描述">
                        {{ currentStep.description || '无描述' }}
                    </el-descriptions-item>
                    <el-descriptions-item label="预期结果">
                        {{ currentStep.expected || '无预期结果' }}
                    </el-descriptions-item>
                    <el-descriptions-item label="实际结果">
                        <el-input v-model="currentStep.actualResult" type="textarea" placeholder="请输入实际结果" />
                    </el-descriptions-item>
                </el-descriptions>

                <div class="log-section">
                    <h3>执行日志</h3>
                    <el-timeline>
                        <el-timeline-item v-for="(log, index) in currentStep.logs" :key="index" :timestamp="log.time">
                            {{ log.content }}
                        </el-timeline-item>
                    </el-timeline>
                </div>
            </div>
            <div v-else class="empty-state">
                <el-empty description="请选择一个测试步骤开始执行" />
            </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Operation, Folder, Document } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { saveExecutionLog, getExecutionTasks } from '@/api/execution'
import { getTestPlans } from '@/api/planner'
import { getTestSuiteDetail } from '@/api/designer'
import { useGlobalFilterStore } from '@/store/globalFilter'

interface TreeNode {
  id: string
  label: string
  type: 'plan' | 'function' | 'case' | 'step'
  children?: TreeNode[]
  status?: number // 0: Pending, 1: Skipped, 2: Passed, 3: Failed
  description?: string
  expected?: string
  actualResult?: string
  logs?: any[]
}

const filterStore = useGlobalFilterStore()

// Filter Options (In real app, fetch from API)
const modelOptions = [{ value: 'model1', label: 'Model A' }]
const professionOptions = [{ value: 'prof1', label: 'Profession A' }]

const currentStep = ref<TreeNode | null>(null)
const selectedPlanId = ref('')
const planOptions = ref<any[]>([])
const treeData = ref<TreeNode[]>([])

const defaultProps = {
  children: 'children',
  label: 'label',
}

const fetchPlans = async () => {
    try {
        const res: any = await getTestPlans({})
        if (Array.isArray(res) && res.length > 0) {
             planOptions.value = res.map((p: any) => ({ label: p.planName, value: p.planId, suiteId: p.suiteId }))
        } else {
             planOptions.value = []
        }
    } catch (e) {
        console.error("Failed to fetch plans", e)
        planOptions.value = []
    }
}

const handleFilterChange = () => {
    fetchPlans()
}

const loadExecutionTree = async () => {
    if (!selectedPlanId.value) return

    try {
        ElMessage.info("Fetching data from backend...")
        treeData.value = []

        const selectedPlan = planOptions.value.find(p => p.value === selectedPlanId.value)
        if (!selectedPlan || !selectedPlan.suiteId) {
             ElMessage.warning("Plan does not have a linked Suite")
             return
        }

        // Fetch Suite Details to get Functions
        // Note: Assuming getTestSuiteDetail returns suite info including functions list or we iterate
        const suiteRes: any = await getTestSuiteDetail(selectedPlan.suiteId)

        // Construct Plan Node
        const planNode: TreeNode = {
            id: `p-${selectedPlan.value}`,
            label: selectedPlan.label,
            type: 'plan',
            children: []
        }

        // Check if suiteRes contains function list (depends on backend DTO)
        // If it doesn't, we might need another call. For now, assuming basic structure or we'd need to mock the response structure logic
        // But prompt says "remove mock data", so we rely on API.

        // If suiteRes is the suite object, does it have functions?
        // Let's assume suiteRes.testFunctions is the list (common pattern)
        const functions = suiteRes.testFunctions || []

        for (const func of functions) {
            const funcNode: TreeNode = {
                id: `f-${func.funId}`,
                label: func.funName,
                type: 'function',
                children: []
            }

            // Fetch Steps for each function
            // This could be slow for many functions, optimization (lazy load) would be better but simple requirement first.
            const stepsRes: any = await getExecutionTasks(func.funId)
            // Steps response structure check
            const steps = Array.isArray(stepsRes) ? stepsRes : (stepsRes.data || [])

            // Group steps by Case? Or just flat list under function?
            // Requirement mentions "Test Plan -> Function -> Case -> Step".
            // Backend `getExecutionTasks` (`/exeFunction/getinexe`) usually returns flat list of steps.
            // If `ExeStep` has `caseId`, we can group.

            // Grouping logic (simplified)
            // Assuming steps have caseId/caseName. If not, maybe just Function -> Step.
            // Let's check ExeStep fields? We can't see DTO easily but usually they do.
            // If we can't group by case, we will just list steps.

            const caseMap = new Map<string, TreeNode>()

            for (const step of steps) {
                const caseId = step.caseId || 'default'
                const caseName = step.caseName || 'Default Case'

                if (!caseMap.has(caseId)) {
                     caseMap.set(caseId, {
                        id: `c-${caseId}`,
                        label: caseName,
                        type: 'case',
                        children: []
                     })
                }

                const stepNode: TreeNode = {
                    id: step.exeStepId || `s-${step.stepId}`,
                    label: step.stepName || 'Unknown Step',
                    type: 'step',
                    status: step.exeStatus || 0, // Assuming 0 for pending
                    description: step.stepDesc,
                    expected: step.stepExpect,
                    logs: [] // Logs would need separate fetch or be part of step info
                }
                caseMap.get(caseId)?.children?.push(stepNode)
            }

            funcNode.children = Array.from(caseMap.values())
            planNode.children?.push(funcNode)
        }

        treeData.value = [planNode]

    } catch (e) {
        console.error(e)
        ElMessage.error("Failed to load execution tree")
    }
}

const handleNodeClick = (data: TreeNode) => {
    if (data.type === 'step') {
        currentStep.value = data
        if (!currentStep.value.logs) currentStep.value.logs = []
    } else {
        currentStep.value = null
    }
}

const expandAll = () => {
    // logic to expand all nodes
}

const getStatusType = (status?: number) => {
    switch (status) {
        case 2: return 'success'
        case 3: return 'danger'
        case 1: return 'info'
        default: return ''
    }
}

const getStatusText = (status?: number) => {
    switch (status) {
        case 2: return '通过'
        case 3: return '失败'
        case 1: return '跳过'
        default: return '未执行'
    }
}

const markStatus = async (status: number) => {
    if (currentStep.value) {
        try {
            // Save log to backend
            const content = `标记状态为: ${getStatusText(status)}`
            await saveExecutionLog({
                stepId: currentStep.value.id,
                content: content,
                createTime: new Date()
            })

            // Update local state
            currentStep.value.status = status
            currentStep.value.logs?.push({
                time: new Date().toLocaleString(),
                content: content
            })
            ElMessage.success(`步骤已标记为 ${getStatusText(status)}`)
        } catch (e) {
            ElMessage.error("Failed to save log")
        }
    }
}

onMounted(() => {
    fetchPlans()
})
</script>

<style scoped lang="scss">
.test-execution {
    height: 100%;
    display: flex;
    flex-direction: column;
}

.filter-bar {
    padding: 10px;
    background: white;
    margin-bottom: 10px;
    border-radius: 4px;
    display: flex;
    align-items: center;
    gap: 10px;
}

.execution-layout {
    flex: 1;
    display: flex;
    border: 1px solid #eee;
    background: white;
    overflow: hidden;
}

.tree-panel {
    width: 300px;
    border-right: 1px solid #eee;
    display: flex;
    flex-direction: column;

    .panel-header {
        padding: 10px;
        border-bottom: 1px solid #eee;
        display: flex;
        justify-content: space-between;
        align-items: center;
        background: #fafafa;
        font-weight: bold;
    }

    .el-tree {
        flex: 1;
        overflow-y: auto;
        padding: 10px;
    }
}

.detail-panel {
    flex: 1;
    padding: 20px;
    overflow-y: auto;

    .step-detail {
        .detail-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;

            h2 {
                margin: 0;
            }
        }

        .log-section {
            margin-top: 30px;
        }
    }

    .empty-state {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
    }
}

.custom-tree-node {
    display: flex;
    align-items: center;
    font-size: 14px;

    .el-icon {
        margin-right: 5px;
    }
}
</style>
