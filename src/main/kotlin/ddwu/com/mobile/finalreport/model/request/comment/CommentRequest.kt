package ddwu.com.mobile.finalreport.model.request.comment

data class CommentRequest (
    var diarySeq : Long,
    var title : String?,
    var content: String?
)