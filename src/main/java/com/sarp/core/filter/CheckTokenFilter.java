package com.sarp.core.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarp.core.config.WebSecurityConfig;
import com.sarp.core.context.Context;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.auth.constant.AuthConstants;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.auth.model.entity.UserAuth;
import com.sarp.core.module.auth.service.LoginService;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.user.enums.UserStatusEnum;
import com.sarp.core.security.jwt.JwtPayLoad;
import com.sarp.core.security.jwt.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @date 2024/1/27 15:59
 */

@Slf4j
@Component
public class CheckTokenFilter extends OncePerRequestFilter {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setContentType("application/json;CHARSET=UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (WebSecurityConfig.EXCLUDED_URL_LIST.contains(request.getRequestURI())
                || request.getRequestURI().contains("/webjars")
                || request.getRequestURI().contains("/swagger-resources")
                || request.getRequestURI().contains("/v2")
                || request.getRequestURI().contains("/druid")) {
            ContextUtils.clear();
            chain.doFilter(request, response);
            return;
        }

        //token验证
        String token = getToken(request);
        JwtPayLoad jwtPayLoad;
        try {
            jwtPayLoad = JwtTokenUtils.jwtPayLoad(token);
        } catch (Exception e) {
            validTokenError(response);
            return;
        }

        if (jwtPayLoad != null) {
            UserAuth userAuth = loginService.getUserAuth(jwtPayLoad.getUserId());
            if (ObjectUtil.isNull(userAuth)) {
                validTokenExpired(response);
                return;
            }

            LoginUser loginUser = objectMapper.treeToValue(userAuth.getValue(), LoginUser.class);
            if (ObjectUtil.isNull(loginUser)) {
                validTokenError(response);
                return;
            }

            if (ObjectUtil.notEqual(userAuth.getToken(), loginUser.getToken())
                    || ObjectUtil.notEqual(token, loginUser.getToken())) {
                validTokenError(response);
                return;
            }
            setContextInfo(request, loginUser);
            chain.doFilter(request, response);
        } else {
            validTokenError(response);
        }
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader(AuthConstants.TOKEN_NAME);
    }

    private void setContextInfo(HttpServletRequest request, LoginUser loginUser) {
        checkLoginUserInfo(loginUser);
        ContextUtils.setContext(Context.builder()
                                       .currentUser(loginUser)
                                       .build());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void checkLoginUserInfo(LoginUser loginUser) {
        if (UserStatusEnum.FREEZE.getCode().equals(loginUser.getStatus())) {
            throw new BizException(HttpResultCode.USER_STATUS_ERROR, "用户已被冻结");
        }
        if (UserStatusEnum.DELETE.getCode().equals(loginUser.getStatus())) {
            throw new BizException(HttpResultCode.USER_STATUS_ERROR, "用户已被删除");
        }
        if (StrUtil.isBlank(loginUser.getId())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "登录用户没有用户ID信息");
        }
        if (StrUtil.isBlank(loginUser.getAccount())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "登录用户没有登录账号信息");
        }
        if (StrUtil.isBlank(loginUser.getPhone())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "登录用户没有手机号信息");
        }
    }

    private void validTokenError(ServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            HttpResult<Void> failure = HttpResult.failure(HttpResultCode.TOKEN_VALIDATED_FAILED);
            out.write(JSON.toJSONString(failure));
            out.flush();
        } catch (IOException e) {
            log.error("Token校验失败");
        }
    }

    private void validTokenExpired(ServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            HttpResult<Void> failure = HttpResult.failure(HttpResultCode.TOKEN_EXPIRED);
            out.write(JSON.toJSONString(failure));
            out.flush();
        } catch (IOException e) {
            log.error("Token已过期");
        }
    }

}
