package com.albatros.springsecurity.domain.model.request

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank
    val token: String
)
