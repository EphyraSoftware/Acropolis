package org.ephyra.acropolis.report.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["org.ephyra.acropolis.report.config", "org.ephyra.acropolis.report.impl"])
open class ReportConfiguration
