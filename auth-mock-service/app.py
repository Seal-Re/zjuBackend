# -*- coding: utf-8 -*-
"""
认证与用户权限 Mock 服务 - 仅用于本地/联调测试，返回测试数据。
接口规范与 OAuth2/User/RBAC 一致。
"""
import time
import uuid
from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app, origins=['*'], allow_headers=['*'])

def std_response(data=None, message='success', code=200):
    return jsonify({
        'code': code,
        'data': data,
        'message': message,
        'timestamp': int(time.time())
    })

# --------------- 测试数据 ---------------
MOCK_USERS = [
    {'id': '1', 'username': 'admin', 'name': '管理员', 'email': 'admin@test.com', 'enabled': True, 'createdAt': '2025-01-01T00:00:00Z'},
    {'id': '2', 'username': 'designer1', 'name': '设计员A', 'email': 'designer@test.com', 'enabled': True, 'createdAt': '2025-01-01T00:00:00Z'},
    {'id': '3', 'username': 'worker1', 'name': '执行员1', 'email': 'worker@test.com', 'enabled': True, 'createdAt': '2025-01-01T00:00:00Z'},
]
# 简单内存 Token 存储（仅测试）
TOKENS = {}  # token -> { user_id, expires }
MOCK_PASSWORD = '123456'

MOCK_ROLES = [
    {'id': 'r1', 'code': 'ADMIN', 'name': '系统管理员', 'description': '全部权限'},
    {'id': 'r2', 'code': 'DESIGNER', 'name': '设计人员', 'description': '设计域'},
    {'id': 'r3', 'code': 'EXECUTOR', 'name': '执行人员', 'description': '执行与计划'},
]
MOCK_PERMISSIONS = [
    {'id': 'p1', 'code': 'sys:user:add', 'name': '新增用户', 'resource': 'user'},
    {'id': 'p2', 'code': 'sys:user:edit', 'name': '编辑用户', 'resource': 'user'},
    {'id': 'p3', 'code': 'sys:file:delete', 'name': '删除文件', 'resource': 'file'},
    {'id': 'p4', 'code': 'designer:module:edit', 'name': '编辑模块', 'resource': 'designer'},
    {'id': 'p5', 'code': 'planner:plan:dispatch', 'name': '派工', 'resource': 'planner'},
]
USER_ROLES = {'1': ['r1'], '2': ['r2'], '3': ['r3']}  # user_id -> role_ids
ROLE_PERMISSIONS = {'r1': ['p1', 'p2', 'p3', 'p4', 'p5'], 'r2': ['p4'], 'r3': ['p5']}


# --------------- 1. 认证与令牌 ---------------
@app.route('/oauth/token', methods=['POST'])
def token():
    """获取令牌。grant_type=password 时传 username, password"""
    # Flask 3 中 get_json() 在非 application/json 请求下会抛 415，
    # 这里使用 silent=True 兼容 x-www-form-urlencoded。
    body = request.get_json(silent=True) or request.form.to_dict() or dict(request.args) or {}
    grant_type = body.get('grant_type', 'password')
    if grant_type == 'password':
        username = body.get('username') or request.form.get('username')
        password = body.get('password') or request.form.get('password')
        user = next((u for u in MOCK_USERS if u['username'] == username and MOCK_PASSWORD == (password or '')), None)
        if not user:
            return std_response(None, '用户名或密码错误', 401)
        access_token = str(uuid.uuid4())
        refresh_token = str(uuid.uuid4())
        TOKENS[access_token] = {'user_id': user['id'], 'expires': time.time() + 7200}
        return std_response({
            'access_token': access_token,
            'refresh_token': refresh_token,
            'token_type': 'Bearer',
            'expires_in': 7200,
            'scope': 'read write'
        })
    if grant_type == 'refresh_token':
        ref = body.get('refresh_token')
        if ref:
            access_token = str(uuid.uuid4())
            TOKENS[access_token] = {'user_id': '1', 'expires': time.time() + 7200}
            return std_response({'access_token': access_token, 'token_type': 'Bearer', 'expires_in': 7200})
    return std_response(None, 'unsupported grant_type', 400)


