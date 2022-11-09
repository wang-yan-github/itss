package com.jsdc.itss.utils;


import com.jsdc.itss.model.SysDict;

import java.util.*;

/**
 * @ClassName DcCacheDataUtil
 * @Description TODO
 * @Date 2022/1/6 15:30
 * @Version 1.0
 */
public class DcCacheDataUtil {

    /**
     * /**
     * 根据字典类型获取对应Hash字典，用于根据key翻译字典值
     *
     * @param dictType 字典类型 value 字典value
     * @return
     */
    public static HashMap<String, SysDict> getMapDicts(String dictType) {
        Map<String, HashMap> dicData = (Map<String, HashMap>) RedisUtils.getBeanValue("dictData");
        HashMap<String, SysDict> dictMap = dicData.get(dictType);
        return dictMap;
    }
}
