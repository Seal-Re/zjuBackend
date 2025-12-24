<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" type="border-card">

      <!-- Tab 1: Function Designer -->
      <el-tab-pane label="Function Designer" name="function">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>Test Functions</span>
              <el-button type="primary" @click="showCreateFunctionDialog = true">Create Function</el-button>
            </div>
          </template>

          <el-table :data="functionList" style="width: 100%" border>
            <el-table-column prop="funId" label="ID" width="80" />
            <el-table-column prop="funName" label="Name" />
            <el-table-column prop="approveStatus" label="Status" width="100" />
            <el-table-column label="Actions" width="350">
              <template #default="scope">
                <el-button size="small" @click="openStructureDialog(scope.row)">Add Structure</el-button>
                <el-button size="small" type="success" @click="openCheckDialog('function', scope.row)">Approve</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>

      <!-- Tab 2: Suite Designer -->
      <el-tab-pane label="Suite Designer" name="suite">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>Test Suites</span>
              <el-button type="primary" @click="showCreateSuiteDialog = true">Create Suite</el-button>
            </div>
          </template>

          <el-table :data="suiteList" style="width: 100%" border>
            <el-table-column prop="suiteId" label="ID" width="80" />
            <el-table-column prop="suiteName" label="Name" />
            <el-table-column label="Actions" width="300">
              <template #default="scope">
                <el-button size="small" @click="openBindDialog(scope.row)">Bind Functions</el-button>
                <el-button size="small" type="success" @click="openCheckDialog('suite', scope.row)">Approve</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>

      <!-- Tab 3: Execution Planner -->
      <el-tab-pane label="Plan Execution" name="planner">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>Test Plans</span>
              <el-button type="primary" @click="showCreatePlanDialog = true">Create Plan</el-button>
            </div>
          </template>

          <el-table :data="planList" style="width: 100%" border>
            <el-table-column prop="planId" label="ID" width="200" />
            <el-table-column prop="planName" label="Name" />
            <el-table-column prop="status" label="Status" width="100" />
            <el-table-column label="Control" width="300">
              <template #default="scope">
                <el-button-group>
                  <el-button size="small" @click="handleDispatch(scope.row.planId)">Dispatch</el-button>
                  <el-button size="small" type="success" @click="handleStart(scope.row.planId)">Start</el-button>
                  <el-button size="small" type="warning" @click="handlePause(scope.row.planId)">Pause</el-button>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>
    </el-tabs>

    <!-- Dialog: Create Function -->
    <el-dialog v-model="showCreateFunctionDialog" title="Create Test Function">
      <el-form :model="functionForm" label-width="120px">
        <el-form-item label="Name">
          <el-input v-model="functionForm.funName" />
        </el-form-item>
        <el-form-item label="Number">
          <el-input-number v-model="functionForm.num" />
        </el-form-item>
        <el-form-item label="Test Base ID">
          <el-input-number v-model="functionForm.testBaseId" />
        </el-form-item>
        <el-form-item label="Effect Min">
          <el-input-number v-model="functionForm.planeEffectMin" />
        </el-form-item>
        <el-form-item label="Effect Max">
          <el-input-number v-model="functionForm.planeEffectMax" />
        </el-form-item>
        <el-form-item label="Description">
          <el-input v-model="functionForm.versionDescription" />
        </el-form-item>
        <el-form-item label="Military">
          <el-switch v-model="functionForm.military" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createFunction" type="primary">Create</el-button>
      </template>
    </el-dialog>

    <!-- Dialog: Add Structure (Module/Case/Step) -->
    <el-dialog v-model="showStructureDialog" title="Add Structure">
      <el-tabs type="card">
        <el-tab-pane label="Add Module">
           <el-form :model="moduleForm">
             <el-form-item label="Function ID">
               <el-input v-model="moduleForm.funId" disabled />
             </el-form-item>
             <el-form-item label="Module Name">
               <el-input v-model="moduleForm.moduleName" />
             </el-form-item>
             <el-button @click="createModule" type="primary">Add Module</el-button>
           </el-form>
        </el-tab-pane>
        <el-tab-pane label="Add Case">
           <el-form :model="caseForm">
             <el-form-item label="Module ID">
               <el-input-number v-model="caseForm.moduleId" placeholder="Enter Module ID" />
             </el-form-item>
             <el-form-item label="Case Name">
               <el-input v-model="caseForm.caseName" />
             </el-form-item>
             <el-button @click="createCase" type="primary">Add Case</el-button>
           </el-form>
        </el-tab-pane>
        <el-tab-pane label="Add Step">
           <el-form :model="stepForm">
             <el-form-item label="Case ID">
               <el-input-number v-model="stepForm.caseId" placeholder="Enter Case ID" />
             </el-form-item>
             <el-form-item label="Step Name">
               <el-input v-model="stepForm.stepName" />
             </el-form-item>
             <el-button @click="createStep" type="primary">Add Step</el-button>
           </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <!-- Dialog: Approval -->
    <el-dialog v-model="showCheckDialog" title="Approve">
      <el-form>
        <el-form-item label="Worker Name">
          <el-input v-model="checkForm.checkWorker" />
        </el-form-item>
        <el-form-item label="Level (0-5)">
          <el-input-number v-model="checkForm.level" :min="0" :max="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="submitCheck" type="primary">Submit Approval</el-button>
      </template>
    </el-dialog>

    <!-- Dialog: Create Suite -->
    <el-dialog v-model="showCreateSuiteDialog" title="Create Test Suite">
      <el-form :model="suiteForm" label-width="120px">
        <el-form-item label="Name">
          <el-input v-model="suiteForm.suiteName" />
        </el-form-item>
        <el-form-item label="Entity Struct ID">
          <el-input-number v-model="suiteForm.entityStructId" />
        </el-form-item>
        <el-form-item label="Test Base ID">
          <el-input-number v-model="suiteForm.testBaseId" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createSuite" type="primary">Create</el-button>
      </template>
    </el-dialog>

    <!-- Dialog: Bind Function to Suite -->
    <el-dialog v-model="showBindDialog" title="Bind Functions to Suite" width="60%">
      <el-transfer
        v-model="selectedFunctionIds"
        :data="transferData"
        :titles="['Available', 'Selected']"
      />
      <template #footer>
        <span class="dialog-footer">
           <el-button @click="bindFunctions" type="primary" style="margin: 10px;">Bind Selected</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- Dialog: Create Plan -->
    <el-dialog v-model="showCreatePlanDialog" title="Create Test Plan">
      <el-form :model="planForm" label-width="120px">
        <el-form-item label="Plan Name">
          <el-input v-model="planForm.planName" />
        </el-form-item>
        <el-form-item label="Entity ID">
          <el-input-number v-model="planForm.entityId" />
        </el-form-item>
        <el-form-item label="Suite ID">
          <el-input-number v-model="planForm.suiteId" />
        </el-form-item>
        <el-form-item label="Start Time">
          <el-date-picker v-model="planForm.planStartTime" type="datetime" />
        </el-form-item>
        <el-form-item label="End Time">
          <el-date-picker v-model="planForm.planEndTime" type="datetime" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createPlan" type="primary">Create</el-button>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { ElMessage } from 'element-plus';
