package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.springframework.stereotype.Component

@Component
class AppState {
    var currentProject: ProjectEntity? = null
}
