package ddwu.com.mobile.finalreport

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan


// 과제명: CHILL PILL - 다이어리 앱의 일종으로, 하루의 걱정을 알약 형태로 작성하고 처방전을 받는 컨셉
// 분반: 01 분반
// 학번: 20210812 성명: 정다연
// 제출일: 2024년 6월 25일
@SpringBootApplication
class FinalReportApplication

fun main(args: Array<String>) {
    val context: ApplicationContext = runApplication<FinalReportApplication>(*args)
    println("Beans in application context:")
    context.beanDefinitionNames.sorted().forEach { println(it) }
}
