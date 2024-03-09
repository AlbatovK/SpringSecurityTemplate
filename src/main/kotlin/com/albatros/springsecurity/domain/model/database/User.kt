package com.albatros.springsecurity.domain.model.database

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import java.io.Serializable

@Entity(name = "users")
@Validated
class User(
    @Column(nullable = false, length = 10)
    @field:NotBlank
    private var username: String,
    @Column(nullable = false, unique = true, length = 20)
    @field:Email
    var email: String,
    @Column(nullable = false, length = 120)
    @field:NotBlank
    private var password: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = Role.ROLE_USER,
) : AbstractEntity(), UserDetails, Serializable {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(
            SimpleGrantedAuthority(role.name),
        )

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}

enum class Role {
    ROLE_USER,
    ROLE_ADMIN,
}
