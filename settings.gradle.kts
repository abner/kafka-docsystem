pluginManagement {
    val kotlinVersion: String by settings
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
        id(quarkusPluginId) version quarkusPluginVersion apply false
    }
}

rootProject.name = "recepcao"
include(
    "docsystem-recepcao-domain",
    "docsystem-recepcao-infra",
    "docsystem-recepcao-web"
)

project(":docsystem-recepcao-domain").projectDir = file("modulos/domain")
project(":docsystem-recepcao-infra").projectDir = file("modulos/infra")
project(":docsystem-recepcao-web").projectDir = file("modulos/web")



