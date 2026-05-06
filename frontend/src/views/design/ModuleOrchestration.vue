<template>
  <div class="module-orchestration">
    <!-- Header Toolbar -->
    <div class="toolbar">
      <div class="left-actions">
        <el-button plain @click="handleBack">返回列表</el-button>
        <el-button type="success" @click="fetchData">刷新</el-button>
        <el-button type="primary" @click="openCreateModule">创建用例</el-button>
      </div>
      <div class="right-actions">
        <el-button plain @click="toggleCollapse">{{ isExpanded ? '收起' : '展开' }}</el-button>
      </div>
    </div>

    <!-- Tree Table -->
    <el-table
      ref="tableRef"
      :data="tableData"
      style="width: 100%; margin-top: 20px"
      row-key="key"
      border
      :default-expand-all="isExpanded"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column label="层级" width="180"> <template #default="{ row }">
          <span v-if="row.type === 'MODULE'" style="font-weight: bold; color: #F56C6C">
            {{ row.indexLabel }} 用例
          </span>
          <span v-else-if="row.type === 'CASE'" style="color: #409EFF">
            {{ row.indexLabel }} 子用例
          </span>
          <span v-else-if="row.type === 'STEP'" style="color: #67C23A">
            {{ row.indexLabel }} 步骤
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="用例步骤名称" min-width="200" />
      <el-table-column prop="operation" label="操作动作" width="150" />
      <el-table-column prop="obj" label="操作对象" width="150" />
      <el-table-column prop="purpose" label="操作目的" width="150" />
      <el-table-column prop="changeUser" label="修改人" width="100" />
      <el-table-column label="操作" width="250" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="openEditDialog(row)">编辑</el-button>
          <el-button
            type="success"
            size="small"
            @click="openCreateDialog(row)"
            v-if="row.type !== 'STEP'"
          >
            新增
          </el-button>
          <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Create/Edit Dialog -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑节点' : '创建节点'"
      width="720px"
    >
      <el-form :model="form" ref="formRef" label-width="120px" :rules="rules">
        <!-- Area 1: Base Info -->
        <div class="form-section">
          <h3>基础信息</h3>
          <el-form-item label="类型" prop="type">
            <el-select v-model="form.type" :disabled="isEdit" placeholder="请选择">
              <!-- If Creating Module (Top Level) -->
              <el-option label="用例 (Module)" value="MODULE" v-if="allowModuleCreation" />
              <!-- If Creating Child -->
              <el-option label="子用例 (Case)" value="CASE" v-if="allowCaseCreation" />
              <el-option label="步骤 (Step)" value="STEP" v-if="allowStepCreation" />
            </el-select>
          </el-form-item>
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" />
          </el-form-item>
          <el-form-item label="注意事项">
            <el-input v-model="form.description" type="textarea" />
          </el-form-item>
        </div>

        <el-divider />

        <!-- Area 2: Operation Config (Step only) -->
        <div class="form-section" v-if="form.type === 'STEP'">
          <h3>操作配置</h3>
          <el-form-item label="操作内容" prop="operation">
            <el-input v-model="form.operation" />
          </el-form-item>
          <el-form-item label="操作对象 (topic)" prop="obj">
            <el-select
              v-model="form.obj"
              filterable
              allow-create
              default-first-option
              placeholder="从设备服务选择或手动输入 topic"
              style="width: 100%"
            >
              <el-option v-for="t in deviceTopics" :key="t" :label="t" :value="t" />
            </el-select>
            <div class="hint">数据来自设备管理服务；派发执行时会写入 EMS 的 eventType。</div>
          </el-form-item>
          <div v-if="paramFields.length" class="param-block">
            <h4>报文可填字段</h4>
            <el-form-item
              v-for="f in paramFields"
              :key="f.path"
              :label="f.path"
            >
              <el-input v-model="paramValues[f.path]" placeholder="填充值" />
            </el-form-item>
          </div>
          <el-form-item label="操作目的">
             <el-input v-model="form.purpose" type="textarea" />
          </el-form-item>
        </div>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getModuleTree, addCase, updateCase, deleteCase, addStep, updateStep, deleteStep, deleteModule } from '@/api/designer/module-orchestration'
import { getDeviceTopics } from '@/api/integration'
import { addModule } from '@/api/designer'

const route = useRoute()
const router = useRouter()
const funId = computed(() => Number(route.params.funId))
const tableRef = ref()
const isExpanded = ref(true)

const tableData = ref<any[]>([])

