package com.albatros.springsecurity.domain.service.impl

import com.albatros.springsecurity.domain.model.database.Role
import com.albatros.springsecurity.domain.model.database.User
import com.albatros.springsecurity.domain.model.request.SignInRequest
import com.albatros.springsecurity.domain.model.request.SignUpRequest
import com.albatros.springsecurity.domain.model.response.JwtAuthenticationResponse
import com.albatros.springsecurity.domain.service.AuthenticationService
import com.albatros.springsecurity.domain.service.JwtService
import com.albatros.springsecurity.domain.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class AuthenticationServiceImpl(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager
) : AuthenticationService {

    override fun signUp(request: SignUpRequest): JwtAuthenticationResponse {
        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.ROLE_USER,
        )

        userService.createUser(user)
        return JwtAuthenticationResponse(
            jwtService.generateToken(user)
        )
    }

    override fun signIn(request: SignInRequest): JwtAuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username, request.password
            )
        )
        val user = userService.userDetailsService().loadUserByUsername(
            request.username
        )
        return JwtAuthenticationResponse(
            jwtService.generateToken(user)
        )
    }
}
