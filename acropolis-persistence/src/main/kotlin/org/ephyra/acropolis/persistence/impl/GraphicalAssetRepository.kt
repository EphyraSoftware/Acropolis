package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.GraphicalAssetEnttiy
import org.springframework.data.repository.CrudRepository

interface GraphicalAssetRepository : CrudRepository<GraphicalAssetEnttiy, Long>