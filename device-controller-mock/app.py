"""
本地设备管理服务 Mock：与 Fastop DEVICE_CONTROLLER_URL 联调。
GET /topics 返回与 Fastop 约定一致的外层结构 { code, msg, data: { topics, example } }。
"""
from flask import Flask, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


@app.get("/topics")
def topics():
    payload = {
        "code": 200,
        "msg": "操作成功",
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
    app.run(host="0.0.0.0", port=5001, debug=True)
