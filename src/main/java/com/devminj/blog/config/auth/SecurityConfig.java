package com.devminj.blog.config.auth;

import com.devminj.blog.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    //.antMatchers("/api/v1/user/**").hasRole(Role.USER.name())
                    .antMatchers("/api/v1/admin/**", "/post/write" ).hasRole(Role.ADMIN.name())
                    .antMatchers("/", "/about", "/post/**", "/contact",
                            "/api/v1/**", "/profile",
                            //test
                            "/ckeditor/**", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/assets/**").permitAll()
//                    .antMatchers("/posts/save").hasRole(Role.ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);

    }
}