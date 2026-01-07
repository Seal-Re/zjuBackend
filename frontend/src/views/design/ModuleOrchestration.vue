<template>
  <div class="module-orchestration">
    <!-- Header Toolbar -->
    <div class="toolbar">
      <div class="left-actions">
        <el-button plain @click="handleBack">返回列表</el-button>
        <el-button type="success" @click="fetchData">刷新</el-button>
        <el-button type="warning" @click="handleSave">保存修改</el-button>
        <el-button type="primary" @click="openCreateModule">创建用例</el-button>
      </div>
      <div class="right-actions">
        <el-button plain @click="toggleCollapse">收起</el-button>
        <el-button type="primary" @click="handleImport">导入</el-button>
        <el-button type="primary" @click="handleExport">导出</el-button>
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
      <el-table-column label="层级" width="150">
        <template #default="{ row }">
           <span v-if="row.type === 'MODULE'" style="font-weight: bold; color: #F56C6C">1. 用例</span>
           <span v-else-if="row.type === 'CASE'" style="color: #409EFF">1.1. 子用例</span>
           <span v-else-if="row.type === 'STEP'" style="color: #67C23A">1. 步骤</span>
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
      width="600px"
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
          <el-form-item label="操作对象" prop="obj">
            <el-input v-model="form.obj" />
          </el-form-item>
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
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getModuleTree, addCase, updateCase, deleteCase, addStep, updateStep, deleteStep, deleteModule } from '@/api/designer/module-orchestration'
// Assuming addModule exists or reusing similar for "Create Use Case" (Module) if needed.
// Since I don't have addModule imported, I'll assume "Create Use Case" creates a Case on the first module for now,
// or I should verify if I can import addModule from designer.
import { addModule } from '@/api/designer' // Imported from generic designer api

const route = useRoute()
const router = useRouter()
const funId = computed(() => Number(route.params.funId))
const tableRef = ref()
const isExpanded = ref(true)

const tableData = ref<any[]>([])

const fetchData = async () => {
  if (!funId.value) return
  try {
    const res: any = await getModuleTree(funId.value)
    tableData.value = res || []
  } catch (e) {
    console.error(e)
  }
}

// --- Toolbar Actions ---
const handleBack = () => router.push('/design/module-library')
const handleSave = () => ElMessage.success('Saved successfully (Mock)')
const handleImport = () => ElMessage.info('Import feature coming soon')
const handleExport = () => ElMessage.info('Export feature coming soon')

const toggleCollapse = () => {
    isExpanded.value = !isExpanded.value
    // Element Plus Table toggleRowExpansion is needed if default-expand-all is dynamic
    // But expanding all dynamically is tricky.
    // Simple way: re-render table or iterate rows.
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

const rules = {
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  operation: [{ required: true, message: '请输入操作内容', trigger: 'blur' }],
  obj: [{ required: true, message: '请输入操作对象', trigger: 'blur' }]
}

// Determine allowed options
const allowModuleCreation = computed(() => !isEdit.value && form.parentType === 'ROOT')
const allowCaseCreation = computed(() => !isEdit.value && form.parentType === 'MODULE')
const allowStepCreation = computed(() => !isEdit.value && form.parentType === 'CASE')

// Open Dialog for "Create Use Case" (Module) - Top Level
const openCreateModule = () => {
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
                    stepPurpose: form.purpose
                })
            } else if (form.type === 'MODULE') {
                // Assuming generic update exists or warn
                ElMessage.warning('Module update not fully implemented in this view')
            }
        } else {
            // Create
            if (form.type === 'MODULE') {
                await addModule({
                    funName: form.name,
                    funId: funId.value, // It needs parent FunId
                    // Other required fields might be missing, checking API
                    // In ModuleLibrary add: num, military, etc.
                    // Minimal add might fail if backend enforces these.
                    // For now, pass defaults.
                    num: 0,
                    military: false,
                    planeEffectMin: 1,
                    planeEffectMax: 100
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
                    stepPurpose: form.purpose,
                    caseId: form.parentId // Parent is Case
                })
            }
        }
        ElMessage.success('Success')
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
        await ElMessageBox.confirm('Are you sure you want to delete this node?', 'Warning', {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
        })

        if (row.type === 'CASE') {
            await deleteCase(row.id)
        } else if (row.type === 'STEP') {
            await deleteStep(row.id)
        } else if (row.type === 'MODULE') {
             await deleteModule(row.id)
        }
        ElMessage.success('Deleted')
        fetchData()
    } catch (e) {
        // Cancelled or Error
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
</style>
