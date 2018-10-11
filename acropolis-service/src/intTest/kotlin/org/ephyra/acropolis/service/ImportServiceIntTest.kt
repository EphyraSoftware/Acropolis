package org.ephyra.acropolis.service

import org.ephyra.acropolis.external.YamlHelper
import org.ephyra.acropolis.service.api.*
import org.ephyra.acropolis.service.config.ServiceConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.IllegalStateException

/**
 * Integration test for the import service
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [IntegrationTestConfiguration::class, ServiceConfiguration::class])
class ImportServiceIntTest {
    @Autowired
    lateinit var importService: IImportService

    @Autowired
    lateinit var projectService: IProjectService

    @Autowired
    lateinit var applicationSoftwareService: IApplicationSoftwareService

    @Autowired
    lateinit var systemSoftwareService: ISystemSoftwareService

    /**
     * Test that YAML can be used to import a project
     */
    @Test
    @DisplayName("Test that YAML can be used to import a project")
    fun importProjectFromYaml() {
        val data = String(javaClass.getResourceAsStream("/sample-project-for-import.yml").readAllBytes())
        importService.importProject(data, ImportType.YAML)

        val yamlHelper = YamlHelper()
        val project = yamlHelper.loadFromString(data)
        assertNotNull(project, "Should be able to deserialize object")
        project ?: throw IllegalStateException("project not null")

        val projectEntity = projectService.find(project.name)
        assertNotNull(projectEntity, "Expected to find the imported project")
        val projectId = projectEntity?.id ?: throw IllegalStateException("projectEntity not null")

        val software = project.software ?: throw IllegalStateException("project.software not null")
        val app = applicationSoftwareService.find(software.applications[0].name, projectId)
        assertNotNull(app, "Expected to find the imported application")
        app ?: throw IllegalStateException("app not null")
        assertNotNull(app.description)
        assertEquals(software.applications[0].description, app.description)

        val system = systemSoftwareService.find(software.systems[0].name, projectId)
        assertNotNull(system, "Expected to find the imported system")
        system ?: throw IllegalStateException("system not null")
        assertNotNull(system.description)
        assertEquals(software.systems[0].description, system.description)
    }
}