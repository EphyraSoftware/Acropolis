package org.ephyra.acropolis.service

import io.kotlintest.Description
import io.kotlintest.Spec
import io.kotlintest.extensions.TestListener
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.impl.ApplicationSoftwareService

/**
 * Tests for the application-software service.
 */
class ApplicationSoftwareServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: ApplicationSoftwarePersistence

    @InjectMockKs
    var testClass: IApplicationSoftwareService = ApplicationSoftwareService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    init {
        "Create a new application software" {
            testClass.create(1, "my-app")
            verify { persistence.create(application = any()) }
        }
    }
}

/**
 * Helper for initialising MockK on a test class.
 *
 * Uses a kotlin-test TestListener to instruct MockK to scan for annotations before
 * any tests run.
 */
class MockKInitializer (
        private var initMe: ApplicationSoftwareServiceTest
) : TestListener {

    override fun beforeSpec(description: Description, spec: Spec) {
        super.beforeSpec(description, spec)

        MockKAnnotations.init(initMe)
    }
}
