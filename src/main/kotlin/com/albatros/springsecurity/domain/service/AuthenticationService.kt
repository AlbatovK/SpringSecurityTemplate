package com.albatros.springsecurity.domain.service

import com.albatros.springsecurity.domain.model.request.SignInRequest
import com.albatros.springsecurity.domain.model.request.SignUpRequest
import com.albatros.springsecurity.domain.model.response.JwtAuthenticationResponse
import org.springframework.validation.annotation.Validated

@Validated
interface AuthenticationService {

    fun signUp(request: SignUpRequest): JwtAuthenticationResponse

    fun signIn(request: SignInRequest): JwtAuthenticationResponse

}