package ddwu.com.mobile.finalreport.model.entity.maria.comment

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import java.time.LocalDateTime

@Entity
@DynamicInsert
@Table(name = "COMMENT")
data class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val commentSeq: Long? = null,

    @Column(nullable = false)
    var diarySeq: Long,

    @Column(nullable = false)
    var userSeq: Int,

    @Column(nullable = false)
    var title: String?,

    @Column(nullable = false)
    var content: String?,

    @Column(nullable = false)
    var createdTime: LocalDateTime
)