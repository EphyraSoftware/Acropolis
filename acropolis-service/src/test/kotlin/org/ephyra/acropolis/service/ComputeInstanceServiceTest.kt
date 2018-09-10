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
import org.ephyra.acropolis.persistence.api.persistence.ComputeInstancePersistence
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.ephyra.acropolis.service.impl.ComputeInstanceService

/**
 * Tests for the compute instance service
 */
class ComputeInstanceServiceTest : StringSpec() {
    private val myInstanceName = "my-instance"

    @MockK(relaxUnitFun = true)
    lateinit var persistence: ComputeInstancePersistence

    @InjectMockKs
    var testClass: IComputeInstanceService = ComputeInstanceService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    init {
        "Create a new compute instance" {
            testClass.create(myInstanceName, "my-project")
            verify { persistence.create(computeInstance = any()) }
        }

        "Create a new compute instance, fails to save" {
            every { persistence.create(computeInstance = any()) } throws Exception("failed to save")

            val exception = shouldThrowAny {
                testClass.create(myInstanceName, "my-project")
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