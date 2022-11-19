package demo.performance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArmeriaHibernateReactiveApplication

fun main(args: Array<String>) {
    runApplication<ArmeriaHibernateReactiveApplication>(*args)
}
