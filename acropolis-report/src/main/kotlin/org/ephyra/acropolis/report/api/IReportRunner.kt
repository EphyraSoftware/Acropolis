package org.ephyra.acropolis.report.api

import org.ephyra.acropolis.report.api.model.GraphContainer

/**
 * Interface for a report runner. This interface provides the entry point into this module.
 */
interface IReportRunner {
    /**
     * Run a report based on the provided model
     *
     * @param graphContainer The container for the model to build the report from
     * @param imageSource Source to allow images to be provided during the rendering process
     */
    fun run(graphContainer: GraphContainer, imageSource: IImageSource)
}
