package org.ephyra.acropolis.service

import io.kotlintest.Description
import io.kotlintest.extensions.TestListener

/**
 * Kotlin-test TestListener for setting up fixtures before each test.
 */
class FixtureListener(
        val fixtureGenerator: () -> Unit
) : TestListener {
    override fun beforeTest(description: Description) {
        fixtureGenerator()
    }
}