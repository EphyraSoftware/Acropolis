package org.ephyra.acropolis.service.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Spring configuration for this module. This class must be loaded by Spring Boot
 * in order to configure this module correctly.
 */
@Configuration
@ComponentScan(basePackages = ["org.ephyra.acropolis.persistence.config", "org.ephyra.acropolis.service.impl"])
class ServiceConfiguration
