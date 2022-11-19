package demo.performance.web

import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.spring.ArmeriaServerConfigurator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class ArmeriaConfiguration {
    @Bean
    fun armeriaServerConfigurator(testController: TestController): ArmeriaServerConfigurator {
        return ArmeriaServerConfigurator { builder ->
            builder.serviceUnder("/docs", DocService())
                .requestTimeout(Duration.ofMillis(0))
                .maxNumConnections(10000)
                .maxNumRequestsPerConnection(10000)
                .idleTimeout(Duration.ofMillis(0))
                .annotatedService(testController)
        }
    }
}
