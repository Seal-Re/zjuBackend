<template>
  <div class="test-review">
    <div class="actions" style="margin-bottom: 20px">
        <el-button type="success">批量审签</el-button>
    </div>

    <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="funName" label="名称" />
        
        <el-table-column prop="statusText" label="状态">
            <template #default="{ row }">
                <el-tag :type="getStatusType(row.Approvestatus)">{{ row.statusText }}</el-tag>
            </template>
        </el-table-column>

        <el-table-column label="操作">
            <template #default="{ row }">
                <el-button type="primary" link @click="openCheckDialog(row)">审签</el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="审签" width="500px">
        <el-form :model="form" label-width="80px">
            <el-form-item label="审批结果">
                <el-radio-group v-model="form.result">
                    <el-radio value="pass">通过</el-radio>
                    <el-radio value="reject">驳回</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="审批等级">
                <el-select v-model="form.level">
                    <el-option label="Level 0" value="0" />
                    <el-option label="Level 1" value="1" />
                    <el-option label="Level 2" value="2" />
                    <el-option label="Level 3" value="3" />
                    <el-option label="Level 4" value="4" />
                    <el-option label="Level 5" value="5" />
                </el-select>
            </el-form-item>
            <el-form-item label="审批意见">
                <el-input v-model="form.comment" type="textarea" />
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitCheck">确定</el-button>
        </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { checkTestFunction, getCheckTestFunction } from '@/api/designer'
import { ElMessage } from 'element-plus'

const STATUS_MAP = ["待提交", "待校对", "待质审", "待审查", "待批准", "待审核", "审签成功", "审签失败"];

const tableData = ref([])
const loading = ref(false) 
const dialogVisible = ref(false)
const currentId = ref<number | null>(null)

const form = reactive({
    result: 'pass',
    level: '0',
    comment: ''
})

const loadData = async () => {
    loading.value = true
    try {
        const res: any = await getCheckTestFunction({}) 
        
        const rawList = res.data || res || [] 

        tableData.value = rawList.map((item: any) => {
            const index = item.Approvestatus; 
            
            return {
                ...item,
                statusText: STATUS_MAP[index] || `未知状态(${index})`
            }
        })
    } catch (error) {
        console.error(error)
        ElMessage.error('获取列表失败')
    } finally {
        loading.value = false
    }
}

const getStatusType = (statusIndex: number) => {
    if (statusIndex === 6) return 'success'
    if (statusIndex === 7) return 'danger'
    return 'primary'
}

const openCheckDialog = (row: any) => {
    currentId.value = row.id 
    dialogVisible.value = true
}

const submitCheck = async () => {
    if (!currentId.value) return
    try {
        await checkTestFunction({
            funId: currentId.value,
            checkWorker: 'worker1',
            level: form.level
        })
        ElMessage.success('Reviewed')
        dialogVisible.value = false
        loadData()
    } catch (e) {
    }
}

onMounted(() => {
    loadData()
})
</script>