package com.jsdc.itss.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SlaEventVo
 * Description:
 * date: 2022/3/8 9:59
 *
 * @author bn
 */
@Data
public class SlaEventVo {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //名称
    private String sla_event_name;
    //描述
    private String remark;
    //考虑优先级 0.否 1.是
    private String is_first;
    //考虑请求类型 0.否 1.是
    private String is_request;


    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    //创建人
    private Integer create_user;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;
    //更新人
    private Integer update_user;
    //是否删除
    private String is_del;

    private List<SlaEventFirstAndRequestVo> slaEventFirstAndRequestVos;
}
