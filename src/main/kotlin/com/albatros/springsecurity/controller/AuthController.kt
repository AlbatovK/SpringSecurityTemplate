package com.albatros.springsecurity.controller

import com.albatros.springsecurity.domain.model.request.SignInRequest
import com.albatros.springsecurity.domain.model.request.SignUpRequest
import com.albatros.springsecurity.domain.service.AuthenticationService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
) {
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody signUpRequest: SignUpRequest) = authenticationService.signUp(signUpRequest)

    @PostMapping("/sign-in")
    fun signIn(@Valid @RequestBody signInRequest: SignInRequest) = authenticationService.signIn(signInRequest)
}
