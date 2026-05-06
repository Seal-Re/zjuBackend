package com.hengtiansoft.fastop.service.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * IntegrationProperties 默认值契约：
 *  - emsUrl 默认空串（生产必须显式 FASTOP_EMS_URL 注入，避免内网 IP 泄露 / 误把请求打到测试环境）
 *  - emsSendPath / emsAbilityDefault 有合理默认
 *  - deviceControllerUrl 默认指向本地 mock，便于快速联调
 *
 * 这些默认值与 application.yml 的 ${FASTOP_*:default} 表达式联动；任何一边改动都得同步。
 */
class IntegrationPropertiesTest {

    @Test
    void emsUrlDefaultIsEmptyToForceProductionInjection() {
        IntegrationProperties props = new IntegrationProperties();
        assertEquals("", props.getEmsUrl(),
                "emsUrl 默认必须为空串，生产由 FASTOP_EMS_URL 注入；之前默认值含真实内网 IP 192.168.1.97 已修复");
    }

    @Test
    void emsSendPathHasSafeDefault() {
        IntegrationProperties props = new IntegrationProperties();
        assertEquals("/addDefault", props.getEmsSendPath());
    }

    @Test
    void emsAbilityDefaultIsFastopSend() {
        IntegrationProperties props = new IntegrationProperties();
        assertEquals("fastop.send", props.getEmsAbilityDefault());
    }

    @Test
    void deviceControllerUrlPointsToLocalMockByDefault() {
        IntegrationProperties props = new IntegrationProperties();
        assertEquals("http://localhost:5001", props.getDeviceControllerUrl(),
                "deviceControllerUrl 默认指向本地 device-controller-mock，便于 vite proxy 联调");
    }

    @Test
    void settersCorrectlyOverrideDefaults() {
        IntegrationProperties props = new IntegrationProperties();
        props.setEmsUrl("http://prod-ems:8080/subscribe/product");
        props.setEmsSendPath("/customSend");
        assertEquals("http://prod-ems:8080/subscribe/product", props.getEmsUrl());
        assertEquals("/customSend", props.getEmsSendPath());
    }
}
