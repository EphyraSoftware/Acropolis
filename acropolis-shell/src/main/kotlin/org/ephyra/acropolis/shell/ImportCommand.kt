package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.GraphicalAssetType
import org.ephyra.acropolis.service.api.IGraphicalAssetService
import org.ephyra.acropolis.service.api.ImportType
import org.ephyra.acropolis.service.impl.ImportService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import java.io.File

/**
 * Shell command for running imports
 */
@ShellComponent
class ImportCommand {
    @Autowired
    lateinit var importService: ImportService

    @Autowired
    lateinit var graphicalAssetService: IGraphicalAssetService

    /**
     * The import method for the import command
     */
    @ShellMethod("Command for running imports")
    fun import(filePath: String) {
        val file = File(filePath)
        if (!file.exists()) {
            println("The file does not exist")
            return
        }

        when (file.extension) {
            "yml" -> importService.importProject(file.readText(), ImportType.YAML)
            "json" -> importService.importProject(file.readText(), ImportType.JSON)
            "png" -> graphicalAssetService.create(file.name, file.readBytes(), GraphicalAssetType.PNG)
            else -> println("Don't know how to import from files of type [${file.extension}]")
        }
    }
}
