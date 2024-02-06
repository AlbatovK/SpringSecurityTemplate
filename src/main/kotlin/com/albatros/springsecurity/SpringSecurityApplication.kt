package com.albatros.springsecurity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan
class SpringSecurityApplication

fun main(args: Array<String>) {
    runApplication<SpringSecurityApplication>(*args)
}
