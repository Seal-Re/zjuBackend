"""
本地设备管理服务 Mock：与 Fastop DEVICE_CONTROLLER_URL 联调。
GET /topics 返回与 Fastop 约定一致的外层结构 { code, message, data: { topics, example } }。
"""
from flask import Flask, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


@app.get("/topics")
def topics():
    payload = {
        "code": 200,
        # 字段名与 fastop 后端 ResponseBody / auth-mock-service 统一为 message
        "message": "操作成功",
        "data": {
            "topics": [
                "ems_topic_switchOn",
                "ems_topic_switchOff",
                "ems_topic_setVoltage",
                "131613"
            ],
            "example": {
                "deviceCode": "",
                "powerLevel": 0,
                "meta": {"channel": 1, "note": ""},
            },
        },
        "totalNum": 0,
    }
    return jsonify(payload)


@app.get("/health")
def health():
    return jsonify({"status": "ok"})


if __name__ == "__main__":
    # debug=False 关闭 werkzeug 交互调试器避免 RCE；仅监听回环口
    app.run(host="127.0.0.1", port=5001, debug=False)
