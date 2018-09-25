package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.external.YamlHelper
import org.ephyra.acropolis.service.api.IImportService

/**
 * Service for importing project data from the external model.
 */
class ImportService : IImportService {
    override fun importProject(yamlData: String) {
        val yamlHelper = YamlHelper()

        println(yamlHelper.loadFromString(yamlData))
    }
}