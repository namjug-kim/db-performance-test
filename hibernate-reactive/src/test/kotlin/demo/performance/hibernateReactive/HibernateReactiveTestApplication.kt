package demo.performance.hibernateReactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HibernateReactiveTestApplication

fun main(args: Array<String>) {
    runApplication<HibernateReactiveTestApplication>(*args)
}
