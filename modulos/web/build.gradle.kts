plugins {
    id("io.quarkus")
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    //implementation("io.quarkus:quarkus-smallrye-jwt")

    implementation(project(":docsystem-recepcao-infra"))


    implementation("io.quarkus:quarkus-smallrye-openapi")

    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("io.quarkus:quarkus-test-security")

    implementation("io.quarkus:quarkus-elytron-security-properties-file")
}