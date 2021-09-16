plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":protocol"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.projectreactor.kotlin", "reactor-kotlin-extensions")
    implementation("org.springframework.boot", "spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot", "spring-boot-starter-data-r2dbc")
    implementation("dev.miku", "r2dbc-mysql")
    implementation("org.liquibase", "liquibase-core")
    implementation("mysql", "mysql-connector-java")
    implementation("org.springframework.kafka", "spring-kafka")
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")

    testImplementation("io.projectreactor", "reactor-test")
    testImplementation("io.projectreactor.kotlin", "reactor-kotlin-extensions")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter", "junit-jupiter-engine")
    testImplementation("org.junit.jupiter", "junit-jupiter-params")
}
