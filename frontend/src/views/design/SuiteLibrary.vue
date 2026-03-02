<template>
  <div class="suite-library">
    <h2 class="page-title">清单库</h2>
    <div class="global-filter filter-bar">
      <el-cascader
        v-model="filterStore.model"
        :options="modelOptions"
        :props="{ emitPath: false, label: 'label', value: 'value' }"  
        placeholder="机型"
        @change="handleModelChange"
        clearable />

      <el-cascader
        v-model="filterStore.profession"
        :options="professionOptions"
        :props="{ emitPath: false, label: 'label', value: 'value' }"  
        placeholder="专业"
        :disabled="!filterStore.model"
        @change="handleProfessionChange"
        clearable
      />

      <el-cascader
        v-model="filterStore.subsystem"
        :options="subsystemOptions"
        :props="{ emitPath: false, label: 'label', value: 'value' }"  
        placeholder="子系统"
        :disabled="!filterStore.profession"
        @change="handleSubsystemChange"
        clearable
      />  

      <div class="filter-actions">
        <el-button type="success" @click="handleSubsystemChange">刷新</el-button>
        <el-button type="primary">审签配置</el-button>
        <el-button 
            color="#001529" 
            style="color: white" 
            :loading="createLoading"
            @click="openCreateDialog"
        >
            新建清单
        </el-button>
      </div>
    </div>

    <div class="table-wrap">
    <el-table 
        :data="tableData" 
        class="data-table"
        border 
        stripe
        v-loading="loading"
        empty-text="请选择机型、专业和子系统以加载数据"
    >
        <el-table-column type="index" label="序号" width="60" align="center"/>
        <el-table-column prop="mesdceCode" label="MES编号" width="150" show-overflow-tooltip />
        <el-table-column prop="suiteName" label="清单名称" min-width="200">
            <template #default="{ row }">
                <span style="color: #1890ff; cursor: pointer; font-weight: bold">{{ row.suiteName }}</span>
            </template>
        </el-table-column>

        <el-table-column label="属性" width="120" align="center">
            <template #default="{ row }">
                <el-tag v-if="row.military" type="danger" size="small" effect="dark" style="margin-right: 5px">密</el-tag>
            </template>
        </el-table-column>

        <el-table-column label="版本" width="80" align="center">
            <template #default="{ row }">
                 <el-tag effect="plain">V{{ row.version || 0 }}</el-tag>
            </template>
        </el-table-column>

        <el-table-column label="审签指派" prop="submitter" width="120" show-overflow-tooltip />

        <el-table-column label="审签状态" width="120" align="center">
            <template #default="{ row }">
                <el-tag :type="getStatusType(row.listApprStatus)">
                    {{ getStatusText(row.listApprStatus) }}
                </el-tag>
            </template>
        </el-table-column>

        <el-table-column label="操作" width="220" align="center" fixed="right">
            <template #default="{ row }">
                <el-button link type="primary" @click="handleDetail(row)">详情</el-button>
                <el-button 
                    link 
                    type="primary" 
                    @click="handleEdit(row)" 
                    :disabled="row.listApprStatus !== 0"
                >
                    修改
                </el-button>
                <el-button 
                    link 
                    type="primary" 
                    :disabled="row.listApprStatus !== 0"
                    @click="handleSubmitToReview(row)"
                >
                    提交
                </el-button>
                <el-button link type="danger" :disabled="row.listApprStatus !== 0">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    </div>

    <el-dialog 
        v-model="dialogVisible" 
        :title="isEditMode ? '修改清单' : '新建清单'" 
        width="900px"
        :close-on-click-modal="false"
        destroy-on-close
    >
        <el-steps :active="activeStep" finish-status="success" simple style="margin-bottom: 20px">
            <el-step title="基础信息" icon="Edit" />
            <el-step title="绑定函数" icon="Connection" />
        </el-steps>

        <div v-show="activeStep === 0" class="step-content">
             <el-form :model="form" ref="infoFormRef" :rules="rules" label-width="120px">
                <el-row :gutter="20">
                    <el-col :span="12">
                         <el-form-item label="清单名称" prop="suiteName">
                            <el-input v-model="form.suiteName" placeholder="请输入清单名称" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                         <el-form-item label="审签指派" prop="approveAssign">
                             <el-input v-model="form.approveAssign" placeholder="请输入指派人员或部门" />
                         </el-form-item>
                    </el-col>
                </el-row>

                 <el-divider content-position="left">函数筛选条件 (仅用于下一步筛选)</el-divider>
                 <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="架次Min">
                            <el-input-number v-model="form.planeEffectMin" :min="1" style="width: 100%" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="架次Max">
                            <el-input-number v-model="form.planeEffectMax" :min="1" style="width: 100%" />
                        </el-form-item>
                    </el-col>
                 </el-row>

                 <el-row :gutter="20">
                    <el-col :span="12">
                         <el-form-item label="是否涉密">
                             <el-switch v-model="form.military" active-text="涉密" inactive-text="非密" />
                         </el-form-item>
                    </el-col>
                 </el-row>

                 <el-form-item label="清单描述" prop="suiteDesc">
                     <el-input v-model="form.suiteDesc" type="textarea" :rows="3" placeholder="请输入清单描述信息" />
                 </el-form-item>
            </el-form>
        </div>

        <div v-show="activeStep === 1" class="step-content">
            <div class="transfer-header">
                <el-alert
                    title="已根据前置条件的架次范围筛选可用模块。"
                    type="success"
                    :closable="false"
                    show-icon
                />
            </div>
            <div class="transfer-container" v-loading="loadingFunctions">
                <el-transfer
                    v-model="selectedFunctionIds"
                    :data="compatibleFunctions"
                    :titles="['可选模块库', '已关联模块']"
                    filterable
                    filter-placeholder="搜索名称/编号"
                    :props="{ key: 'funId', label: 'displayName' }"
                >
                    <template #default="{ option }">
                        <span :title="option.funName">
                            <el-tag size="small" type="info" style="margin-right:5px">{{ option.num }}</el-tag>
                            {{ option.funName }}
                        </span>
                    </template>
                </el-transfer>
            </div>
            
            <div class="filter-tip" v-if="excludedCount > 0">
                <el-icon><InfoFilled /></el-icon> 
                <span>
                    注：库中有 <b>{{ excludedCount }}</b> 个模块因架次不匹配（需 Min={{ form.planeEffectMin }}, Max={{ form.planeEffectMax }}）未显示。
                </span>
            </div>
        </div>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="handleCancel">取消</el-button>
                <el-button v-if="activeStep > 0" @click="activeStep--">上一步</el-button>
                <el-button v-if="activeStep < 1" type="primary" @click="handleNextStep">下一步</el-button>
                
                <el-button 
                    v-if="activeStep === 1" 
                    type="primary" 
                    :loading="submitting" 
                    @click="submitSuite"
                >
                    {{ isEditMode ? '保存修改' : '完成创建' }}
                </el-button>
            </span>
        </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useGlobalFilterStore } from '@/store/globalFilter'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Edit, Connection, InfoFilled } from '@element-plus/icons-vue'
