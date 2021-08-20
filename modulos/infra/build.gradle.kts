
val quarkusPluginId: String by project

apply(plugin=quarkusPluginId)


val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    api(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    api("io.quarkus:quarkus-resteasy-reactive-jackson")
    api("io.quarkus:quarkus-mutiny")
    api("io.quarkus:quarkus-apicurio-registry-avro")
    implementation("io.quarkus:quarkus-apicurio-registry-avro")
    api("io.quarkus:quarkus-hibernate-reactive-panache")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging")
    api("io.quarkus:quarkus-smallrye-reactive-messaging")
    api("io.quarkus:quarkus-resteasy-reactive")
    api("io.quarkus:quarkus-smallrye-reactive-messaging-kafka")

    api("io.quarkus:quarkus-kotlin")
    api("io.quarkus:quarkus-reactive-pg-client")
    api("io.quarkus:quarkus-smallrye-context-propagation")
    api("io.quarkus:quarkus-arc")
    api("io.quarkus:quarkus-opentelemetry")
    api("io.quarkus:quarkus-opentelemetry-exporter-jaeger")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")

    api(project(":docproc-recepcao-domain"))
}