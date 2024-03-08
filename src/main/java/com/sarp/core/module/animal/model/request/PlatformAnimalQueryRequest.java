package com.sarp.core.module.animal.model.request;

import com.sarp.core.module.animal.enums.AnimalSearchTypeEnum;
import com.sarp.core.module.common.enums.GenderEnum;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

/**
 * @date 2024/3/6 13:25
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformAnimalQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索类型")
    private AnimalSearchTypeEnum searchType;

    @ApiModelProperty(value = "搜索关键字")
    private String searchContent;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    @ApiModelProperty(value = "动物类目id合集")
    private Set<String> categoryIds;

    @ApiModelProperty(value = "是否领养")
    private Integer isAdopt;

    @ApiModelProperty(value = "是否遗失")
    private Integer isLost;

    @ApiModelProperty(value = "创建开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartDate;

    @ApiModelProperty(value = "创建结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndDate;

}
