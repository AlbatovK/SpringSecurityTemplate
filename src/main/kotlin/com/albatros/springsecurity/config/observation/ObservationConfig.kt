package com.albatros.springsecurity.config.observation

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationHandler
import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
class ObservationConfig {
    @Bean
    fun observedAspect(registry: ObservationRegistry): ObservedAspect = ObservedAspect(registry)
}

@Component
class ObservationLoggingHandler : ObservationHandler<Observation.Context> {

    private val logger = LoggerFactory.getLogger(ObservationLoggingHandler::class.java)

    override fun onStart(context: Observation.Context) {
        logger.info("Observing {} context", context.name)
    }

    override fun supportsContext(context: Observation.Context) = true
}
