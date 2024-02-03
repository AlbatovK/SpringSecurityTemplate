package com.albatros.springsecurity.controller

import com.albatros.springsecurity.domain.model.database.User
import com.albatros.springsecurity.domain.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/user")
class UserController(private val service: UserService) {

    @GetMapping("/get/all")
    fun getAll() = service.list()

    @GetMapping("/get/all/paginated")
    fun getAllPaginated(page: Pageable) = service.listPaginated(page)

    @GetMapping("/delete/{userId}")
    fun deleteUser(@PathVariable userId: Long) = service.deleteById(userId)

    @PostMapping("/save", consumes = ["application/json"])
    fun saveUser(@Valid @RequestBody user: User) = service.saveUser(user)

    @GetMapping("/get/{userId}")
    fun getById(@PathVariable userId: Long) = service.getUserById(userId)
}
