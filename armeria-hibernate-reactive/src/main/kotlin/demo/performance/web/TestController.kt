package demo.performance.web

import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.ProducesJson
import demo.performance.hibernateReactive.entity.User
import demo.performance.hibernateReactive.entity.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
@ProducesJson
class TestController(private val userRepository: UserRepository) {
    @Get("/users")
    suspend fun getUsers(): List<User> {
        return userRepository.findAll(PageRequest.of(0, 100)).content
    }
}
