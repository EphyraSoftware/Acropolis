package org.ephyra.acropolis.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * Spring Boot entry point for the REST service.
 */
@SpringBootApplication
@ComponentScan(basePackages = ["org.ephyra.acropolis.rest", "org.ephyra.acropolis.service.config"])
class Application

/**
 * Application entry point for the REST service.
 * Note that this delegates to the Spring Boot entry point to start this service.
 */
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}