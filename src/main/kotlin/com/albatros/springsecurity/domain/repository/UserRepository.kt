package com.albatros.springsecurity.domain.repository

import com.albatros.springsecurity.domain.model.database.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CommonRepository<User> {
    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean

}
