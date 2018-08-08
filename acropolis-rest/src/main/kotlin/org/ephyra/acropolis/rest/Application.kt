package org.ephyra.acropolis.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["org.ephyra.acropolis.rest", "org.ephyra.acropolis.service.config"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}