const generateHierarchyIndices = (data: any[], parentIndex = '') => {
  if (!data) return []
  data.forEach((item, index) => {
    // 生成逻辑：顶层直接用 index+1，子层拼接 parentIndex
    const currentIndex = parentIndex ? `${parentIndex}.${index + 1}` : `${index + 1}`
    item.indexLabel = currentIndex // 赋值给临时属性
    
    if (item.children && item.children.length) {
      generateHierarchyIndices(item.children, currentIndex)
    }
  })
  return data
}


const fetchData = async () => {
  if (!funId.value) return
  try {
    const res: any = await getModuleTree(funId.value)
    
    const rawData = res || []
    tableData.value = generateHierarchyIndices(rawData) 
    
  } catch (e) {
    console.error(e)
  }
}

// --- Toolbar Actions ---
const handleBack = () => router.push('/design/module')

const toggleCollapse = () => {
    isExpanded.value = !isExpanded.value
    if (tableRef.value) {
       toggleRowExpansionRecursive(tableData.value, isExpanded.value)
    }
}

const toggleRowExpansionRecursive = (data: any[], expanded: boolean) => {
    data.forEach(item => {
        tableRef.value!.toggleRowExpansion(item, expanded)
        if (item.children && item.children.length) {
            toggleRowExpansionRecursive(item.children, expanded)
        }
    })
}

// --- Dialog Logic ---
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref()

const form = reactive({
  id: undefined as number | undefined,
  name: '',
  type: 'STEP', // 'MODULE', 'CASE', 'STEP'
  description: '',
  operation: '',
  obj: '',
  purpose: '',
  parentId: undefined as number | undefined,
  parentType: ''
})

const deviceTopics = ref<string[]>([])
const deviceExample = ref<Record<string, unknown> | null>(null)
const paramFields = ref<{ path: string; sample: unknown }[]>([])
const paramValues = reactive<Record<string, string>>({})
const stepEditContext = ref<any>(null)

function collectLeafFields(obj: unknown, prefix = ''): { path: string; sample: unknown }[] {
  const out: { path: string; sample: unknown }[] = []
  if (obj === null || typeof obj !== 'object') {
    if (prefix) out.push({ path: prefix, sample: obj })
    return out
  }
  if (Array.isArray(obj)) {
    if (prefix) out.push({ path: prefix, sample: JSON.stringify(obj) })
    return out
  }
  const keys = Object.keys(obj as object)
  for (const k of keys) {
    const p = prefix ? `${prefix}.${k}` : k
    const v = (obj as Record<string, unknown>)[k]
    if (v !== null && typeof v === 'object' && !Array.isArray(v)) {
      out.push(...collectLeafFields(v, p))
    } else {
      out.push({ path: p, sample: v })
    }
  }
  return out
}

async function loadDeviceTopics() {
  const res: any = await getDeviceTopics()
  deviceTopics.value = Array.isArray(res?.topics) ? res.topics : []
  const skipExample =
    isEdit.value && stepEditContext.value && stepEditContext.value.commandExample
  if (!skipExample) {
    deviceExample.value = (res?.example && typeof res.example === 'object')
      ? (res.example as Record<string, unknown>)
      : {}
  }
}

function rebuildParamUiFromExample() {
  const ex = deviceExample.value
  paramFields.value = ex ? collectLeafFields(ex) : []
  const srcParams =
    isEdit.value && stepEditContext.value?.commandParams
      ? (() => {
          try {
            return JSON.parse(stepEditContext.value.commandParams) as Record<string, unknown>
          } catch {
            return {}
          }
        })()
      : null
  Object.keys(paramValues).forEach((k) => delete paramValues[k])
  for (const f of paramFields.value) {
    const fromSaved = srcParams && srcParams[f.path] != null
    if (fromSaved) {
      paramValues[f.path] = String(srcParams![f.path])
    } else {
      paramValues[f.path] =
        f.sample === undefined || f.sample === null ? '' : String(f.sample)
    }
  }
}

watch(dialogVisible, async (v) => {
  if (!v || form.type !== 'STEP') return
  try {
    await loadDeviceTopics()
    if (isEdit.value && stepEditContext.value?.commandExample) {
      try {
        deviceExample.value = JSON.parse(stepEditContext.value.commandExample)
      } catch {
        deviceExample.value = {}
      }
    }
    rebuildParamUiFromExample()
  } catch (e) {
    console.error(e)
    ElMessage.error('设备服务暂不可用，请稍后重试或联系管理员')
  }
})

const rules = {
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  operation: [{ required: true, message: '请输入操作内容', trigger: 'blur' }],
  obj: [{ required: true, message: '请输入操作对象', trigger: 'blur' }]
}

