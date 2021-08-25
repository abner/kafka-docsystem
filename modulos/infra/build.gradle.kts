plugins {
    id("io.quarkus")
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    api(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    api("io.quarkus:quarkus-mutiny")

    implementation("io.quarkus:quarkus-smallrye-reactive-messaging")
    api("io.quarkus:quarkus-smallrye-reactive-messaging")
    api("io.quarkus:quarkus-resteasy-reactive")
    api("io.quarkus:quarkus-smallrye-reactive-messaging-kafka")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-kafka")


    api("io.quarkus:quarkus-kotlin")

    api("io.quarkus:quarkus-reactive-pg-client")
    implementation("io.quarkus:quarkus-reactive-pg-client")

    //implementation("io.quarkus:quarkus-narayana-jta")

    api("io.quarkus:quarkus-smallrye-context-propagation")
    api("io.quarkus:quarkus-arc")
    api("io.quarkus:quarkus-opentelemetry")
    implementation("io.quarkus:quarkus-opentelemetry")
    api("io.quarkus:quarkus-opentelemetry-exporter-jaeger")
    implementation("io.quarkus:quarkus-opentelemetry-exporter-jaeger")

    api(project(":docsystem-recepcao-domain"))

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
}