package com.jsdc.itss.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysDepartmentVo {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名字
     */
    private String name;
    /**
     * 同 name 字段 构建页面tree用
     */
    private String label;
    public String getLabel() {
        return this.name;
    }

    /**
     * 上级ID
     */
    private Integer parent_id;

    /**
     * 上级名字
     */
    private String parentName;

    /**
     * 上级名字
     */
    private String parent_name;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 唯一ID
     */
    private String unique_id;

    /**
     * 公司名字
     */
    private String companyName;

    /**
     * 状态 0：禁用 1：启用
     */
    private Short is_use;

    /**
     * 服务群组名字
     */
    private String manageName;

    /**
     * 快速保障支持群组ID
     */
    private Integer group_id;
    /**
     * 公司ID
     */
    private Integer company_id;

    @Transient
    @TableField(exist = false)
    private List<SysDepartmentVo> children =new ArrayList<>();

}