import { designerApi, plannerApi } from './api';

const activeTab = ref('function');

// --- Data Lists ---
const functionList = ref([]);
const suiteList = ref([]);
const planList = ref([]);

// --- Forms & States ---

// Function Creation
const showCreateFunctionDialog = ref(false);
const functionForm = reactive({
  funName: 'Test Function 1',
  num: 1001,
  testBaseId: 1,
  planeEffectMin: 1,
  planeEffectMax: 100,
  versionDescription: 'Initial version',
  military: false
});

// Structure Creation
const showStructureDialog = ref(false);
const moduleForm = reactive({ funId: null, moduleName: 'Module 1' });
const caseForm = reactive({ moduleId: null, caseName: 'Case 1' });
const stepForm = reactive({ caseId: null, stepName: 'Step 1' });

// Check/Approval
const showCheckDialog = ref(false);
const checkTarget = ref({ type: '', id: null }); // type: 'function' or 'suite'
const checkForm = reactive({ checkWorker: 'worker1', level: 0 });

// Suite Creation
const showCreateSuiteDialog = ref(false);
const suiteForm = reactive({
  suiteName: 'Suite 1',
  entityStructId: 1,
  testBaseId: 1,
  planeEffectMin: 0,
  planeEffectMax: 9999
});

// Bind Function
const showBindDialog = ref(false);
const currentSuiteId = ref(null);
const selectedFunctionIds = ref([]);
const transferData = computed(() => {
  return functionList.value.map(f => ({
    key: f.funId,
    label: `${f.funName} (ID: ${f.funId})`,
    disabled: false
  }));
});

