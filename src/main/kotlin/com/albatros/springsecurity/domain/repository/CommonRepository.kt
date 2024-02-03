package com.albatros.springsecurity.domain.repository

import com.albatros.springsecurity.domain.model.database.AbstractEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface CommonRepository<T : AbstractEntity> : JpaRepository<T, Long> {
    fun findEntityById(id: Long): T?
}
