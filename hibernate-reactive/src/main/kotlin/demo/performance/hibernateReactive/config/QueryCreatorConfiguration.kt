package demo.performance.hibernateReactive.config

import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.hibernate.reactive.provider.ReactivePersistenceProvider
import org.hibernate.reactive.session.ReactiveSession
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URL
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.persistence.SharedCacheMode
import javax.persistence.ValidationMode
import javax.persistence.spi.ClassTransformer
import javax.persistence.spi.PersistenceUnitInfo
import javax.persistence.spi.PersistenceUnitTransactionType
import javax.sql.DataSource


@Configuration
@ConditionalOnClass(ReactiveSession::class)
class QueryCreatorConfiguration {
    @Bean
    @ConditionalOnMissingBean(SubqueryCreator::class)
    fun subqueryCreator(): SubqueryCreator {
        return SubqueryCreatorImpl()
    }

    @Bean
    fun entityManagerFactory(): EntityManagerFactory {
        val persistenceUnitInfo = object : PersistenceUnitInfo {
            override fun getPersistenceUnitName(): String {
                return "default"
            }

            override fun getPersistenceProviderClassName(): String {
                return ReactivePersistenceProvider::class.java.name
            }

            override fun getTransactionType(): PersistenceUnitTransactionType {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL
            }

            override fun getJtaDataSource(): DataSource? {
                return null
            }

            override fun getNonJtaDataSource(): DataSource? {
                return null
            }

            override fun getMappingFileNames(): MutableList<String> {
                return mutableListOf()
            }

            override fun getJarFileUrls(): MutableList<URL> {
                return mutableListOf()
            }

            override fun getPersistenceUnitRootUrl(): URL? {
                return null
            }

            override fun getManagedClassNames(): MutableList<String> {
                return mutableListOf("demo.performance.hibernateReactive.entity.User")
            }

            override fun excludeUnlistedClasses(): Boolean {
                return false
            }

            override fun getSharedCacheMode(): SharedCacheMode {
                return SharedCacheMode.ALL
            }

            override fun getValidationMode(): ValidationMode? {
                return null
            }

            override fun getProperties(): Properties {
                return Properties()
            }

            override fun getPersistenceXMLSchemaVersion(): String? {
                return null
            }

            override fun getClassLoader(): ClassLoader? {
                return null
            }

            override fun addTransformer(transformer: ClassTransformer?) {
            }

            override fun getNewTempClassLoader(): ClassLoader? {
                return null
            }
        }

        return ReactivePersistenceProvider().createContainerEntityManagerFactory(
            persistenceUnitInfo,
            mutableMapOf(
                "javax.persistence.jdbc.url" to "jdbc:mysql://localhost:3306/performance",
                "javax.persistence.jdbc.user" to "root",
                "javax.persistence.jdbc.password" to "demo-password",
                "hibernate.connection.pool_size" to "100",
                "hibernate.show_sql" to "false",
                "hibernate.vertx.pool.connect_timeout" to "0",
                "hibernate.jdbc.batch_size" to "1000",
                "hibernate.order_inserts" to "true"
            )
        )
    }

    @Bean
    fun mutinySessionFactory(entityManagerFactory: EntityManagerFactory): SessionFactory {
        return entityManagerFactory.unwrap(SessionFactory::class.java)
    }

    @Bean
    fun queryFactory(sessionFactory: SessionFactory, subqueryCreator: SubqueryCreator): SpringDataHibernateMutinyReactiveQueryFactory {
        return SpringDataHibernateMutinyReactiveQueryFactory(
            sessionFactory = sessionFactory,
            subqueryCreator = subqueryCreator
        )
    }
}
