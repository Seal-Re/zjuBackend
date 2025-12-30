<template>
  <div class="test-plan">
    <div class="filter-bar" style="margin-bottom: 20px; display: flex; gap: 10px">
        <el-input placeholder="搜索..." style="width: 200px" />
        <el-button type="primary" @click="openDrawer">创建计划</el-button>
    </div>

    <el-table :data="tableData" border>
        <el-table-column prop="planName" label="计划名称" />
        <el-table-column prop="planStartTime" label="开始时间" />
        <el-table-column prop="planEndTime" label="结束时间" />
    </el-table>

    <el-drawer v-model="drawerVisible" title="创建测试计划" size="50%">
        <el-form :model="form" label-width="100px">
            <el-form-item label="计划名称">
                <el-input v-model="form.planName" />
            </el-form-item>
            <el-form-item label="关联清单">
                <el-select v-model="form.suiteId" placeholder="选择测试清单">
                    <el-option label="Suite 1" :value="1" />
                </el-select>
            </el-form-item>
            <el-form-item label="起止时间">
                <el-date-picker
                    v-model="form.dateRange"
                    type="datetimerange"
                    start-placeholder="Start Date"
                    end-placeholder="End Date"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitPlan">创建</el-button>
                <el-button @click="drawerVisible = false">取消</el-button>
            </el-form-item>
        </el-form>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { createTestPlan } from '@/api/planner'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const drawerVisible = ref(false)
const form = reactive({
    planName: '',
    suiteId: null,
    dateRange: [] as any[]
})

const openDrawer = () => {
    drawerVisible.value = true
}

const submitPlan = async () => {
    try {
        await createTestPlan({
            planName: form.planName,
            entityId: 1, // hardcoded
            suiteId: form.suiteId,
            planStartTime: form.dateRange[0],
            planEndTime: form.dateRange[1]
        })
        ElMessage.success('Plan created')
        drawerVisible.value = false
    } catch (e) {
        // demo
    }
}
</script>
