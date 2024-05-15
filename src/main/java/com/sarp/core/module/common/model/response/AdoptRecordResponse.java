package com.sarp.core.module.common.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @date 2024/5/15 17:28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AdoptRecordResponse extends BaseResponse {

    private String animalId;

    private String animalNo;

    private String animalName;

    private String categoryName;

    private String animalPic;

    private String contactPhone;

    private String remark;

    private String auditId;

    private String auditorName;

    private Integer status;

    private Integer reapplyFlag;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    private String auditRemark;

}
