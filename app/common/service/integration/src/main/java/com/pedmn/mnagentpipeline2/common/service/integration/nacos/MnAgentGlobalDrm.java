package com.pedmn.mnagentpipeline2.common.service.integration.nacos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "global")
@Getter
@Setter
public class MnAgentGlobalDrm {

    /**
     * 应用名
     */
    private String appName;
}
