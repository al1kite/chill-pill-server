package ddwu.com.mobile.finalreport.model.response.diary

import ddwu.com.mobile.finalreport.model.enumaration.IconType
import java.time.LocalDate

data class DiaryResponse(
    val diarySeq: Long,
    var userSeq: Int,
    var title: String,
    var iconType: IconType,
    var content: String,
    var createdTime: LocalDate
)