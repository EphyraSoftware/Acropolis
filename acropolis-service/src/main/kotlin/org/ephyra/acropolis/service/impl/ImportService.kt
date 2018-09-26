package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.external.YamlHelper
import org.ephyra.acropolis.external.model.ApplicationSoftware
import org.ephyra.acropolis.external.model.Project
import org.ephyra.acropolis.external.model.SystemSoftware
import org.ephyra.acropolis.service.api.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Service for importing project data from the external model.
 */
@Service
class ImportService @Autowired constructor(
        val projectService: IProjectService,

        val applicationSoftwareService: IApplicationSoftwareService,

        val systemSoftwareService: ISystemSoftwareService
) : IImportService {
    val Logger = LoggerFactory.getLogger(ImportService::class.java)

    @Transactional
    override fun importProject(data: String, importType: ImportType) {
        val yamlHelper = YamlHelper()

        val project: Project? = when (importType) {
            ImportType.YAML ->
                yamlHelper.loadFromString(data)
            ImportType.JSON -> {
                println("Oops, can't work with JSON yet")
                null }
        }

        if (project == null) {
            Logger.error("Failed to extract project model from input [$data]")
            return
        }

        importProjectFromExternalModel(project)
    }

    private fun importProjectFromExternalModel(project: Project) {
        projectService.create(project.name)

        importApplications(project.name, project.software?.applications)
        importSystems(project.name, project.software?.systems)
    }

    private fun importSystems(projectName: String, systems: List<SystemSoftware>?) {
        if (systems == null) {
            Logger.info("No systems to import")
            return
        }

        systems.forEach { sys ->
            systemSoftwareService.create(sys.name, projectName)
        }
    }

    private fun importApplications(projectName: String, applications: List<ApplicationSoftware>?) {
        if (applications == null) {
            Logger.info("No applications to import")
            return
        }

        applications.forEach { app ->
            applicationSoftwareService.create(app.name, projectName)
        }
    }
}
