<template>
  <div class="test-plan-container">
    <h2 class="page-title">测试计划</h2>
    <el-card shadow="never" class="main-card">
      <div class="header-actions filter-bar">
        <div class="left-panel">
          <el-input
            v-model="queryParams.keyword"
            placeholder="请输入计划名称或编号搜索"
            clearable
            style="width: 240px"
            @keyup.enter="handleQuery"
            @clear="handleQuery"
          >
            <template #prefix><el-icon><Search /></el-icon></template>
          </el-input>
          <el-button type="primary" @click="handleQuery">搜索</el-button>
        </div>
        
        <div class="right-panel">
          <el-button type="success" plain @click="handleAdd">
            <el-icon><Plus /></el-icon> 新建计划
          </el-button>
          <el-button 
            type="danger" 
            plain 
            :disabled="selectedIds.length === 0"
            @click="handleBatchDelete"
          >
            <el-icon><Delete /></el-icon> 批量删除
          </el-button>
        </div>
      </div>

      <div class="table-wrap">
      <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        class="data-table"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="planName" label="计划名称" min-width="150" show-overflow-tooltip />
        <el-table-column prop="planNumber" label="计划编号" width="140" show-overflow-tooltip />
        
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)" effect="light">
              {{ getStatusLabel(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="planStartTime" label="计划开始时间" width="170">
          <template #default="scope">{{ formatDate(scope.row.planStartTime) }}</template>
        </el-table-column>
        <el-table-column prop="planEndTime" label="计划结束时间" width="170">
          <template #default="scope">{{ formatDate(scope.row.planEndTime) }}</template>
        </el-table-column>
        
        <el-table-column label="操作" width="260" fixed="right">
          <template #default="scope">
            <el-button v-if="scope.row.status === 5" link type="primary" @click="handleDispatch(scope.row)">派发</el-button>
            <el-button v-if="scope.row.status === 0 || scope.row.status === 3" link type="success" @click="handleStart(scope.row)">
                {{ scope.row.status === 3 ? '继续' : '开始' }}
            </el-button>
            <el-button v-if="scope.row.status === 2" link type="warning" @click="handlePause(scope.row)">暂停</el-button>
            <el-divider direction="vertical" />
            <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      </div>
    </el-card>

    <el-drawer
      v-model="drawerVisible"
      :title="isEdit ? '编辑测试计划' : '创建测试计划'"
      size="600px"
      :close-on-click-modal="false"
    >
      <el-form 
        ref="formRef" 
        :model="form" 
        :rules="rules" 
        label-width="100px"
      >
        <el-form-item label="计划名称" prop="planName">
          <el-input v-model="form.planName" placeholder="请输入计划名称" />
        </el-form-item>
        
        <el-form-item label="计划编号" prop="planNumber">
          <el-input v-model="form.planNumber" placeholder="例如：TP-2025-001" />
        </el-form-item>

        <el-divider content-position="left">测试目标与清单</el-divider>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="机型" label-width="50px">
              <el-select 
                v-model="cascadeFilter.model" 
                placeholder="选择机型" 
                @change="handleModelChange"
                filterable
              >
                <el-option v-for="m in modelOptions" :key="m.value" :label="m.label" :value="m.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
             <el-form-item label="专业" label-width="50px">
              <el-select 
                v-model="cascadeFilter.profession" 
                placeholder="选择专业" 
                :disabled="!cascadeFilter.model"
                @change="handleProfessionChange"
                filterable
              >
                <el-option v-for="p in professionOptions" :key="p.value" :label="p.label" :value="p.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
             <el-form-item label="子系统" label-width="60px">
              <el-select 
                v-model="cascadeFilter.subsystem" 
                placeholder="选择子系统" 
                :disabled="!cascadeFilter.profession"
                @change="handleSubsystemChange"
                filterable
              >
                <el-option v-for="s in subsystemOptions" :key="s.value" :label="s.label" :value="s.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="测试清单" prop="suiteId">
          <el-select 
            v-model="form.suiteId" 
            placeholder="请先选择子系统以加载可用清单" 
            style="width: 100%"
            :loading="loadingSuites"
            :disabled="!form.entityId"
            no-data-text="该构型下暂无已发布的测试清单"
          >
             <el-option 
                v-for="suite in suiteOptions" 
                :key="suite.suiteId" 
                :label="suite.suiteName + ' (V' + (suite.version||0) + ')'" 
                :value="suite.suiteId" 
             >
                <span style="float: left">{{ suite.suiteName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">V{{ suite.version }}</span>
             </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="起止时间" prop="dateRange">
          <el-date-picker
            v-model="form.dateRange"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="YYYY-MM-DDTHH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="是否记录" prop="forRecordData">
          <el-radio-group v-model="form.forRecordData">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input 
            v-model="form.remark" 
            type="textarea" 
            :rows="3" 
            placeholder="请输入备注信息"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <div style="flex: auto">
          <el-button @click="drawerVisible = false">取消</el-button>
          <el-button type="primary" :loading="submitLoading" @click="submitForm">
            {{ isEdit ? '保存修改' : '立即创建' }}
          </el-button>
        </div>
      </template>
    </el-drawer>

    <el-dialog v-model="dispatchVisible" title="计划派发详情" width="70%" top="5vh">
      <el-tabs type="border-card">
        <el-tab-pane label="计划信息">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="计划名称">{{ dispatchResult.plan?.planName }}</el-descriptions-item>
            <el-descriptions-item label="计划编号">{{ dispatchResult.plan?.planNumber }}</el-descriptions-item>
            <el-descriptions-item label="当前状态">
               <el-tag :type="getStatusType(dispatchResult.plan?.status)">{{ getStatusLabel(dispatchResult.plan?.status) }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="负责人ID">{{ dispatchResult.plan?.dispatcherId || '-' }}</el-descriptions-item>
            <el-descriptions-item label="开始时间">{{ formatDate(dispatchResult.plan?.planStartTime) }}</el-descriptions-item>
            <el-descriptions-item label="结束时间">{{ formatDate(dispatchResult.plan?.planEndTime) }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="功能列表">
          <el-table :data="dispatchResult.functions || []" border height="400">
             <el-table-column type="index" label="序号" width="60" />
             <el-table-column property="exeFunctionId" label="功能ID" width="100" />
             <el-table-column property="exeFunctionName" label="功能名称" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="步骤列表">
          <el-table :data="dispatchResult.steps || []" border height="400">
             <el-table-column type="index" label="序号" width="60" />
             <el-table-column property="exeStepId" label="步骤ID" width="100" />
             <el-table-column property="stepName" label="步骤名称" />
             <el-table-column property="stepDesc" label="步骤描述" show-overflow-tooltip />
          </el-table>
        </el-tab-pane>
      </el-tabs>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="dispatchVisible = false">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Plus, Delete } from '@element-plus/icons-vue'

// 引入 API
import { 
  getTestPlans, createTestPlan, updateTestPlan, deleteSingleTestPlan, 
  deleteTestPlanWithBatch, dispatchPlan, startPlan, pausePlan
} from '@/api/planner'
// 【新增】引入获取构型和清单的 API
import { listAllBaseStructAndId } from '@/api/base' 
import { getTestSuites } from '@/api/designer' 

// --- 类型定义 ---
interface TestPlan {
  planId: string
  planName: string
  planNumber: string
  status: number
  suiteId: number
  entityId: number // 对应后端构型ID
  planStartTime: string
  planEndTime: string
  forRecordData: number
  remark?: string
  deleted?: boolean | number
  [key: string]: any
}

// --- 响应式变量 ---
const loading = ref(false)
const submitLoading = ref(false)
const drawerVisible = ref(false)
const isEdit = ref(false)
const tableData = ref<TestPlan[]>([])
const selectedIds = ref<string[]>([])
const formRef = ref()

const dispatchVisible = ref(false)
const dispatchResult = ref<any>({ plan: {}, functions: [], steps: [] })

const queryParams = reactive({ keyword: '' })

// 表单数据
const form = reactive({
  planId: '',
  planName: '',
  planNumber: '',
  suiteId: undefined as number | undefined,
  entityId: undefined as number | undefined, // 测试构型ID
  dateRange: [] as string[],
  forRecordData: 0,
  remark: '',
  // 隐藏字段
  entityStructId: 1, 
  subjectId: 1, 
  funGroupId: 1, 
  areaId: 1, 
  management: 'Default'
})

// --- 级联筛选与动态清单逻辑 ---
const allStructs = ref<any[]>([])
const loadingSuites = ref(false)
// 下拉选项
const modelOptions = ref<any[]>([])
const professionOptions = ref<any[]>([])
const subsystemOptions = ref<any[]>([])
const suiteOptions = ref<any[]>([]) // 动态加载的清单列表

// 级联绑定值
const cascadeFilter = reactive({
    model: '',
    profession: '',
    subsystem: ''
})

// 1. 初始化构型数据
const initBaseStructs = async () => {
    try {
        const data: any = await listAllBaseStructAndId()
        if (Array.isArray(data)) {
            allStructs.value = data
            const models = new Set(data.map((item: any) => item.baseStruct?.model).filter(Boolean))
            modelOptions.value = Array.from(models).map(m => ({ value: m, label: m }))
        }
    } catch (e) { console.error(e) }
}

// 2. 机型变更
const handleModelChange = (val: any) => {
    cascadeFilter.profession = ''
    cascadeFilter.subsystem = ''
    professionOptions.value = []
    subsystemOptions.value = []
    
    // 清空已选的子系统和清单
    form.entityId = undefined
    form.suiteId = undefined
    suiteOptions.value = []

    if (!val) return
    const filtered = allStructs.value.filter((item: any) => item.baseStruct?.model === val)
    const profs = new Set(filtered.map((item: any) => item.baseStruct?.profession).filter(Boolean))
    professionOptions.value = Array.from(profs).map(p => ({ value: p, label: p }))
}

// 3. 专业变更
const handleProfessionChange = (val: any) => {
    cascadeFilter.subsystem = ''
    subsystemOptions.value = []
    
    form.entityId = undefined
    form.suiteId = undefined
    suiteOptions.value = []

    if (!val) return
    const filtered = allStructs.value.filter((item: any) => 
        item.baseStruct?.model === cascadeFilter.model &&
        item.baseStruct?.profession === val
    )
    const subs = new Set(filtered.map((item: any) => item.baseStruct?.subsystem).filter(Boolean))
    subsystemOptions.value = Array.from(subs).map(s => ({ value: s, label: s }))
}

// 4. 子系统变更 -> 锁定 ID -> 加载清单
const handleSubsystemChange = async (val: any) => {
    // 重置清单
    form.suiteId = undefined
    suiteOptions.value = []

    if (!val) {
        form.entityId = undefined
        return
    }

    // 查找 baseId
    const target = allStructs.value.find((item: any) => 
        item.baseStruct?.model === cascadeFilter.model &&
        item.baseStruct?.profession === cascadeFilter.profession &&
        item.baseStruct?.subsystem === val
    )

    if (target && target.baseId) {
        form.entityId = target.baseId
        // 加载该构型下的清单
        await fetchSuites(target.baseId)
    } else {
        ElMessage.warning('未找到对应构型ID')
    }
}

// 获取清单列表
const fetchSuites = async (testBaseId: number) => {
    if (!testBaseId) {
        suiteOptions.value = []
        return
    }
    loadingSuites.value = true
    try {
        const res: any = await getTestSuites({ testBaseId })
        const list = Array.isArray(res) ? res : (res.data || [])
        // 过滤掉未发布的清单？视业务需求而定。目前全部显示
        suiteOptions.value = list
    } catch (e) {
        console.error(e)
        suiteOptions.value = []
    } finally {
        loadingSuites.value = false
    }
}

const rules = {
  planName: [{ required: true, message: '请输入计划名称', trigger: 'blur' }],
  suiteId: [{ required: true, message: '请选择测试清单', trigger: 'change' }],
  dateRange: [{ required: true, message: '请选择起止时间', trigger: 'change' }]
}

onMounted(() => {
  loadData()
  initBaseStructs()
})

const loadData = async () => {
  loading.value = true
  try {
    const list: any = await getTestPlans(queryParams)
    const arr = Array.isArray(list) ? list : []
    let filteredList = arr.filter((item: TestPlan) => item.deleted !== true && item.deleted !== 1)
    if (queryParams.keyword) {
      const k = queryParams.keyword.toLowerCase()
      filteredList = filteredList.filter((item: TestPlan) => 
        (item.planName && item.planName.toLowerCase().includes(k)) || 
        (item.planNumber && item.planNumber.toLowerCase().includes(k))
      )
    }
    tableData.value = filteredList
  } catch (error) { console.error(error) } finally { loading.value = false }
}

const handleQuery = () => loadData()
const getStatusLabel = (status: number) => { const map: any={0:'已派工',1:'待检验',2:'执行中',3:'已暂停',4:'待军检',5:'待派工',6:'已完工'}; return map[status]||`未知` }
const getStatusType = (status: number) => { const map: any={5:'info',0:'primary',2:'success',3:'warning',1:'danger',4:'danger',6:'success'}; return map[status]||'' }
const formatDate = (d:string) => d ? d.replace('T', ' ') : '-'

const handleAdd = () => {
  isEdit.value = false
  resetForm()
  drawerVisible.value = true
}

const handleEdit = async (row: TestPlan) => {
  isEdit.value = true
  resetForm()
  
  form.planId = row.planId
  form.planName = row.planName
  form.planNumber = row.planNumber
  form.forRecordData = row.forRecordData || 0
  form.remark = row.remark || ''
  if (row.planStartTime && row.planEndTime) form.dateRange = [row.planStartTime, row.planEndTime]

  // --- 回显级联选择器 ---
  // 1. 获取 EntityId (后端叫 entityId, 有时可能叫 entityStructId, 注意匹配)
  form.entityId = row.entityId 
  form.suiteId = row.suiteId // 此时 suiteOptions 还是空的，显示会是 ID，需要马上加载列表

  if (form.entityId && allStructs.value.length > 0) {
      // 2. 根据 ID 反查构型信息
      const target = allStructs.value.find((item: any) => item.baseId === form.entityId)
      if (target && target.baseStruct) {
          // 3. 填充级联框
          cascadeFilter.model = target.baseStruct.model
          handleModelChange(cascadeFilter.model) // 触发加载专业选项
          
          cascadeFilter.profession = target.baseStruct.profession
          handleProfessionChange(cascadeFilter.profession) // 触发加载子系统选项
          
          cascadeFilter.subsystem = target.baseStruct.subsystem
          // 注意：这里不需要调 handleSubsystemChange，因为 entityId 已经有了
          // 4. 但必须加载清单列表，suiteId 才能正确显示名称
          await fetchSuites(form.entityId)
      }
  }

  drawerVisible.value = true
}

const resetForm = () => {
  form.planId = ''
  form.planName = ''
  form.planNumber = ''
  form.suiteId = undefined
  form.entityId = undefined
  form.dateRange = []
  form.remark = ''
  form.forRecordData = 0
  
  // 重置级联
  cascadeFilter.model = ''
  cascadeFilter.profession = ''
  cascadeFilter.subsystem = ''
  suiteOptions.value = []
  
  nextTick(() => { formRef.value?.resetFields() })
}

const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid: boolean) => {
    if (valid) {
      if (!form.entityId) {
          ElMessage.warning('请选择完整的子系统')
          return 
      }
      submitLoading.value = true
      try {
        const payload = {
          ...form,
          planStartTime: form.dateRange[0],
          planEndTime: form.dateRange[1],
          dateRange: undefined
        }
        if (isEdit.value) {
          await updateTestPlan(payload)
          ElMessage.success('更新成功')
        } else {
          await createTestPlan(payload)
          ElMessage.success('创建成功')
        }
        drawerVisible.value = false
        loadData()
      } catch (e) { } finally { submitLoading.value = false }
    }
  })
}

const handleDelete = (row: TestPlan) => { ElMessageBox.confirm('确认删除?', '警告', { type: 'warning' }).then(async () => { await deleteSingleTestPlan(row.planId); ElMessage.success('删除成功'); loadData(); }).catch(()=>{}) }
const handleBatchDelete = () => { if (!selectedIds.value.length) return; ElMessageBox.confirm('确认批量删除?', '警告', { type: 'warning' }).then(async () => { await deleteTestPlanWithBatch({ planIdLists: selectedIds.value }); ElMessage.success('删除成功'); loadData(); selectedIds.value = [] }).catch(()=>{}) }
const handleSelectionChange = (s: TestPlan[]) => selectedIds.value = s.map(i => i.planId)
const handleDispatch = async (row: TestPlan) => { try { const res: any = await dispatchPlan(row.planId); dispatchResult.value = res; dispatchVisible.value = true; loadData(); } catch(e){} }
const handleStart = async (row: TestPlan) => { await startPlan(row.planId); ElMessage.success('开始'); loadData() }
const handlePause = async (row: TestPlan) => { await pausePlan(row.planId); ElMessage.warning('暂停'); loadData() }
</script>

<style scoped>
.test-plan-container { padding: 0; }
.header-actions { display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 12px; }
.left-panel, .right-panel { display: flex; gap: 10px; align-items: center; }
.main-card { border-radius: var(--app-radius); }
.main-card :deep(.el-card__body) { padding: 20px; }
.table-wrap { margin-top: 16px; border-radius: var(--app-radius); overflow: hidden; }
.data-table { width: 100%; }
</style>