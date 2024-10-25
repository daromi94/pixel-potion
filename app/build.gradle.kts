plugins {
  alias(libs.plugins.kotlin.jvm)
  application
}

group = "com.daromi.pixel.potion"

version = "0.1.0"

repositories { mavenCentral() }

dependencies { testImplementation(libs.kotlin.test) }

kotlin { jvmToolchain(21) }

application { mainClass = "$group.cli.MainKt" }

tasks.test { useJUnitPlatform() }
