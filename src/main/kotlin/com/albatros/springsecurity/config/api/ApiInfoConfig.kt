package com.albatros.springsecurity.config.api

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "api-info")
data class ApiInfoConfig(
    @field:NotEmpty
    val title: String,
    @field:NotEmpty
    val description: String,
    @field:NotEmpty
    val version: String,
    @field:NotEmpty
    val licenseName: String,
    @field:NotEmpty
    val licenseUrl: String,
    @field:Email
    @field:NotEmpty
    val contactEmail: String,
    @field:NotEmpty
    val contactName: String,
    @field:NotEmpty
    val contactUrl: String,
    @field:NotEmpty
    val externalDocsDescription: String,
    @field:NotEmpty
    val externalDocsUrl: String
)