// 父子关系：ROOT→MODULE、MODULE→CASE、CASE→STEP
const ALLOWED_CHILD: Record<string, string> = { ROOT: 'MODULE', MODULE: 'CASE', CASE: 'STEP' }
const allowModuleCreation = computed(() => !isEdit.value && ALLOWED_CHILD[form.parentType] === 'MODULE')
const allowCaseCreation = computed(() => !isEdit.value && ALLOWED_CHILD[form.parentType] === 'CASE')
const allowStepCreation = computed(() => !isEdit.value && ALLOWED_CHILD[form.parentType] === 'STEP')

// Open Dialog for "Create Use Case" (Module) - Top Level
const openCreateModule = () => {
    stepEditContext.value = null
    isEdit.value = false
    form.id = undefined
    form.name = ''
    form.description = ''
    form.operation = ''
    form.obj = ''
    form.purpose = ''

    form.parentId = undefined
    form.parentType = 'ROOT'
    form.type = 'MODULE' // Default

    dialogVisible.value = true
}

const openCreateDialog = (row: any) => {
  stepEditContext.value = null
  isEdit.value = false
  form.id = undefined
  form.name = ''
  form.description = ''
  form.operation = ''
  form.obj = ''
  form.purpose = ''

  // Set context
  form.parentId = row.id // Real ID
  form.parentType = row.type // MODULE or CASE

  // Set default type
  if (row.type === 'MODULE') {
      form.type = 'CASE'
  } else if (row.type === 'CASE') {
      form.type = 'STEP'
  }

  dialogVisible.value = true
}

const openEditDialog = (row: any) => {
  stepEditContext.value = row.type === 'STEP' ? row : null
  isEdit.value = true
  form.id = row.id
  form.name = row.name
  form.type = row.type
  form.description = row.description
  form.operation = row.operation
  form.obj = row.obj
  form.purpose = row.purpose

  dialogVisible.value = true
}

const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        if (isEdit.value) {
            // Update
            if (form.type === 'CASE') {
                await updateCase({
                    caseId: form.id,
                    caseName: form.name,
                    caseDescription: form.description
                })
            } else if (form.type === 'STEP') {
                await updateStep({
                    stepId: form.id,
                    stepName: form.name,
                    stepDescription: form.description,
                    stepOperation: form.operation,
                    stepObj: form.obj,
                    stepCommandExample: JSON.stringify(deviceExample.value ?? {}),
                    stepCommandParams: JSON.stringify(paramValues),
                    stepPurpose: form.purpose
                })
            } else if (form.type === 'MODULE') {
                // 当前视图未实现 Module 直接编辑，请在模块库列表页修改
                ElMessage.warning('请在模块库列表页修改用例基本信息')
            }
        } else {
            // Create
            if (form.type === 'MODULE') {
                await addModule({
                    moduleName: form.name,
                    funId: funId.value
                })
            } else if (form.type === 'CASE') {
                await addCase({
                    caseName: form.name,
                    caseDescription: form.description,
                    moduleId: form.parentId // Parent is Module
                })
            } else if (form.type === 'STEP') {
                await addStep({
                    stepName: form.name,
                    stepDescription: form.description,
                    stepOperation: form.operation,
                    stepObj: form.obj,
                    stepCommandExample: JSON.stringify(deviceExample.value ?? {}),
                    stepCommandParams: JSON.stringify(paramValues),
                    stepPurpose: form.purpose,
                    caseId: form.parentId // Parent is Case
                })
            }
        }
        ElMessage.success('保存成功')
        dialogVisible.value = false
        fetchData()
      } catch (e) {
        console.error(e)
      }
    }
  })
}

const handleDelete = async (row: any) => {
    try {
        await ElMessageBox.confirm(`确认删除该节点【${row.name}】吗？`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
    } catch {
        return // 用户取消
    }
    try {
        if (row.type === 'CASE') {
            await deleteCase(row.id)
        } else if (row.type === 'STEP') {
            await deleteStep(row.id)
        } else if (row.type === 'MODULE') {
            await deleteModule(row.id)
        }
        ElMessage.success('删除成功')
        fetchData()
    } catch (e) {
        console.error(e)
    }
}

onMounted(() => {
    fetchData()
})
</script>

<style scoped lang="scss">
.toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: white;
    padding: 15px;
    border-radius: 4px;
    margin-bottom: 20px;

    .left-actions, .right-actions {
        display: flex;
        gap: 10px;
    }
}

.form-section {
    margin-bottom: 20px;
}
h3 {
    margin-top: 0;
    margin-bottom: 15px;
    font-size: 16px;
    font-weight: bold;
    color: #333;
}
.param-block {
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px dashed #e4e7ed;
}
.param-block h4 {
  margin: 0 0 12px;
  font-size: 14px;
  color: #606266;
}
.hint {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}
</style>
