package com.jsdc.itss.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetEventServiceCategoryVo {

    /**
     * 自动服务类别管理名字
     */
    private  String name ;

    /**
     * ID
     */
    private  Integer id;

    /**
     * 状态
     */

    private  Integer is_use;

    /**
     * 排序
     */
    private  Integer sort;

    /**
     * 公司名字
     */
    private String companyName;

    /**
     * 分组名字
     */
    private String groupName;
    /**
     * 工程师名字
     */
    private String userName;
    /**
     * 描述
     */
    private String description;
    /**
     * 父级ID
     */
    private Integer parent_type_id;

    /**
     * 模块ID
     */
    private  String modular_id;

    @Transient
    @TableField(exist = false)
    private List<GetEventServiceCategoryVo> list =new ArrayList<>();
}
