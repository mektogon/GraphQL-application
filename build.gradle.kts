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
    implementation("com.tailrocks.graphql:graphql-datetime-kickstart-spring-boot-starter:6.0.0")
    implementation("com.graphql-java-kickstart:voyager-spring-boot-starter:11.1.0")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:15.1.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")

    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")
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