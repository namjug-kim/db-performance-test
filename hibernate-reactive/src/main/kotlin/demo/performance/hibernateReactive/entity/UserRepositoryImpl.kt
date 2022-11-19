package demo.performance.hibernateReactive.entity

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.pageQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQueryOrNull
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
    private val sessionFactory: SessionFactory,
) : UserRepository {
    override suspend fun findById(id: UUID): User? {
        return queryFactory.singleQueryOrNull {
            select(entity(User::class))
            from(entity(User::class))
            where(col(User::userId).equal(id))
        }
    }

    override suspend fun findAll(pageable: Pageable): Page<User> {
        return queryFactory.pageQuery(pageable) {
            select(entity(User::class))
            from(entity(User::class))
        }
    }

    override suspend fun save(user: User) {
        sessionFactory.withTransaction { session, _ -> session.persist(user) }
            .awaitSuspending()
    }

    override suspend fun saveAllStateless(users: Collection<User>) {
        sessionFactory.withStatelessTransaction { session ->
            session.insertAll(*users.toTypedArray())
        }
            .awaitSuspending()
    }

    override suspend fun saveAll(users: Collection<User>) {
        sessionFactory.withTransaction { session, _ ->
            session.persistAll(*users.toTypedArray())
        }
            .awaitSuspending()
    }
}
