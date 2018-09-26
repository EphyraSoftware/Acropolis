package org.ephyra.acropolis.service.api

/**
 * Service interface for importing project data
 */
interface IImportService {
    /**
     * Import a project from a YAML serialised model
     */
    fun importProject(data: String, importType: ImportType)
}

/**
 * Enumeration for the format used with the external model
 */
enum class ImportType {
    YAML,
    JSON
}