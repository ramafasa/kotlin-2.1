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
        freeCompilerArgs.add("-Xmulti-dollar-interpolation")
        freeCompilerArgs.add("-Xnon-local-break-continue")
    }
    jvmToolchain(21)
}