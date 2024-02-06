package com.albatros.springsecurity.domain.service.impl

import com.albatros.springsecurity.domain.model.exception.NotFoundException
import com.albatros.springsecurity.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username) ?: throw NotFoundException()
}
