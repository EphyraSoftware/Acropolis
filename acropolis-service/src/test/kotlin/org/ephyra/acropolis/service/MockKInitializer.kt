package org.ephyra.acropolis.service

import io.kotlintest.Description
import io.kotlintest.Spec
import io.kotlintest.extensions.TestListener
import io.mockk.MockKAnnotations

/**
 * Helper for initialising MockK on a test class.
 *
 * Uses a kotlin-test TestListener to instruct MockK to scan for annotations before
 * any tests run.
 */
class MockKInitializer (
        private var initMe: Any
) : TestListener {

    override fun beforeSpec(description: Description, spec: Spec) {
        super.beforeSpec(description, spec)

        MockKAnnotations.init(initMe)
    }
}