// API
import { 
    getTestSuites, 
    createTestSuite, 
    updateTestSuite,
    getTestFunctions,
    submitTestSuite,
    getTestSuiteDetail,
    getRely
} from '@/api/designer'
import { listAllBaseStructAndId } from '@/api/base' 

const filterStore = useGlobalFilterStore()

// --- 状态变量 ---
const loading = ref(false)
const createLoading = ref(false)
const submitting = ref(false)
const loadingFunctions = ref(false)
const tableData = ref<any[]>([])
const currentTestBaseId = ref<number | null>(null)

// --- 弹窗相关 ---
const dialogVisible = ref(false)
const isEditMode = ref(false)
const activeStep = ref(0)
const infoFormRef = ref()

// ================= 级联筛选 =================
const allStructs = ref<any[]>([])
const modelOptions = ref<any[]>([])
const professionOptions = ref<any[]>([])
const subsystemOptions = ref<any[]>([])

const initBaseStructs = async () => {
    try {
        const data: any = await listAllBaseStructAndId()
        if (Array.isArray(data)) {
            allStructs.value = data
            const models = new Set(data.map((item: any) => item.baseStruct?.model).filter(Boolean))
            modelOptions.value = Array.from(models).map(m => ({ value: m, label: m }))
            if (filterStore.model) handleModelChange(filterStore.model, false)
        }
    } catch (e) { console.error(e) }
}

const handleModelChange = (val: any, clearNext = true) => {
    if (clearNext) {
        filterStore.profession = ''; filterStore.subsystem = '';
        professionOptions.value = []; subsystemOptions.value = []; tableData.value = []
    }
    if (!val) return
    const filtered = allStructs.value.filter((item: any) => item.baseStruct?.model === val)
    const profs = new Set(filtered.map((item: any) => item.baseStruct?.profession).filter(Boolean))
    professionOptions.value = Array.from(profs).map(p => ({ value: p, label: p }))
    if (!clearNext && filterStore.profession) handleProfessionChange(filterStore.profession, false)
}

