package org.ephyra.acropolis.external

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.ephyra.acropolis.external.model.Project
import org.slf4j.LoggerFactory

class YamlHelper {
    private val logger = LoggerFactory.getLogger(YamlHelper::class.java)

    fun loadFromString(yamlData: String): Project? {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())
        return try {
            mapper.readValue(yamlData, Project::class.java)
        }
        catch (e: Exception) {
            logger.error("Failed to load project from string [$yamlData]", e)
            null
        }
    }
}