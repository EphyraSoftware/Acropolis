package org.ephyra.acropolis.service

import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.persistence.api.persistence.ProjectPersistence
import org.ephyra.acropolis.service.api.IProjectService
import org.ephyra.acropolis.service.impl.ProjectService

/**
 * Tests for the project service
 */
class ProjectServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: ProjectPersistence

    @InjectMockKs
    var testClass: IProjectService = ProjectService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    private val testProjectName = "my-project"

    private val testProjectId: Long = 1

    init {
        "Create project" {
            val capture = slot<ProjectEntity>()
            every { persistence.create(entity = capture(capture)) } answers {
                capture.captured.name.shouldBe(testProjectName)
            }

            testClass.create(testProjectName)

            verify(exactly = 1) { persistence.create(entity = any()) }
        }

        "Delete project" {
            testClass.delete(testProjectId)

            verify(exactly = 1) { persistence.delete(id = testProjectId) }
        }

        "Get all projects" {
            every { persistence.getAll() } returns listOf(mockk(), mockk())

            val allProjects = testClass.list()

            verify(exactly = 1) { persistence.getAll() }
            allProjects.size.shouldBe(2)
        }

        "Get a project by name" {
            every { persistence.findByName(name = testProjectName) } returns mockk()

            val project = testClass.find(testProjectName)

            verify(exactly = 1) { persistence.findByName(testProjectName) }
            project.shouldNotBe(null)
        }
    }
}
