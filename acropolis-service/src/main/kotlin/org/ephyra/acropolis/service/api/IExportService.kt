package org.ephyra.acropolis.service.api

interface IExportService {
    fun export(projectName: String, exportType: ImportType): String
}
