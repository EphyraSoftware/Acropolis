package org.ephyra.acropolis.rest

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class ProjectController {

    @Autowired
    private lateinit var service: IProjectService

    @RequestMapping(value = ["/projects"], method = [(RequestMethod.POST)])
    @ResponseStatus(value = HttpStatus.OK)
    fun createProject(@RequestParam(value="name", defaultValue="World") name: String) {
        service.createProject(name)
    }

    @RequestMapping(value = ["/projects"], method = [(RequestMethod.GET)])
    fun getProjects(): List<ProjectEntity> {
        return service.listProjects()
    }

}
