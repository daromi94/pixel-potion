plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.daromi.pixel-potion"

version = "0.1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies { testImplementation(libs.kotlin.test) }

kotlin { jvmToolchain(21) }

tasks.test { useJUnitPlatform() }
