package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.service.api.IProject
import org.springframework.stereotype.Component

@Component
class Project : IProject {
    override fun createProject(name: String) {
        println("Acropolis service is creating project with name $name")
    }
}
