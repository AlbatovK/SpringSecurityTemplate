package com.albatros.springsecurity.config.api

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig(private val apiInfo: ApiInfoConfig) {
    @Bean
    fun openApi(): OpenAPI =
        OpenAPI()
            .info(
                Info().apply {
                    title = apiInfo.title
                    description = apiInfo.description
                    version = apiInfo.version
                    license = License().name(apiInfo.licenseName).url(apiInfo.licenseUrl)
                    contact =
                        Contact().apply {
                            email = apiInfo.contactEmail
                            name = apiInfo.contactName
                            url = apiInfo.contactUrl
                        }
                },
            ).externalDocs(
                ExternalDocumentation().apply {
                    description = apiInfo.externalDocsDescription
                    url = apiInfo.externalDocsUrl
                },
            ).addSecurityItem(
                SecurityRequirement().addList("Bearer Authentication"),
            ).components(
                Components().addSecuritySchemes(
                    "Bearer Authentication",
                    SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT")
                        .scheme("bearer"),
                ),
            )
}
