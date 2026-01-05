<template>
  <div class="test-execution">
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

const currentStep = ref<TreeNode | null>(null)

const defaultProps = {
  children: 'children',
  label: 'label',
}

const treeData = ref<TreeNode[]>([])

const fetchExecutionTree = async () => {
    // Mock Data representing hierarchy: Plan -> Function -> Case -> Step
    treeData.value = [
        {
            id: 'p1',
            label: '测试计划 A',
            type: 'plan',
            children: [
                {
                    id: 'f1',
                    label: '测试模块 1',
                    type: 'function',
                    children: [
                        {
                            id: 'c1',
                            label: '测试用例 1-1',
                            type: 'case',
                            children: [
                                {
                                    id: 's1',
                                    label: '步骤 1: 启动电源',
                                    type: 'step',
                                    status: 0,
                                    description: '按下电源按钮',
                                    expected: '电源指示灯亮起',
                                    logs: []
                                },
                                {
                                    id: 's2',
                                    label: '步骤 2: 检查电压',
                                    type: 'step',
                                    status: 0,
                                    description: '使用万用表检查输出电压',
                                    expected: '电压为 220V +/- 5%',
                                    logs: []
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]
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

const markStatus = (status: number) => {
    if (currentStep.value) {
        currentStep.value.status = status
        currentStep.value.logs?.push({
            time: new Date().toLocaleString(),
            content: `标记状态为: ${getStatusText(status)}`
        })
        ElMessage.success(`步骤已标记为 ${getStatusText(status)}`)
    }
}

onMounted(() => {
    fetchExecutionTree()
})
</script>

<style scoped lang="scss">
.test-execution {
    height: 100%;
}

.execution-layout {
    display: flex;
    height: calc(100vh - 140px); // Adjust based on header/padding
    border: 1px solid #eee;
    background: white;
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
