package org.ephyra.acropolis.service

import org.ephyra.acropolis.external.YamlHelper
import org.ephyra.acropolis.service.api.IExportService
import org.ephyra.acropolis.service.api.IImportService
import org.ephyra.acropolis.service.api.ImportType
import org.ephyra.acropolis.service.config.ServiceConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * Test that the import and export services represent
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [IntegrationTestConfiguration::class, ServiceConfiguration::class])
class ImportExportRoundTripIntTest {
    @Autowired
    private lateinit var importService: IImportService

    @Autowired
    private lateinit var exportService: IExportService

    /**
     * Test that YAML can be symmetrically imported and exported
     */
    @Test
    @DisplayName("Test that YAML can be symmetrically imported and exported")
    fun importExportRTT() {
        val data = String(javaClass.getResourceAsStream("/round-trip-test-project.yml").readAllBytes())
        importService.importProject(data, ImportType.YAML)

        val yamlHelper = YamlHelper()
        val originalProject = yamlHelper.loadFromString(data)
                ?: throw IllegalStateException("Failed to deserialize project")

        val exportedData = exportService.export(originalProject.name, ImportType.YAML)

        // Have to serialize the data which was loaded from file again to make sure the result will match.
        val expected = yamlHelper.serialize(originalProject)

        assertEquals(expected, exportedData)
    }
}
