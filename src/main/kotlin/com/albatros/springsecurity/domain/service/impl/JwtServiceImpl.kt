package com.albatros.springsecurity.domain.service.impl

import com.albatros.springsecurity.config.security.JwtConfig
import com.albatros.springsecurity.domain.model.database.User
import com.albatros.springsecurity.domain.service.JwtService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.Date

@Service
@Validated
class JwtServiceImpl(private val jwtConfig: JwtConfig) : JwtService {
    private val signingKey =
        Keys.hmacShaKeyFor(
            jwtConfig.key.toByteArray(),
        )

    override fun generateToken(
        userDetails: UserDetails,
        expirationDate: Date,
    ): String {
        return generateToken(
            userDetails,
            expirationDate,
            mutableMapOf<String, Any>().apply {
                if (userDetails is User) {
                    put("id", userDetails.id)
                    put("email", userDetails.email)
                    put("role", userDetails.role)
                }
            },
        )
    }

    private fun generateToken(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any>,
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date())
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(signingKey)
            .compact()

    override fun extractUsername(token: String): String? = extractAllClaims(token).subject

    override fun isTokenValid(
        token: String,
        userDetails: UserDetails,
    ): Boolean = extractUsername(token) == userDetails.username && !isTokenExpired(token)

    private fun isTokenExpired(token: String) =
        extractAllClaims(token).expiration.before(
            Date(),
        )

    private fun extractAllClaims(token: String) =
        Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
}
