import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    // mavenCentral()
    maven { url = uri("https://maven.aliyun.com/repository/public/") }
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // 单元测试
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("script-runtime"))

    // 请求与响应
    implementation("org.springframework.boot:spring-boot-starter-web")
    // 自定义注解
    implementation("org.springframework.boot:spring-boot-starter-aop")
    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // 邮箱模块
    implementation("org.springframework.boot:spring-boot-starter-mail")
    // 热部署
    implementation("org.springframework.boot:spring-boot-devtools")
    // 授权与认证，但只使用了其中的加密模块
//    implementation("org.springframework.boot:spring-boot-starter-security")
    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // mysql 连接器
    implementation("mysql:mysql-connector-java")
    // lombok getter/setter 生成
//    implementation("org.projectlombok:lombok")

    // mybatis-plus
    implementation("com.baomidou:mybatis-plus-boot-starter:3.4.3.1")
    // jwt
    implementation("com.auth0:java-jwt:3.17.0")
    // api接口文档 —— 测试接口
    implementation("io.springfox:springfox-boot-starter:3.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
