package com.sarp.core.module.auth.model.request;

import com.sarp.core.module.auth.enums.UserSearchTypeEnum;
import com.sarp.core.module.common.enums.GenderEnum;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.user.enums.UserStatusEnum;
import com.sarp.core.module.user.enums.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @date 2024/3/18 10:45
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索类型")
    private UserSearchTypeEnum searchType;

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;

    @ApiModelProperty(value = "用户类型")
    private UserTypeEnum userType;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    @ApiModelProperty(value = "用户状态")
    private UserStatusEnum status;

    @ApiModelProperty(value = "创建开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartDate;

    @ApiModelProperty(value = "创建结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndDate;

}
