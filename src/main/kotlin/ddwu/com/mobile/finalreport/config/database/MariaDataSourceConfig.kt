import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = ["ddwu.com.mobile.finalreport.repository.maria"]
)
@EnableConfigurationProperties(JpaProperties::class)
class MariaDataSourceConfig(
    private val jpaProperties: JpaProperties
) {

    @Bean(name = ["dataSourceProperties"])
    @ConfigurationProperties("spring.datasource")
    fun usersDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean(name = ["dataSource"])
    @ConfigurationProperties("spring.datasource.configuration")
    fun dataSource(@Qualifier("dataSourceProperties") usersDataSourceProperties: DataSourceProperties): DataSource {
        return usersDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean(name = ["entityManagerFactory"])
    fun entityManagerFactory(@Qualifier("dataSource") dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        return LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            setPackagesToScan("ddwu.com.mobile.finalreport.model.entity.maria")
            jpaVendorAdapter = vendorAdapter
            setPersistenceUnitName("entityManager")
            setJpaPropertyMap(getHibernateProperties())
        }
    }

    @Bean(name = ["transactionManager"])
    fun transactionManager(@Qualifier("entityManagerFactory") entityManagerFactory: EntityManagerFactory): JpaTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    private fun getHibernateProperties(): Map<String, Any> {
        val properties = mutableMapOf<String, Any>()
        properties.putAll(jpaProperties.properties)
        properties["hibernate.physical_naming_strategy"] = "ddwu.com.mobile.finalreport.component.jpa.ImprovedNamingStrategy"
        return properties
    }
}
