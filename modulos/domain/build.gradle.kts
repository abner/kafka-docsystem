
val mutinyVersion: String by project
val vavrVersion: String by project
val cdiApiVersion: String by project

dependencies {
    api("io.vavr:vavr:$vavrVersion")
    compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:$cdiApiVersion")
    api("io.smallrye.reactive:mutiny:$mutinyVersion")
    api("io.smallrye.reactive:mutiny-kotlin:$mutinyVersion")
}

allOpen {
    annotation("javax.inject.Singleton")
}