package ddwu.com.mobile.finalreport.repository.maria.diary

import ddwu.com.mobile.finalreport.model.entity.maria.diary.Diary
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface DiaryRepository : JpaRepository<Diary, Long> {
    fun findFirstByCreatedTimeAndIsDeletedIsFalse(createdTime : LocalDate) : Diary?
    fun findByDiarySeq(diarySeq : Long) : Diary?
}