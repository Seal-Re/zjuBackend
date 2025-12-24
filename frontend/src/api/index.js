import axios from 'axios';
import { ElMessage } from 'element-plus';

// Create Axios instance
const service = axios.create({
  baseURL: '/api', // Configure your proxy or backend URL here
  timeout: 10000
});

// Response Interceptor
service.interceptors.response.use(
  response => {
    // Backend returns { code: 200, msg: "...", data: ... }
    const res = response.data;

    if (res.code !== 200) {
      ElMessage.error(res.msg || 'Error');
      return Promise.reject(new Error(res.msg || 'Error'));
    } else {
      // Return the actual data payload
      return res.data;
    }
  },
  error => {
    console.error('Request Error:', error);
    ElMessage.error(error.message || 'Request Failed');
    return Promise.reject(error);
  }
);

export const designerApi = {
  // --- Test Function ---
  addFunction: (data) => service.post('/designer/testFunction/add', data),
  listFunctions: () => service.get('/designer/testFunction/listAll'),
  checkFunction: (funId, checkWorker, level) =>
    service.post('/designer/testFunction/check', null, {
      params: { funId, checkWorker, level }
    }),

  // --- Hierarchy (Module/Case/Step) ---
  addModule: (data) => service.post('/designer/module/add', data),
  addCase: (data) => service.post('/designer/case/add', data),
  addStep: (data) => service.post('/designer/step/add', data),

  // --- Test Suite ---
  addSuite: (data) => service.post('/designer/testSuite/add', data),
  listSuites: () => service.get('/designer/testSuite/listAll'),
  checkSuite: (suiteId, checkWorker, level) =>
    service.post('/designer/testSuite/check', null, {
      params: { suiteId, checkWorker, level }
    }),

  // Bind Functions to Suite
  bindFunctionToSuite: (data) => service.post('/functionSuite/createFunctionSuite', data),
};

export const plannerApi = {
  // --- Test Plan ---
  createPlan: (data) => service.post('/planner/plan/createTestPlan', data),
  listPlans: () => service.get('/planner/plan/listAll'),

  // Execution Control
  dispatchPlan: (planId) => service.get(`/planner/plan/dispatch/${planId}`),
  startPlan: (planId) => service.post(`/planner/plan/start/${planId}`),
  pausePlan: (planId) => service.post(`/planner/plan/pause/${planId}`),
};
