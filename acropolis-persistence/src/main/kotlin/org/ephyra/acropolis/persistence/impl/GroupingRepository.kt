package org.ephyra.acropolis.persistence.impl

import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.springframework.data.repository.CrudRepository

interface GroupingRepository : CrudRepository<GroupingEntity, Long>