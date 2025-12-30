<template>
  <div class="suite-library">
    <div class="actions" style="margin-bottom: 20px">
        <el-button type="primary" @click="openCreateDialog">新建清单</el-button>
    </div>

    <!-- Simple Table for Suites -->
    <el-table :data="tableData" border>
        <el-table-column prop="suiteName" label="清单名称" />
        <el-table-column label="操作">
            <template #default>
                <el-button link type="primary">编辑</el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="新建清单" width="800px">
        <el-steps :active="activeStep" finish-status="success" simple style="margin-bottom: 20px">
            <el-step title="基础信息" />
            <el-step title="绑定函数" />
        </el-steps>

        <!-- Step 1 -->
        <div v-if="activeStep === 0">
             <el-form :model="form" label-width="100px">
                <el-form-item label="清单名称">
                    <el-input v-model="form.suiteName" />
                </el-form-item>
                 <el-form-item label="架次Min">
                    <el-input-number v-model="form.planeEffectMin" />
                </el-form-item>
                 <el-form-item label="架次Max">
                    <el-input-number v-model="form.planeEffectMax" />
                </el-form-item>
            </el-form>
        </div>

        <!-- Step 2: Transfer -->
        <div v-if="activeStep === 1">
            <el-transfer
                v-model="selectedFunctions"
                :data="functionSource"
                :titles="['可选模块', '已选函数']"
                filterable
                filter-placeholder="搜索模块"
            />
        </div>

        <template #footer>
            <el-button @click="handleCancel">取消</el-button>
            <el-button v-if="activeStep > 0" @click="activeStep--">上一步</el-button>
            <el-button v-if="activeStep < 1" type="primary" @click="activeStep++">下一步</el-button>
            <el-button v-if="activeStep === 1" type="primary" @click="submitSuite">完成</el-button>
        </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const activeStep = ref(0)

const form = reactive({
    suiteName: '',
    planeEffectMin: 0,
    planeEffectMax: 9999
})

// Transfer Data
const selectedFunctions = ref([])
const functionSource = ref([
    { key: 1209, label: 'Test Function 1209', disabled: false }, // Mock data
    { key: 1208, label: 'Test Function 1208', disabled: false }
])

const openCreateDialog = () => {
    activeStep.value = 0
    dialogVisible.value = true
}

const handleCancel = () => {
    dialogVisible.value = false
}

const submitSuite = async () => {
    try {
        // 1. Create Suite
        // const res = await createTestSuite({
        //     suiteName: form.suiteName,
        //     entityStructId: 1, // hardcoded per requirement example
        //     testBaseId: 1,
        //     planeEffectMin: form.planeEffectMin,
        //     planeEffectMax: form.planeEffectMax,
        //     funIds: selectedFunctions.value
        //     // Note: API doc says createTestSuite takes funIds in the same call?
        //     // The requirement doc says:
        //     // URL: POST /designer/testSuite/add with funIds: [1209]
        //     // URL: POST /functionSuite/createFunctionSuite with detailed list?
        //     // I will try to follow the first one first.
        // })

        // Mock success
        ElMessage.success('Suite created successfully')
        dialogVisible.value = false
    } catch (e) {
        console.error(e)
    }
}
</script>
