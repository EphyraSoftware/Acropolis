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
                      talks_to:
                        - system.RenderProxy
                    - name: PrettyChart
                      description: 'Web app for rendering charts in a browser'
                      talks_to:
                        - application.IceWeasel
                  systems:
                    - name: ChartRenderer
                      description: 'Engine for rendering charts to offload work from the browser'
                    - name: RenderQueue
                      description: Queue for render jobs
                      specialization: Queue
                      talks_to:
                        - system.ChartRenderer
                    - name: RenderProxy
                      description: Reverse Proxy for the render service
                      specialization: ReverseProxy
                      talks_to:
                        - system.RenderLoadBalancer
                    - name: RenderLoadBalancer
                      description: Load balancer for the render service
                      specialization: LoadBalancer
                      talks_to:
                        - system.RenderQueue
            """.trimIndent())

            loaded.shouldNotBe(null)
            loaded?.version.shouldBe("1.0")
            loaded?.name.shouldBe("my test project")

            loaded?.software?.applications?.size.shouldBe(2)
            loaded?.software?.applications?.get(0)?.name.shouldBe("IceWeasel")
            loaded?.software?.applications?.get(1)?.name.shouldBe("PrettyChart")

            loaded?.software?.systems?.size.shouldBe(4)
            loaded?.software?.systems?.get(0)?.name.shouldBe("ChartRenderer")
            loaded?.software?.systems?.get(1)?.name.shouldBe("RenderQueue")
            loaded?.software?.systems?.get(2)?.name.shouldBe("RenderProxy")
            loaded?.software?.systems?.get(3)?.name.shouldBe("RenderLoadBalancer")

            loaded?.software?.applications?.get(0)?.talks_to?.size.shouldBe(1)
            loaded?.software?.applications?.get(0)?.talks_to?.get(0).shouldBe("system.RenderProxy")

            loaded?.software?.applications?.get(1)?.talks_to?.size.shouldBe(1)
            loaded?.software?.applications?.get(1)?.talks_to?.get(0).shouldBe("application.IceWeasel")

            loaded?.software?.systems?.get(0)?.talks_to.shouldBe(null)
            loaded?.software?.systems?.get(0)?.specialization.shouldBe(null)

            loaded?.software?.systems?.get(1)?.talks_to?.size.shouldBe(1)
            loaded?.software?.systems?.get(1)?.talks_to?.get(0).shouldBe("system.ChartRenderer")
            loaded?.software?.systems?.get(1)?.specialization.shouldBe("Queue")

            loaded?.software?.systems?.get(2)?.talks_to?.size.shouldBe(1)
            loaded?.software?.systems?.get(2)?.talks_to?.get(0).shouldBe("system.RenderLoadBalancer")
            loaded?.software?.systems?.get(2)?.specialization.shouldBe("ReverseProxy")

            loaded?.software?.systems?.get(3)?.talks_to?.size.shouldBe(1)
            loaded?.software?.systems?.get(3)?.talks_to?.get(0).shouldBe("system.RenderQueue")
            loaded?.software?.systems?.get(3)?.specialization.shouldBe("LoadBalancer")
        }
    }
}