package org.ephyra.acropolis.service.api

/**
 * Service interface for exporting project data
 */
interface IExportService {
    /**
     * Export a project identified by name to a specified data format.
     *
     * @param projectName The name of the project to export
     * @param exportType The type of data format to output
     */
    fun export(projectName: String, exportType: ImportType): String?
}
