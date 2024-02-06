import com.google.cloud.tools.jib.gradle.JibExtension
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    kotlin("plugin.noarg") version "1.9.10"
    kotlin("plugin.allopen") version "1.9.10"
    id("com.google.cloud.tools.jib") version "3.3.1"
}

allOpen {
    annotations(
        "jakarta.persistence.Entity",
        "jakarta.persistence.Embeddable",
        "jakarta.persistence.MappedSuperclass"
    )
}

noArg {
    annotations(
        "jakarta.persistence.Entity",
        "jakarta.persistence.Embeddable",
        "jakarta.persistence.MappedSuperclass"
    )
}

group = "com.albatros"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {

    // Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Persistence
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    runtimeOnly("org.postgresql:postgresql")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // OpenApi + SwaggerUI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // SpringMVC
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    // Liquibase
    implementation("org.liquibase:liquibase-core")
}

val jibBaseImage = "eclipse-temurin:17-jre"
val jibImageArch = "amd64"

configure<JibExtension> {
    container.creationTime.set("USE_CURRENT_TIMESTAMP")
    from {
        image = jibBaseImage
        platforms {
            platform {
                architecture = jibImageArch
                os = "linux"
            }
        }
    }
    to {
        image = "spring-security:$version"
        tags = setOf("$version", "latest")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom(
        file("detekt.yml")
    )
}

ktlint {
    android.set(false)
    ignoreFailures.set(true)
    reporters {
        reporter(ReporterType.HTML)
    }
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
