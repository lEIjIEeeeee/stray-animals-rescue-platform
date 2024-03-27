package com.sarp.core.config;

import com.sarp.core.filter.CheckTokenFilter;
import com.sarp.core.module.common.constant.CommonConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2024/1/27 14:47
 */

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    public static final List<String> EXCLUDED_URL_LIST;

    static {
        EXCLUDED_URL_LIST = new ArrayList<>();
        EXCLUDED_URL_LIST.add("/doc.html");
        EXCLUDED_URL_LIST.add("/authModule/login/register");
        EXCLUDED_URL_LIST.add("/authModule/login/login");
    }

    private CheckTokenFilter checkTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

        http.logout().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        for (String excludedUrl : EXCLUDED_URL_LIST) {
            http.authorizeRequests()
                .antMatchers(excludedUrl)
                .permitAll();
        }

        http.authorizeRequests()
            .anyRequest()
            .authenticated();

        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers()
            .frameOptions()
            .disable()
            .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/css/**", "/js/**", "/image/**")
           .and()
           .ignoring()
           .antMatchers()
           .and()
           .ignoring()
           .antMatchers(
                   "/doc.html",
                   "/webjars/**",
                   "/swagger-resources/**",
                   "/v2/api-docs/**",
                   "/druid/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/avatar/**", "/image/animal/**", "/image/post/**")
                .addResourceLocations(
                        "file:" + CommonConstants.STATIC_LOCATION + "avatar/",
                        "file:" + CommonConstants.STATIC_LOCATION + "animal/",
                        "file:" + CommonConstants.STATIC_LOCATION + "post/");
    }
}