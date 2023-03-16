plugins {
    alias(libs.plugins.jvmPlugin)
    alias(libs.plugins.ktorPlugin)
    alias(libs.plugins.serializationPlugin)
    alias(libs.plugins.spotlessPlugin)
    alias(libs.plugins.domPlugin)
}

group = "com.adikmt"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
//    mavenLocal()
}

dependencies {
    implementation(platform(libs.arrowstack))

    implementation(libs.arrowcore)

    implementation(libs.bundles.coroutines)

    implementation(libs.bundles.koin)

    implementation(libs.bundles.logging)

    implementation(libs.bundles.ktor)

    implementation(libs.bundles.auth)

    implementation(libs.bundles.db)

    implementation(libs.scientist)

    testImplementation(libs.bundles.testing)
}

spotless {
    kotlin {
        ktlint(libs.versions.ktlint.get()).userData(
            mapOf(
                "indent_size" to "2",
                "indent_style" to "space",
                "tab_width" to "2",
                "max_line_length" to "120",
                "disabled_rules" to "no-wildcard-imports",
            )
        )
    }
    kotlinGradle {
        target("*.gradle.kts")
    }
}

testSets {
    val integrationTest by creating
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        languageVersion = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
            "-Xopt-in=io.ktor.locations.KtorExperimentalLocationsAPI",
            "-Xopt-in=kotlin.time.ExperimentalTime",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xcontext-receivers"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}