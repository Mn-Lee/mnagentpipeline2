package com.pedmn.mnagentpipeline2.common.service.facade.user.request;

import com.pedmn.mnagentpipeline2.common.service.facade.base.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentPipelineUserSessionRequest extends ToString {

    private String token;
}
