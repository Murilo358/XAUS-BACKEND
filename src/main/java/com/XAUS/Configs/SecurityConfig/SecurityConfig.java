package com.XAUS.Configs.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors-> cors.configurationSource(myWebsiteConfigurationSource()))//when default uses a bean by the name of CorsConfigurationSource
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize-> {
                                authorize
                                        .requestMatchers(new RegexRequestMatcher("(.*)/ws-endpoint(.*)", "POST")).authenticated()
                                        .requestMatchers(new RegexRequestMatcher("(.*)/password(.*)", "POST")).permitAll()
                                        .requestMatchers(new RegexRequestMatcher("(.*)/password(.*)", "GET")).permitAll()
                                        .requestMatchers(new RegexRequestMatcher("(.*)/ws-endpoint(.*)", "GET")).permitAll()
                                        .requestMatchers(new RegexRequestMatcher("(.*)/auth/login", "POST")).permitAll()
                                        .requestMatchers(new RegexRequestMatcher("(.*)/auth/allRoles", "GET")).permitAll()
                                        .requestMatchers(new RegexRequestMatcher("(.*)/auth/validate", "POST")).permitAll()
                                        .requestMatchers(new RegexRequestMatcher("(.*)/auth/register", "POST")).hasRole("ADMIN")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/products(.*)", "POST")).hasRole("ADMIN")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/products(.*)", "PUT")).hasRole( "ADMIN")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/products(.*)", "DELETE")).hasRole( "ADMIN")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/orders/create", "POST")).hasRole( "SALES")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/orders/(.*)/setPayed", "POST")).hasRole( "SALES")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/orders/(.*)/setPackaged", "POST")).hasRole("PACKAGER")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/clients(.*)", "POST")).hasRole( "SALES")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/clients(.*)", "GET")).hasRole( "SALES")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/clients(.*)", "DELETE")).hasRole( "ADMIN")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/users/getAll", "GET")).hasRole( "ADMIN")
                                        .requestMatchers(new RegexRequestMatcher("(.*)/users/allRoles", "GET")).authenticated()
                                        .requestMatchers(new RegexRequestMatcher("(.*)/users(.*)", "DELETE")).hasRole("ADMIN")
                                        .anyRequest().authenticated();
                        }
//
                ) //Adicionar um filtro antes da verificação do UsernamePasswordAuthenticationFilter, (tratar o token e validar se está autenticado)
                .addFilterBefore(securityFilter , UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource myWebsiteConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList( "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}