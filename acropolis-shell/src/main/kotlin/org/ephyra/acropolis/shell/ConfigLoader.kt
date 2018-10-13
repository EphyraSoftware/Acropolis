package org.ephyra.acropolis.shell

import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.PropertySource

/**
 * Custom loader for YAML properties.
 *
 * This is a work around for Spring Boot's inability to load multiple YAML files containing properties.
 */
class ConfigLoader : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private var configLocations = arrayOf(
            Pair("persistence-application-yml", "classpath:application.persistence.yml")
    )

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        configLocations.forEach { configLocation ->
            loadConfig(applicationContext, configLocation.first, configLocation.second)
        }
    }

    private fun loadConfig(
            applicationContext: ConfigurableApplicationContext,
            configName: String,
            configLocation: String
    ) {
        val resource = applicationContext.getResource(configLocation)

        val loader = YamlPropertySourceLoader()
        val properties: MutableList<PropertySource<*>>? = loader.load(configName, resource)

        applicationContext.environment
                .propertySources
                .addLast(properties?.elementAt(0)!!)
    }
}
