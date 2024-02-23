package com.sarp.core.security.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sarp.core.module.common.constant.PropertiesConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类：token生成和校验
 * @date 2024/1/27 13:50
 */

public class JwtTokenUtils {

    /**
     * token生成器
     * @param jwtPayLoad
     * @return token
     */
    public static String generateToken(JwtPayLoad jwtPayLoad) {
        final Date expireDate = DateUtil.date(System.currentTimeMillis() + getExpireSeconds() * 1000);
        return generateToken(jwtPayLoad.getUserId(), expireDate, jwtPayLoad.toMap());
    }

    /**
     * token校验
     * @param token
     * @return jwtPayLoad
     */
    public static JwtPayLoad jwtPayLoad(String token) {
        Claims claims = getClaimsByToken(token);
        return JwtPayLoad.toBean(claims);
    }

    public static boolean isTokenExpired(String token) {
        try {
            final Date expireDate = getClaimsByToken(token).getExpiration();
            return expireDate.before(DateUtil.date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private static String generateToken(String userId, Date expireDate, Map<String, Object> claims) {
        final Date currentDate = DateUtil.date();
        String jwtSecret = getJwtSecret();

        if (claims != null) {
            return Jwts.builder()
                       .setClaims(claims)
                       .setSubject(userId)
                       .setIssuedAt(currentDate)
                       .setExpiration(expireDate)
                       .signWith(SignatureAlgorithm.HS512, jwtSecret)
                       .compact();
        } else {
            return Jwts.builder()
                       .setSubject(userId)
                       .setIssuedAt(currentDate)
                       .setExpiration(expireDate)
                       .signWith(SignatureAlgorithm.HS512, jwtSecret)
                       .compact();
        }
    }

    private static Claims getClaimsByToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new IllegalArgumentException("token参数为空");
        }

        String jwtSecret = getJwtSecret();

        return Jwts.parser()
                   .setSigningKey(jwtSecret)
                   .parseClaimsJws(token)
                   .getBody();
    }

    private static String getJwtSecret() {
        return SpringUtil.getProperty(PropertiesConstants.JWT_SECRET);
    }

    public static Long getExpireSeconds() {
        return (long) (86400 *365);
    }

}
