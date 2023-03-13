package com.guavapay.apigate.configuration;

import com.guavapay.apigate.security.JwtConfigurer;
import com.guavapay.apigate.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/create", "/api/auth/login", "/swagger-ui.html").permitAll()
                .antMatchers(
                        "/api/order/changeDestination",
                        "/api/order/cancel",
                        "/api/order/create",
                        "/api/order/getOrderById",
                        "/api/userdetails/details",
                        "/api/userdetails/create",
                        "/api/order/getUserOrders").hasRole("USER")
                .antMatchers(
                        "/api/order/changeDestination",
                        "/api/order/changeStatus",
                        "/api/order/getOrderById",
                        "/api/order/gerCourierOrders",
                        "/api/order/setCoordinates").hasRole("COURIER")
                .antMatchers(
                        "/api/order/changeStatus",
                        "/api/order/getAll",
                        "/api/order/changeStatus",
                        "/api/order/assignToCourier",
                        "/api/order/getUserOrders",
                        "/api/courier/create",
                        "/api/courier/getAll"
                ).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
