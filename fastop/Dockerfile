# ─── Runtime ──────────────────────────────────────────────────────────────
FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive \
    TZ=Asia/Shanghai

RUN sed -i 's@//archive.ubuntu.com@//mirrors.aliyun.com@g; s@//security.ubuntu.com@//mirrors.aliyun.com@g' /etc/apt/sources.list \
 && apt-get update \
 && apt-get install -y --no-install-recommends \
        openjdk-8-jre-headless \
        ca-certificates \
        tzdata \
 && rm -rf /var/lib/apt/lists/*

RUN groupadd --system fastop && useradd --system --gid fastop --home /app fastop

WORKDIR /app

COPY fastop-service/target/fastop-service-0.0.1-SNAPSHOT.jar app.jar
RUN chown fastop:fastop app.jar

USER fastop

EXPOSE 10001

ENV JDK_JAVA_OPTIONS="-Xms256m -Xmx512m"

ENTRYPOINT ["java", "-jar", "app.jar"]
