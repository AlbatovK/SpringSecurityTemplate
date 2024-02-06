package com.albatros.springsecurity.domain.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated

@Validated
interface JwtService {
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
    fun extractUsername(token: String): String?
    fun generateToken(userDetails: UserDetails): String
}
