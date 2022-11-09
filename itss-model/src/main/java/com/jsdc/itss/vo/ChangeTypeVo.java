package com.jsdc.itss.vo;

import com.jsdc.itss.model.ChangeCommonlyApprove;
import com.jsdc.itss.model.ChangeMajorApprove;
import com.jsdc.itss.model.ChangeType;
import com.jsdc.itss.model.ChangeUrgentApprove;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChangeTypeVo {

    private ChangeType changeType ;
    //一般变更审批人
    private List<ChangeCommonlyApprove> changeCommonlyApproveList = new ArrayList<>();
    //紧急变更审批人
    private List<ChangeUrgentApprove> changeUrgentApproveList = new ArrayList<>();
    //重大变更审批人
    private List<ChangeMajorApprove> changeMajorApproveList = new ArrayList<>();


}
