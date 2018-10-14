package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.external.YamlHelper
import org.ephyra.acropolis.external.model.ApplicationSoftware
import org.ephyra.acropolis.external.model.Project
import org.ephyra.acropolis.external.model.SoftwareContainer
import org.ephyra.acropolis.external.model.SystemSoftware
import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.IExportService
import org.ephyra.acropolis.service.api.IProjectService
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.ephyra.acropolis.service.api.ImportType
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.util.stream.Collectors

@Service
class ExportService(
        private val projectService: IProjectService,

        private val applicationSoftwareService: IApplicationSoftwareService,

        private val systemSoftwareService: ISystemSoftwareService
) : IExportService {
    private val yamlHelper = YamlHelper()

    override fun export(projectName: String, exportType: ImportType): String? {
        val project = projectService.find(projectName)
                ?: throw IllegalStateException("No project with name [$projectName]")

        return when (exportType) {
            ImportType.YAML -> {
                yamlHelper.serialize(exportProject(project))
            }
            else -> {
                throw IllegalStateException("Request to output to format [$exportType] which is not supported")
            }
        }
    }

    private fun exportProject(project: ProjectEntity): Project {
        val projectSoftware = exportProjectSoftware(project)

        return Project(
                "1.0", // TODO need to coordinate the version with the import service.
                project.name,
                projectSoftware
        )
    }

    private fun exportProjectSoftware(project: ProjectEntity): SoftwareContainer? {
        val applications = applicationSoftwareService.findAll(project.name)
        val systems = systemSoftwareService.findAll(project.name)

        if (applications.isEmpty() && systems.isEmpty()) {
            return null
        }

        return SoftwareContainer(
                exportApplications(applications),
                exportSystems(systems)
        )
    }

    private fun exportApplications(applications: List<ApplicationSoftwareEntity>): List<ApplicationSoftware> {
        return applications.stream().map { application ->
            ApplicationSoftware(
                    name = application.name,
                    description = application.description ?: "",
                    talks_to = null // TODO talks to connections
            )
        }.collect(Collectors.toList())
    }

    private fun exportSystems(systems: List<SystemSoftwareEntity>): List<SystemSoftware> {
        return systems.stream().map { system ->
            SystemSoftware(
                    name = system.name,
                    description = system.description ?: "",
                    specialization = null, // TODO specializations
                    talks_to = null // TODO talks to connections
            )
        }.collect(Collectors.toList())
    }
}
