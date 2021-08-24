
val mutinyVersion: String by project
val vavrVersion: String by project
val cdiApiVersion: String by project
val arrowVersion: String by project

plugins {
    jacoco
    kotlin("jvm")
    kotlin("plugin.allopen")
}

dependencies {
    api("io.vavr:vavr:$vavrVersion")
    api("io.arrow-kt:arrow-core:$arrowVersion")
    compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:$cdiApiVersion")
    api("io.smallrye.reactive:mutiny:$mutinyVersion")
    api("io.smallrye.reactive:mutiny-kotlin:$mutinyVersion")
}

allOpen {
    annotation("javax.inject.Singleton")
}