package ddwu.com.mobile.finalreport.repository.maria.diary

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import ddwu.com.mobile.finalreport.model.dto.diary.DiaryFeelingCountInfo
import ddwu.com.mobile.finalreport.model.entity.maria.diary.Diary
import ddwu.com.mobile.finalreport.model.entity.maria.diary.QDiary.diary
import ddwu.com.mobile.finalreport.model.request.diary.DiarySearchRequest
import org.springframework.stereotype.Repository
import org.springframework.util.ObjectUtils

@Repository
class DiaryQueryRepository (private val queryFactory: JPAQueryFactory) {
    fun getDiaryList(request: DiarySearchRequest): List<Diary> {
        val builder: BooleanBuilder = getBooleanBuilder(request)
        return queryFactory.selectFrom(diary)
            .where(builder)
            .where(diary.isDeleted.isFalse)
            .orderBy(diary.createdTime.desc())
            .fetch()

    }

    fun getMyDiaryList(request: DiarySearchRequest, userSeq: Int): List<Diary> {
        val builder: BooleanBuilder = getBooleanBuilder(request)
        return queryFactory.selectFrom(diary)
            .where(builder)
            .where(diary.userSeq.eq(userSeq))
            .where(diary.isDeleted.isFalse)
            .orderBy(diary.createdTime.desc())
            .fetch()
    }

    fun getFeelingCount() : List<DiaryFeelingCountInfo> {
        return queryFactory
            .select(Projections.constructor(
                DiaryFeelingCountInfo::class.java,
                diary.iconType,
                diary.diarySeq.count().`as`("count")
            ))
            .from(diary)
            .groupBy(diary.iconType)
            .fetch()
    }

    private fun getBooleanBuilder(request: DiarySearchRequest): BooleanBuilder {
        val builder = BooleanBuilder()

        if (!ObjectUtils.isEmpty(request.keyword)) {
            builder.or(diary.title.contains(request.keyword))
            builder.or(diary.content.contains(request.keyword))
        }

        return builder
    }
}