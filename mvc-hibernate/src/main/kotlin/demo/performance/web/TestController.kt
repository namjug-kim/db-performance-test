package demo.performance.web

import demo.performance.hibernate.entity.User
import demo.performance.hibernate.entity.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(private val userRepository: UserRepository) {
    @GetMapping("/users")
    fun getUsers(): List<User> {
        return userRepository.findAll(PageRequest.of(0, 100)).content
    }
}
