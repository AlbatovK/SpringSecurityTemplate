package com.albatros.springsecurity.domain.repository

import com.albatros.springsecurity.domain.model.database.RefreshToken
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CommonRepository<RefreshToken> {
    fun findByOwnerUsername(ownerUsername: String): RefreshToken?

    fun findByValue(value: String): RefreshToken?
}
