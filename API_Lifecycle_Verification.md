# API Lifecycle Verification

## Phase 1: Designer Domain

### Task 1: Create Test Function
**URL**: `POST /designer/testFunction/add`
**Input**:
```json
{
  "funName": "Test Function 1",
  "num": 1001,
  "testBaseId": 1,
  "planeEffectMin": 1,
  "planeEffectMax": 100,
  "versionDescription": "Initial version",
  "military": false
}
```
**Verification**: Check `changeFlag` is 4 and `approveStatus` is 0 in DB.

### Task 2: Build Function Hierarchy
**URL**: `POST /designer/testFunctionModule/add`
**Input**:
```json
{
  "moduleName": "Module 1",
  "funId": 1
}
```

**URL**: `POST /designer/testFunctionCase/add`
**Input**:
```json
{
  "caseName": "Case 1",
  "moduleId": 1
}
```

**URL**: `POST /designer/testFunctionStep/add`
**Input**:
```json
{
  "stepName": "Step 1",
  "caseId": 1
}
```

### Task 3: Function Approval
**URL**: `POST /designer/testFunction/check`
**Input**:
```
funId=1209&checkWorker=worker1&level=0
```

**URL**: `POST /designer/testFunction/check`
**Input**:
```
funId=1209&checkWorker=worker1&level=1
```

**URL**: `POST /designer/testFunction/check`
**Input**:
```
funId=1209&checkWorker=worker1&level=2
```

**URL**: `POST /designer/testFunction/check`
**Input**:
```
funId=1209&checkWorker=worker1&level=3
```

**URL**: `POST /designer/testFunction/check`
**Input**:
```
funId=1209&checkWorker=worker1&level=4
```

**URL**: `POST /designer/testFunction/check`
**Input**:
```
funId=1209&checkWorker=worker1&level=5
```

### Task 4: Create Test Suite & Binding
**URL**: `POST /designer/testSuite/add`
**Input**:
```json
{
  "suiteName": "Suite 1",
  "entityStructId": 1,
  "testBaseId": 1,
  "planeEffectMin": 0,
  "planeEffectMax": 9999,
  "funIds": [1209]
}
```

**URL**: `POST /functionSuite/createFunctionSuite`
**Input**:
```json
{
  "suiteId": 347,
  "testFunctions":[
    {
        "funId": 1208,
        "funName": "Test Function 1",
        "num": 1001,
        "testBaseId": 1,
        "planeEffectMin": 1,
        "planeEffectMax": 100,
        "versionDescription": "Initial version",
        "military": false
    },
    {
        "funId": 1209,
        "funName": "Test Function 1",
        "num": 1001,
        "testBaseId": 1,
        "planeEffectMin": 1,
        "planeEffectMax": 100,
        "versionDescription": "Initial version",
        "military": false
    }
  ]
}
```

**Verification**: Check `test_suite` created and `function_suite` records inserted.

### Task 5: Suite Approval
**URL**: `POST /designer/testSuite/check`
**Input**:
```
suiteId=1&checkWorker=worker1&level=0
```

## Phase 2: Execution Domain

### Task 6: Create Test Plan & Snapshot
**URL**: `POST /planner/plan/createTestPlan`
**Input**:
```json
{
  "planName": "Plan 1",
  "entityId": 1,
  "suiteId": 1,
  "planStartTime": "2023-10-27T10:00:00.000+00:00",
  "planEndTime": "2023-10-28T10:00:00.000+00:00"
}
```
**Verification**: Check `test_plan` created, `exe_function` and `exe_step` records created via snapshot.

## Phase 3: Execution Control

### Task 7: Dispatch
**URL**: `GET /planner/plan/dispatch/{planId}`

### Task 8: Get Task
**URL**: `POST /exeFunction/getinexe/{functionId}`

### Task 9: Pause Task
**URL**: `POST /exeFunction/pause/{exeFunctionId}`
**Verification**: `test_plan.status` = 3, `exe_function.exe_status` = 3.

#### other Operate
**URL**: `POST /exeFunction/stepOperate`
input:@RequestBody Map<String, String> params

### Task 10: Start Task
**URL**: `POST /exeFunction/do`
**Input**:
```json
{
  "exeStepId": "sadsfsdf",
  "deviceId": "sdfsdf",
  "command": "sdfsdf",
  "url": "http:fffff" 
}