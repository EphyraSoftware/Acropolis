package org.ephyra.acropolis.service.api

/**
 * Service interface for importing project data
 */
interface IImportService {
    /**
     * Import a project from a YAML serialised model
     */
    fun importProject(yamlData: String)
}