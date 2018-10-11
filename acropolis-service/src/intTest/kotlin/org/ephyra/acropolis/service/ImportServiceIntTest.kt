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

        val projectEntity = projectService.get(project!!.name)
        assertNotNull(projectEntity, "Expected to find the imported project")
        val projectId = projectEntity!!.id

        val app = applicationSoftwareService.find(project.software!!.applications[0].name, projectId)
        assertNotNull(app, "Expected to find the imported application")
        assertNotNull(app!!.description)
        assertEquals(project.software!!.applications[0].description, app.description)

        val system = systemSoftwareService.get(project.software!!.systems[0].name, projectId)
        assertNotNull(system, "Expected to find the imported system")
        assertNotNull(system!!.description)
        assertEquals(project.software!!.systems[0].description, system.description)
    }
}