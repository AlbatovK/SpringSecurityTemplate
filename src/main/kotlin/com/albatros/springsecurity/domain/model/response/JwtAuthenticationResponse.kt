package com.albatros.springsecurity.domain.model.response

data class JwtAuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)
