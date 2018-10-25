package org.ephyra.acropolis.service

import org.ephyra.acropolis.persistence.api.GraphicalAssetType
import org.ephyra.acropolis.service.api.IGraphicalAssetService
import org.ephyra.acropolis.service.config.ServiceConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [IntegrationTestConfiguration::class, ServiceConfiguration::class])
class GraphicalAssetServiceIntTest {
    @Autowired
    private lateinit var graphicalAssetService: IGraphicalAssetService

    /**
     * Test that a PNG file can be used to create a graphical asset
     */
    @Test
    @DisplayName("Test that a PNG file can be used to create a graphical asset")
    fun createGraphicalAssetFromFile() {
        val data = javaClass.getResourceAsStream("/test-img.png").readAllBytes()
        graphicalAssetService.create("test-asset", data, GraphicalAssetType.PNG)

        val allAssets = graphicalAssetService.findAll()
        assertEquals(1, allAssets.size)

        val asset = allAssets[0]
        assertEquals(data.size, asset.data.size)
    }
}
