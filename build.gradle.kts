import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent



plugins {
    jacoco
    kotlin("jvm")
    kotlin("plugin.allopen")
    id("io.quarkus") apply false
    java
}

val docprocVersion: String by project

group = "io.abner.docproc"
version = docprocVersion

repositories {
    mavenCentral()

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
        // KOTLIN COROUTINES TESTING
//        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$kotlinxCoroutinesVersion")
//        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")

        // KOTEST
        //testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")

        // MOCKK
        testImplementation("io.mockk:mockk:$mockkVersion")
    }
}