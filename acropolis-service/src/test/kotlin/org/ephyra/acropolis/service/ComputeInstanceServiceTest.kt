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
import org.ephyra.acropolis.persistence.api.persistence.ComputeInstancePersistence
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.ephyra.acropolis.service.api.IProjectService
import org.ephyra.acropolis.service.impl.ComputeInstanceService

/**
 * Tests for the compute instance service
 */
class ComputeInstanceServiceTest : StringSpec() {
    private val myInstanceName = "my-instance"

    @MockK(relaxUnitFun = true)
    lateinit var persistence: ComputeInstancePersistence

    @MockK()
    lateinit var projectService: IProjectService

    @InjectMockKs
    var testClass: IComputeInstanceService = ComputeInstanceService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    private val testProjectName = "my-project"

    init {
        "Create a new compute instance" {
            every { projectService.find(testProjectName) } returns mockk()
            testClass.create(myInstanceName, testProjectName)
            verify { persistence.create(computeInstance = any()) }
        }

        "Create compute instance with project not found, throws exception" {
            every { projectService.find(testProjectName) } returns null

            val exception = shouldThrow<IllegalStateException> {
                testClass.create(myInstanceName, testProjectName)
            }

            exception.message.shouldBe("Project not found [$testProjectName]")
        }

        "Create a new compute instance, fails to save" {
            every { projectService.find(testProjectName) } returns mockk()
            every { persistence.create(computeInstance = any()) } throws Exception("failed to save")

            val exception = shouldThrowAny {
                testClass.create(myInstanceName, testProjectName)
            }
            exception.message.shouldStartWith("failed to save")
        }

        "Find compute instance by name, not found" {
            val name = myInstanceName
            val projectId: Long = 1

            every { persistence.findByName(name, projectId) } returns null

            testClass.find(name, projectId).shouldBe(null)

            verify { persistence.findByName(name, projectId) }
        }

        "Find compute instance by name" {
            val name = myInstanceName
            val projectId: Long = 1

            every { persistence.findByName(name, projectId) } returns mockk()

            testClass.find(name, projectId).shouldNotBe(null)

            verify { persistence.findByName(name, projectId) }
        }

        "Find compute instance by name, fails to lookup" {
            every { persistence.findByName(name = any(), projectId = any()) } throws Exception("failed to lookup")

            val exception = shouldThrowAny {
                testClass.find("", 1)
            }
            exception.message.shouldStartWith("failed to lookup")
        }
    }
}
