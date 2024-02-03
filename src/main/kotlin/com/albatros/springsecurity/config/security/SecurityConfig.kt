package com.albatros.springsecurity.config.security

// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
// import org.springframework.http.HttpMethod
// import org.springframework.security.config.annotation.web.builders.HttpSecurity
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
// import org.springframework.security.web.SecurityFilterChain
//
// @Configuration
// @EnableWebSecurity
// class SecurityConfig {
//    @Bean
//    fun configure(http: HttpSecurity): SecurityFilterChain = http.authorizeHttpRequests { requests ->
//        requests.requestMatchers("/**", "/swagger-ui/**").permitAll()
//        requests.requestMatchers(HttpMethod.POST).permitAll()
//        requests.anyRequest().permitAll()
//    }.build()
// }
//
