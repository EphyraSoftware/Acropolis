package org.ephyra.acropolis.report.api

import org.ephyra.acropolis.report.api.model.GraphContainer

interface IReportRunner {
    fun run(graphContainer: GraphContainer, imageSource: IImageSource)
}
