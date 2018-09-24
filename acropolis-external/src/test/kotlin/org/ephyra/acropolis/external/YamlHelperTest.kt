package org.ephyra.acropolis.external

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.ephyra.acropolis.external.model.Project

/**
 * Tests for @see org.ephyra.acropolis.external.YamlHelper
 */
class YamlHelperTest : StringSpec() {
    private val testClass = YamlHelper()

    init {
        "Deserialize project from string" {
            val loaded: Project? = testClass.loadFromString("""
                version: '1.0'
                name: 'my test project'
                software:
                  applications:
                    - name: IceWeasel
                      description: 'Browser which the user interacts with'
                    - name: PrettyChart
                      description: 'Web app for rendering charts in a browser'
                  systems:
                    - name: ChartRenderer
                      description: 'Engine for rendering charts to offload work from the browser'
            """.trimIndent())

            loaded.shouldNotBe(null)
            loaded?.version.shouldBe("1.0")
            loaded?.name.shouldBe("my test project")
            loaded?.software?.applications?.size.shouldBe(2)
            loaded?.software?.applications?.get(0)?.name.shouldBe("IceWeasel")
            loaded?.software?.applications?.get(1)?.name.shouldBe("PrettyChart")
            loaded?.software?.systems?.size.shouldBe(1)
            loaded?.software?.systems?.get(0)?.name.shouldBe("ChartRenderer")
        }
    }
}