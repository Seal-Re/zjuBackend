package com.hengtiansoft.fastop.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "fastop.integration")
public class IntegrationProperties {

    private String deviceControllerUrl = "http://localhost:5001";
    private String emsUrl = "";
    private String emsSendPath = "/addDefault";
    private String emsAbilityDefault = "fastop.send";
}
