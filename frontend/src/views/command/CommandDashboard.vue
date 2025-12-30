<template>
  <div class="command-dashboard">
    <!-- Control Flow -->
    <div class="control-panel">
        <el-select v-model="selectedModel" placeholder="机型/构型" style="width: 200px">
            <el-option label="Model A" value="modelA" />
        </el-select>
        <el-select v-model="selectedPlan" placeholder="选择测试计划" style="width: 200px; margin-left: 10px">
            <el-option label="Plan 1" value="plan1" />
        </el-select>
        <el-button type="primary" style="margin-left: 10px" @click="queryTasks">执行组</el-button>
    </div>

    <!-- Task Cards -->
    <div class="card-grid">
        <div v-for="(task, index) in tasks" :key="index" class="task-card">
            <div class="card-header">
                <span class="seq">{{ index + 1 }}</span>
                <span class="title">{{ task.name }}</span>
            </div>
            <div class="card-body">
                <div class="plan-name">{{ task.planName }}</div>
                <div class="status-row">
                    <span class="status-dot" :class="getStatusClass(task.status)"></span>
                    <span>{{ getStatusText(task.status) }}</span>
                </div>
                <div class="progress-container">
                    <el-progress :percentage="task.percentage" :format="() => `${task.stepCurrent}/${task.stepTotal}`" />
                </div>
                <div class="actions">
                    <el-button circle size="small" @click="handleStart(task)"><el-icon><VideoPlay /></el-icon></el-button>
                    <el-button circle size="small" @click="handlePause(task)"><el-icon><VideoPause /></el-icon></el-button>
                    <el-button circle size="small"><el-icon><Upload /></el-icon></el-button>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { VideoPlay, VideoPause, Upload } from '@element-plus/icons-vue'
import { startExecution, pauseExecution } from '@/api/execution'
import { ElMessage } from 'element-plus'

const selectedModel = ref('')
const selectedPlan = ref('')
const tasks = ref<any[]>([])

const queryTasks = () => {
    // Mock data
    tasks.value = [
        { id: '1', name: 'Task 1', planName: 'Plan 1', status: 0, percentage: 0, stepCurrent: 0, stepTotal: 80 },
        { id: '2', name: 'Task 2', planName: 'Plan 1', status: 1, percentage: 50, stepCurrent: 40, stepTotal: 80 },
        { id: '3', name: 'Task 3', planName: 'Plan 1', status: 2, percentage: 100, stepCurrent: 80, stepTotal: 80 },
    ]
}

const getStatusClass = (status: number) => {
    if (status === 2) return 'green'
    if (status === 1) return 'blue'
    return 'grey'
}

const getStatusText = (status: number) => {
    if (status === 2) return '已完工'
    if (status === 1) return '进行中'
    return '未开始'
}

const handleStart = async (task: any) => {
    try {
        await startExecution({ id: task.id })
        task.status = 1
        ElMessage.success('Started')
    } catch (e) {
        // demo
        task.status = 1
    }
}

const handlePause = async (task: any) => {
    try {
        await pauseExecution(task.id)
        task.status = 0 // or paused status
        ElMessage.success('Paused')
    } catch (e) {
        // demo
    }
}
</script>

<style scoped lang="scss">
.command-dashboard {
    padding: 20px;
}
.control-panel {
    background: white;
    padding: 20px;
    border-radius: 4px;
    margin-bottom: 20px;
}

.card-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
}

.task-card {
    background: white;
    border-radius: 4px;
    overflow: hidden;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);

    .card-header {
        background-color: #001529;
        color: white;
        padding: 10px;
        display: flex;
        align-items: center;
        gap: 10px;

        .seq {
            background: rgba(255,255,255,0.2);
            width: 24px;
            height: 24px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
        }

        .title {
            font-weight: bold;
        }
    }

    .card-body {
        padding: 15px;

        .plan-name {
            color: #666;
            margin-bottom: 10px;
            font-size: 14px;
        }

        .status-row {
            display: flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 15px;
            font-size: 14px;

            .status-dot {
                width: 10px;
                height: 10px;
                border-radius: 50%;

                &.green { background: #67C23A; }
                &.blue { background: #409EFF; }
                &.grey { background: #909399; }
            }
        }

        .actions {
            display: flex;
            justify-content: flex-end;
            margin-top: 15px;
        }
    }
}
</style>
