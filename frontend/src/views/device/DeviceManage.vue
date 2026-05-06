<template>
  <div class="device-manage">
    <h2 class="page-title">设备管理</h2>

    <div class="filter-bar">
      <el-input
        v-model="query.name"
        placeholder="设备名称/编号"
        clearable
        style="width: 220px"
        @keyup.enter="loadData"
        @clear="loadData"
      />
      <el-select v-model="query.type" placeholder="设备类型" clearable style="width: 160px">
        <el-option v-for="t in DEVICE_TYPE_OPTIONS" :key="t.value" :label="t.label" :value="t.value" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
        <el-option label="可用" :value="DEVICE_STATUS.ENABLED" />
        <el-option label="禁用" :value="DEVICE_STATUS.DISABLED" />
        <el-option label="维护中" :value="DEVICE_STATUS.MAINTAIN" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button @click="resetFilter">重置</el-button>

      <div style="flex:1" />
      <el-button type="success" @click="openCreateDialog" :disabled="!canManage">
        新增设备
      </el-button>
    </div>

    <div class="table-wrap">
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column type="index" label="#" width="50" />
        <el-table-column prop="code" label="设备编号" width="140" />
        <el-table-column prop="name" label="设备名称" min-width="180" />
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">
            {{ renderType(row.type) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="说明" min-width="220" show-overflow-tooltip />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openEditDialog(row)" :disabled="!canManage">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)" :disabled="!canManage">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑设备' : '新增设备'" width="480px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="设备编号" prop="code">
          <el-input v-model="form.code" placeholder="如 DEV-001" />
        </el-form-item>
        <el-form-item label="设备名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择设备类型" style="width: 100%">
            <el-option v-for="t in DEVICE_TYPE_OPTIONS" :key="t.value" :label="t.label" :value="t.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="DEVICE_STATUS.ENABLED">可用</el-radio>
            <el-radio :label="DEVICE_STATUS.MAINTAIN">维护中</el-radio>
            <el-radio :label="DEVICE_STATUS.DISABLED">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="说明">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" :loading="saving" @click="submitForm">
          保 存
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import { getDeviceList, createDevice, updateDevice, deleteDevice, type DeviceDto } from '@/api/device'
import { useAuthStore } from '@/store/auth'

const authStore = useAuthStore()
const canManage = computed(() => authStore.hasRole('ADMIN'))

// 设备状态枚举 / 文本 / Tag 类型映射（与后端 device.status 对齐）
const DEVICE_STATUS = { DISABLED: 0, ENABLED: 1, MAINTAIN: 2 } as const
const DEVICE_STATUS_TEXT: Record<number, string> = {
    [DEVICE_STATUS.DISABLED]: '禁用',
    [DEVICE_STATUS.ENABLED]: '可用',
    [DEVICE_STATUS.MAINTAIN]: '维护中'
}
const DEVICE_STATUS_TAG: Record<number, string> = {
    [DEVICE_STATUS.DISABLED]: 'info',
    [DEVICE_STATUS.ENABLED]: 'success',
    [DEVICE_STATUS.MAINTAIN]: 'warning'
}

// 设备类型枚举（联调时改字典表驱动；先抽常量去重）
const DEVICE_TYPE_OPTIONS = [
    { value: 'industrial_pc', label: '工控机' },
    { value: 'sensor', label: '传感器' },
    { value: 'actuator', label: '执行机构' }
] as const
const DEVICE_TYPE_TEXT: Record<string, string> = Object.fromEntries(
    DEVICE_TYPE_OPTIONS.map(o => [o.value, o.label])
)

const loading = ref(false)
const tableData = ref<DeviceDto[]>([])

const query = reactive({
  name: '',
  type: '',
  status: undefined as number | undefined
})

const dialogVisible = ref(false)
const isEdit = ref(false)
const saving = ref(false)
const currentId = ref<string | undefined>()
const formRef = ref<FormInstance>()

const form = reactive<DeviceDto>({
  code: '',
  name: '',
  type: '',
  status: DEVICE_STATUS.ENABLED,
  description: ''
})

const rules = {
  code: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  type: [{ required: true, message: '请选择设备类型', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getDeviceList({
      name: query.name || undefined,
      type: query.type || undefined,
      status: query.status
    })
    // 设备后端未就绪时，这里可能返回空或 404；联调时按 DEVICE_API_SPEC 实现
    const list = Array.isArray(res?.list) ? res.list : (Array.isArray(res) ? res : [])
    tableData.value = list
  } catch (e) {
    console.error(e)
    tableData.value = []
  } finally {
    loading.value = false
  }
}

const resetFilter = () => {
  query.name = ''
  query.type = ''
  query.status = undefined
  loadData()
}

const openCreateDialog = () => {
  isEdit.value = false
  currentId.value = undefined
  form.code = ''
  form.name = ''
  form.type = ''
  form.status = DEVICE_STATUS.ENABLED
  form.description = ''
  dialogVisible.value = true
}

const openEditDialog = (row: DeviceDto) => {
  isEdit.value = true
  currentId.value = row.id
  form.code = row.code
  form.name = row.name
  form.type = row.type || ''
  form.status = row.status ?? DEVICE_STATUS.ENABLED
  form.description = row.description || ''
  dialogVisible.value = true
}

const submitForm = () => {
  if (!formRef.value) return
  formRef.value.validate(async (valid) => {
    if (!valid) return
    saving.value = true
    try {
      if (isEdit.value && currentId.value) {
        await updateDevice(currentId.value, form)
        ElMessage.success('更新成功')
      } else {
        await createDevice(form)
        ElMessage.success('创建成功')
      }
      dialogVisible.value = false
      loadData()
    } catch (e) {
      console.error(e)
    } finally {
      saving.value = false
    }
  })
}

const handleDelete = (row: DeviceDto) => {
  if (!row.id) {
    ElMessage.warning('缺少设备ID，无法删除')
    return
  }
  ElMessageBox.confirm(`确认删除设备【${row.name || row.code}】吗？`, '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteDevice(row.id as string)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

const statusText = (status?: number) =>
  DEVICE_STATUS_TEXT[status ?? DEVICE_STATUS.ENABLED] || '未知'

const statusType = (status?: number) =>
  DEVICE_STATUS_TAG[status ?? DEVICE_STATUS.ENABLED] || 'info'

const renderType = (type?: string) =>
  DEVICE_TYPE_TEXT[type || ''] || type || '-'

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.device-manage {
  padding: 0;
}
</style>