const handleProfessionChange = (val: any, clearNext = true) => {
    if (clearNext) {
        filterStore.subsystem = ''; subsystemOptions.value = []; tableData.value = []
    }
    if (!val) return
    const filtered = allStructs.value.filter((item: any) => 
        item.baseStruct?.model === filterStore.model && item.baseStruct?.profession === val
    )
    const subs = new Set(filtered.map((item: any) => item.baseStruct?.subsystem).filter(Boolean))
    subsystemOptions.value = Array.from(subs).map(s => ({ value: s, label: s }))
    if (!clearNext && filterStore.subsystem) handleSubsystemChange()
}

const handleSubsystemChange = async () => {
    const { model, profession, subsystem } = filterStore
    if (!model || !profession || !subsystem) {
        tableData.value = []; currentTestBaseId.value = null; return
    }
    const target = allStructs.value.find((item: any) => 
        item.baseStruct?.model === model &&
        item.baseStruct?.profession === profession &&
        item.baseStruct?.subsystem === subsystem
    )
    if (target && target.baseId) {
        currentTestBaseId.value = target.baseId
        await fetchData() 
    } else {
        tableData.value = []; currentTestBaseId.value = null
        ElMessage.warning('未找到对应构型ID')
    }
}
// ============================================

const STATUS_TEXT_MAP: Record<number, string> = { 0:'待提交', 1:'待校对', 2:'待批准', 3:'审签成功' }
const getStatusText = (status: number) => STATUS_TEXT_MAP[status] || '未知状态'
const getStatusType = (status: number) => {
    if (status === 3) return 'success'; if (status === 0) return 'info';
    if (status === 4) return 'warning'; return 'primary'
}

// --- 表单对象 ---
const form = reactive({
    suiteId: undefined as number | undefined, 
    suiteName: '',
    suiteDesc: '',
    military: false,
    approveAssign: '',
    planeEffectMin: 1,
    planeEffectMax: 100 
})

const rules = {
    suiteName: [{ required: true, message: '请输入清单名称', trigger: 'blur' }],
}

const allFunctions = ref<any[]>([]) 
const selectedFunctionIds = ref<number[]>([]) // 选中的ID
const excludedCount = ref(0) 

const compatibleFunctions = computed(() => {
    if (!allFunctions.value.length) return []
    
    const sMin = form.planeEffectMin
    const sMax = form.planeEffectMax
    
    // 创建 Set 用于快速判断是否已选中
    const selectedSet = new Set(selectedFunctionIds.value.map(id => Number(id)))

    const validFuncs = allFunctions.value.filter(func => {
        // 【核心逻辑】
        // 1. 如果该模块已经被选中 (在 getRely 返回的列表中)，强制显示，忽略架次筛选
        if (selectedSet.has(Number(func.funId))) {
            return true
        }

        // 2. 如果未选中，则严格匹配架次范围
        const fMin = Number(func.planeEffectMin) || 0
        const fMax = Number(func.planeEffectMax) || 9999
        return fMin === sMin && fMax === sMax
    }).map(func => ({ 
        ...func, 
        displayName: func.funName,
        funId: Number(func.funId) // 确保 ID 类型统一
    }))

    excludedCount.value = allFunctions.value.length - validFuncs.length
    return validFuncs
})

const fetchData = async () => {
    if (!currentTestBaseId.value) { tableData.value = []; return }
    loading.value = true
    try {
        const suiteRes: any = await getTestSuites({ testBaseId: currentTestBaseId.value })
        if (Array.isArray(suiteRes)) { tableData.value = suiteRes }
        else if (suiteRes && Array.isArray(suiteRes.data)) { tableData.value = suiteRes.data }
        else { tableData.value = [] }
    } catch (e: any) {
        console.error(e); ElMessage.warning('列表加载异常'); tableData.value = []
    } finally { loading.value = false }
}

const resetForm = () => {
    form.suiteId = undefined
    form.suiteName = ''; form.suiteDesc = ''; form.military = false; form.approveAssign = ''
    form.planeEffectMin = 1; form.planeEffectMax = 100
    selectedFunctionIds.value = []; excludedCount.value = 0; activeStep.value = 0
}

const openCreateDialog = async () => {
    if (!currentTestBaseId.value) { ElMessage.warning('请先选择完整的构型'); return }
    isEditMode.value = false
    resetForm()
    dialogVisible.value = true
}

