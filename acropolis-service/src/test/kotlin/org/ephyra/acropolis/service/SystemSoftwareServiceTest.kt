package org.ephyra.acropolis.service

import io.kotlintest.extensions.TestListener
import io.kotlintest.matchers.string.shouldStartWith
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrowAny
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.ephyra.acropolis.service.impl.ApplicationSoftwareService
import org.ephyra.acropolis.service.impl.SystemSoftwareService

/**
 * Tests for the system software service
 */
class SystemSoftwareServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: SystemSoftwarePersistence

    @InjectMockKs
    var testClass: ISystemSoftwareService = SystemSoftwareService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    init {
        "Create a new system software" {
            testClass.create(1, "my-service")
            verify { persistence.create(systemSoftware = any()) }
        }

        "Create a new system software, fails to save" {
            every { persistence.create(systemSoftware = any()) } throws Exception("failed to save")

            val exception = shouldThrowAny {
                testClass.create(1, "my-service")
            }
            exception.message.shouldStartWith("failed to save")
        }

        "Find system software by name, not found" {
            val name = "mysoft"
            val projectId: Long = 1

            every { persistence.findByName(name, projectId) } returns null

            testClass.get(name, projectId).shouldBe(null)

            verify { persistence.findByName(name, projectId) }
        }

        "Find system software by name" {
            val name = "mysoft"
            val projectId: Long = 1

            every { persistence.findByName(name, projectId) } returns mockk()

            testClass.get(name, projectId).shouldNotBe(null)

            verify { persistence.findByName(name, projectId) }
        }

        "Find system software by name, fails to lookup" {
            every { persistence.findByName(name = any(), projectId = any()) } throws Exception("failed to lookup")

            val exception = shouldThrowAny {
                testClass.get("", 1)
            }
            exception.message.shouldStartWith("failed to lookup")
        }
    }
}