package com.pedmn.mnagentpipeline2.common.dal.dao;

import com.pedmn.mnagentpipeline2.common.dal.dataobject.MnAgentSequenceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MnAgentSequenceDao {

    MnAgentSequenceDO lockBySequenceName(@Param("sequenceName") String sequenceName);

    int insert(MnAgentSequenceDO sequence);

    int updateCurrentValue(@Param("sequenceName") String sequenceName, @Param("currentValue") Long currentValue);
}
