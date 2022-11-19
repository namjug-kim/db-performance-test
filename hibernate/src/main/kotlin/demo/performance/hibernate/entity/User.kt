package demo.performance.hibernate.entity

import org.hibernate.annotations.GenericGenerator
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(
    val nickname: String,

    val joinDateTime: ZonedDateTime,
) {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    val userId: UUID? = null
}
