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
import org.ephyra.acropolis.persistence.api.persistence.ReverseProxyPersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IReverseProxyService
import org.ephyra.acropolis.service.impl.ReverseProxyService

/**
 * Tests for the reverse proxy service
 */
class ReverseProxyServiceTest : StringSpec() {
    private lateinit var fixture: ReverseProxyServiceTestFixture

    override fun listeners(): List<TestListener> = listOf(FixtureListener {
        fixture = ReverseProxyServiceTestFixture()
    })

    init {
        "Create reverse proxy with base software missing, throws exception" {
            val baseSoftwareId: Long = 1
            fixture.givenBaseSoftwareDoesNotExist(baseSoftwareId)
            fixture.whenReverseProxyCreatedThenExceptionThrown(baseSoftwareId)
        }

        "Create reverse proxy" {
            val baseSoftwareId: Long = 2
            fixture.givenBaseSoftwareExists(baseSoftwareId)
            fixture.whenReverseProxyCreated(baseSoftwareId)
            fixture.thenReverseProxyCreated()
            fixture.thenSpecializationUpdated()
        }
    }
}

/**
 * Fixture to encapsulate state for the reverse proxy service tests
 */
internal class ReverseProxyServiceTestFixture {
    private val persistence: ReverseProxyPersistence = mockk(relaxUnitFun = true)

    private val systemSoftwarePersistence: SystemSoftwarePersistence = mockk(relaxUnitFun = true)

    private var testClass: IReverseProxyService = ReverseProxyService(persistence, systemSoftwarePersistence)

    private var specialization: SystemSoftwareSpecializationEntity? = null

    internal fun givenBaseSoftwareDoesNotExist(baseSoftwareId: Long) {
        every { systemSoftwarePersistence.find(baseSoftwareId) } returns null
    }

    internal fun whenReverseProxyCreatedThenExceptionThrown(baseSoftwareId: Long) {
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

    internal fun whenReverseProxyCreated(baseSoftwareId: Long) {
        testClass.create(baseSoftwareId)
    }

    internal fun thenReverseProxyCreated() {
        verify { persistence.create(entity = any()) }
    }

    internal fun thenSpecializationUpdated() {
        specialization.shouldNotBe(null)
        verify { systemSoftwarePersistence.update(systemSoftware = any()) }
    }
}
