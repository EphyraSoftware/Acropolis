package org.ephyra.acropolis.service.impl

import org.ephyra.acropolis.persistence.api.entity.ComputeInstanceEntity
import org.ephyra.acropolis.persistence.api.persistence.ApplicationSoftwarePersistence
import org.ephyra.acropolis.persistence.api.persistence.ComputeInstancePersistence
import org.ephyra.acropolis.persistence.api.persistence.SystemSoftwarePersistence
import org.ephyra.acropolis.service.api.IComputeInstanceService
import org.ephyra.acropolis.service.api.IProjectService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Service for interactions and mutations around ComputeInstanceEntity
 * */
@Service
class ComputeInstanceService : IComputeInstanceService {

    val Logger = LoggerFactory.getLogger(ComputeInstanceService::class.java)

    @Autowired
    private lateinit var persistence: ComputeInstancePersistence

    @Autowired
    private lateinit var applicationSoftwarePersistence: ApplicationSoftwarePersistence

    @Autowired
    private lateinit var systemSoftwarePersistence: SystemSoftwarePersistence

    @Autowired
    private lateinit var projectService: IProjectService

    /**
     * Creates a new entity, to be associated with the given project
     * @param name the name of the entity to create
     * @param projectName the name of the project to associate this entity with
     */
    override fun create(name: String, projectName: String) {
        val project = projectService.find(projectName)

        if (project == null) {
            Logger.error("Could not find project with name [$projectName]")
            throw IllegalStateException("Project not found [$projectName]")
        }

        val entity = ComputeInstanceEntity(name, project)
        persistence.create(entity)
    }

    /**
     * Find an instance with the given name that exists within the scope of the given project ID
     * @param name the name of the entity to try and find
     * @param projectId the ID of the project to scope the query to
     * @return an instance of the entity if found, or nil
     */
    override fun find(name: String, projectId: Long): ComputeInstanceEntity? {
        return persistence.findByName(name, projectId)
    }

    /**
     * Include an application software in the specified network.
     *
     * @param computeInstanceId The id of the computeInstance to link to
     * @param applicationSoftwareName The name of the application software to be linked
     * @param projectId The parent project for the application software
     */
    override fun linkApplicationSoftware(computeInstanceId: Long, applicationSoftwareName: String, projectId: Long) {
        Logger.info("Linking application-software [$applicationSoftwareName]")
        val computeInstance = persistence.find(computeInstanceId)
                ?: throw IllegalStateException("Cannot link application-software to compute-instance because compute-instance with id [$computeInstanceId] was not found")

        val applicationSoftware = applicationSoftwarePersistence.findByName(applicationSoftwareName, projectId)
                ?: throw IllegalStateException("Cannot link application-software to compute-instance because application-software with name [$applicationSoftwareName] was not found")

        computeInstance.applicationSoftwareList.add(applicationSoftware)
        persistence.update(computeInstance)
    }

    /**
     * Include a system software in the specified network.
     *
     * @param computeInstanceId The id of the computeInstance to link to
     * @param systemSoftwareName The name of the system software to be linked
     * @param projectId The parent project for the system software
     */
    override fun linkSystemSoftware(computeInstanceId: Long, systemSoftwareName: String, projectId: Long) {
        Logger.info("Linking system-software [$systemSoftwareName]")
        val computeInstance = persistence.find(computeInstanceId)
                ?: throw IllegalStateException("Cannot link system-software to compute-instance because compute-instance with id [$computeInstanceId] was not found")

        val systemSoftware = systemSoftwarePersistence.findByName(systemSoftwareName, projectId)
                ?: throw IllegalStateException("Cannot link system-software to compute-instance because system-software with name [$systemSoftwareName] was not found")

        computeInstance.systemSoftwareList.add(systemSoftware)
        persistence.update(computeInstance)
    }
}
