package org.ephyra.acropolis.persistence.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan("org.ephyra.acropolis.persistence.impl")
@EnableJpaRepositories("org.ephyra.acropolis.persistence.impl")
@EntityScan("org.ephyra.acropolis.persistence.impl")
class PersistenceConfiguration
