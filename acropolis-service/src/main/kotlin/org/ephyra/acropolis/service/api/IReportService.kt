package org.ephyra.acropolis.service.api

/**
 * Service for creation reports on a project.
 * These may be different formats and contain all or a subset of the data contained in the project.
 */
interface IReportService {
    /**
     * Create a report which contains details of the software in the given project
     *
     * @param projectName The name of the project to run the report on
     */
    fun runSoftwareReport(projectName: String)
}
