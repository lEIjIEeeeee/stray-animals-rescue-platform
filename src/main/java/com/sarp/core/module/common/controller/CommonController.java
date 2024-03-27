package com.sarp.core.module.common.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.constant.CommonConstants;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.enums.PicTypeEnum;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.dto.CategoryTreeDTO;
import com.sarp.core.module.common.model.dto.PersonalInfoDTO;
import com.sarp.core.module.common.model.dto.UserBizCountsDTO;
import com.sarp.core.module.common.model.dto.UserListDTO;
import com.sarp.core.module.common.service.CommonService;
import com.sarp.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @date 2024/3/7 9:24
 */

@Api(tags = "公共模块-公共接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commonModule/common")
public class CommonController {

    public static final String HOST = "http://127.0.0.1:8080/commonModule/common/uploadFile/";

    private CommonService commonService;

    @ApiOperation(value = "获取动物类目树")
    @GetMapping("/getCategoryTree")
    public HttpResult<CategoryTreeDTO> getCategoryTree() {
        return HttpResult.success(commonService.getCategoryTree());
    }

    @ApiOperation(value = "获取宠物主人下拉列表")
    @GetMapping("/getAnimalOwnerList")
    public HttpResult<List<UserListDTO>> getAnimalOwnerList() {
        return HttpResult.success(JavaBeanUtils.mapList(commonService.getAnimalOwnerList(), UserListDTO.class));
    }

    @ApiOperation(value = "获取登录用户个人信息")
    @GetMapping("/personalInfo")
    public HttpResult<PersonalInfoDTO> personalInfo() {
        return HttpResult.success(commonService.personalInfo());
    }

    @ApiOperation(value = "获取用户各个模块业务数据统计")
    @GetMapping("/getCounts")
    public HttpResult<UserBizCountsDTO> getCounts() {
        return HttpResult.success(commonService.getCounts());
    }

    @ApiOperation(value = "文件上传")
    @PostMapping("/uploadFile")
    public HttpResult<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam UploadBizTypeEnum uploadBizType) throws IOException {
        String fileType = FileTypeUtil.getType(file.getInputStream(), file.getOriginalFilename());
        if (StrUtil.isBlank(fileType)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "图片类型异常");
        }
        PicTypeEnum picTypeEnum = PicTypeEnum.getEnumByCode(fileType);
        if (ObjectUtil.isNull(picTypeEnum)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "图片类型异常");
        }

        try {
            String fileNameMd5 = SecureUtil.md5(file.getOriginalFilename() + System.currentTimeMillis());
            File rootFile = new File(CommonConstants.STATIC_LOCATION + uploadBizType.getCode());
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            File fileNew = new File(rootFile + StrUtil.SLASH + fileNameMd5 + picTypeEnum.getEnumSuffix());
            file.transferTo(fileNew);
            return HttpResult.success(CommonConstants.UPLOAD_URL_PATTERN + uploadBizType.getCode() + StrUtil.SLASH + fileNew.getName());
        } catch (Exception e) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "图片上传异常");
        }
    }

}
