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
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareEntity
import org.ephyra.acropolis.persistence.api.entity.SystemSoftwareSpecializationEntity
import org.ephyra.acropolis.persistence.api.persistence.ReverseProxyPersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IReverseProxyService
import org.ephyra.acropolis.service.impl.ReverseProxyService

class ReverseProxyServiceTest : StringSpec() {
    lateinit var fixture: ReverseProxyServiceTestFixture

    override fun listeners(): List<TestListener> = listOf(FixtureListener {
        fixture = ReverseProxyServiceTestFixture()
        MockKAnnotations.init(fixture)
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

class ReverseProxyServiceTestFixture {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: ReverseProxyPersistence

    @MockK
    lateinit var systemSoftwarePersistence: SystemSoftwarePersistence

    @InjectMockKs
    var testClass: IReverseProxyService = ReverseProxyService()

    private var specialization: SystemSoftwareSpecializationEntity? = null

    fun givenBaseSoftwareDoesNotExist(baseSoftwareId: Long) {
        every { systemSoftwarePersistence.find(baseSoftwareId) } returns null
    }

    fun whenReverseProxyCreatedThenExceptionThrown(baseSoftwareId: Long) {
        val exception = shouldThrow<IllegalStateException> {
            testClass.create(baseSoftwareId)
        }
        exception.message.shouldBe("Cannot specialize system software because no system software exists with id [$baseSoftwareId]")
    }

    fun givenBaseSoftwareExists(baseSoftwareId: Long) {
        val mockSystemSoftwareEntity: SystemSoftwareEntity = mockk()
        every { mockSystemSoftwareEntity.specialization = any() } propertyType SystemSoftwareSpecializationEntity::class answers { specialization = value }
        every { systemSoftwarePersistence.find(baseSoftwareId) } returns mockSystemSoftwareEntity
    }

    fun whenReverseProxyCreated(baseSoftwareId: Long) {
        testClass.create(baseSoftwareId)
    }

    fun thenReverseProxyCreated() {
        verify { persistence.create(entity = any() ) }
    }

    fun thenSpecializationUpdated() {
        specialization.shouldNotBe(null)
        verify { systemSoftwarePersistence.updateSpecialization(systemSoftware = any()) }
    }
}

