#!/usr/bin/env bash
# fastop 一键启动脚本（适用于已 docker load 完镜像、没有 docker-compose 的环境）
# 用法：
#   chmod +x start.sh
#   ./start.sh          # 启动
#   ./start.sh stop     # 停止并删除容器
#   ./start.sh restart  # 重启
#   ./start.sh logs     # 查看日志

set -e

NETWORK=fastop-network
BACKEND=fastop-backend
FRONTEND=fastop-frontend
BACKEND_IMAGE=fastop-backend:latest
FRONTEND_IMAGE=fastop-frontend:latest

# 部署目标主机 IP（IDP / MySQL / 前端访问地址）
HOST_IP=192.168.1.97

start() {
    # 创建 docker 网络（前端 nginx 通过容器名 fastop-backend 访问后端）
    docker network inspect "$NETWORK" >/dev/null 2>&1 || docker network create "$NETWORK"

    # 清理同名旧容器
    docker rm -f "$BACKEND" "$FRONTEND" 2>/dev/null || true

    echo "==> 启动后端 $BACKEND ..."
    docker run -d \
        --name "$BACKEND" \
        --network "$NETWORK" \
        --restart unless-stopped \
        -p 10001:10001 \
        -e FASTOP_DATASOURCE_URL="jdbc:mysql://${HOST_IP}:3306/autosys_1014" \
        -e FASTOP_DATASOURCE_USERNAME=root \
        -e FASTOP_DATASOURCE_PASSWORD='fastop@123' \
        -e FASTOP_FRONTEND_ORIGIN="http://${HOST_IP}:5173" \
        -e FASTOP_POST_LOGIN_REDIRECT="http://${HOST_IP}:5173/" \
        "$BACKEND_IMAGE"

    echo "==> 启动前端 $FRONTEND ..."
    docker run -d \
        --name "$FRONTEND" \
        --network "$NETWORK" \
        --restart unless-stopped \
        -p 5173:5173 \
        "$FRONTEND_IMAGE"

    echo
    echo "==> 启动完成"
    docker ps --filter "name=$BACKEND" --filter "name=$FRONTEND" \
        --format 'table {{.Names}}\t{{.Status}}\t{{.Ports}}'
    echo
    echo "前端: http://${HOST_IP}:5173"
    echo "后端: http://${HOST_IP}:10001/fastop"
}

stop() {
    echo "==> 停止并删除容器 ..."
    docker rm -f "$BACKEND" "$FRONTEND" 2>/dev/null || true
    echo "==> 完成"
}

logs() {
    docker logs -f --tail=200 "$BACKEND" &
    docker logs -f --tail=200 "$FRONTEND" &
    wait
}

case "${1:-start}" in
    start)   start ;;
    stop)    stop ;;
    restart) stop; start ;;
    logs)    logs ;;
    *)       echo "用法: $0 {start|stop|restart|logs}"; exit 1 ;;
esac
