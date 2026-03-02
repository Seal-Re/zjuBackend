# 设备管理 API 规范

与现有后端风格一致，受保护接口需在 Header 中携带：`Authorization: Bearer {access_token}`。

## 响应结构

```json
{
  "code": 200,
  "data": { ... },
  "message": "success",
  "timestamp": 1709423600
}
```

## 设备管理 (Device Management)

设备主数据 CRUD，与测试功能 `device_pool`、执行步骤设备字段关联。路径与 Fastop 同域时使用相对路径（如 /devices），与业务后端共用 context-path。

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /devices/list | 设备列表（支持分页与筛选） |
| GET | /devices/{id} | 获取单个设备详情 |
| POST | /devices | 新增设备 |
| PUT | /devices/{id} | 更新设备 |
| DELETE | /devices/{id} | 删除设备（软删或硬删视实现而定） |

### 请求/响应示例

**GET /devices/list**

Query: `page=1&size=10&name=&type=&status=`

Response `data`:
```json
{
  "list": [
    {
      "id": "1",
      "code": "DEV-001",
      "name": "工控机A",
      "type": "industrial_pc",
      "status": 1,
      "description": "",
      "createdAt": "2025-01-01T00:00:00Z",
      "updatedAt": "2025-01-01T00:00:00Z"
    }
  ],
  "total": 1
}
```

**POST /devices**  
Request body:
```json
{
  "code": "DEV-001",
  "name": "工控机A",
  "type": "industrial_pc",
  "description": "可选描述",
  "status": 1
}
```

**PUT /devices/{id}**  
Request body: 同上，仅传需更新字段。

**设备状态 (status)**：0-禁用，1-可用，2-维护中。

**设备类型 (type)**：由业务约定，如 `industrial_pc`、`sensor`、`actuator` 等。

## 与测试模块/步骤的关联

- 测试功能表 `test_function` 的 `device_pool` 存储设备 ID 或编码的 JSON 数组（如 `[["device1"],["device2"]]`），在编辑模块时通过「设备选择」回写。
- 执行步骤表 `exe_step` 中步骤执行指令可携带 `deviceId`，与设备主数据通过 `id` 或 `code` 关联。
- 实现时：设备列表接口供模块编排、步骤编辑页下拉选择；无需单独「绑定」接口时，在保存 test_function / exe_step 时写入 device_pool / deviceId 即可。
