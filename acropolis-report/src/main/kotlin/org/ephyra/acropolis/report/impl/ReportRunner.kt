package org.ephyra.acropolis.report.impl

import org.ephyra.acropolis.report.api.IReportRunner
import org.ephyra.acropolis.report.api.model.GraphContainer
import org.springframework.stereotype.Component

@Component
private class ReportRunner : IReportRunner {
    override fun run(graphContainer: GraphContainer) {
        println("Running report")
    }
}
