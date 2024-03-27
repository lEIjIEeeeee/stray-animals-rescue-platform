package com.sarp.core.module.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/3/25 23:30
 */

@Getter
@AllArgsConstructor
public enum PicTypeEnum {

    JPG("图像", "jpg", ".jpg"),
    PNG("图像", "png", ".png"),
    GIF("动图", "gif", ".gif"),
//    MP4("视频", "mp4", ".mp4"),
    ;

    private String name;
    private String code;
    private String suffix;

    public String getEnumName() {
        return this.name;
    }

    public String getEnumCode() {
        return this.code;
    }

    public String getEnumSuffix() {
        return this.suffix;
    }

    public static PicTypeEnum getEnumByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }

        for (PicTypeEnum enumObj : PicTypeEnum.class.getEnumConstants()) {
            if (enumObj.getEnumCode().equals(code)) {
                return enumObj;
            }
        }
        return null;
    }

}
