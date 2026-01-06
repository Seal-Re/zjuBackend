<template>
  <div class="module-orchestration">
    <div class="header">
      <h2>Module Orchestration: {{ functionName }}</h2>
      <el-button type="primary" @click="handleBack">Back to Library</el-button>
    </div>

    <!-- Tree Table -->
    <el-table
      :data="tableData"
      style="width: 100%; margin-top: 20px"
      row-key="key"
      border
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="层级/名称" min-width="200" />
      <el-table-column prop="operation" label="操作动作" width="150" />
      <el-table-column prop="obj" label="操作对象" width="150" />
      <el-table-column prop="purpose" label="操作目的" width="150" />
      <el-table-column prop="changeUser" label="修改人" width="100" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEditDialog(row)">编辑</el-button>
          <el-button
            link
            type="primary"
            @click="openCreateDialog(row)"
            v-if="row.type !== 'STEP'"
          >
            新增
          </el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Create/Edit Dialog -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑节点' : '创建步骤'"
      width="600px"
    >
      <el-form :model="form" ref="formRef" label-width="120px" :rules="rules">
        <!-- Area 1: Base Info -->
        <div class="form-section">
          <h3>基础信息</h3>
          <el-form-item label="步骤层级" prop="type">
            <el-select v-model="form.type" :disabled="isEdit" placeholder="请选择">
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

// --- API MOCK/DEFINITION (Should be in api folder) ---
// const getTreeByFunId = (funId: number) => axios.get(`/api/designer/module/treeByFunId?funId=${funId}`)
// But since I need to provide "Vue Component Code", I'll assume imports.
// I will create the API file next.
import { getModuleTree, addCase, updateCase, deleteCase, addStep, updateStep, deleteStep } from '@/api/designer/module-orchestration'

const route = useRoute()
const router = useRouter()
const funId = computed(() => Number(route.params.funId))
const functionName = ref('') // Ideally fetched or passed via query

const tableData = ref<any[]>([])

const fetchData = async () => {
  if (!funId.value) return
  try {
    const res: any = await getModuleTree(funId.value)
    // res is the data array directly due to interceptor?
    // Memory says "API response interceptors unwrap the response structure... return the data payload directly"
    // So `res` should be `List<DesignNodeDto>`
    tableData.value = res || []
  } catch (e) {
    console.error(e)
  }
}

// --- Dialog Logic ---
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref()

const form = reactive({
  id: undefined as number | undefined,
  name: '',
  type: 'STEP', // 'CASE' or 'STEP'
  description: '',
  operation: '',
  obj: '',
  purpose: '',
  parentId: undefined as number | undefined, // Needed for creation
  parentType: '' // Needed to determine what we can create
})

const rules = {
  type: [{ required: true, message: '请选择层级', trigger: 'change' }],
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  operation: [{ required: true, message: '请输入操作内容', trigger: 'blur' }],
  obj: [{ required: true, message: '请输入操作对象', trigger: 'blur' }]
}

// Determine allowed options based on parent
const allowCaseCreation = computed(() => !isEdit.value && form.parentType === 'MODULE')
const allowStepCreation = computed(() => !isEdit.value && form.parentType === 'CASE')

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
            }
        } else {
            // Create
            if (form.type === 'CASE') {
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
             // Optional: Delete module? But button usually on Case/Step
             // But if I put delete on all rows:
             // await deleteModule(row.id)
             ElMessage.warning('Deleting Module not supported here.')
             return
        }
        ElMessage.success('Deleted')
        fetchData()
    } catch (e) {
        // Cancelled or Error
    }
}

const handleBack = () => {
    router.push('/design/module-library')
}

onMounted(() => {
    fetchData()
})
</script>

<style scoped>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
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
