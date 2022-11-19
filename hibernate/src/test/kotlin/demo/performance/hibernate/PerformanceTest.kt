package demo.performance.hibernate

import demo.performance.hibernate.entity.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.util.StopWatch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class PerformanceTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    private val executor = Executors.newFixedThreadPool(200)

    @Test
    internal fun insert_20000_to_4000000() {
        repeat(10) {
//            runBlocking {
//                sessionFactory.withTransaction { session, _ -> session.createNativeQuery<Unit>("TRUNCATE TABLE User").executeUpdate() }
//                    .awaitSuspending()
//                runBatchInsert(10000)
//
//                for (i in 1..20) {
//                    sessionFactory.withTransaction { session, _ -> session.createNativeQuery<Unit>("TRUNCATE TABLE User").executeUpdate() }
//                        .awaitSuspending()
//                    runBatchInsert(i * 20000)
//                }
//            }
        }
    }

    @Test
    internal fun read_4000000_row() {
        repeat(30) {
            read(100 * it)
        }
    }

    private fun read(count: Int) {
        val stopWatch = StopWatch()
        stopWatch.start("$count")
        val countDownLatch = CountDownLatch(count)
        repeat(count) {
            executor.execute {
                userRepository.findAll(PageRequest.of(0, 100))
                countDownLatch.countDown()
            }
        }

        countDownLatch.await()
        stopWatch.stop()
        println("${stopWatch.lastTaskName} : ${stopWatch.lastTaskTimeMillis}")
    }

//    private suspend fun runBatchInsert(count: Int) {
//        val stopWatch = StopWatch()
//        stopWatch.start("$count")
//        val batchSize = 1000
//        val parallelSize = 20
//
//        coroutineScope {
//            (1..parallelSize).map {
//                launch {
//                    for (i in 0 until ceil(count / batchSize / parallelSize.toDouble()).toInt()) {
//                        val users = List(batchSize) { 0 }
//                            .map { User(nickname = "test", joinDateTime = ZonedDateTime.now()) }
//                        userRepository.saveAllStateless(users)
//                    }
//                }
//            }
//        }
//        stopWatch.stop()
//        println("${stopWatch.lastTaskName} : ${stopWatch.lastTaskTimeMillis}")
//    }
}
