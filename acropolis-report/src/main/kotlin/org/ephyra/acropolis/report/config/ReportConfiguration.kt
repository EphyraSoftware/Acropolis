package org.ephyra.acropolis.report.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Configuration root for the module. By directing the Spring framework to load this class as configuration
 * the entire module will be configured for use.
 */
@Configuration
@ComponentScan(basePackages = ["org.ephyra.acropolis.report.config", "org.ephyra.acropolis.report.impl"])
open class ReportConfiguration
