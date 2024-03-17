plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version("7.1.2")
}

group = "io.github.facuu16"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.unnamed.team/repository/unnamed-public/")
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.0")
    implementation("team.unnamed:inject:2.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.facuu16.translator.TranslatorApplication"
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}