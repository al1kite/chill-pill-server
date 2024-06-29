package ddwu.com.mobile.finalreport.mapper.diary

import ddwu.com.mobile.finalreport.mapper.EntityMapper
import ddwu.com.mobile.finalreport.model.entity.maria.diary.Diary
import ddwu.com.mobile.finalreport.model.response.diary.DiaryResponse
import ddwu.com.mobile.finalreport.service.diary.DiaryService
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN,
)
interface DiaryResponseMapper : EntityMapper<DiaryResponse?, Diary?> {
    companion object {
        val INSTANCE: DiaryResponseMapper = Mappers.getMapper(DiaryResponseMapper::class.java)
    }
}