@app.route('/oauth/check_token', methods=['POST', 'GET'])
def check_token():
    """校验 Token"""
    token = request.form.get('token') or request.args.get('token') or _bearer_token()
    if not token or token not in TOKENS:
        return std_response(None, 'invalid or expired token', 401)
    info = TOKENS[token]
    if info['expires'] < time.time():
        return std_response(None, 'token expired', 401)
    user = next((u for u in MOCK_USERS if u['id'] == info['user_id']), MOCK_USERS[0])
    return std_response({
        'active': True,
        'user_name': user['username'],
        'user_id': user['id'],
        'exp': int(info['expires']),
        'scope': ['read', 'write']
    })


@app.route('/oauth/revoke', methods=['POST'])
def revoke():
    """撤销令牌"""
    body = request.get_json(silent=True) or request.form.to_dict() or {}
    token = body.get('token') or request.form.get('token') or _bearer_token()
    if token and token in TOKENS:
        del TOKENS[token]
    return std_response(None, 'revoked')


@app.route('/userinfo', methods=['GET'])
def userinfo():
    """获取当前用户信息（需 Bearer Token）"""
    token = _bearer_token()
    if not token or token not in TOKENS:
        return std_response(None, 'unauthorized', 401)
    info = TOKENS[token]
    user = next((u for u in MOCK_USERS if u['id'] == info['user_id']), MOCK_USERS[0])
    return std_response({
        'id': user['id'],
        'username': user['username'],
        'name': user['name'],
        'email': user.get('email'),
        'enabled': user.get('enabled', True),
        'sub': user['id'],
    })


def _bearer_token():
    auth = request.headers.get('Authorization')
    if auth and auth.startswith('Bearer '):
        return auth[7:].strip()
    return None


# --------------- 2. 用户管理 ---------------
@app.route('/api/v1/users', methods=['POST'])
def create_user():
    """注册/创建用户"""
    body = request.get_json() or {}
    uid = str(len(MOCK_USERS) + 1)
    MOCK_USERS.append({
        'id': uid,
        'username': body.get('username', 'user' + uid),
        'name': body.get('name', ''),
        'email': body.get('email', ''),
        'enabled': body.get('enabled', True),
        'createdAt': time.strftime('%Y-%m-%dT%H:%M:%SZ', time.gmtime()),
    })
    return std_response(MOCK_USERS[-1])


@app.route('/api/v1/users/<uid>', methods=['GET'])
def get_user(uid):
    u = next((x for x in MOCK_USERS if x['id'] == uid), None)
    if not u:
        return std_response(None, 'user not found', 404)
    return std_response(u)


@app.route('/api/v1/users/<uid>', methods=['PUT', 'PATCH'])
def update_user(uid):
    u = next((x for x in MOCK_USERS if x['id'] == uid), None)
    if not u:
        return std_response(None, 'user not found', 404)
    body = request.get_json() or {}
    for k in ('name', 'email', 'enabled'):
        if k in body:
            u[k] = body[k]
    return std_response(u)


@app.route('/api/v1/users/<uid>/password/reset', methods=['POST'])
def reset_password(uid):
    body = request.get_json() or {}
    new_password = body.get('newPassword') or body.get('password')
    if not new_password:
        return std_response(None, 'newPassword required', 400)
    return std_response({'userId': uid, 'message': 'password reset'})


# --------------- 3. RBAC ---------------
@app.route('/api/v1/roles', methods=['GET'])
def list_roles():
    return std_response(MOCK_ROLES)


@app.route('/api/v1/roles', methods=['POST'])
def create_role():
    body = request.get_json() or {}
    rid = 'r' + str(len(MOCK_ROLES) + 1)
    MOCK_ROLES.append({
        'id': rid,
        'code': body.get('code', 'ROLE_' + rid),
        'name': body.get('name', ''),
        'description': body.get('description', ''),
    })
    return std_response(MOCK_ROLES[-1])


@app.route('/api/v1/roles/<rid>/permissions', methods=['POST'])
def bind_role_permissions(rid):
    body = request.get_json() or {}
    perm_codes = body.get('permissions') or body.get('permissionCodes') or []
    ROLE_PERMISSIONS[rid] = perm_codes if isinstance(perm_codes[0], str) else [p.get('code') for p in perm_codes]
    return std_response({'roleId': rid, 'permissions': ROLE_PERMISSIONS.get(rid, [])})


@app.route('/api/v1/permissions', methods=['GET'])
def list_permissions():
    return std_response(MOCK_PERMISSIONS)


@app.route('/api/v1/users/<uid>/roles', methods=['GET'])
def get_user_roles(uid):
    role_ids = USER_ROLES.get(uid, [])
    roles = [r for r in MOCK_ROLES if r['id'] in role_ids]
    return std_response(roles)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
