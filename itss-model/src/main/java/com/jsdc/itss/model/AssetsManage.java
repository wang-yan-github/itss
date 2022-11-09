package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.vo.AssetsSupplierContractVo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 配置项管理表(assets_manage)
 *
 * @Author libin
 * @create 2022/3/15 10:36
 */
@Entity
@TableName("assets_manage")
@Table(name = "assets_manage")
@Data
public class AssetsManage extends Model<AssetsManage> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String assets_number;                         //     资产编号
    private Integer assets_type_id;                         //     配置项类型id
    private Integer child_asset;                            //     配置子类
    private String assets_name;                             //     资产名称
    private Integer dept_id;                                //     部门
    private String person_liable;                           //     负责人
    private Integer receiver;                                //     领用人
    private Integer assets_status;                          //     资产状态
    private Integer is_secrecy;                             //     保密
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date purchase_date;                             //     购置（创建）日期
    private String finance_number;                          //     财务编号
    private String secrecy_number;                          //     保密编号
    private Integer purchase_company;                       //     购置公司
    private Integer use_scope_id;                           //     使用范围
    private Integer it_status;                              //     IT状态
    private String purpose;                                //     用途
    private String position;                                //     位置
    private String remark;                                  //     备注
    private Integer brand_id;                               //     品牌
    private String model;                                   //     型号
    private String sn;                                      //     序列号（SN）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date start_time;                                //     维保开始日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end_time;                                  //     维保结束日期
    private Integer cabinet_id;                             //     机柜
    private String start_seat;                              //     起始机位
    private String end_seat;                                //     终止机位
    private String selenium_drum_model;                     //     硒鼓型号
    private String mac;                                     //     MAC地址
    private String ip;                                      //     IP地址
    private String wireless_mac;                            //     无线MAC地址
    private String wireless_ip;                             //     无线IP地址
    private String nbu;                                     //     NBU
    private String mac2;                                    //     MAC地址2
    private String mac3;                                    //     MAC地址3
    private String ip2;                                     //     IP地址2
    private String cpu;                                     //     CPU
    private String memory;                                  //     内存
    private String hard_disk;                               //     硬盘
    private String hard_disk_type;                          //     硬盘类型
    private String server_config;                           //     服务器配置
    private Integer os_id;                                  //     操作系统
    private Integer is_invirtual;                           //     是否纳入虚拟化资源池
    private String server_code;                             //     服务编号/快速服务代码
    private Integer os_licence_id;                          //     OS许可证
    private String allocated_capacity;                      //     分配容量
    private String raid;                                    //     Raid信息
    private String capacity;                                //     总容量
    private String os_edition;                              //     系统版本
    private Integer server_id;                              //     服务器
    private Integer farm_id;                                //     farm
    private String phone;                                   //     电话号码
    private String imei;                                    //     IMEI
    private String pin;                                     //     硬件PIN
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date os_install_time;                           //     系统安装时间
    private String harddisk_serial;                         //     硬盘序列号
    private Integer assetsSoftwareLicence_id;                    //     软件许可证
    private String path;                                    //     路径
    private String edition;                                 //     版本
    private String system_name;                             //     系统名称
    private String url;                                     //     URL
    private Integer databaseName;                               //     数据库
    private String extranet_ip;                             //     外网IP
    private String intranet_ip;                             //     内网IP
    private String mid_name_edition;                        //     中间件名称及版本
    private String software_manufacturer;                   //     软件厂商名称
    private String implementation_manufacturer;             //     实施厂商名称
    private String js_liable;                               //     应用系统技术负责人
    private String js_phone;                                //     技术负责人联系电话
    private String yw_liable;                               //     系统业务负责人
    private String yw_phone;                                //     业务负责人联系电话
    private Integer is_enable;                              //     启用类型
    private Integer backup_id;                              //     备份方式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date online_time;                               //     系统上线时间
    private String extend;                                  //     扩展字段
    private Integer is_inventory;                           // 是否已生成资产盘点 0 生成 1.未生成

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    /**
     * 创建人id
     */
    private Integer create_user;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;

    /**
     * 更新人id
     */
    private Integer update_user;

    /**
     * 是否删除 0：未删除 1：已删除
     */
    private String is_del;


    /**
     * 部门名称
     */
    @Transient
    @TableField(exist = false)
    private String deptName;
    @Transient
    @TableField(exist = false)
    private String assetsTypeName;                         //     配置项类型id 对应的名称
    @Transient
    @TableField(exist = false)
    private String childAssetName;                            //     配置子类 对应的名称
    @Transient
    @TableField(exist = false)
    private String receiverName;                            //     配置子类 对应的名称
    @Transient
    @TableField(exist = false)
    private String purchaseCompanyName;                            //     公司
    @Transient
    @TableField(exist = false)
    private String purchase_date_start;                      //购置日期开始
    @Transient
    @TableField(exist = false)
    private String purchase_date_end;                        //购置日期结束
    // 资产状态名称
    @Transient
    @TableField(exist = false)
    private String assetsStatusName;
    //部门名称
    @Transient
    @TableField(exist = false)
    private String dept_name;
    //领用人
    @Transient
    @TableField(exist = false)
    private String receiver_name;
    //负责人
    @Transient
    @TableField(exist = false)
    private String person_liable_name;

    //关键字
    @Transient
    @TableField(exist = false)
    private String keyword;

    //关联联系人ids
    @Transient
    @TableField(exist = false)
    private String contractIds;

    //关联供应商ids
    @Transient
    @TableField(exist = false)
    private String supplierIds;

    //关联配置项ids
    @Transient
    @TableField(exist = false)
    private String assetsIds;

    //关联联系人
    @Transient
    @TableField(exist = false)
    private List<AssetsContractContact> contractContactList = new ArrayList<>();
    //关联供应商
    @Transient
    @TableField(exist = false)
    private List<AssetsSupplierContractVo> supplierContractList = new ArrayList<>();
    //关联配置项
    @Transient
    @TableField(exist = false)
    private List<AssetsManage> assetsTypeList = new ArrayList<>();
    //关联配置项
    @Transient
    @TableField(exist = false)
    private List<FileManage> fileManageList = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<FileManage> pictureList = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private String uploadedFiles;

    @Transient
    @TableField(exist = false)
    private String uploadedpictures;
    /**
     * 添加地址
     */
    @Transient
    @TableField(exist = false)
    private String add_address;

    /**
     * 修改地址
     */
    @Transient
    @TableField(exist = false)
    private String edit_address;

    /**
     * 列表地址
     */
    @Transient
    @TableField(exist = false)
    private String list_address;

    /**
     * 查看地址
     */
    @Transient
    @TableField(exist = false)
    private String check_address;

    @Transient
    @TableField(exist = false)
    private Integer pageNo = 1;
    @Transient
    @TableField(exist = false)
    private Integer pageSize = 10;

    @Transient
    @TableField(exist = false)
    private List<Integer> ids;
    // 品牌名称
    private String brandName;
    // 使用范围
    private String useScopeName;
    // IT状态
    private String itStatusName;


    /**
     * 操作系统名称
     */
    @Transient
    @TableField(exist = false)
    private String os_id_name;

    /**
     * OS许可证名称
     */
    @Transient
    @TableField(exist = false)
    private String os_licence_name;


    /**
     * 软件许可证名称
     */
    @Transient
    @TableField(exist = false)
    private String software_licence_name;

    /**
     * 机柜名称
     */
    @Transient
    @TableField(exist = false)
    private String cabinet_name;

    /**
     * 起始机位名称
     */
    @Transient
    @TableField(exist = false)
    private String start_seat_name;

    /**
     * 终止机位名称
     */
    @Transient
    @TableField(exist = false)
    private String end_seat_name;
    /**
     * 二维码
     */
    @Transient
    @TableField(exist = false)
    private String qrCode;

    //是否打印 0已打印 1未打印
    private String is_print;

    /**
     * 终止机位名称
     */
    @Transient
    @TableField(exist = false)
    private String real_data_base_Name;

    @Transient
    @TableField(exist = false)
    private Integer userId;


    @Transient
    @TableField(exist = false)
    private String is_success;



    @Transient
    @TableField(exist = false)
    List<AssetsManage> success = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    List<AssetsManage> error = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    List<AssetsManage> all  = new ArrayList<>();
}
