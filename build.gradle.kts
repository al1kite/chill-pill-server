import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
    kotlin("kapt") version "1.9.20"
    idea
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    apply(plugin = "kotlin-kapt")
    // DEFAULT
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.0")
    // LOGGING
    implementation("org.springframework.boot:spring-boot-starter-log4j2:3.0.4")
    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
    // QUERY_DSL
    implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt ("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt ("jakarta.annotation:jakarta.annotation-api")
    kapt ("jakarta.persistence:jakarta.persistence-api")

    // DATABASE
    runtimeOnly("com.mysql:mysql-connector-j:8.0.32")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    // MARIADB
    implementation("org.mariadb.jdbc:mariadb-java-client:2.4.0")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    // SECURITY
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.4")
    implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
    implementation("com.sun.xml.bind:jaxb-impl:4.0.1")
    implementation("com.sun.xml.bind:jaxb-core:4.0.1")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    testImplementation("org.springframework.security:spring-security-test:6.0.2")

    //TEST
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // VALIDATING
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")

    // JSON PARSING
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.13.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.2")

    // MAPPING
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")
    kaptTest("org.mapstruct:mapstruct-processor:1.5.3.Final")

    // CUSTOM toString & equals & hashCode
    implementation("com.github.consoleau:kassava:2.1.0")

    // IMAGE STORAGE
    implementation("io.awspring.cloud:spring-cloud-starter-aws:2.3.1")
}

configurations.forEach {
    it.exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    it.exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

// Querydsl 설정부 추가 - start
val generated = file("src/main/generated")

// querydsl QClass 파일 생성 위치를 지정
tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(generated)
}

// kotlin source set 에 querydsl QClass 위치 추가
sourceSets {
    main {
        kotlin.srcDirs += generated
    }
}

// gradle clean 시에 QClass 디렉토리 삭제
tasks.named("clean") {
    doLast {
        generated.deleteRecursively()
    }
}
kapt {
    correctErrorTypes = true
    generateStubs = true
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

noArg {
    annotation("jakarta.persistence.Entity")
}