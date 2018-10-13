package org.ephyra.acropolis.shell

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * Spring Boot entry point for the shell.
 */
@SpringBootApplication
@ComponentScan(basePackages = ["org.ephyra.acropolis.shell", "org.ephyra.acropolis.service.config"])
class AcropolisShell

/**
 * Application entry point for the shell.
 * Note that this delegates to the Spring Boot entry point.
 */
@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<AcropolisShell>(*args)
}
