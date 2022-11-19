package demo.performance.hibernate.entity

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.util.*

interface UserRepository : Repository<User, UUID> {
    fun findByUserId(id: UUID): User?
    fun findAll(pageable: Pageable): Page<User>
    fun save(user: User): User
    fun saveAll(users: Iterable<User>): List<User>
}
