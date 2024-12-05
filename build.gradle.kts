plugins {
    kotlin("jvm") version "2.0.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11"
    }
}

dependencies {
    implementation("com.github.ajalt.mordant:mordant-jvm:3.0.1")
}
