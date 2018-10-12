package org.ephyra.acropolis.persistence.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Spring configuration for this module. This class must be loaded by Spring Boot
 * in order to configure this module correctly.
 */
@Configuration
@ComponentScan("org.ephyra.acropolis.persistence.api")
@EnableJpaRepositories("org.ephyra.acropolis.persistence.impl")
@EntityScan("org.ephyra.acropolis.persistence.api.entity")
class PersistenceConfiguration
