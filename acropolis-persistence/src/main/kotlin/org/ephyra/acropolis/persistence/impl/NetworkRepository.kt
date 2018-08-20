package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.NetworkEntity
import org.springframework.data.repository.CrudRepository

interface NetworkRepository : CrudRepository<NetworkEntity, Long>
