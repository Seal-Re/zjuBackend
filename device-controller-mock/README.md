# 设备管理服务 Mock

用于本地验证：配置 `fastop.integration.device-controller-url: http://localhost:5001`，Fastop 将请求 `GET /topics`。

## 启动

```bash
cd device-controller-mock
pip install -r requirements.txt
python app.py
```

服务监听 `http://localhost:5001`。

## 响应约定

`data.topics`：topic 字符串列表（与 EMS `eventType` 一致）。

`data.example`：示例 JSON 对象；前端据此生成可填字段，设计态保存 `step_command_example` / `step_command_params`。
