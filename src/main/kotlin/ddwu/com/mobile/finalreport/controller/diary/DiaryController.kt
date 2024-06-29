package ddwu.com.mobile.finalreport.controller.diary

import ddwu.com.mobile.finalreport.model.dto.diary.DiaryFeelingCountInfo
import ddwu.com.mobile.finalreport.model.request.diary.DiaryDateRequest
import ddwu.com.mobile.finalreport.model.request.diary.DiaryRequest
import ddwu.com.mobile.finalreport.model.request.diary.DiarySearchRequest
import ddwu.com.mobile.finalreport.model.response.diary.DiaryResponse
import ddwu.com.mobile.finalreport.service.diary.DiaryService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping(value = ["/api/diary"])
class DiaryController (private val dairyService: DiaryService){
    @PostMapping("")
    fun createDiary(@RequestBody request: DiaryRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(dairyService.createDiary(request))
    }

    @PostMapping("/{diarySeq}")
    fun updateDiary(@PathVariable("diarySeq") seq : Long, @RequestBody request: DiaryRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(dairyService.updateDiary(seq, request))
    }

    @DeleteMapping("/{diarySeq}")
    fun deleteDiary(@PathVariable("diarySeq") seq : Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(dairyService.deleteDiary(seq))
    }

    @GetMapping("")
    fun getTodayDairy() : ResponseEntity<DiaryResponse> {
        return ResponseEntity.ok(dairyService.getTodayDairy())
    }

    @GetMapping("/date")
    fun getDairyByDate(@RequestBody request : DiaryDateRequest) : ResponseEntity<DiaryResponse> {
        return ResponseEntity.ok(dairyService.getDairyByDate(request))
    }

    @GetMapping("/my-list")
    fun getMyDairyList(request : DiarySearchRequest) : ResponseEntity<List<DiaryResponse?>> {
        return ResponseEntity.ok(dairyService.getMyDairyList(request))
    }

    @GetMapping("/list")
    fun getDairyList(request : DiarySearchRequest) : ResponseEntity<List<DiaryResponse?>> {
        return ResponseEntity.ok(dairyService.getDairyList(request))
    }

    @GetMapping("/feeling-count")
    fun getFeelingCount() : ResponseEntity<List<DiaryFeelingCountInfo>> {
        return ResponseEntity.ok(dairyService.getFeelingCount())
    }

}