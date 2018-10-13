package org.ephyra.acropolis.service

import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.entity.DatastoreEntity
import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.ephyra.acropolis.persistence.api.persistence.GroupingPersistence
import org.ephyra.acropolis.persistence.api.persistence.NetworkPersistence
import org.ephyra.acropolis.service.api.*
import org.ephyra.acropolis.service.impl.NetworkService

/**
 * Tests for the network service
 */
class NetworkServiceTest : StringSpec() {
    private lateinit var fixture: NetworkServiceTestFixture

    override fun listeners(): List<TestListener> = listOf(FixtureListener {
        fixture = NetworkServiceTestFixture()
        MockKAnnotations.init(fixture, relaxUnitFun = true)
    })

    init {
        "Create network with project not found, throws exception" {
            fixture.givenProjectNotFound()
            fixture.whenNetworkCreatedExceptionIsThrown()
        }

        "Create a network" {
            fixture.givenProjectExists()
            fixture.whenNetworkCreated()
            fixture.thenNetworkCreated()
        }

        "Get network by name not found, no network found" {
            fixture.givenNetworkNotFoundByName()
            fixture.whenNetworkLookedUpByNameThenNothingFound()
        }

        "Get network by name" {
            fixture.givenNetworkFoundByName()
            fixture.whenNetworkLookedUpByNameThenNetworkFound()
        }
    }
}

/**
 * Fixture to encapsulate state for the network service tests
 */
@Suppress("TooManyFunctions")
internal class NetworkServiceTestFixture {
    @MockK
    private lateinit var persistence: NetworkPersistence

    @MockK
    private lateinit var groupingPersistence: GroupingPersistence

    @MockK
    private lateinit var projectService: IProjectService

    @MockK
    private lateinit var datastoreService: IDatastoreService

    @MockK
    private lateinit var applicationSoftwareService: IApplicationSoftwareService

    @MockK
    private lateinit var systemSoftwareService: ISystemSoftwareService

    @InjectMockKs
    var testClass: INetworkService = NetworkService()

    private val testProjectName = "testProjectName"
    private val testProjectId: Long = 1

    private val testNetworkName = "testNetworkName"
    private val testNetworkId: Long = 2

    private val testDatastoreName = "testDatastoreName"

    private var newGrouping: GroupingEntity? = null
    private var datastoreList: MutableList<DatastoreEntity> = ArrayList()

    fun givenProjectNotFound() {
        every { projectService.find(testProjectName) } returns null
    }

    fun whenNetworkCreatedExceptionIsThrown() {
        val exception = shouldThrow<IllegalStateException> {
            testClass.create(testNetworkName, testProjectName)
        }
        exception.message.shouldBe("Project not found [$testProjectName]")
    }

    fun givenProjectExists() {
        every { projectService.find(testProjectName) } returns mockk()
    }

    fun whenNetworkCreated() {
        testClass.create(testNetworkName, testProjectName)
    }

    fun thenNetworkCreated() {
        verify { persistence.create(entity = any()) }
    }

    fun givenNetworkNotFoundByName() {
        every { persistence.findByName(testNetworkName, testProjectId) } returns null
    }

    fun whenNetworkLookedUpByNameThenNothingFound() {
        testClass.find(testNetworkName, testProjectId).shouldBe(null)
    }

    fun givenNetworkFoundByName() {
        every { persistence.findByName(testNetworkName, testProjectId) } returns mockk()
    }

    fun whenNetworkLookedUpByNameThenNetworkFound() {
        testClass.find(testNetworkName, testProjectId).shouldNotBe(null)
    }
}
