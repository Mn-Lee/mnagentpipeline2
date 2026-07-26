package com.pedmn.mnagentpipeline2.core.converter;

import com.pedmn.mnagentpipeline2.common.dal.dataobject.MnAgentPipelineUserDO;
import com.pedmn.mnagentpipeline2.core.model.domain.MnAgentPipelineUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MnAgentPipelineUserConverter {

    MnAgentPipelineUserConverter INSTANCE = Mappers.getMapper(MnAgentPipelineUserConverter.class);

    MnAgentPipelineUser toModel(MnAgentPipelineUserDO userDO);

    MnAgentPipelineUserDO toDO(MnAgentPipelineUser user);
}
