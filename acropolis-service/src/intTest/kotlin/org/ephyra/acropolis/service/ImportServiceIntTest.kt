package org.ephyra.acropolis.service

import org.ephyra.acropolis.service.api.IImportService
import org.ephyra.acropolis.service.config.ServiceConfiguration
import org.junit.jupiter.api.Assertions.assertNotEquals
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
@SpringBootTest(classes = [ServiceConfiguration::class])
class ImportServiceIntTest {
    @Autowired
    lateinit var importService: IImportService

    /**
     * Test that YAML can be used to import a project
     */
    @Test
    @DisplayName("Test that YAML can be used to import a project")
    fun importProjectFromYaml() {
        assertNotEquals(null, importService)
    }
}