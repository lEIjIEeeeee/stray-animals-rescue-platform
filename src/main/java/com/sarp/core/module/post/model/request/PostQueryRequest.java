package com.sarp.core.module.post.model.request;

import com.sarp.core.module.common.enums.BizTypeEnum;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.post.enums.PostSearchTypeEnum;
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
 * @date 2024/1/31 14:40
 */

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PostQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索类型")
    private PostSearchTypeEnum searchType;

    @ApiModelProperty(value = "搜索文本")
    private String searchContent;

    @ApiModelProperty(value = "业务类型")
    private BizTypeEnum bizType;

    @ApiModelProperty(value = "动物类目id集合")
    private Set<String> categoryIds;

    @ApiModelProperty(value = "创建开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createStartTime;

    @ApiModelProperty(value = "创建结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createEndTime;

}
