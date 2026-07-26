package com.pedmn.mnagentpipeline2.core.service.sequence;

import com.pedmn.mnagentpipeline2.common.dal.enums.MnAgentTableEnum;

public interface MnAgentSequenceService {

    String generateSingleSequenceId(MnAgentTableEnum tableEnum);
}
