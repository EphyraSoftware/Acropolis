package org.ephyra.acropolis.persistence.api.persistence

import org.ephyra.acropolis.persistence.api.entity.GroupingEntity
import org.ephyra.acropolis.persistence.impl.GroupingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GroupingPersistence {
    @Autowired
    private lateinit var repo: GroupingRepository

    fun create(grouping: GroupingEntity) {
        repo.save(grouping)
    }
}
