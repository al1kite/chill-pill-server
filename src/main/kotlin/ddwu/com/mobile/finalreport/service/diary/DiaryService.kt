package ddwu.com.mobile.finalreport.service.diary

import ddwu.com.mobile.finalreport.mapper.diary.DiaryResponseMapper
import ddwu.com.mobile.finalreport.model.dto.diary.DiaryFeelingCountInfo
import ddwu.com.mobile.finalreport.model.entity.maria.diary.Diary
import ddwu.com.mobile.finalreport.model.request.diary.DiaryDateRequest
import ddwu.com.mobile.finalreport.model.request.diary.DiaryRequest
import ddwu.com.mobile.finalreport.model.request.diary.DiarySearchRequest
import ddwu.com.mobile.finalreport.model.response.diary.DiaryResponse
import ddwu.com.mobile.finalreport.repository.maria.diary.DiaryQueryRepository
import ddwu.com.mobile.finalreport.repository.maria.diary.DiaryRepository
import ddwu.com.mobile.finalreport.security.LoginManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiaryService @Autowired constructor(
    private val diaryRepository: DiaryRepository,
    private val diaryQueryRepository: DiaryQueryRepository) {

    fun createDiary(request: DiaryRequest) {
        val userSeq = LoginManager.userDetails?.userSeq ?:
        throw RuntimeException("No UserInfo. Please check if you're logged in")

        val diary = Diary(
            userSeq = userSeq,
            title = request.title,
            iconType = request.iconType,
            content = request.content,
            createdTime = LocalDate.now(),
            isDeleted = false
        )

        diaryRepository.save(diary)
    }

    fun updateDiary(dairySeq : Long, request : DiaryRequest){
        val userSeq = LoginManager.userDetails?.userSeq ?:
        throw RuntimeException("No UserInfo. Please check if you're logged in")

        val diary = Diary(
            diarySeq = dairySeq,
            userSeq = userSeq,
            title = request.title,
            iconType = request.iconType,
            content = request.content,
            createdTime = LocalDate.now(),
            isDeleted = false
        )

        diaryRepository.save(diary)
    }

    fun deleteDiary(diarySeq : Long){
        val userSeq = LoginManager.userDetails?.userSeq ?:
        throw RuntimeException("No UserInfo. Please check if you're logged in")

        val diary = diaryRepository.findByDiarySeq(diarySeq)

        if (diary != null) {
            if(diary.userSeq != userSeq)
                throw RuntimeException("Only Writer is allowed to delete")
            diary.isDeleted = true
            diaryRepository.save(diary)
        }

    }

    fun getTodayDairy() : DiaryResponse? {
        val diary = diaryRepository.findFirstByCreatedTimeAndIsDeletedIsFalse(LocalDate.now())
        return DiaryResponseMapper.INSTANCE.toDto(diary)
    }

    fun getDairyByDate(request : DiaryDateRequest) : DiaryResponse? {
        val date = LocalDate.of(request.year, request.month, request.dayOfMonth)
        val diary = diaryRepository.findFirstByCreatedTimeAndIsDeletedIsFalse(date)
        return DiaryResponseMapper.INSTANCE.toDto(diary)
    }

    fun getMyDairyList(request : DiarySearchRequest): List<DiaryResponse?>? {
        val userSeq = LoginManager.userDetails?.userSeq ?:
        throw RuntimeException("No UserInfo. Please check if you're logged in")

        return DiaryResponseMapper.INSTANCE.toDto(diaryQueryRepository.getMyDiaryList(request, userSeq))
    }

    fun getDairyList(request : DiarySearchRequest): List<DiaryResponse?>? {
        return DiaryResponseMapper.INSTANCE.toDto(diaryQueryRepository.getDiaryList(request))
    }

    fun getFeelingCount() : List<DiaryFeelingCountInfo>? {
        return diaryQueryRepository.getFeelingCount()
    }
}