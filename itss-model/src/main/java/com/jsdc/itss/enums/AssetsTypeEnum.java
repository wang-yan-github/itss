package com.jsdc.itss.enums;

import lombok.Getter;

public enum  AssetsTypeEnum {
    ASSETS_ENCLOSURE("Enclosure", "机位"),
    ASSETS_RACK("Rack", "机柜"),
    ASSETS_NAS("NAS", "NAS"),
    ASSETS_SANSWITCH("SANSwitch", "交换机"),
    ASSETS_VM("VM", "虚拟机"),
    ASSETS_HYPERVISOR("Hypervisor", "超管理器"),
    ASSETS_OTHERADD("otherAdd", "其他"),
    ASSETS_PDU("pdu", "PDU管理"),
    ASSETS_MOBILEPHONE("mobilePhone", "电话管理"),
    ASSETS_STORAGEDEVICE("storageDevice", "存储设备管理"),
    ASSETS_PHONE("phone", "手机"),
    ASSETS_PAD("pad", "平板电脑管理"),
    ASSETS_OPERATIONFLOW("operationFlow", "业务流程管理"),
    ASSETS_DATABASEMODE("databaseMode", "数据库模式管理"),
    ASSETS_NETWORKAPPLICATION("networkApplication", "网络应用管理"),
    ASSETS_NETWORKMANAGE("networkmanage", "网络设备管理"),
    ASSETS_PC("pc", "PC"),
    ASSETS_FARM("Farm", "Farm处理机"),
    ASSETS_SERVER("server", "服务器"),
    ASSETS_PERIPHERAL("peripheral", "外部设备"),
    ASSETS_PRINTER("printer", "打印机"),
    ASSETS_SOFTWARE("software", "软件管理"),
    ASSETS_APPLY("apply", "应用管理");


    @Getter
    private String value;
    @Getter
    private String desc;

    AssetsTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
