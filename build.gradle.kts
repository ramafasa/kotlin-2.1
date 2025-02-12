plugins {
    kotlin("jvm") version "2.1.0"
}

group = "pl.codesmithy.kotlin21"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xwhen-guards")
    }
    jvmToolchain(21)
}