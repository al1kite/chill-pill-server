package ddwu.com.mobile.finalreport.model.dto.comment

import java.time.LocalDateTime

data class CommentInfo (
    val commentSeq: Long?,
    var userSeq: Int?,
    var title: String?,
    var content: String,
    var createdTime: LocalDateTime
)