const loadFunctions = async () => {
    if (!currentTestBaseId.value) return
    loadingFunctions.value = true
    try {
        const res: any = await getTestFunctions({ testBaseId: currentTestBaseId.value })
        if (Array.isArray(res)) allFunctions.value = res
        else if (res && Array.isArray(res.data)) allFunctions.value = res.data
        else allFunctions.value = []
    } catch (e) { console.error(e) } finally { loadingFunctions.value = false }
}

const handleNextStep = async () => {
    await infoFormRef.value.validate(async (valid: boolean) => {
        if (valid) {
            await loadFunctions()
            activeStep.value = 1
        }
    })
}

// 修改 handleEdit 方法
const handleEdit = async (row: any) => {
    isEditMode.value = true
    
    // 1. 基础信息回显
    form.suiteId = row.suiteId
    form.suiteName = row.suiteName
    form.suiteDesc = row.suiteDesc
    form.military = !!row.military
    form.approveAssign = row.submitter || row.approveAssign || ''
    form.planeEffectMin = row.planeEffectMin || 1
    form.planeEffectMax = row.planeEffectMax || 100
    
    // 2. 先加载全量可选库 (左侧数据源)
    // 必须先有数据源，穿梭框才能通过 ID 匹配显示出名称
    await loadFunctions()

    // 3. 【核心修改】调用 getRely 获取已关联模块
        try {
            loadingFunctions.value = true
            const data: any = await getRely(row.suiteId)
            const functions = (data && Array.isArray(data.testFunctions)) ? data.testFunctions : []

            selectedFunctionIds.value = functions.map((f: any) => Number(f.funId))

            console.log("已回显关联模块 ID:", selectedFunctionIds.value)
    } catch (e) {
        console.error("获取关联模块失败", e)
        selectedFunctionIds.value = []
        ElMessage.warning('关联模块数据加载失败')
    } finally {
        loadingFunctions.value = false
    }
    
    dialogVisible.value = true
}

const handleDetail = (row: any) => { console.log('查看详情', row) }

const handleSubmitToReview = async (row: any) => {
    try {
        await ElMessageBox.confirm('确定要提交该清单进行审签吗？', '提示', {
            confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        })
        await submitTestSuite(row.suiteId);
        ElMessage.success('提交成功'); fetchData()
    } catch (e) { }
}

// --- 提交 ---
const submitSuite = async () => {
    if (!currentTestBaseId.value) return
    
    submitting.value = true
    try {
        const payload = {
            suiteId: isEditMode.value ? form.suiteId : undefined,
            testBaseId: currentTestBaseId.value,
            suiteName: form.suiteName,
            suiteDesc: form.suiteDesc,
            military: form.military,
            submitter: form.approveAssign,
            planeEffectMin: form.planeEffectMin,
            planeEffectMax: form.planeEffectMax,
            // 选中的 ID 列表
            funIds: selectedFunctionIds.value,
        }

        if (isEditMode.value) {
            if (!payload.suiteId) throw new Error("ID丢失")
            await updateTestSuite(payload)
            ElMessage.success('修改成功')
        } else {
            await createTestSuite(payload)
            ElMessage.success('清单创建成功')
        }

        dialogVisible.value = false
        fetchData() 
    } catch (e: any) {
        console.error(e)
        ElMessage.error('操作失败: ' + (e.msg || e.message))
    } finally {
        submitting.value = false
    }
}

const handleCancel = () => { dialogVisible.value = false }

onMounted(() => { initBaseStructs() })
</script>

<style scoped lang="scss">
.suite-library .page-title { margin-top: 0; }
.global-filter {
  display: flex;
  gap: 12px;
  align-items: center;
  flex-wrap: wrap;
  padding: 12px 0;

  .filter-actions {
      margin-left: auto;
      display: flex;
      gap: 10px;
  }
}
.table-wrap { margin-top: 16px; border-radius: var(--app-radius); overflow: hidden; box-shadow: var(--app-shadow-card); }
.data-table { width: 100%; }

.step-content {
    padding: 20px;
    border: 1px solid #eee;
    border-radius: 4px;
    background: #fff;
    margin-top: 10px;
    min-height: 400px;
    display: flex;
    flex-direction: column;
}

.transfer-header { margin-bottom: 15px; }

.transfer-container {
    flex: 1;
    display: flex;
    justify-content: center;
    
    :deep(.el-transfer-panel) { width: 320px; }
    :deep(.el-transfer__buttons) { padding: 0 20px; }
}

.filter-tip {
    margin-top: 15px;
    text-align: center;
    color: #e6a23c;
    background-color: #fdf6ec;
    padding: 8px;
    border-radius: 4px;
    font-size: 13px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
}
</style>