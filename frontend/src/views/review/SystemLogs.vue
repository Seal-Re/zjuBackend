<template>
  <div class="system-logs">
    <h2 class="page-title">系统日志</h2>
    <el-tabs v-model="activeTab">
      <el-tab-pane label="操作日志" name="operation">
        <div class="filter-bar">
          <el-input
            v-model="opFilters.operatorName"
            placeholder="操作人"
            clearable
            style="width: 140px"
            @keyup.enter="loadOperationLogs"
          />
          <el-select v-model="opFilters.module" placeholder="模块" clearable style="width: 140px">
            <el-option label="测试计划" value="测试计划" />
            <el-option label="模块库" value="模块库" />
            <el-option label="清单库" value="清单库" />
            <el-option label="测试执行" value="测试执行" />
          </el-select>
          <el-select v-model="opFilters.action" placeholder="动作" clearable style="width: 120px">
            <el-option label="创建" value="创建" />
            <el-option label="修改" value="修改" />
            <el-option label="删除" value="删除" />
            <el-option label="派发" value="派发" />
            <el-option label="开始" value="开始" />
            <el-option label="暂停" value="暂停" />
          </el-select>
          <el-date-picker
            v-model="opTimeRange"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="YYYY-MM-DD HH:mm:ss"
            :default-time="[new Date(2000, 0, 1, 0, 0, 0), new Date(2000, 0, 1, 23, 59, 59)]"
            style="width: 360px"
          />
          <el-button type="primary" @click="loadOperationLogs">查询</el-button>
        </div>
        <div class="table-wrap">
          <el-table v-loading="opLoading" :data="operationList" border stripe class="data-table">
            <el-table-column type="index" label="序号" width="60" align="center" />
            <el-table-column prop="operatorName" label="操作人" width="100" />
            <el-table-column prop="module" label="模块" width="100" />
            <el-table-column prop="action" label="动作" width="80" />
            <el-table-column prop="targetType" label="对象类型" width="90" />
            <el-table-column prop="targetId" label="对象ID" width="120" show-overflow-tooltip />
            <el-table-column prop="detail" label="详情" min-width="200" show-overflow-tooltip />
            <el-table-column prop="createTime" label="时间" width="170">
              <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
            </el-table-column>
          </el-table>
          <el-pagination
            v-model:current-page="opPage"
            v-model:page-size="opSize"
            :total="opTotal"
            :page-sizes="[10, 20, 50]"
            layout="total, sizes, prev, pager, next"
            class="pagination"
            @size-change="loadOperationLogs"
            @current-change="loadOperationLogs"
          />
        </div>
      </el-tab-pane>
      <el-tab-pane label="执行日志" name="execution">
        <div class="filter-bar">
          <el-input
            v-model="exeFilters.stepId"
            placeholder="步骤ID"
            clearable
            style="width: 200px"
            @keyup.enter="loadExeLogs"
          />
          <el-input
            v-model="exeFilters.planId"
            placeholder="计划ID"
            clearable
            style="width: 200px"
            @keyup.enter="loadExeLogs"
          />
          <el-date-picker
            v-model="exeTimeRange"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="YYYY-MM-DD HH:mm:ss"
            :default-time="[new Date(2000, 0, 1, 0, 0, 0), new Date(2000, 0, 1, 23, 59, 59)]"
            style="width: 360px"
          />
          <el-button type="primary" @click="loadExeLogs">查询</el-button>
        </div>
        <div class="table-wrap">
          <el-table v-loading="exeLoading" :data="exeLogList" border stripe class="data-table">
            <el-table-column type="index" label="序号" width="60" align="center" />
            <el-table-column prop="logId" label="日志ID" width="280" show-overflow-tooltip />
            <el-table-column prop="stepId" label="步骤ID" width="120" show-overflow-tooltip />
            <el-table-column prop="planId" label="计划ID" width="120" show-overflow-tooltip />
            <el-table-column prop="content" label="内容" min-width="200" show-overflow-tooltip />
            <el-table-column prop="createTime" label="时间" width="170">
              <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
            </el-table-column>
          </el-table>
          <el-pagination
            v-model:current-page="exePage"
            v-model:page-size="exeSize"
            :total="exeTotal"
            :page-sizes="[10, 20, 50]"
            layout="total, sizes, prev, pager, next"
            class="pagination"
            @size-change="loadExeLogs"
            @current-change="loadExeLogs"
          />
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { getExeLogList, getOperationLogList } from '@/api/log'

const activeTab = ref<'operation' | 'execution'>('operation')

const opLoading = ref(false)
const operationList = ref<any[]>([])
const opPage = ref(1)
const opSize = ref(20)
const opTotal = ref(0)
const opFilters = reactive({ operatorName: '', module: '', action: '' })
const opTimeRange = ref<[string, string] | null>(null)

const exeLoading = ref(false)
const exeLogList = ref<any[]>([])
const exePage = ref(1)
const exeSize = ref(20)
const exeTotal = ref(0)
const exeFilters = reactive({ stepId: '', planId: '' })
const exeTimeRange = ref<[string, string] | null>(null)

function formatTime(t: string | undefined) {
  if (!t) return '-'
  const d = new Date(t)
  return isNaN(d.getTime()) ? t : d.toLocaleString('zh-CN', { hour12: false })
}

async function loadOperationLogs() {
  opLoading.value = true
  try {
    const [startTime, endTime] = opTimeRange.value || [undefined, undefined]
    // 请求层拦截器已返回 res.data，故 res 即为 { list, total } 或兼容结构
    const res: any = await getOperationLogList({
      operatorName: opFilters.operatorName || undefined,
      module: opFilters.module || undefined,
      action: opFilters.action || undefined,
      startTime,
      endTime,
      page: opPage.value,
      size: opSize.value
    })
    const data = res && typeof res === 'object' ? res : {}
    operationList.value = Array.isArray(data.list) ? data.list : (Array.isArray(data) ? data : [])
    opTotal.value = typeof data.total === 'number' ? data.total : (Array.isArray(data) ? data.length : 0)
  } catch (e) {
    operationList.value = []
    opTotal.value = 0
    console.warn('操作日志加载失败（可能未建 operation_log 表）:', e)
  } finally {
    opLoading.value = false
  }
}

async function loadExeLogs() {
  exeLoading.value = true
  try {
    const [startTime, endTime] = exeTimeRange.value || [undefined, undefined]
    const res: any = await getExeLogList({
      stepId: exeFilters.stepId || undefined,
      planId: exeFilters.planId || undefined,
      startTime,
      endTime,
      page: exePage.value,
      size: exeSize.value
    })
    const data = res && typeof res === 'object' ? res : {}
    exeLogList.value = Array.isArray(data.list) ? data.list : (Array.isArray(data) ? data : [])
    exeTotal.value = typeof data.total === 'number' ? data.total : (Array.isArray(data) ? data.length : 0)
  } catch (e) {
    exeLogList.value = []
    exeTotal.value = 0
    console.warn('执行日志加载失败:', e)
  } finally {
    exeLoading.value = false
  }
}

watch(activeTab, (name) => {
  if (name === 'execution') loadExeLogs()
  else loadOperationLogs()
})

onMounted(() => {
  loadOperationLogs()
})
</script>

<style scoped>
.system-logs .page-title { margin-top: 0; }
.filter-bar { margin-bottom: 16px; }
.table-wrap { margin-top: 12px; }
.pagination { margin-top: 16px; justify-content: flex-end; }
</style>
