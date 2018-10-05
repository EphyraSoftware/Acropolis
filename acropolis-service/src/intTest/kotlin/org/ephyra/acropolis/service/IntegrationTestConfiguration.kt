package org.ephyra.acropolis.service

import org.ephyra.acropolis.service.config.ServiceConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource
import com.google.common.base.Preconditions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import java.lang.IllegalStateException
import java.util.*

@Configuration
@ComponentScan(basePackageClasses = [ServiceConfiguration::class])
class IntegrationTestConfiguration {
    @Autowired
    lateinit var environment: Environment

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

        dataSource.setDriverClassName(getProperty("spring.datasource.driverClassName"))
        dataSource.url = Preconditions.checkNotNull(getProperty("spring.datasource.url"))
        dataSource.username = Preconditions.checkNotNull(getProperty("spring.datasource.username"))
        dataSource.password = Preconditions.checkNotNull(getProperty("spring.datasource.password"))

        return dataSource
    }

    fun additionalProperties(): Properties {
        val hibernateProperties = Properties()
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", getProperty("spring.jpa.hibernate.ddl-auto"))
        hibernateProperties.setProperty("hibernate.dialect", getProperty("spring.jpa.hibernate.dialect"))
        return hibernateProperties
    }

    private fun getProperty(key: String): String {
        return environment.getProperty(key)
                ?: throw IllegalStateException("Missing property [$key]")
    }
}