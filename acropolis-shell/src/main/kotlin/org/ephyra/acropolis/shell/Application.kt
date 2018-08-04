package org.ephyra.acropolis.shell

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["org.ephyra.acropolis.shell", "org.ephyra.acropolis.service.config"])
class AcropolisShell

fun main(args: Array<String>) {
    runApplication<AcropolisShell>(*args)
}