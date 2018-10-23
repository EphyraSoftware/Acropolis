package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.GraphicalAssetEntity
import org.springframework.data.repository.CrudRepository

interface GraphicalAssetRepository : CrudRepository<GraphicalAssetEntity, Long>