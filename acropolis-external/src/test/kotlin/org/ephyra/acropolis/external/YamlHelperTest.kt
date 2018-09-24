package org.ephyra.acropolis.external

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.ephyra.acropolis.external.model.Project

class YamlHelperTest : StringSpec() {
    private val testClass = YamlHelper()

    init {
        "Deserialize project from string" {
            val loaded: Project? = testClass.loadFromString("""
                version: '1.0'
                name: 'my test project'
            """.trimIndent())

            loaded.shouldNotBe(null)
            loaded?.version.shouldBe("1.0")
            loaded?.name.shouldBe("my test project")
        }
    }
}