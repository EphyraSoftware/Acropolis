package org.ephyra.acropolis.service

import com.google.common.base.Preconditions
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

        dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"))
        dataSource.url = Preconditions.checkNotNull(environment.getRequiredProperty("spring.datasource.url"))
        dataSource.username = Preconditions.checkNotNull(environment.getRequiredProperty("spring.datasource.username"))
        dataSource.password = Preconditions.checkNotNull(environment.getRequiredProperty("spring.datasource.password"))

        return dataSource
    }

    fun additionalProperties(): Properties {
        val hibernateProperties = Properties()
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", environment.getRequiredProperty("spring.jpa.hibernate.ddl-auto"))
        hibernateProperties.setProperty("hibernate.dialect", environment.getRequiredProperty("spring.jpa.hibernate.dialect"))
        return hibernateProperties
    }
}