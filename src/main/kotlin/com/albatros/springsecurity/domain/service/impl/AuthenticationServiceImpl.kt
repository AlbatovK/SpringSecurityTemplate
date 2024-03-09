package com.albatros.springsecurity.domain.service.impl

import com.albatros.springsecurity.config.security.JwtConfig
import com.albatros.springsecurity.domain.model.database.RefreshToken
import com.albatros.springsecurity.domain.model.database.Role
import com.albatros.springsecurity.domain.model.database.User
import com.albatros.springsecurity.domain.model.exception.InvalidTokenException
import com.albatros.springsecurity.domain.model.exception.NotFoundException
import com.albatros.springsecurity.domain.model.request.RefreshTokenRequest
import com.albatros.springsecurity.domain.model.request.SignInRequest
import com.albatros.springsecurity.domain.model.request.SignUpRequest
import com.albatros.springsecurity.domain.model.response.JwtAuthenticationResponse
import com.albatros.springsecurity.domain.repository.RefreshTokenRepository
import com.albatros.springsecurity.domain.service.AuthenticationService
import com.albatros.springsecurity.domain.service.JwtService
import com.albatros.springsecurity.domain.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.Date

@Service
@Validated
class AuthenticationServiceImpl(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtConfig: JwtConfig,
) : AuthenticationService {
    override fun signUp(request: SignUpRequest): JwtAuthenticationResponse {
        val user =
            User(
                username = request.username,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                role = Role.ROLE_USER,
            )

        userService.createUser(user)

        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)

        refreshTokenRepository.save(
            RefreshToken(
                refreshToken,
                user.username,
            ),
        )

        return JwtAuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    override fun refreshAccessToken(refreshTokenRequest: RefreshTokenRequest): JwtAuthenticationResponse {
        val token = refreshTokenRequest.token
        val extractedUsername = jwtService.extractUsername(token) ?: throw InvalidTokenException()
        val currentUserDetails = userDetailsService.loadUserByUsername(extractedUsername)
        val refreshToken = refreshTokenRepository.findByValue(token) ?: throw InvalidTokenException()

        if (!jwtService.isTokenValid(token, currentUserDetails)) {
            throw InvalidTokenException()
        }

        if (currentUserDetails.username != refreshToken.ownerUsername) {
            throw InvalidTokenException()
        }

        return JwtAuthenticationResponse(
            accessToken = generateAccessToken(currentUserDetails),
            refreshToken = refreshToken.value,
        )
    }

    private fun generateAccessToken(user: UserDetails) =
        jwtService.generateToken(
            user,
            Date(System.currentTimeMillis() + jwtConfig.accessTokenExpiration),
        )

    private fun generateRefreshToken(user: UserDetails) =
        jwtService.generateToken(
            user,
            Date(System.currentTimeMillis() + jwtConfig.refreshTokenExpiration),
        )

    override fun signIn(request: SignInRequest): JwtAuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password,
            ),
        )
        val user =
            userDetailsService.loadUserByUsername(
                request.username,
            )

        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)

        val oldToken = refreshTokenRepository.findByOwnerUsername(user.username) ?: throw NotFoundException()
        oldToken.value = refreshToken
        refreshTokenRepository.save(oldToken)

        return JwtAuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}
