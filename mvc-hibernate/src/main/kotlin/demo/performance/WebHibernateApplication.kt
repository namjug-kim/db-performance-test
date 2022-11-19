package demo.performance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebHibernateApplication

fun main(args: Array<String>) {
    runApplication<WebHibernateApplication>(*args)
}
