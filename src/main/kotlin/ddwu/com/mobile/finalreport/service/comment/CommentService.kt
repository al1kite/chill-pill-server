package ddwu.com.mobile.finalreport.service.comment

import ddwu.com.mobile.finalreport.mapper.comment.CommentInfoMapper
import ddwu.com.mobile.finalreport.model.entity.maria.comment.Comment
import ddwu.com.mobile.finalreport.model.entity.maria.diary.Diary
import ddwu.com.mobile.finalreport.model.request.comment.CommentRequest
import ddwu.com.mobile.finalreport.model.response.comment.CommentResponse
import ddwu.com.mobile.finalreport.repository.maria.comment.CommentRepository
import ddwu.com.mobile.finalreport.repository.maria.diary.DiaryRepository
import ddwu.com.mobile.finalreport.security.LoginManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentService (private val commentRepository: CommentRepository,
    private val diaryRepository: DiaryRepository){
    fun getDiaryComment(diarySeq : Long): CommentResponse? {
        val userSeq = LoginManager.userDetails?.userSeq ?:
        throw RuntimeException("No UserInfo. Please check if you're logged in")
        val diary : Diary? = diaryRepository.findByDiarySeq(diarySeq)

        return CommentResponse(
            commentList = CommentInfoMapper.INSTANCE.toDto(commentRepository.findAllByUserSeqAndDiarySeq(userSeq, diarySeq)),
            title = diary?.title,
            content = diary?.content,
            createdTime = diary?.createdTime,
            diarySeq = diarySeq,
            userSeq = userSeq
        )
    }

    fun createComment(request : CommentRequest) {
        val userSeq = LoginManager.userDetails?.userSeq ?:
        throw RuntimeException("No UserInfo. Please check if you're logged in")

        val comment = Comment(
            userSeq = userSeq,
            diarySeq = request.diarySeq,
            title = request.title,
            content = request.content,
            createdTime = LocalDateTime.now()
        )
        commentRepository.save(comment)
    }
}