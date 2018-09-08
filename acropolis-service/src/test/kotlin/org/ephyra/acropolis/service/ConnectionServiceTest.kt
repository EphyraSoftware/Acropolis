package org.ephyra.acropolis.service

import io.kotlintest.extensions.TestListener
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.persistence.ConnectionPersistence
import org.ephyra.acropolis.service.api.IConnectionService
import org.ephyra.acropolis.service.impl.ConnectionService

/**
 * Tests for the connection service
 */
class ConnectionServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: ConnectionPersistence

    @InjectMockKs
    var testClass: IConnectionService = ConnectionService()

    override fun listeners(): List<TestListener> = listOf(MockKInitializer(this))

    init {
        "Create a new TALKS_TO connection" {
            val connectFrom: IConnectable = mockk()
            every { connectFrom.getConnectionId() } returns 1
            every { connectFrom.getConnectionEndpointType() } returns ConnectionEndpointType.APPLICATION_SOFTWARE.type

            val connectTo: IConnectable = mockk()
            every { connectTo.getConnectionId() } returns 2
            every { connectTo.getConnectionEndpointType() } returns ConnectionEndpointType.SYSTEM_SOFTWARE.type

            testClass.create(connectFrom, connectTo, ConnectionType.TALKS_TO)
            verify { persistence.create(connection = any()) }
        }
    }
}