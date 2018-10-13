package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.springframework.stereotype.Component

/**
 * Single instance state object for storing state in the shell.
 *
 * This should be the only place where values are saved between commands. The command components
 * themselves should be stateless.
 */
@Component
class AppState {
    var currentProject: ProjectEntity? = null
}
