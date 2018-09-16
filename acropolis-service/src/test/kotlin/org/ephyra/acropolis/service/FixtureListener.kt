package org.ephyra.acropolis.service

import io.kotlintest.Description
import io.kotlintest.extensions.TestListener

class FixtureListener(
        val fixtureGenerator: () -> Unit
) : TestListener {
    override fun beforeTest(description: Description) {
        fixtureGenerator()
    }
}