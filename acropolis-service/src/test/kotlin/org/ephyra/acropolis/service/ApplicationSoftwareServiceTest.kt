package org.ephyra.acropolis.service

import io.kotlintest.extensions.TestListener
import io.kotlintest.matchers.string.shouldStartWith
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.shouldThrowAny
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.IProjectService
import org.ephyra.acropolis.service.impl.ApplicationSoftwareService

/**
 * Tests for the application-software service.
 */
class ApplicationSoftwareServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: ApplicationSoftwarePersistence

    @MockK
    lateinit var projectService: IProjectService

    @InjectMockKs
    var testClass: IApplicationSoftwareService = ApplicationSoftwareService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    private val testProjectName = "my-project"

    private val testAppName = "my-app"

    init {
        "Create a new application software" {
            every { projectService.find(testProjectName) } returns mockk()
            testClass.create(testAppName, testProjectName)
            verify { persistence.create(applicationSoftware = any()) }
        }

        "Create application software with project not found, throws exception" {
            every { projectService.find(testProjectName) } returns null

            val exception = shouldThrow<IllegalStateException> {
                testClass.create(testAppName, testProjectName)
            }
            exception.message.shouldBe("Project not found [$testProjectName]")
        }

        "Create a new application software, fails to save" {
            every { projectService.find(testProjectName) } returns mockk()
            every { persistence.create(applicationSoftware = any()) } throws Exception("failed to save")

            val exception = shouldThrowAny {
                testClass.create(testAppName, testProjectName)
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
