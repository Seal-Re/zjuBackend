# 认证与用户权限 Mock 服务

仅用于本地/联调测试，模拟 OAuth2 + 用户与 RBAC 接口，返回测试数据。

## 启动

```bash
cd auth-mock-service
pip install -r requirements.txt
python app.py
```

服务地址：`http://localhost:5000`

## 测试账号

| 用户名   | 密码   | 说明     |
|----------|--------|----------|
| admin    | 123456 | 管理员   |
| designer1| 123456 | 设计员   |
| worker1  | 123456 | 执行员   |

## 接口与规范

与主项目 `API_DOCUMENTATION.md` 中「认证与用户权限服务」章节一致。
