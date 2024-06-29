package ddwu.com.mobile.finalreport.model.request.diary

import ddwu.com.mobile.finalreport.model.enumaration.IconType

data class DiaryRequest (
    var title: String,
    var iconType: IconType,
    var content: String
)