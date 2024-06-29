package ddwu.com.mobile.finalreport.repository.maria.comment

import ddwu.com.mobile.finalreport.model.entity.maria.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByUserSeqAndDiarySeq(userSeq: Int, diarySeq: Long) : List<Comment>?
}