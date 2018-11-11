package org.ephyra.acropolis.shell

import org.ephyra.acropolis.persistence.api.GraphicalAssetType
import org.ephyra.acropolis.service.api.IGraphicalAssetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import java.io.File

@ShellComponent
class BootstrapCommand {
    @Autowired
    private lateinit var graphicalAssetService: IGraphicalAssetService

    @ShellMethod("Bootstrap Acropolis")
    fun bootstrap() {
        println("Bootstrapping the acropolis system.")

        importGraphicalAssets()

        println("Done. Acropolis is now ready to use!")
    }

    private fun importGraphicalAssets() {
        println("Importing graphical assets")

        graphicalAssetService.create(
                "load-balancer",
                File("user/graphics/load-balancer.png").readBytes(),
                GraphicalAssetType.PNG
        )

        graphicalAssetService.create(
                "reverse-proxy",
                File("user/graphics/reverse-proxy.png").readBytes(),
                GraphicalAssetType.PNG
        )

        graphicalAssetService.create(
                "queue",
                File("user/graphics/queue.png").readBytes(),
                GraphicalAssetType.PNG
        )

        graphicalAssetService.create(
                "datastore",
                File("user/graphics/datastore.png").readBytes(),
                GraphicalAssetType.PNG
        )

        graphicalAssetService.create(
                "application",
                File("user/graphics/application.png").readBytes(),
                GraphicalAssetType.PNG
        )

        graphicalAssetService.create(
                "system",
                File("user/graphics/system.png").readBytes(),
                GraphicalAssetType.PNG
        )
    }
}