<template>
  <div class="module-library">
    <!-- Global Filter -->
    <div class="global-filter">
      <el-cascader
        v-model="filterStore.model"
        :options="modelOptions"
        :props="{ emitPath: false }"  placeholder="机型"
        @change="handleFilterChange"
        clearable />

      <el-cascader
        v-model="filterStore.profession"
        :options="professionOptions"
        :props="{ emitPath: false }"  placeholder="专业"
        @change="handleFilterChange"
        clearable
      />

      <el-cascader
        v-model="filterStore.subsystem"
        :options="subsystemOptions"
        :props="{ emitPath: false }"  placeholder="子系统"
        :disabled="!subsystemOptions.length"
        @change="handleFilterChange"
        clearable
      />  

      <div class="filter-actions">
        <el-button type="success" @click="fetchData">刷新</el-button>
        <el-button type="primary">审签配置</el-button>
        <el-button color="#001529" style="color: white" @click="openCreateDialog">添加模块</el-button>
      </div>
    </div>

    <!-- Data Table -->
    <el-table :data="tableData" style="width: 100%; margin-top: 20px" border>
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
      <el-table-column label="审签状态">
        <template #default="{ row }">
          <el-tag :type="row.approveStatus === 1 ? 'success' : 'warning'">
            {{ row.approveStatus === 1 ? '通过' : '待审签' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button link type="primary" @click="goToDetails(row)">详情</el-button>
          <el-button link type="primary">升级</el-button>
          <el-button link type="primary">相关人员</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Create Module Dialog -->
    <el-dialog
      v-model="dialogVisible"
      title="创建模块"
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
          <el-button type="primary" @click="submitCreate">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { useGlobalFilterStore } from '@/store/globalFilter'
import { createTestFunction, getTestFunctions, getTestBaseWithLimit } from '@/api/designer'
import { Delete } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const filterStore = useGlobalFilterStore()

// Dummy options for filter
const modelOptions = [
  { value: 'model1', label: 'Model A' },
  { value: 'model2', label: 'Model B' }
]
const professionOptions = [
  { value: 'prof1', label: '飞控' },
  { value: 'prof2', label: '动力' }
]

const subsystemData: Record<string, any[]> = {
  'prof1': [ 
    { value: 'sub_fc_1', label: '主飞控' },
    { value: 'sub_fc_2', label: '自动飞控' }
  ],
  'prof2': [ 
    { value: 'sub_power_2', label: '燃油系统' }
  ]
}

const subsystemOptions = computed(() => {
  let currentProf = filterStore.profession
  if (Array.isArray(currentProf) && currentProf.length > 0) {
    currentProf = currentProf[0]
  }
  
  return subsystemData[currentProf as string] || [] 
})

const tableData = ref<any[]>([])

const fetchData = async () => {
    try {
        const res: any = await getTestBaseWithLimit({
            model: filterStore.model,
            profession: filterStore.profession,
            subsystem: filterStore.subsystem
        })

        let testBaseId = 0;
        if (Number.isInteger(res)) {
            testBaseId = res
        } else if (res && Number.isInteger(res.data)) {
            testBaseId = res.data
        } else {
            testBaseId = 0
        }

        // Pass filter params from store
        const res1: any = await getTestFunctions({
            testBaseId: testBaseId
        })

        if (Array.isArray(res1)) {
            tableData.value = res1;
        } else if (res1 && Array.isArray(res1.data)) {
            tableData.value = res1.data;
        } else {
            tableData.value = [];
        }
        
    } catch (e) {
        console.error(e)
        tableData.value = []
    }
}

const handleFilterChange = () => {
    fetchData()
}

const goToDetails = (row: any) => {
    router.push({ name: 'ModuleOrchestration', params: { funId: row.funId } })
}

// Dialog Logic
const dialogVisible = ref(false)
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

const openCreateDialog = () => {
    dialogVisible.value = true
}

const addTechFile = () => form.techFiles.push({ value: '' })
const removeTechFile = (index: number) => form.techFiles.splice(index, 1)

const addDevice = () => form.devices.push({ value: '' })
const removeDevice = (index: number) => form.devices.splice(index, 1)

const submitCreate = async () => {
    try {
        const res: any = await getTestBaseWithLimit({
            model: filterStore.model,
            profession: filterStore.profession,
            subsystem: filterStore.subsystem
        })

        let testBaseId = 0;
        if (Number.isInteger(res)) {
            testBaseId = res
        } else if (res && Number.isInteger(res.data)) {
            testBaseId = res.data
        } else {
            testBaseId = 0
        }

        await createTestFunction({
            funName: form.funName,
            num: Number(form.num),
            testBaseId: testBaseId, 
            planeEffectMin: form.planeEffectMin,
            planeEffectMax: form.planeEffectMax,
            versionDescription: form.versionDescription,
            military: form.military
        })
        ElMessage.success('Created successfully')
        dialogVisible.value = false
        fetchData()
    } catch (e) {
        // Error handled in interceptor
    }
}

onMounted(() => {
    fetchData()
})
</script>

<style scoped lang="scss">
.global-filter {
  display: flex;
  gap: 10px;
  align-items: center;
  flex-wrap: wrap;
  background: white;
  padding: 10px;
  border-radius: 4px;

  .filter-actions {
      margin-left: auto;
      display: flex;
      gap: 10px;
  }
}

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
