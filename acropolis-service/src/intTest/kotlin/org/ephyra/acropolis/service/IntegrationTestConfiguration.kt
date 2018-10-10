package org.ephyra.acropolis.service

import org.ephyra.acropolis.service.config.ServiceConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource

/**
 * Configuration class for loading consistent configuration across integration tests
 */
@Configuration
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

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver")
        dataSource.url = "jdbc:mysql://localhost:3306/acropolisdev"
        dataSource.username = "root"
        dataSource.password = "root"

        return dataSource
    }

    fun additionalProperties(): Properties {
        val hibernateProperties = Properties()
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create")
        // TODO (Gregory Jensen 11/10/2018) This should be the MySQL8 dialect but that isn't available in the version of hibernate distributed with spring-data-jpa
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
        return hibernateProperties
    }
}