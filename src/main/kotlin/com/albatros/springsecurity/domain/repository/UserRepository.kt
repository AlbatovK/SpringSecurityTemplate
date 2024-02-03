package com.albatros.springsecurity.domain.repository

import com.albatros.springsecurity.domain.model.database.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CommonRepository<User>
