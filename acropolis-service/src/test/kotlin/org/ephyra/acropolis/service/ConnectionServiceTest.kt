package org.ephyra.acropolis.service

import io.kotlintest.extensions.TestListener
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.ephyra.acropolis.persistence.api.ConnectionEndpointType
import org.ephyra.acropolis.persistence.api.ConnectionType
import org.ephyra.acropolis.persistence.api.IConnectable
import org.ephyra.acropolis.persistence.api.entity.ConnectionEntity
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.persistence.api.persistence.ConnectionPersistence
import org.ephyra.acropolis.persistence.api.persistence.DatastorePersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IConnectionService
import org.ephyra.acropolis.service.impl.ConnectionService

/**
 * Tests for the connection service
 */
class ConnectionServiceTest : StringSpec() {
    @MockK(relaxUnitFun = true)
    lateinit var persistence: ConnectionPersistence

    @MockK
    lateinit var systemSoftwarePersistence: SystemSoftwarePersistence

    @MockK
    lateinit var applicationSoftwarePersistence: ApplicationSoftwarePersistence

    @MockK
    lateinit var datastorePersistence: DatastorePersistence

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

        "Get connections from with no connections found, yields empty result" {
            every { persistence.getConnectionsFrom(fromId = any(), fromEndpointType = any()) } returns listOf()

            val connection: IConnectable = mockk()
            every { connection.getConnectionId() } returns 1
            every { connection.getConnectionEndpointType() } returns ConnectionEndpointType.APPLICATION_SOFTWARE.type

            val connectionsFrom = testClass.getConnectionsFrom(connection)

            connectionsFrom.shouldBeEmpty()
        }

        "Get connections from with invalid connection in lookup, throws exception" {
            // toType 9999 does not exist
            val toConnection = ConnectionEntity(0, 0, 0, 9999)
            every { persistence.getConnectionsFrom(fromId = any(), fromEndpointType = any()) } returns listOf(toConnection)

            val fromConnection: IConnectable = mockk()
            every { fromConnection.getConnectionId() } returns 1
            every { fromConnection.getConnectionEndpointType() } returns ConnectionEndpointType.APPLICATION_SOFTWARE.type

            val exception = shouldThrow<IllegalStateException> {
                testClass.getConnectionsFrom(fromConnection)
            }

            exception.message.shouldBe("Connection entity with connection to unknown type [9999]")
        }

        "Get connections from with valid connections looked up, returns list of connected items" {
            val toSystemSoftwareConnection = ConnectionEntity(1, ConnectionEndpointType.APPLICATION_SOFTWARE.type, 2, ConnectionEndpointType.SYSTEM_SOFTWARE.type)
            val toApplicationSoftwareConnection = ConnectionEntity(1, ConnectionEndpointType.APPLICATION_SOFTWARE.type, 3, ConnectionEndpointType.APPLICATION_SOFTWARE.type)
            val toDatastoreConnection = ConnectionEntity(1, ConnectionEndpointType.APPLICATION_SOFTWARE.type, 4, ConnectionEndpointType.DATASTORE.type)

            every { persistence.getConnectionsFrom(fromId = 1, fromEndpointType = ConnectionEndpointType.APPLICATION_SOFTWARE.type) } returns
                    listOf(toSystemSoftwareConnection, toApplicationSoftwareConnection, toDatastoreConnection)

            val fromConnection: IConnectable = mockk()
            every { fromConnection.getConnectionId() } returns 1
            every { fromConnection.getConnectionEndpointType() } returns ConnectionEndpointType.APPLICATION_SOFTWARE.type

            every { systemSoftwarePersistence.find(id = 2) } returns mockk()
            every { applicationSoftwarePersistence.find(id = 3) } returns mockk()
            every { datastorePersistence.find(id = 4) } returns mockk()

            val connections = testClass.getConnectionsFrom(fromConnection)

            connections.size.shouldBe(3)

            verify { systemSoftwarePersistence.find(2) }
            verify { applicationSoftwarePersistence.find(3) }
            verify { datastorePersistence.find(4) }
        }

        "Get connections from with a missing endpoint looked up, invalid endpoint filtered" {
            val toSystemSoftwareConnection = ConnectionEntity(1, ConnectionEndpointType.APPLICATION_SOFTWARE.type, 2, ConnectionEndpointType.SYSTEM_SOFTWARE.type)
            val toApplicationSoftwareConnection = ConnectionEntity(1, ConnectionEndpointType.APPLICATION_SOFTWARE.type, 3, ConnectionEndpointType.APPLICATION_SOFTWARE.type)
            val toDatastoreConnection = ConnectionEntity(1, ConnectionEndpointType.APPLICATION_SOFTWARE.type, 4, ConnectionEndpointType.DATASTORE.type)

            every { persistence.getConnectionsFrom(fromId = 1, fromEndpointType = ConnectionEndpointType.APPLICATION_SOFTWARE.type) } returns
                    listOf(toSystemSoftwareConnection, toApplicationSoftwareConnection, toDatastoreConnection)

            val fromConnection: IConnectable = mockk()
            every { fromConnection.getConnectionId() } returns 1
            every { fromConnection.getConnectionEndpointType() } returns ConnectionEndpointType.APPLICATION_SOFTWARE.type

            every { systemSoftwarePersistence.find(id = 2) } returns mockk()
            every { applicationSoftwarePersistence.find(id = 3) } returns null
            every { datastorePersistence.find(id = 4) } returns mockk()

            val connections = testClass.getConnectionsFrom(fromConnection)

            connections.size.shouldBe(2)

            verify { systemSoftwarePersistence.find(2) }
            verify { applicationSoftwarePersistence.find(3) }
            verify { datastorePersistence.find(4) }
        }
    }
}
