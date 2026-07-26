package com.pedmn.mnagentpipeline2.common.dal.dataobject;

import com.pedmn.mnagentpipeline2.common.service.facade.base.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MnAgentBaseDO extends ToString {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String isDeleted;
}
