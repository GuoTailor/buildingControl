package com.gyh.buildingcontrol.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyh.buildingcontrol.model.ResponseInfo;
import com.gyh.buildingcontrol.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gyh on 2021/2/3
 */
@EnableWebSecurity
@ControllerAdvice
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        AppAuthenticationSuccessFailureHandler successFailureHandler = new AppAuthenticationSuccessFailureHandler();
        httpSecurity
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/register", "/login",
                        "/swagger-ui/*", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**",
                        "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.ico", "/static/**",
                        "/post/all", "/post", "/get/accesstoken", "/deal/all", "/deal", "/imgs/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, ex) ->
                        response.getWriter().write(objectMapper.writeValueAsString(ResponseInfo.failed(ex.getMessage())))
                ).and()
                .formLogin()
                .successHandler(successFailureHandler)
                .failureHandler(successFailureHandler)
                .and().addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

    /**
     * ????????????
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(Role.SUPER_ADMIN + " > " + Role.ADMIN + " > " + Role.USER);
        return hierarchy;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseInfo<String> exceptionHandler(Exception e, HttpServletRequest request) {
        logger.warn("????????????: { " + e.getMessage() + " } uri: " + request.getMethod() + ":" + request.getRequestURI());
        e.printStackTrace();
        return ResponseInfo.failed(e.getMessage());
    }

}
