package com.jsdc.itss.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SlaQuestionVo
 * Description:
 * date: 2022/3/8 10:49
 *
 * @author bn
 */
@Data
public class SlaQuestionVo {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //名称
    private String sla_question_name;
    //状态 0 启用 1.禁用
    private String is_use;
    //描述
    private String remark;
    //考虑优先级 0.是 1.否
    private String is_first;


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

    private List<SlaQuestionConfigVo> slaQuestionConfigVos;

}
