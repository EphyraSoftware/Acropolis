package org.ephyra.acropolis.service.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["org.ephyra.acropolis.persistence.config", "org.ephyra.acropolis.service.impl"])
class ServiceConfiguration
