package com.shoestore.client.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Autowired
  private CustomUserDetailService customUserDetailService;  // Tiêm CustomUserDetailService vào cấu hình

  @Autowired
  private PasswordEncoder passwordEncoder;  // Tiêm PasswordEncoder vào cấu hình

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> {
              csrf.disable();
            })
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/page/Admin/**").hasRole("Admin")  // Chỉ cho phép admin vào các trang admin
                    .requestMatchers("/page/Customer/**").hasRole("Customer")  // Chỉ cho phép customer vào các trang customer
                    .anyRequest().permitAll()  // Các trang còn lại sẽ được phép truy cập công khai
            )
            .formLogin(form -> form
                    .loginPage("/login")  // Trang đăng nhập của bạn
                    .defaultSuccessUrl("/home", true)  // Định tuyến trang chủ sau khi đăng nhập thành công
                    .permitAll()
            )
            .logout(logout -> logout
                    .permitAll()
            );

    return http.build();
  }

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    // Cấu hình AuthenticationManager để sử dụng CustomUserDetailService và PasswordEncoder
    auth.userDetailsService(customUserDetailService)
            .passwordEncoder(passwordEncoder);
  }
}
