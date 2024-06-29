package ddwu.com.mobile.finalreport.model.entity.maria.diary

import ddwu.com.mobile.finalreport.model.enumaration.IconType
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import java.time.LocalDate

@Entity
@DynamicInsert
@Table(name = "DIARY")
data class Diary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val diarySeq: Long? = null,

    @Column(nullable = false)
    var userSeq: Int,

    @Column(nullable = false)
    var title: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var iconType: IconType,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    var createdTime: LocalDate,

    var isDeleted : Boolean
)