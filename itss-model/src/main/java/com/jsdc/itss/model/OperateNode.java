package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @projectName: itss
 * @className: OperateNode
 * @author: wp
 * @description: 关联事件或问题的OLA计时以及操作人表
 * @date: 2022/7/5 14:05
 */
@Entity
@Table(name = "operate_node")
@TableName("operate_node")
@Data
public class OperateNode extends Model<OperateNode> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //事件或问题工单id
    private Integer bill_id;

    //工单类型 0:事件 1:问题
    private String bill_type;

    //当前操作人
    private Integer operate_user;

    //当前操作类型 1:响应 2:处理
    private String operate_type;

    //开始计时时间
    private Date timing_start;

    //用时
    private Integer time_spent;

    //是否超时
    private String is_timeout;

    //当前节点是否已完成 0:未完成 1:已完成
    //完成的意思是 当前节点的操作人是否还具有操作权限，例：事件被指派，则原响应人便失去了对该事件的操作权限，即已完成该节点
    private String is_complete;

    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

}
