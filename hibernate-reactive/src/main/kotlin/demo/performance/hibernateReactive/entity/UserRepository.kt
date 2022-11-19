package demo.performance.hibernateReactive.entity

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserRepository {
    suspend fun findById(id: UUID): User?
    suspend fun findAll(pageable: Pageable): Page<User>
    suspend fun save(user: User)
    suspend fun saveAll(users: Collection<User>)
    suspend fun saveAllStateless(users: Collection<User>)
}
