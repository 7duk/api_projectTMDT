
package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;
    private final UnauthorizedHandler unauthorizedHandler;

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors().and().csrf().disable()
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(AbstractHttpConfigurer::disable)
            .exceptionHandling(h -> h.authenticationEntryPoint(unauthorizedHandler))
            .securityMatcher("/**")
            .authorizeHttpRequests(registry -> registry
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                            .requestMatchers("/chat").permitAll()
                            .requestMatchers("/chat/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/events").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/events/filtered").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/events/event/decrease-price").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/events/event/giveaway/code-public").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/events/event/giveaway/code-private").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/events/event/{id}").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/gifcodes/random").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/gifcodes/percent").hasAuthority("USER")
                            .requestMatchers(HttpMethod.PUT,"/api/gifcodes/usaged").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST,"/api/auth").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/auth/google").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/bills").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/bills").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST,"/api/chats/room-chat").hasAuthority("USER")
                            .requestMatchers(HttpMethod.GET,"/api/chats").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/colors").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/comments").hasAuthority("USER")
                            .requestMatchers(HttpMethod.PATCH,"/api/comments/check/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/comments/checked/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/comments/uncheck").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/comments/comment/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/itemgroups").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/itemgroups").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/itemgroups/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/imageitems").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/imageitems/image/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/imageitems/imageitem/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/itemcarts/**").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST,"/api/itemcarts").hasAuthority("USER")
                            .requestMatchers(HttpMethod.DELETE,"/api/itemcarts/**").hasAuthority("USER")
                            .requestMatchers(HttpMethod.DELETE,"/api/itemcarts").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST,"/api/items").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PUT,"/api/items/update/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/items/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/items/{igId}").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/items/item/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/items/item-id/**").permitAll()
                            .requestMatchers(HttpMethod.PATCH,"/api/items/discount/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PATCH,"/api/items/name/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PATCH,"/api/items/description/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/items/item-search/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/items/search/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/itemdetails").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PATCH,"/api/itemdetails/itemdetail/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/messages").hasAuthority("USER")
                            .requestMatchers(HttpMethod.GET,"/api/orders").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/orders/paylive").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST,"/api/orders/user/statistical").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PATCH,"/api/orders/order/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PATCH,"/api/orders/paylive/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/orderdetails").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/paypal/payment").hasAuthority("USER")
                            .requestMatchers(HttpMethod.GET,"/api/paypal/success").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/paypal/cancel").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/payment/result").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/users").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/users/register").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/users/forgot-password/**").permitAll()
                            .requestMatchers(HttpMethod.PATCH,"/api/users/forgot-password/reset").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/users/verify-mail").permitAll()
                            .requestMatchers(HttpMethod.PUT,"/api/users/user/**").hasAuthority("USER")
                            .requestMatchers(HttpMethod.POST,"/api/vnpay/payment").hasAuthority("USER")
                            .requestMatchers(HttpMethod.GET,"/api/vnpay/result").permitAll()
                            .anyRequest().authenticated()

            );
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(List.of("x-auth-token", "X-Total-Fee", "X-Total-Order", "X-Total-Page", "X-Total"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}

