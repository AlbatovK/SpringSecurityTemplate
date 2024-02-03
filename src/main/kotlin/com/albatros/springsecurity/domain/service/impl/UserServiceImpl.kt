package com.albatros.springsecurity.domain.service.impl

import com.albatros.springsecurity.domain.model.database.User
import com.albatros.springsecurity.domain.model.exception.NotFoundException
import com.albatros.springsecurity.domain.repository.UserRepository
import com.albatros.springsecurity.domain.service.UserService
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class UserServiceImpl(private val repository: UserRepository) : UserService {

    @Caching(
        put = [CachePut(value = ["User"], key = "#user.id")],
        evict = [CacheEvict(value = ["Users"], allEntries = true)]
    )
    override fun saveUser(@Valid user: User) = repository.save(user)

    @Cacheable(value = ["User"], key = "#userId")
    override fun getUserById(userId: Long): User = repository.findEntityById(userId) ?: throw NotFoundException()

    @Cacheable(value = ["Users"])
    override fun list(): List<User> = repository.findAll()

    override fun listPaginated(page: Pageable): Slice<User> = repository.findAll(page)

    @Caching(
        evict = [
            CacheEvict(value = ["Users"], allEntries = true),
            CacheEvict(value = ["User"], key = "#userId")
        ]
    )
    override fun deleteById(userId: Long) = repository.deleteById(userId)

    @CachePut(value = ["User"], key = "#userId")
    override fun updateUser(@Valid user: User, userId: Long): User {
        val found = repository.findEntityById(userId) ?: throw NotFoundException()
        found.name = user.name
        return repository.save(found)
    }
}
