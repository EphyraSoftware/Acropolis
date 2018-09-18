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
import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.ephyra.acropolis.persistence.api.persistence.GroupingPersistence
import org.ephyra.acropolis.persistence.api.persistence.NetworkPersistence
import org.ephyra.acropolis.service.api.*
import org.ephyra.acropolis.service.impl.NetworkService
import java.lang.IllegalStateException

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

        "Link datastore with network not found, throws exception" {
            fixture.givenNetworkNotFoundById()
            fixture.whenDatastoreLinkedThenExceptionThrownForMissingNetwork()
        }

        "Link datastore with datastore not found, throws exception" {
            fixture.givenNetworkFoundById()
            fixture.givenDatastoreNotFoundById()
            fixture.whenDatastoreLinkedThenExceptionThrownForMissingDatastore()
        }

        "Link datastore to network with no existing links" {
            fixture.givenNetworkWithNoGroupingIsFoundById()
            fixture.givenDatastoreFoundByName()
            fixture.whenDatastoreLinked()
            fixture.thenNewGroupingCreated()
            fixture.thenNewGroupingLinkedToNetwork()
        }

        "Link datastore to network with existing links" {
            fixture.givenNetworkWithExistingGroupingIsFoundById()
            fixture.givenDatastoreFoundByName()
            fixture.whenDatastoreLinked()
            fixture.thenTheGroupingIsUpdated()
            fixture.thenDatastoreLinkedToNetwork()
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
        every { projectService.get(testProjectName) } returns null
    }

    fun whenNetworkCreatedExceptionIsThrown() {
        val exception = shouldThrow<IllegalStateException> {
            testClass.create(testNetworkName, testProjectName)
        }
        exception.message.shouldBe("Project not found [$testProjectName]")
    }

    fun givenProjectExists() {
        every { projectService.get(testProjectName) } returns mockk()
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
        testClass.get(testNetworkName, testProjectId).shouldBe(null)
    }

    fun givenNetworkFoundByName() {
        every { persistence.findByName(testNetworkName, testProjectId)} returns mockk()
    }

    fun whenNetworkLookedUpByNameThenNetworkFound() {
        testClass.get(testNetworkName, testProjectId).shouldNotBe(null)
    }

    fun givenNetworkNotFoundById() {
        every { persistence.find(testNetworkId, testProjectId) } returns null
    }

    fun whenDatastoreLinkedThenExceptionThrownForMissingNetwork() {
        val exception = shouldThrow<IllegalStateException> {
            testClass.linkDatastore(testNetworkId, testDatastoreName, testProjectId)
        }
        exception.message.shouldBe("Cannot link datastore to network because network with id [$testNetworkId] was not found")
    }

    fun givenNetworkFoundById() {
        every { persistence.find(testNetworkId, testProjectId) } returns mockk()
    }

    fun givenDatastoreNotFoundById() {
        every { datastoreService.get(testDatastoreName, testProjectId) } returns null
    }

    fun whenDatastoreLinkedThenExceptionThrownForMissingDatastore() {
        val exception = shouldThrow<IllegalStateException> {
            testClass.linkDatastore(testNetworkId, testDatastoreName, testProjectId)
        }
        exception.message.shouldBe("Cannot link datastore to network because datastore with name [$testDatastoreName] was not found")
    }

    fun givenNetworkWithNoGroupingIsFoundById() {
        val network: NetworkEntity = mockk()
        every { network.groupingEntity } returns null
        every { network.groupingEntity = any() } propertyType GroupingEntity::class answers {
            newGrouping = value
        }
        every { persistence.find(testNetworkId, testProjectId) } returns network
    }

    fun givenDatastoreFoundByName() {
        every { datastoreService.get(testDatastoreName, testProjectId) } returns mockk()
    }

    fun whenDatastoreLinked() {
        testClass.linkDatastore(testNetworkId, testDatastoreName, testProjectId)
    }

    fun thenNewGroupingCreated() {
        verify { groupingPersistence.create(entity = any()) }
    }

    fun thenNewGroupingLinkedToNetwork() {
        // Means that the grouping entity has been assigned to the groupingEntity property on the network
        newGrouping.shouldNotBe(null)
        verify { persistence.updateGrouping(entity = any()) }
    }

    fun givenNetworkWithExistingGroupingIsFoundById() {
        val network: NetworkEntity = mockk()

        val grouping: GroupingEntity = mockk()
        every { grouping.datastoreList } returns datastoreList

        every { network.groupingEntity } returns grouping
        every { persistence.find(testNetworkId, testProjectId) } returns network
    }

    fun thenTheGroupingIsUpdated() {
        verify { groupingPersistence.update(entity = any()) }
    }

    fun thenDatastoreLinkedToNetwork() {
        datastoreList.size.shouldBe(1)
    }
}

