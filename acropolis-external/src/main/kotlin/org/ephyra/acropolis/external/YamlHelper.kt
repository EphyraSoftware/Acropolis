package org.ephyra.acropolis.external

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.ephyra.acropolis.external.model.Project
import org.slf4j.LoggerFactory
import java.io.StringWriter

/**
 * Helper for working with YAML as an external format for the Acropolis external data model.
 */
class YamlHelper {
    private val logger = LoggerFactory.getLogger(YamlHelper::class.java)

    /**
     * Deserialize the input string to a project model.
     *
     * @param yamlData The YAML data to deserialize
     * @return The external project model
     */
    fun loadFromString(yamlData: String): Project? {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())
        return try {
            mapper.readValue(yamlData, Project::class.java)
        } catch (e: JsonMappingException) {
            logger.error("Failed to load project from string [$yamlData]", e)
            null
        }
    }

    /**
     * Serialize the input project model to string.
     *
     * @param project The project to serialize
     * @return The project data as a YAML formatted string
     */
    fun serialize(project: Project): String? {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())
        return try {
            val stringWriter = StringWriter()
            mapper.writeValue(stringWriter, project)
            String(stringWriter.buffer)
        } catch (e: JsonMappingException) {
            logger.error("Failed to convert project with name [${project.name}] from external model to string", e)
            null
        }
    }
}
