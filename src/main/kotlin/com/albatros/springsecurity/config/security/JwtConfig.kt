package com.albatros.springsecurity.config.security

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@EnableConfigurationProperties(JwtConfig::class)
@ConfigurationProperties("jwt")
data class JwtConfig(
    @field:NotEmpty
    val key: String,
    @field:Min(0)
    val accessTokenExpiration: Long,
    @field:Min(0)
    val refreshTokenExpiration: Long,
)