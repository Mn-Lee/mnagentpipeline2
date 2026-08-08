package com.pedmn.mnagentpipeline2.core.service.repository;

import com.pedmn.mnagentpipeline2.core.model.domain.MnAgentPipelineUser;

public interface MnAgentPipelineUserRepository {

    MnAgentPipelineUser insert(MnAgentPipelineUser user);

    MnAgentPipelineUser update(MnAgentPipelineUser user);

    MnAgentPipelineUser findByUserId(String userId);

    MnAgentPipelineUser findByEmail(String email);

    int delete(String userId);
}
