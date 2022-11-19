package demo.performance.test

import io.gatling.javaapi.core.CoreDsl.atOnceUsers
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http

class FrontPageApiCallSimulation : Simulation() {
    private val httpProtocol = http.baseUrl("http://localhost:8080")

    init {
        setUp(
//            userScenario(0).injectOpen(rampUsersPerSec(5.0).to(10.0).during(10))
//                .andThen(userScenario(1).injectOpen(atOnceUsers(100)))
//                .andThen(userScenario(2).injectOpen(atOnceUsers(200)))
//                .andThen(userScenario(3).injectOpen(atOnceUsers(300)))
//                .andThen(userScenario(4).injectOpen(atOnceUsers(400)))
//                .andThen(userScenario(5).injectOpen(atOnceUsers(500)))
//                .andThen(userScenario(6).injectOpen(atOnceUsers(600)))
//                .andThen(userScenario(7).injectOpen(atOnceUsers(700)))
//                .andThen(userScenario(8).injectOpen(atOnceUsers(800)))
//                .andThen(userScenario(9).injectOpen(atOnceUsers(900)))
//                .andThen(userScenario(10).injectOpen(atOnceUsers(1000)))
//                .andThen(userScenario(11).injectOpen(atOnceUsers(1100)))
//                .andThen(userScenario(12).injectOpen(atOnceUsers(1200)))
            userScenario(12).injectOpen(atOnceUsers(1200))
        )
            .protocols(httpProtocol)
    }

    private fun userScenario(times: Int): ScenarioBuilder {
        return scenario("유저조회-$times").exec(http("유저조회-$times").get("/users"))
    }
}
