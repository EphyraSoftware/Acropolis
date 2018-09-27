package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.external.RefType
import org.ephyra.acropolis.external.YamlHelper
import org.ephyra.acropolis.external.extractRef
import org.ephyra.acropolis.external.model.ApplicationSoftware
import org.ephyra.acropolis.external.model.Project
import org.ephyra.acropolis.external.model.SoftwareContainer
import org.ephyra.acropolis.external.model.SystemSoftware
import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.service.api.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import javax.transaction.Transactional

/**
 * Service for importing project data from the external model.
 */
@Service
class ImportService @Autowired constructor(
        private val projectService: IProjectService,

        private val applicationSoftwareService: IApplicationSoftwareService,

        private val systemSoftwareService: ISystemSoftwareService,

        private val connectionService: IConnectionService
) : IImportService {
    private val logger: Logger = LoggerFactory.getLogger(ImportService::class.java)

    @Transactional
    override fun importProject(data: String, importType: ImportType) {
        val yamlHelper = YamlHelper()

        val project: Project? = when (importType) {
            ImportType.YAML ->
                yamlHelper.loadFromString(data)
            ImportType.JSON -> {
                println("Oops, can't work with JSON yet")
                null
            }
        }

        if (project == null) {
            logger.error("Failed to extract project model from input [$data]")
            return
        }

        importProjectFromExternalModel(project)
    }

    private fun importProjectFromExternalModel(project: Project) {
        projectService.create(project.name)

        importApplications(project.name, project.software?.applications)
        importSystems(project.name, project.software?.systems)
        importTalksToConnections(project.name, project.software)
    }

    private fun importSystems(projectName: String, systems: List<SystemSoftware>?) {
        if (systems == null) {
            logger.info("No systems to import")
            return
        }

        systems.forEach { sys ->
            systemSoftwareService.create(sys.name, projectName)
        }
    }

    private fun importApplications(projectName: String, applications: List<ApplicationSoftware>?) {
        if (applications == null) {
            logger.info("No applications to import")
            return
        }

        applications.forEach { app ->
            applicationSoftwareService.create(app.name, projectName)
        }
    }

    private val missingApplicationForTalksToMessage = "Application must exist to import talks_to connection"

    private val missingSystemForTalksToMessage = "System must exist to import talks_to connection"

    private fun importTalksToConnections(projectName: String, software: SoftwareContainer?) {
        if (software == null) {
            logger.info("Not importing talks_to links because there is no software to be imported")
            return
        }

        val project = projectService.get(projectName)
        if (project == null) {
            logger.error("Attempting to import talks_to connections for project [$projectName] but the project was not found")
            throw IllegalStateException("Project must exist to import talks_to connection")
        }

        software.applications.forEach { app ->
            val fromApplication = applicationSoftwareService.find(app.name, project.id)
            if (fromApplication == null) {
                logger.error("Attempting to import talks to connections for application [${app.name}] but the application was not found")
                throw IllegalStateException(missingApplicationForTalksToMessage)
            }

            configureConnectionsFrom(app.talks_to, project.id, fromApplication)
        }

        software.systems.forEach { system ->
            val fromSystem = systemSoftwareService.get(system.name, project.id)
            if (fromSystem == null) {
                logger.error("Attempting to import talks to connections for system [${system.name}] but the system was not found")
                throw IllegalStateException(missingSystemForTalksToMessage)
            }

            configureConnectionsFrom(system.talks_to, project.id, fromSystem)
        }
    }

    private fun configureConnectionsFrom(talks_to: List<String>?, projectId: Long, fromApplication: IConnectable) {
        talks_to?.forEach { talksToRef ->
            val (refType, name) = extractRef(talksToRef)

            val toElement = when (refType) {
                RefType.APPLICATION -> {
                    val toApplication = applicationSoftwareService.find(name, projectId)

                    if (toApplication == null) {
                        logger.error("Attempting to import talks to connection for application [$name] but the application was not found")
                        throw IllegalStateException(missingApplicationForTalksToMessage)
                    }

                    toApplication
                }
                RefType.SYSTEM -> {
                    val toSystem = systemSoftwareService.get(name, projectId)

                    if (toSystem == null) {
                        logger.error("Attempting to import talks to connection for system [$name] but the system was not found")
                        throw IllegalStateException(missingSystemForTalksToMessage)
                    }

                    toSystem
                }
            }

            connectionService.create(fromApplication, toElement, ConnectionType.TALKS_TO)
        }
    }
}
