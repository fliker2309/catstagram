buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
}
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.1")
}

detekt {
    toolVersion = "1.10.0"
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true

    reports {
        html {
            enabled = true
            destination = file("app/build/detekt/detekt.html")
        }
    }
}

task("clean") {
    doLast {
        delete(rootProject.buildDir)
    }
}
