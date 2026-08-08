package com.pedmn.mnagentpipeline2.common.service.facade.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentPipelineUserLoginRequest {

    private String email;

    private String password;
}
