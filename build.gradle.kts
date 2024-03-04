import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "ru.dorofeev"
version = "01.000.00"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-graphql")

    compileOnly("org.projectlombok:lombok")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework:spring-webflux")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencyLocking {
    lockFile.set(file("$projectDir/gradle/lockfiles/${rootProject.name}-${version}.lockfile"))
    lockAllConfigurations()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    enabled = false //Disable generation plain-file for jar-package
}

tasks.bootRun {
    if (project.hasProperty("args")) { //Many arguments for bootRun
        args(project.properties["args"]?.toString()?.split(","))
    }
}

tasks.register("createschema") {

    /**
     * Получение имени пользователя, которое хранится в .gitconfig
     *
     * В случае отсутствия или пустого значения выводится заглушка: "<AUTHOR NAME>"
     */
    fun getGitUserName(): String {
        val defaultAuthorText = "<AUTHOR NAME>"
        val outputStream = ByteArrayOutputStream()

        try {
            exec {
                executable = "git"
                args("config", "--get", "user.name")
                standardOutput = outputStream
            }

            return outputStream.toString().trimIndent().ifEmpty {
                println("Warn! The username is empty! Return the stub value: $defaultAuthorText")
                defaultAuthorText
            }
        } catch (e: Exception) {
            println("Error! Couldn't find git username! Return the stub value: $defaultAuthorText")
        }

        return defaultAuthorText
    }

    if (!project.hasProperty("schemaname")) {
        throw GradleException("The name of the schema is missing!")
    }

    val versionToCatalogSchema = if (project.hasProperty("schemaversion")) {
        project.property("schemaversion")
    } else {
        project.version
    }

    val basePathToFile = project.layout.projectDirectory.dir("src/main/resources/graphql")
    val originalName = project.property("schemaname");
    val schemaName = "${System.currentTimeMillis()}_${originalName}.graphqls"

    val defaultDataScript = """
        #DATE CREATED: ${LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_ZONED_DATE_TIME)}
        #CREATE BY: ${getGitUserName()}
        #SCHEMA NAME: $originalName
    """.trimIndent()

    outputs.files(
            "$basePathToFile/$versionToCatalogSchema/$schemaName"
    )

    doFirst {
        outputs.files.forEach { file -> file.writeText(defaultDataScript) }
    }
}