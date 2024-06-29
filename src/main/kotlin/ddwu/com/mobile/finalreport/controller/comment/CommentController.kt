package ddwu.com.mobile.finalreport.controller.comment

import ddwu.com.mobile.finalreport.model.request.comment.CommentRequest
import ddwu.com.mobile.finalreport.model.response.comment.CommentResponse
import ddwu.com.mobile.finalreport.service.comment.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping(value = ["/api/diary/comment"])
class CommentController (private val commentService: CommentService){
    @PostMapping("")
    fun createComment(@RequestBody request: CommentRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(commentService.createComment(request))
    }

    @GetMapping("/{diarySeq}")
    fun getDiaryComment(@PathVariable("diarySeq") seq: Long): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.getDiaryComment(seq))
    }
}