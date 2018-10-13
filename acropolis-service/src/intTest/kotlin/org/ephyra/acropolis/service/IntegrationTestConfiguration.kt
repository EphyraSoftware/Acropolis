package org.ephyra.acropolis.service

import org.ephyra.acropolis.service.config.ServiceConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.EntityManagerFactoryAccessor
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

/**
 * Configuration class for loading consistent configuration across integration tests
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackageClasses = [ServiceConfiguration::class])
class IntegrationTestConfiguration {
    @Autowired
    lateinit var environment: Environment

    /**
     * Bean constructor for an entityManagerFactory
     */
    @Bean
    fun entityManagerFactory(@Autowired dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.setPackagesToScan("org.ephyra.acropolis.persistence")

        val vendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.jpaVendorAdapter = vendorAdapter
        entityManagerFactoryBean.setJpaProperties(additionalProperties())

        return entityManagerFactoryBean
    }

    /**
     * Bean constructor for a dataSource
     */
    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()

        dataSource.setDriverClassName(environment.getRequiredProperty("datastore.driverClassName"))
        dataSource.url = environment.getRequiredProperty("datastore.url")
        dataSource.username = environment.getRequiredProperty("datastore.username")
        dataSource.password = environment.getRequiredProperty("datastore.password")

        return dataSource
    }

    /**
     * Bean constructor for a transactionManager
     */
    @Bean
    fun transactionManager(
            @Autowired entityManagerFactoryBean: EntityManagerFactory,
            @Autowired dataSource: DataSource
    ): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()

        transactionManager.entityManagerFactory = entityManagerFactoryBean
        transactionManager.dataSource = dataSource

        return transactionManager
    }

    /**
     * Create additional properties for the entityManagerFactory
     *
     * This is where hibernate settings are loaded
     */
    fun additionalProperties(): Properties {
        val hibernateProperties = Properties()
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
                environment.getRequiredProperty("datastore.hibernate.ddl-auto"))
        /* TODO (Gregory Jensen 11/10/2018) This should be the MySQL8 dialect but that isn't available in the version
           of hibernate distributed with spring-data-jpa */
        hibernateProperties.setProperty("hibernate.dialect",
                environment.getRequiredProperty("datastore.hibernate.dialect"))
        return hibernateProperties
    }
}
