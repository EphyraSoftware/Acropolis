package org.ephyra.acropolis.service

import io.kotlintest.*
import io.kotlintest.extensions.TestListener
import io.kotlintest.matchers.string.shouldStartWith
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.entity.ApplicationSoftwareEntity
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.impl.ApplicationSoftwareService

/**
 * Tests for the application-software service.
 */
class ApplicationSoftwareServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: ApplicationSoftwarePersistence

    @InjectMockKs
    var testClass: IApplicationSoftwareService = ApplicationSoftwareService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    init {
        "Create a new application software" {
            testClass.create("my-app", "my-project")
            verify { persistence.create(applicationSoftware = any()) }
        }

        "Create a new application software, fails to save" {
            every { persistence.create(applicationSoftware = any()) } throws Exception("failed to save")

            val exception = shouldThrowAny {
                testClass.create("my-app", "my-project")
            }
            exception.message.shouldStartWith("failed to save")
        }

        "Find application software by name, not found" {
            val name = "mysoft"
            val projectId: Long = 1

            every { persistence.findByName(name, projectId) } returns null

            testClass.find(name, projectId).shouldBe(null)

            verify { persistence.findByName(name, projectId) }
        }

        "Find application software by name" {
            val name = "mysoft"
            val projectId: Long = 1

            every { persistence.findByName(name, projectId) } returns mockk()

            testClass.find(name, projectId).shouldNotBe(null)

            verify { persistence.findByName(name, projectId) }
        }

        "Find application software by name, fails to lookup" {
            every { persistence.findByName(name = any(), projectId = any()) } throws Exception("failed to lookup")

            val exception = shouldThrowAny {
                testClass.find("", 1)
            }
            exception.message.shouldStartWith("failed to lookup")
        }
    }
}


