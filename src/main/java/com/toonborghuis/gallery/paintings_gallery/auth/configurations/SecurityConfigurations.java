package com.toonborghuis.gallery.paintings_gallery.auth.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.toonborghuis.gallery.paintings_gallery.auth.services.AuthFilterService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations implements WebMvcConfigurer {

        private final AuthFilterService authFilterService;
        private final AuthenticationProvider authenticationProvider;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Zorg ervoor dat de uploads-directory toegankelijk is
                registry.addResourceHandler("/uploads/**")
                                .addResourceLocations("file:uploads\\");
        }

        @Override
        public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                registry.addMapping("/api/**")
                                .allowedOrigins("http://localhost:3000", "https://paintings-gallery-client.vercel.app")
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                                .allowedHeaders("Content-Type", "Authorization");

                registry.addMapping("/forgotPassword/**")
                                .allowedOrigins("http://localhost:3000", "https://paintings-gallery-client.vercel.app")
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                                .allowedHeaders("Content-Type", "Authorization");
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(customizer -> customizer.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/ws/**", "/api/v1/**", "/api/v1/auth/**",
                                                                "/forgotPassword/**", "/api/v1/paintings/**",
                                                                "/api/v1/galleries/**", "/uploads/**",
                                                                "/api/v1/content_management/**", "/env/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(authFilterService,
                                                UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

}
