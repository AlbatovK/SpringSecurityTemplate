package com.albatros.springsecurity.domain.service

import com.albatros.springsecurity.domain.model.database.User
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.validation.annotation.Validated

@Validated
interface UserService {
    fun deleteById(userId: Long)
    fun createUser(@Valid user: User): User
    fun getUserById(userId: Long): User
    fun list(): List<User>
    fun listPaginated(page: Pageable): Slice<User>
    fun updateUser(@Valid user: User, userId: Long): User

    fun userDetailsService(): UserDetailsService

    @Deprecated(message = "For demonstration purposes only")
    fun getAdmin()
}
