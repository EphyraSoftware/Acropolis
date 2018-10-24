package org.ephyra.acropolis.shell

import org.ephyra.acropolis.service.api.IExportService
import org.ephyra.acropolis.service.api.ImportType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import java.io.File

/**
 * Shell command for running exports
 */
@ShellComponent
class ExportCommand {
    @Autowired
    lateinit var appState: AppState

    @Autowired
    lateinit var exportService: IExportService

    /**
     * The export method for the export command
     */
    @ShellMethod("Command for running exports")
    fun export(filePath: String) {
        val currentProject = appState.currentProject
        if (currentProject == null) {
            println("No project selected")
            return
        }

        val file = File(filePath)
        if (file.exists()) {
            println("The specified file [$filePath] already exists")
            return
        }
        else if (file.extension != "yml") {
            println("Expected a YAML file with extension [yml] to export to but found extension [${file.extension}]")
            return
        }

        val exportData = exportService.export(currentProject.name, ImportType.YAML)
        if (exportData == null) {
            println("Failed to export project [${currentProject.name}]")
            return
        }

        file.writeText(exportData)
    }
}
