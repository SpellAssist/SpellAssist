plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "com.github.SpellAssist"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://github.com/haifengl/smile.git")
    }
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.haifengl:smile-kotlin:3.0.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}
