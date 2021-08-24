plugins {
    jacoco
    kotlin("jvm")
    kotlin("plugin.allopen")
    id("io.quarkus") apply false
    java
}

val docSystemVersion: String by project

group = "io.abner.docsystem"
version = docSystemVersion

repositories {
    mavenCentral()
    gradlePluginPortal()

}

val spekVersion: String by project
val mockitoKotlinVersion: String by project
val kotlinxCoroutinesVersion: String by project
val kotestVersion: String by project
val mockkVersion: String by project


subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()

    }

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
//    apply(plugin = "java")
//    java {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }

    allOpen {
        annotation("javax.ws.rs.Path")
        annotation("javax.enterprise.context.ApplicationScoped")
        annotation("io.quarkus.test.junit.QuarkusTest")
        annotation("javax.inject.Singleton")
    }

    tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile::class.java).all {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
        kotlinOptions.javaParameters = true
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs
                //.plus("-Xopt-in=kotlin.RequiresOptIn")
                .plus("-Xuse-experimental=kotlin.Experimental")
                .plus("-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi")
        }
    }

    dependencies {
        implementation(kotlin("stdlib"))

        // DEPENDENCIAS DE TESTE
        testImplementation(kotlin("test"))

        // KOTLIN COROUTINES TESTING
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$kotlinxCoroutinesVersion")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")

        // KOTEST
        //testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")

        // MOCKK
        testImplementation("io.mockk:mockk:$mockkVersion")
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            includeEngines("junit-jupiter", "spek2", "kotest")
        }
        systemProperty("root.project.dir", project.rootProject.rootDir.absolutePath)

        testLogging {
            lifecycle {
                events = mutableSetOf(
                    org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
                )
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
                showStandardStreams = true
            }
            info.events = lifecycle.events
            info.exceptionFormat = lifecycle.exceptionFormat
        }

        val failedTests = mutableListOf<TestDescriptor>()
        val skippedTests = mutableListOf<TestDescriptor>()

        // See https://github.com/gradle/kotlin-dsl/issues/836
        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                when (result.resultType) {
                    TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                    TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
                    else                          -> Unit
                }
            }

            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (suite.parent == null) { // root suite
                    logger.lifecycle("----")
                    logger.lifecycle("Test result: ${result.resultType}")
                    logger.lifecycle(
                        "Test summary: ${result.testCount} tests, " +
                                "${result.successfulTestCount} succeeded, " +
                                "${result.failedTestCount} failed, " +
                                "${result.skippedTestCount} skipped"
                    )
                    failedTests
                        .takeIf { it.isNotEmpty() }
                        ?.prefixedSummary("\tFailed Tests")
                    skippedTests
                        .takeIf { it.isNotEmpty() }
                        ?.prefixedSummary("\tSkipped Tests:")
                }
            }

            private infix fun List<TestDescriptor>.prefixedSummary(subject: String) {
                logger.lifecycle(subject)
                forEach { test -> logger.lifecycle("\t\t${test.displayName()}") }
            }

            private fun TestDescriptor.displayName() =
                parent?.let { "${it.name} - $name" } ?: "$name"
        })

    }
}