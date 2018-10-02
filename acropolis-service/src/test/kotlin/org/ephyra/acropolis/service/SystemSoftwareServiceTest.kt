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
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IProjectService
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.ephyra.acropolis.service.impl.SystemSoftwareService

/**
 * Tests for the system software service
 */
class SystemSoftwareServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: SystemSoftwarePersistence

    @MockK
    lateinit var projectService: IProjectService

    @InjectMockKs
    var testClass: ISystemSoftwareService = SystemSoftwareService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    private val testProjectName = "my-project"

    private val testServiceName = "my-service"

    init {
        "Create a new system software" {
            every { projectService.get(testProjectName) } returns mockk()
            testClass.create(testServiceName, testProjectName)
            verify { persistence.create(systemSoftware = any()) }
        }

        "Create system software with project not found, throws exception" {
            every { projectService.get(testProjectName) } returns null

            val exception = shouldThrow<IllegalStateException> {
                testClass.create(testServiceName, testProjectName)
            }
            exception.message.shouldBe("Project not found [$testProjectName]")
        }

        "Create a new system software, fails to save" {
            every { projectService.get(testProjectName) } returns mockk()
            every { persistence.create(systemSoftware = any()) } throws Exception("failed to save")

            val exception = shouldThrowAny {
                testClass.create(testServiceName, testProjectName)
            }
            exception.message.shouldStartWith("failed to save")
        }

        "Find system software by name, not found" {
            val name = "mysoft"
            val projectId: Long = 1

            every { persistence.findByName(name, projectId) } returns null

            testClass.find(name, projectId).shouldBe(null)

            verify { persistence.findByName(name, projectId) }
        }

        "Find system software by name" {
            val name = "mysoft"
            val projectId: Long = 1

            every { persistence.findByName(name, projectId) } returns mockk()

            testClass.find(name, projectId).shouldNotBe(null)

            verify { persistence.findByName(name, projectId) }
        }

        "Find system software by name, fails to lookup" {
            every { persistence.findByName(name = any(), projectId = any()) } throws Exception("failed to lookup")

            val exception = shouldThrowAny {
                testClass.find("", 1)
            }
            exception.message.shouldStartWith("failed to lookup")
        }
    }
}