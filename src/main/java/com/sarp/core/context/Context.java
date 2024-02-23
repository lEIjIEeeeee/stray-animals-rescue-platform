package com.sarp.core.context;

import com.sarp.core.module.auth.model.dto.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上下文信息实体类
 * @date 2024/1/26 16:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Context {

    private LoginUser currentUser;

}
