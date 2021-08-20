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
    "docproc-recepcao-domain",
    "docproc-recepcao-infra",
    "docproc-recepcao-web"
)

project(":docproc-recepcao-domain").projectDir = file("modulos/domain")
project(":docproc-recepcao-infra").projectDir = file("modulos/infra")
project(":docproc-recepcao-web").projectDir = file("modulos/web")



