package ddwu.com.mobile.finalreport.mapper.comment

import ddwu.com.mobile.finalreport.mapper.EntityMapper
import ddwu.com.mobile.finalreport.model.dto.comment.CommentInfo
import ddwu.com.mobile.finalreport.model.entity.maria.comment.Comment
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
interface CommentInfoMapper : EntityMapper<CommentInfo?, Comment?> {
    companion object {
        val INSTANCE: CommentInfoMapper = Mappers.getMapper(CommentInfoMapper::class.java)
    }
}