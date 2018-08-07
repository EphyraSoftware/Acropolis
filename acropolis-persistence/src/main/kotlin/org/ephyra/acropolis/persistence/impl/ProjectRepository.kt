package org.ephyra.acropolis.persistence.impl

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

interface ProjectRepository : CrudRepository<ProjectEntity, Long>
