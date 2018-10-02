package org.ephyra.acropolis.service

import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareSpecializationEntity
import org.ephyra.acropolis.persistence.api.persistence.DatastorePersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IDatastoreService
import org.ephyra.acropolis.service.impl.DatastoreService

/**
 * Tests for the datastore service
 */
class DatastoreServiceTest : StringSpec() {
    private lateinit var fixture: DatastoreServiceTestFixture

    override fun listeners(): List<TestListener> = listOf(FixtureListener {
        fixture = DatastoreServiceTestFixture()
    })

    init {
        "Create datastore with base software missing, throws exception" {
            val baseSoftwareId: Long = 1
            fixture.givenBaseSoftwareDoesNotExist(baseSoftwareId)
            fixture.whenDatastoreCreatedThenExceptionThrown(baseSoftwareId)
        }

        "Create datastore" {
            val baseSoftwareId: Long = 2
            fixture.givenBaseSoftwareExists(baseSoftwareId)
            fixture.whenDatastoreCreated(baseSoftwareId)
            fixture.thenDatastoreCreated()
            fixture.thenSpecializationUpdated()
        }
    }
}

/**
 * Fixture to encapsulate state for the datastore service tests
 */
internal class DatastoreServiceTestFixture {
    private val persistence: DatastorePersistence = mockk(relaxUnitFun = true)

    private val systemSoftwarePersistence: SystemSoftwarePersistence = mockk(relaxUnitFun = true)

    private var testClass: IDatastoreService = DatastoreService(persistence, systemSoftwarePersistence)

    private var specialization: SystemSoftwareSpecializationEntity? = null

    internal fun givenBaseSoftwareDoesNotExist(baseSoftwareId: Long) {
        every { systemSoftwarePersistence.find(baseSoftwareId) } returns null
    }

    internal fun whenDatastoreCreatedThenExceptionThrown(baseSoftwareId: Long) {
        val exception = shouldThrow<IllegalStateException> {
            testClass.create(baseSoftwareId)
        }
        exception.message.shouldBe("Cannot specialize system software because no system software exists with id [$baseSoftwareId]")
    }

    internal fun givenBaseSoftwareExists(baseSoftwareId: Long) {
        val mockSystemSoftwareEntity: SystemSoftwareEntity = mockk()
        every { mockSystemSoftwareEntity.specialization = any() } propertyType SystemSoftwareSpecializationEntity::class answers { specialization = value }
        every { systemSoftwarePersistence.find(baseSoftwareId) } returns mockSystemSoftwareEntity
    }

    internal fun whenDatastoreCreated(baseSoftwareId: Long) {
        testClass.create(baseSoftwareId)
    }

    internal fun thenDatastoreCreated() {
        verify { persistence.create(datastore = any()) }
    }

    internal fun thenSpecializationUpdated() {
        specialization.shouldNotBe(null)
        verify { systemSoftwarePersistence.update(systemSoftware = any()) }
    }
}

