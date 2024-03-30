package com.sarp.core.module.animal.model.request;

import com.sarp.core.module.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @date 2024/3/25 10:13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlatformAnimalAddRequest {

    @ApiModelProperty(value = "宠物名称")
    private String name;

    @ApiModelProperty(value = "动物类目id")
    @NotBlank(message = "动物类目id不能为空")
    private String categoryId;

    @ApiModelProperty(value = "当前主人id")
    private String ownerId;

    @ApiModelProperty(value = "性别")
    @NotNull(message = "性别不能为空")
    private GenderEnum gender;

    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "体重")
    private BigDecimal weight;

    @ApiModelProperty(value = "是否领养")
    @NotNull(message = "领养状态不能为空")
    private Integer isAdopt;

    @ApiModelProperty(value = "是否遗失")
    @NotNull(message = "遗失状态不能为空")
    private Integer isLost;

//    @ApiModelProperty(value = "宠物图片")
//    @Size(max = 6, message = "最多只能上传6张图片")
//    @NotEmpty(message = "宠物图片不能为空")
//    private List<String> imgList;

    @ApiModelProperty(value = "宠物图片")
    @NotBlank(message = "宠物图片不能为空")
    private String picUrl;

    @ApiModelProperty(value = "描述信息")
    private String desc;

}