// Plan Creation
const showCreatePlanDialog = ref(false);
const planForm = reactive({
  planName: 'Plan 1',
  entityId: 1,
  suiteId: 1,
  planStartTime: '',
  planEndTime: ''
});

// --- Methods ---

const loadFunctions = async () => {
  try {
    const data = await designerApi.listFunctions();
    functionList.value = data || [];
  } catch (e) { console.error(e); }
};

const loadSuites = async () => {
  try {
    const data = await designerApi.listSuites();
    suiteList.value = data || [];
  } catch (e) { console.error(e); }
};

const loadPlans = async () => {
  try {
    const data = await plannerApi.listPlans();
    planList.value = data || [];
  } catch (e) { console.error(e); }
};

// Function Actions
const createFunction = async () => {
  try {
    await designerApi.addFunction(functionForm);
    ElMessage.success('Function Created');
    showCreateFunctionDialog.value = false;
    loadFunctions();
  } catch (e) {}
};

const openStructureDialog = (row) => {
  moduleForm.funId = row.funId;
  showStructureDialog.value = true;
};

const createModule = async () => {
  await designerApi.addModule(moduleForm);
  ElMessage.success('Module Added');
};

const createCase = async () => {
  await designerApi.addCase(caseForm);
  ElMessage.success('Case Added');
};

const createStep = async () => {
  await designerApi.addStep(stepForm);
  ElMessage.success('Step Added');
};

// Approval Actions
const openCheckDialog = (type, row) => {
  checkTarget.value.type = type;
  checkTarget.value.id = type === 'function' ? row.funId : row.suiteId;
  checkForm.level = 0; // Reset
  showCheckDialog.value = true;
};

const submitCheck = async () => {
  try {
    const { type, id } = checkTarget.value;
    const { checkWorker, level } = checkForm;
    if (type === 'function') {
      await designerApi.checkFunction(id, checkWorker, level);
    } else {
      await designerApi.checkSuite(id, checkWorker, level);
    }
    ElMessage.success(`${type} Approved (Level ${level})`);
    showCheckDialog.value = false;
    // Refresh lists
    loadFunctions();
    loadSuites();
  } catch (e) {}
};

// Suite Actions
const createSuite = async () => {
  try {
    await designerApi.addSuite(suiteForm);
    ElMessage.success('Suite Created');
    showCreateSuiteDialog.value = false;
    loadSuites();
  } catch (e) {}
};

const openBindDialog = (row) => {
  currentSuiteId.value = row.suiteId;
  selectedFunctionIds.value = [];
  showBindDialog.value = true;
};

const bindFunctions = async () => {
  try {
    // Find the full function objects for the selected IDs
    const functionsToBind = functionList.value.filter(f => selectedFunctionIds.value.includes(f.funId));

    await designerApi.bindFunctionToSuite({
      suiteId: currentSuiteId.value,
      testFunctions: functionsToBind
    });
    ElMessage.success('Functions Bound to Suite');
    showBindDialog.value = false;
  } catch (e) {}
};

// Plan Actions
const createPlan = async () => {
  try {
    await plannerApi.createPlan(planForm);
    ElMessage.success('Plan Created');
    showCreatePlanDialog.value = false;
    loadPlans();
  } catch (e) {}
};

const handleDispatch = async (id) => {
  try {
    await plannerApi.dispatchPlan(id);
    ElMessage.success('Plan Dispatched');
    loadPlans();
  } catch (e) {}
};

const handleStart = async (id) => {
  try {
    await plannerApi.startPlan(id);
    ElMessage.success('Plan Started');
    loadPlans();
  } catch (e) {}
};

const handlePause = async (id) => {
  try {
    await plannerApi.pausePlan(id);
    ElMessage.warning('Plan Paused');
    loadPlans();
  } catch (e) {}
};

// Initial Load
onMounted(() => {
  loadFunctions();
  loadSuites();
  loadPlans();
});
</script>

<style>
.app-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.box-card {
  margin-bottom: 20px;
}
</style>
