package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.report.api.IReportRunner
import org.ephyra.acropolis.service.api.IApplicationSoftwareService
import org.ephyra.acropolis.service.api.IReportService
import org.ephyra.acropolis.service.api.ISystemSoftwareService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Report service implementation
 */
@Service
class ReportService : IReportService {
    private val logger: Logger = LoggerFactory.getLogger(ApplicationSoftwareService::class.java)

    @Autowired
    private lateinit var applicationService: IApplicationSoftwareService

    @Autowired
    private lateinit var systemService: ISystemSoftwareService

    @Autowired
    private lateinit var reportRunner: IReportRunner

    override fun runSoftwareReport(projectName: String) {
        logger.trace("Starting to run software report for project [$projectName]")

        applicationService.findAll(projectName)
        systemService.findAll(projectName)
    }
}
