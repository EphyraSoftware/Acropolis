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
import org.ephyra.acropolis.persistence.api.persistence.DatastorePersistence
import org.ephyra.acropolis.service.api.IDatastoreService
import org.ephyra.acropolis.service.api.IProjectService
import org.ephyra.acropolis.service.impl.DatastoreService

/**
 * Tests for the datastore service
 */
class DatastoreServiceTest : StringSpec() {
    private val myStoreName = "my-store"

    @MockK(relaxUnitFun = true)
    lateinit var persistence: DatastorePersistence

    @MockK
    lateinit var projectService: IProjectService

    @InjectMockKs
    var testClass: IDatastoreService = DatastoreService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    private val testProjectName = "my-project"

    init {
        "Create a new datastore" {
            every { projectService.get(testProjectName) } returns mockk()
            testClass.create(myStoreName, testProjectName)
            verify { persistence.create(datastore = any()) }
        }

        "Create datastore with project not found, throws exception" {
            every { projectService.get(testProjectName) } returns null

            val exception = shouldThrow<IllegalStateException> {
                testClass.create(myStoreName, testProjectName)
            }
            exception.message.shouldBe("Project not found [$testProjectName]")
        }

        "Create a new datastore, fails to save" {
            every { projectService.get(testProjectName) } returns mockk()
            every { persistence.create(datastore= any()) } throws Exception("failed to save")

            val exception = shouldThrowAny {
                testClass.create(myStoreName, testProjectName)
            }
            exception.message.shouldStartWith("failed to save")
        }

        "Find datastore by name, not found" {
            val name = myStoreName

            every { persistence.findByName(name, 1) } returns null

            testClass.get(name, 1).shouldBe(null)

            verify { persistence.findByName(name, 1) }
        }

        "Find datastore by name" {
            val name = myStoreName

            every { persistence.findByName(name, 1) } returns mockk()

            testClass.get(name, 1).shouldNotBe(null)

            verify { persistence.findByName(name, 1) }
        }

        "Find datastore by name, fails to lookup" {
            every { persistence.findByName(name = any(), projectId = 1) } throws Exception("failed to lookup")

            val exception = shouldThrowAny {
                testClass.get("", 1)
            }
            exception.message.shouldStartWith("failed to lookup")
        }
    }
}