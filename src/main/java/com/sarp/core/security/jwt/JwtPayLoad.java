package com.sarp.core.security.jwt;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @date 2024/1/27 13:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtPayLoad {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 手机号
     */
    private String phone;

    public Map<String, Object> toMap() {
        return BeanUtil.beanToMap(this);
    }

    public static JwtPayLoad toBean(Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return new JwtPayLoad();
        }
        return BeanUtil.toBean(map, JwtPayLoad.class);
    }

}
