package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.ReverseProxyEntity
import org.springframework.data.repository.CrudRepository

internal interface ReverseProxyRepository : CrudRepository<ReverseProxyEntity, Long>
