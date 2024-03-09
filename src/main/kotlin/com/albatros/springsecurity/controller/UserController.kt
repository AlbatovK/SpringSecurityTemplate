package com.albatros.springsecurity.controller

import com.albatros.springsecurity.domain.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api")
class UserController(private val service: UserService) {
    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete user by id")
    fun deleteUser(
        @PathVariable userId: Long,
    ) = service.deleteById(userId)

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user by id")
    fun getById(
        @PathVariable userId: Long,
    ) = service.getUserById(userId)

    @PutMapping("/user/get-admin")
    @Operation(summary = "Grants admin role for current user. For test purpose only")
    fun getAdminForCurrentUser() = service.getAdmin()
}
