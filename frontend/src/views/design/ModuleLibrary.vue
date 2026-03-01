<template>
  <div class="module-library">
    <h2 class="page-title">模块库</h2>
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
        <el-button color="#001529" style="color: white" @click="openCreateDialog">添加模块</el-button>
      </div>
    </div>

    <div class="table-wrap">
    <el-table :data="tableData" class="data-table" border stripe>
      <el-table-column prop="num" label="模块编号" />
      <el-table-column label="模块名称">
        <template #default="{ row }">
          <span style="color: #1890ff; cursor: pointer">{{ row.funName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="special" label="特殊项" />
      <el-table-column label="架次有效性">
        <template #default="{ row }">
           {{ row.planeEffectMin }} - {{ row.planeEffectMax }}
        </template>
      </el-table-column>
      <el-table-column prop="versionDescription" label="版本" />
      <el-table-column label="修改状态">
        <template #default="{ row }">
           <span v-if="row.changeFlag === 4" style="color: #F56C6C">修改状态</span>
           <span v-else>正常</span>
        </template>
      </el-table-column>
      <el-table-column label="审签状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.approveStatus)">
            {{ APPROVE_STATUS_MAP[row.approveStatus] || '未知状态' }}
          </el-tag>
        </template>
      </el-table-column>
      
      <el-table-column label="操作" width="250">
        <template #default="{ row }">
          <el-button link type="primary" @click="goToDetails(row)">详情</el-button>
          <el-button 
            link 
            type="primary" 
            :disabled="row.approveStatus !== 0"
            @click="openEditDialog(row)"
          >
            修改
          </el-button>
          <el-button 
            link 
            type="primary" 
            :disabled="row.approveStatus !== 0"
            @click="handleSubmitToReview(row)"
          >
            提交
          </el-button>
          <el-button link type="primary">相关人员</el-button>
        </template>
      </el-table-column>
    </el-table>
    </div>

    <el-dialog
      v-model="dialogVisible"
      :title="isEditMode ? '修改模块' : '创建模块'" 
      width="60%"
    >
      <el-form :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="模块编号">
              <el-input v-model="form.num" type="number"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
              <el-form-item label="模块名称">
              <el-input v-model="form.funName" />
            </el-form-item>
          </el-col>
        </el-row>
         <el-row :gutter="20">
          <el-col :span="12">
              <el-form-item label="是否涉密">
              <el-switch v-model="form.military" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
              <el-form-item label="版本描述">
              <el-input v-model="form.versionDescription" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
           <el-col :span="12">
            <el-form-item label="架次Min">
               <el-input-number v-model="form.planeEffectMin" :min="1" />
            </el-form-item>
           </el-col>
            <el-col :span="12">
            <el-form-item label="架次Max">
               <el-input-number v-model="form.planeEffectMax" :min="1" />
            </el-form-item>
           </el-col>
        </el-row>

        <div class="dynamic-section">
            <h4>所需其他技术文件</h4>
            <div v-for="(item, index) in form.techFiles" :key="'tech'+index" class="dynamic-row">
                <el-input v-model="item.value" placeholder="请输入文件名" />
                <el-icon class="delete-icon" color="red" @click="removeTechFile(index)"><Delete /></el-icon>
            </div>
            <el-button type="success" size="small" @click="addTechFile">+ 新增</el-button>
        </div>

        <div class="dynamic-section">
            <h4>设备汇总</h4>
             <div v-for="(item, index) in form.devices" :key="'dev'+index" class="dynamic-row">
                <el-input v-model="item.value" placeholder="请输入设备名" />
                 <el-icon class="delete-icon" color="red" @click="removeDevice(index)"><Delete /></el-icon>
            </div>
             <el-button type="success" size="small" @click="addDevice">+ 新增</el-button>
        </div>

        <div class="security-check" style="margin-top: 20px; border-top: 1px solid #eee; padding-top: 10px;">
           <el-checkbox-group v-model="form.securityChecks">
              <el-checkbox label="Check 1">安全检查项 1</el-checkbox>
              <el-checkbox label="Check 2">安全检查项 2</el-checkbox>
           </el-checkbox-group>
           <div class="warning-text" style="color: red; margin-top: 10px">
               注意：请务必确认以上安全事项！
           </div>
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
import { useGlobalFilterStore } from '@/store/globalFilter'
import { 
  createTestFunction, 
  getTestFunctions, 
  updateTestFunction, 
  submitTestFunction 
} from '@/api/designer'
// 【新增】引入新接口
import { listAllBaseStructAndId } from '@/api/base' 

import { Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const filterStore = useGlobalFilterStore()

// 存储当前的 testBaseId，防止 row 中缺失
const currentTestBaseId = ref<number>(0)
const tableData = ref<any[]>([])

// --- 状态与颜色映射 ---
const APPROVE_STATUS_MAP = [ "待提交", "待校对", "待质审", "待审查", "待批准", "待审核", "审签成功", "审签失败" ]
const getStatusType = (status: number) => {
  if (status === 6) return 'success'
  if (status === 7) return 'danger' 
  if (status === 0) return 'info'    
  return 'warning'                   
}

// ================= 级联筛选核心逻辑 =================

// 存储 API 返回的原始全量数据
const allStructs = ref<any[]>([])

// 下拉框选项
const modelOptions = ref<any[]>([])
const professionOptions = ref<any[]>([])
const subsystemOptions = ref<any[]>([])

// 1. 初始化：获取所有构型数据
const initBaseStructs = async () => {
    try {
        const res: any = await listAllBaseStructAndId()
        // 兼容处理：API 返回 data 数组
        const data = res.data || res.result || res || []
        
        if (Array.isArray(data)) {
            allStructs.value = data
            
            // 提取所有机型 (item.baseStruct.model)
            const models = new Set(data.map((item: any) => item.baseStruct?.model).filter(Boolean))
            modelOptions.value = Array.from(models).map(m => ({ value: m, label: m }))
            
            // 如果 Store 里有缓存，尝试回显
            if (filterStore.model) {
                handleModelChange(filterStore.model, false)
            }
        }
    } catch (e) {
        console.error("加载构型数据失败", e)
    }
}

// 2. 机型改变 -> 过滤专业
const handleModelChange = (val: any, clearNext = true) => {
    if (clearNext) {
        filterStore.profession = ''
        filterStore.subsystem = ''
        professionOptions.value = []
        subsystemOptions.value = []
        tableData.value = []
    }
    
    if (!val) return

    // 筛选匹配的行
    const filtered = allStructs.value.filter((item: any) => item.baseStruct?.model === val)
    
    // 提取专业
    const profs = new Set(filtered.map((item: any) => item.baseStruct?.profession).filter(Boolean))
    professionOptions.value = Array.from(profs).map(p => ({ value: p, label: p }))

    // 回显逻辑
    if (!clearNext && filterStore.profession) {
        handleProfessionChange(filterStore.profession, false)
    }
}

// 3. 专业改变 -> 过滤子系统
const handleProfessionChange = (val: any, clearNext = true) => {
    if (clearNext) {
        filterStore.subsystem = ''
        subsystemOptions.value = []
        tableData.value = []
    }

    if (!val) return

    // 筛选匹配 Model + Profession 的行
    const filtered = allStructs.value.filter((item: any) => 
        item.baseStruct?.model === filterStore.model &&
        item.baseStruct?.profession === val
    )

    // 提取子系统
    const subs = new Set(filtered.map((item: any) => item.baseStruct?.subsystem).filter(Boolean))
    subsystemOptions.value = Array.from(subs).map(s => ({ value: s, label: s }))

    // 回显逻辑
    if (!clearNext && filterStore.subsystem) {
        handleSubsystemChange()
    }
}

// 4. 子系统改变 (或点击刷新) -> 查找 ID 并获取列表
const handleSubsystemChange = async () => {
    const { model, profession, subsystem } = filterStore
    
    if (!model || !profession || !subsystem) {
        return
    }

    // 在本地数据中查找对应的 baseId (无需再调 getTestBaseWithLimit 接口)
    const target = allStructs.value.find((item: any) => 
        item.baseStruct?.model === model &&
        item.baseStruct?.profession === profession &&
        item.baseStruct?.subsystem === subsystem
    )

    if (target && target.baseId) {
        currentTestBaseId.value = target.baseId
        await fetchFunctions(target.baseId)
    } else {
        // 如果找不到 ID (理论上不应该发生，因为选项是从数据里提取的)
        tableData.value = []
        ElMessage.warning('未找到对应构型ID')
    }
}

// 真正请求表格数据的函数
const fetchFunctions = async (baseId: number) => {
    try {
        const res: any = await getTestFunctions({ testBaseId: baseId })
        if (Array.isArray(res)) { tableData.value = res } 
        else if (res && Array.isArray(res.data)) { tableData.value = res.data } 
        else { tableData.value = [] }
    } catch (e) {
        console.error(e)
        tableData.value = []
    }
}

// ================= 级联逻辑结束 =================

const goToDetails = (row: any) => { router.push({ name: 'ModuleOrchestration', params: { funId: row.funId } }) }

const parseComplexField = (val: any) => {
    if (!val) return []
    if (Array.isArray(val)) return val
    try { return JSON.parse(val) } catch (e) { return [] }
}

// --- 弹窗与表单逻辑 ---
const dialogVisible = ref(false)
const isEditMode = ref(false)
const currentEditId = ref<number | null>(null)

const form = reactive({
    num: '',
    funName: '',
    military: false,
    versionDescription: '',
    planeEffectMin: 1,
    planeEffectMax: 100,
    techFiles: [] as {value: string}[],
    devices: [] as {value: string}[],
    securityChecks: []
})

const resetForm = () => {
    form.num = ''
    form.funName = ''
    form.military = false
    form.versionDescription = ''
    form.planeEffectMin = 1
    form.planeEffectMax = 100
    form.techFiles = []
    form.devices = []
    form.securityChecks = []
}

const openCreateDialog = () => {
    isEditMode.value = false
    currentEditId.value = null
    resetForm()
    dialogVisible.value = true
}

const openEditDialog = (row: any) => {
    isEditMode.value = true
    currentEditId.value = row.funId
    form.num = row.num
    form.funName = row.funName
    form.military = !!row.military
    form.versionDescription = row.versionDescription
    form.planeEffectMin = row.planeEffectMin || 1
    form.planeEffectMax = row.planeEffectMax || 100
    
    const techFilesRaw = parseComplexField(row.otherTechFiles)
    form.techFiles = techFilesRaw.map((item: any[]) => ({ value: item[0] || '' }))

    const deviceRaw = parseComplexField(row.devicePool)
    form.devices = deviceRaw.map((item: any[]) => ({ value: item[0] || '' }))
    
    form.securityChecks = []
    dialogVisible.value = true
}

const addTechFile = () => form.techFiles.push({ value: '' })
const removeTechFile = (index: number) => form.techFiles.splice(index, 1)
const addDevice = () => form.devices.push({ value: '' })
const removeDevice = (index: number) => form.devices.splice(index, 1)

const submitForm = async () => {
    try {
        if (!currentTestBaseId.value) {
            ElMessage.error('当前构型ID无效，请先选择正确的构型')
            return
        }

        const commonData = {
            num: Number(form.num),
            funName: form.funName,
            military: form.military,
            versionDescription: form.versionDescription,
            planeEffectMin: form.planeEffectMin,
            planeEffectMax: form.planeEffectMax,
            testBaseId: currentTestBaseId.value,
            approveStatus: 0, 
            otherTechFiles: form.techFiles.map(item => [item.value]),
            devicePool: form.devices.map(item => [item.value])
        }

        if (isEditMode.value) {
            if (!currentEditId.value) return
            await updateTestFunction({ funId: currentEditId.value, ...commonData })
            ElMessage.success('修改成功')
        } else {
            await createTestFunction(commonData)
            ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        // 刷新当前列表
        fetchFunctions(currentTestBaseId.value)
    } catch (e) {
        console.error(e)
    }
}

const handleSubmitToReview = async (row: any) => {
    try {
        await ElMessageBox.confirm('确定要提交该模块进行审签吗？', '提示', {
            confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        })
        await submitTestFunction(row.funId);
        ElMessage.success('提交成功')
        // 刷新当前列表
        if (currentTestBaseId.value) {
            fetchFunctions(currentTestBaseId.value)
        }
    } catch (e) { }
}

onMounted(() => {
    // 页面加载时初始化构型字典
    initBaseStructs()
})
</script>

<style scoped lang="scss">
.module-library .page-title { margin-top: 0; }
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

.dynamic-row {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 10px;

    .delete-icon {
        cursor: pointer;
    }
}
</style>