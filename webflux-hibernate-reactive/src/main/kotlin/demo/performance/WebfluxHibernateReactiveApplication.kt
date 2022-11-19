package demo.performance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebfluxHibernateReactiveApplication

fun main(args: Array<String>) {
    runApplication<WebfluxHibernateReactiveApplication>(*args)
}
