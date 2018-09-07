package org.ephyra.acropolis.service

import io.kotlintest.extensions.TestListener
import io.kotlintest.matchers.string.shouldStartWith
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrowAny
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.persistence.DatastorePersistence
import org.ephyra.acropolis.service.api.IDatastoreService
import org.ephyra.acropolis.service.impl.DatastoreService

/**
 * Tests for the datastore service
 */
class DatastoreServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: DatastorePersistence

    @InjectMockKs
    var testClass: IDatastoreService = DatastoreService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    init {
        "Create a new datastore" {
            testClass.create(1, "my-store")
            verify { persistence.create(datastore = any()) }
        }

        "Create a new datastore, fails to save" {
            every { persistence.create(datastore= any()) } throws Exception("failed to save")

            val exception = shouldThrowAny {
                testClass.create(1, "my-store")
            }
            exception.message.shouldStartWith("failed to save")
        }

        "Find datastore by name, not found" {
            val name = "my-store"

            every { persistence.findByName(name) } returns null

            testClass.get(name).shouldBe(null)

            verify { persistence.findByName(name) }
        }

        "Find datastore by name" {
            val name = "my-store"

            every { persistence.findByName(name) } returns mockk()

            testClass.get(name).shouldNotBe(null)

            verify { persistence.findByName(name) }
        }

        "Find datastore by name, fails to lookup" {
            every { persistence.findByName(name = any()) } throws Exception("failed to lookup")

            val exception = shouldThrowAny {
                testClass.get("")
            }
            exception.message.shouldStartWith("failed to lookup")
        }
    }
}