package com.pedmn.mnagentpipeline2.controller.nacos;

import com.pedmn.mnagentpipeline2.common.service.integration.nacos.MnAgentGlobalDrm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drm")
public class MnAgentGlobalDrmController {

    private final MnAgentGlobalDrm mnAgentGlobalDrm;

    public MnAgentGlobalDrmController(MnAgentGlobalDrm mnAgentGlobalDrm) {
        this.mnAgentGlobalDrm = mnAgentGlobalDrm;
    }

    @GetMapping("/app-name")
    public String getAppName() {
        return mnAgentGlobalDrm.getAppName();
    }
}
