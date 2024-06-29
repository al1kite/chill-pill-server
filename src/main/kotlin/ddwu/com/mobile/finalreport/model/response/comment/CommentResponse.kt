package ddwu.com.mobile.finalreport.model.response.comment

import ddwu.com.mobile.finalreport.model.dto.comment.CommentInfo
import java.time.LocalDate

data class CommentResponse(
    val diarySeq: Long?,
    var userSeq: Int?,
    var title: String?,
    var content: String?,
    var createdTime: LocalDate?,
    var commentList: List<CommentInfo?>?
)