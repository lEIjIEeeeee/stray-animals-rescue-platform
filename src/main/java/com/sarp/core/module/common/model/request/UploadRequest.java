package com.sarp.core.module.common.model.request;

import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/26 9:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadRequest {

    @NotNull
    private MultipartFile file;

    @NotNull
    private UploadBizTypeEnum uploadBizType;

}
