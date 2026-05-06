<template>
  <div class="test-review">
    <el-tabs v-model="activeTab" type="card" @tab-change="handleTabChange">
      <el-tab-pane label="模块审签" name="function"></el-tab-pane>
      <el-tab-pane label="清单审签" name="suite"></el-tab-pane>
    </el-tabs>

    <div class="actions" style="margin-bottom: 20px">
        <el-button type="primary" link @click="loadData">刷新列表</el-button>
    </div>

    <el-table :data="tableData" border v-loading="loading">
        <el-table-column type="index" label="序号" width="60" align="center" />
        
        <el-table-column :label="activeTab === 'function' ? '模块名称' : '清单名称'">
            <template #default="{ row }">
                {{ row.funName || row.suiteName }}
            </template>
        </el-table-column>

        <el-table-column label="版本" width="100" align="center">
            <template #default="{ row }">V{{ row.version || 0 }}</template>
        </el-table-column>
        
        <el-table-column label="当前状态" align="center" width="150">
            <template #default="{ row }">
                <el-tag :type="getStatusType(row.approveStatus)">
                    {{ getStatusText(row.approveStatus) }}
                </el-tag>
            </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="120">
            <template #default="{ row }">
                <el-button 
                    type="primary" 
                    link 
                    @click="openCheckDialog(row)"
                    :disabled="isFinished(row.approveStatus)"
                >
                    审签
                </el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="activeTab === 'function' ? '模块审签' : '清单审签'" width="500px">
        <el-form :model="form" label-width="100px">
            <el-form-item label="当前节点">
                <el-tag>{{ getStatusText(form.level) }}</el-tag>
            </el-form-item>

            <el-form-item label="审批结果">
                <el-radio-group v-model="form.result">
                    <el-radio value="pass">通过</el-radio>
                    <el-radio value="reject">驳回</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="确认等级">
                <el-select v-model="form.level" disabled placeholder="当前等级">
                    <el-option 
                        v-for="item in currentApprovalOptions" 
                        :key="item.value" 
                        :label="item.label" 
                        :value="item.value" 
                    />
                </el-select>
                <div style="font-size: 12px; color: #999; line-height: 1.2; margin-top: 5px">
                   {{ getNextStepTip() }}
                </div>
            </el-form-item>

            <el-form-item label="审批意见">
                <el-input v-model="form.comment" type="textarea" placeholder="请输入审批意见" />
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
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import {
    checkTestFunction,
    getCheckTestFunction,
    checkTestSuite,
    getCheckTestSuite
} from '@/api/designer'
import { useAuthStore } from '@/store/auth'

const authStore = useAuthStore()

// 模块：0-6 (6是成功，7是失败)
const FUNS_APP_LEVEL = [
  "待提交",   // 0
  "待校对",   // 1
  "待质审",   // 2
  "待审查",   // 3
  "待批准",   // 4
  "待审核",   // 5
  "审签成功", // 6
  "审签失败"  // 7
];

// 清单：0-4（3是成功，4是失败）
const SUITE_APP_LEVEL = [
  "待提交",   // 0
  "待校对",   // 1
  "待批准",   // 2
  "审签成功", // 3
  "审签失败"  // 4
];

const activeTab = ref('function')
const tableData = ref<any[]>([])
const loading = ref(false) 
const dialogVisible = ref(false)
const currentId = ref<number | string | null>(null)

const form = reactive({
    result: 'pass',
    level: 0, 
    comment: ''
})

// 成功状态码界定（失败状态码 success+1，前端不再模拟，后端返回真值）
const SUCCESS_CODE = computed(() => activeTab.value === 'function' ? 6 : 3)

// 获取当前 Tab 的下拉选项 (不包含成功状态，只包含待处理状态)
const currentApprovalOptions = computed(() => {
    if (activeTab.value === 'function') {
        // 模块: 0-5 是待处理状态
        return FUNS_APP_LEVEL.slice(0, 6).map((label, index) => ({ label, value: index }));
    } else {
        // 清单: 0-2 是待处理状态
        return SUITE_APP_LEVEL.slice(0, 3).map((label, index) => ({ label, value: index }));
    }
});

const getStatusText = (status: number) => {
    if (activeTab.value === 'function') {
        return FUNS_APP_LEVEL[status] || `未知(${status})`;
    } else {
        return SUITE_APP_LEVEL[status] || `未知(${status})`;
    }
}

const getStatusType = (status: number) => {
    const success = SUCCESS_CODE.value;
    if (status === success) return 'success';
    if (status > success) return 'danger'; // 失败
    if (status === 0) return 'info';
    return 'warning';
}

// 判断是否完结 (成功或失败)
const isFinished = (status: number) => {
    return status >= SUCCESS_CODE.value;
};

// 提示下一步
const getNextStepTip = () => {
    const current = form.level;
    if (form.result === 'reject') return '驳回后将变为：审签失败';
    
    // 逻辑统一为 +1，但上限不同
    const max = SUCCESS_CODE.value;
    const next = current >= max ? max : current + 1;
    
    const nextText = getStatusText(next);
    return `通过后将流转至：${nextText} (${next})`;
}

const handleTabChange = () => {
    tableData.value = [];
    loadData();
}

const loadData = async () => {
    loading.value = true
    try {
        let res: any;
        if (activeTab.value === 'function') {
            res = await getCheckTestFunction({})
        } else {
            res = await getCheckTestSuite({}) 
        }
        const rawList = Array.isArray(res) ? res : [];
        
        // 后端字段：TestFunction.approveStatus / TestSuite.listApprStatus
        tableData.value = rawList.map((item: any) => ({
            ...item,
            approveStatus: Number(
              activeTab.value === 'function'
                ? (item.approveStatus ?? 0)
                : (item.listApprStatus ?? 0)
            ),
            _rowKey: item.funId || item.suiteId || item.id
        }))
    } catch (error) {
        console.error(error)
        ElMessage.error('列表加载失败')
    } finally {
        loading.value = false
    }
}

const openCheckDialog = (row: any) => {
    currentId.value = row._rowKey;
    form.level = Number(row.approveStatus || 0); 
    form.result = 'pass';
    form.comment = '';
    dialogVisible.value = true
}

const submitCheck = async () => {
    if (!currentId.value) return;
    const currentLevel = Number(form.level);

    // 审批人取自当前登录用户而非硬编码 'worker'，否则审计日志失去溯源意义
    const currentUser = authStore.user?.username || authStore.user?.name
    if (!currentUser) {
        ElMessage.error('登录态异常，请重新登录后再审签')
        return
    }

    try {
        if (activeTab.value === 'function') {
            await checkTestFunction({
                funId: currentId.value,
                checkWorker: currentUser,
                level: currentLevel,
                result: form.result,
                comment: form.comment
            })
        } else {
            await checkTestSuite({
                suiteId: currentId.value,
                checkWorker: currentUser,
                level: currentLevel,
                result: form.result,
                comment: form.comment
            })
        }

        ElMessage.success('审签操作成功')
        dialogVisible.value = false
        // 不再前端模拟状态机，从后端拉真实状态避免漂移
        loadData()

    } catch (e: any) {
        console.error(e)
        ElMessage.error('审签失败，请稍后重试')
    }
}

onMounted(() => {
    loadData()
})
</script>

<style scoped>
.test-review {
    padding: 20px;
    background: #fff;
}
</style>