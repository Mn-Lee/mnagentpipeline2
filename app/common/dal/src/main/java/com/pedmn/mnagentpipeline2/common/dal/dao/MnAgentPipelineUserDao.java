package com.pedmn.mnagentpipeline2.common.dal.dao;

import com.pedmn.mnagentpipeline2.common.dal.dataobject.MnAgentPipelineUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MnAgentPipelineUserDao {

    int insert(MnAgentPipelineUserDO user);

    int update(MnAgentPipelineUserDO user);

    MnAgentPipelineUserDO findByKey(@Param("userId") String userId, @Param("isDeleted") String isDeleted);

    MnAgentPipelineUserDO lockByUserId(@Param("userId") String userId);

    MnAgentPipelineUserDO lockByEmail(@Param("email") String email);

    int delete(@Param("userId") String userId);
}
