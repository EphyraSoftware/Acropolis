package org.ephyra.acropolis.shell

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AcropolisShell

fun main(args: Array<String>) {
    runApplication<AcropolisShell>(*args)
}
