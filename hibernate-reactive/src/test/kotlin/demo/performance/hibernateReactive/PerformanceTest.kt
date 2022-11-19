package demo.performance.hibernateReactive

import demo.performance.hibernateReactive.entity.User
import demo.performance.hibernateReactive.entity.UserRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.util.StopWatch
import java.time.ZonedDateTime
import kotlin.math.ceil

@SpringBootTest
class PerformanceTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var sessionFactory: Mutiny.SessionFactory

    @Test
    internal fun insert_20000_to_4000000() {
        repeat(10) {
            runBlocking {
                sessionFactory.withTransaction { session, _ -> session.createNativeQuery<Unit>("TRUNCATE TABLE User").executeUpdate() }
                    .awaitSuspending()
                runBatchInsert(10000)

                for (i in 1..20) {
                    sessionFactory.withTransaction { session, _ -> session.createNativeQuery<Unit>("TRUNCATE TABLE User").executeUpdate() }
                        .awaitSuspending()
                    runBatchInsert(i * 20000)
                }
            }
        }
    }

    @Test
    internal fun read_4000000_row() {
        runBlocking {
            repeat(30) {
                read(100 * it)
            }
        }
    }

    private suspend fun read(count: Int) {
        val stopWatch = StopWatch()
        stopWatch.start("$count")
        coroutineScope {
            repeat(count) {
                launch {
                    userRepository.findAll(PageRequest.of(0, 1000))
                }
            }
        }
        stopWatch.stop()
        println("${stopWatch.lastTaskName} : ${stopWatch.lastTaskTimeMillis}")
    }

    private suspend fun runBatchInsert(count: Int) {
        val stopWatch = StopWatch()
        stopWatch.start("$count")
        val batchSize = 1000
        val parallelSize = 20

        coroutineScope {
            (1..parallelSize).map {
                launch {
                    for (i in 0 until ceil(count / batchSize / parallelSize.toDouble()).toInt()) {
                        val users = List(batchSize) { 0 }
                            .map { User(nickname = "test", joinDateTime = ZonedDateTime.now()) }
                        userRepository.saveAllStateless(users)
                    }
                }
            }
        }
        stopWatch.stop()
        println("${stopWatch.lastTaskName} : ${stopWatch.lastTaskTimeMillis}")
    }
}
