package com.albatros.springsecurity.config.api

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig(private val apiInfo: ApiInfoConfig) {

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(
            Info().apply {
                title = apiInfo.title
                description = apiInfo.description
                version = apiInfo.version
                license = License().name(apiInfo.licenseName).url(apiInfo.licenseUrl)
                contact = Contact().apply {
                    email = apiInfo.contactEmail
                    name = apiInfo.contactName
                    url = apiInfo.contactUrl
                }
            }
        ).externalDocs(
            ExternalDocumentation().apply {
                description = apiInfo.externalDocsDescription
                url = apiInfo.externalDocsUrl
            }
        )
}
