package org.ephyra.acropolis.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface ProjectRepository : JpaRepository<ProjectEntity, Long>
