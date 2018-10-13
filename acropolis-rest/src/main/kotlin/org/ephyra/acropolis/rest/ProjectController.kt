package org.ephyra.acropolis.rest

import org.ephyra.acropolis.persistence.api.entity.ProjectEntity
import org.ephyra.acropolis.service.api.IProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Controller to handle RESTful requests for projects.
 */
@RestController
class ProjectController {

    @Autowired
    private lateinit var service: IProjectService

    /**
     * Creates a project.
     */
    @RequestMapping(value = ["/projects"], method = [(RequestMethod.POST)])
    @ResponseStatus(value = HttpStatus.OK)
    fun createProject(@RequestParam(value = "name", defaultValue = "World") name: String) {
        service.create(name)
    }

    /**
     * Deletes a project by id.
     */
    @RequestMapping(value = ["/project/{id}"], method = [(RequestMethod.DELETE)])
    fun deleteProject(@PathVariable("id") id: Long) {
        service.delete(id)
    }

    /**
     * Gets a project by id.
     */
    @RequestMapping(value = ["/projects"], method = [(RequestMethod.GET)])
    fun getProjects(): List<ProjectEntity> {
        return service.list()
    }

}
