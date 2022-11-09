package com.jsdc.itss.vo;

import com.jsdc.itss.model.AssetsContractContact;
import com.jsdc.itss.model.AssetsManage;
import com.jsdc.itss.model.AssetsSupplierContract;
import com.jsdc.itss.model.FileManage;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: AssetsSupplierContractVo
 * Description:
 * date: 2022/3/25 17:07
 *
 * @author bn
 */
@Data
public class AssetsSupplierContractVo extends AssetsSupplierContract {


    // 币种
    private String currency_name;

    // 状态
    private String status_name;

    // 部门名称
    private String deptName;


    /**
     * 得到联系人信息
     */
    private List<AssetsContractContact> contractContacts = new ArrayList<>();

    /**
     *  关联的配置信息
     */
    private List<AssetsManage> assetsManages= new ArrayList<>();

    private List<MultipartFile> fileTemp = new ArrayList();
    private List<FileManage> fileManageList = new ArrayList<>();

    private String uploadedFiles;

}
