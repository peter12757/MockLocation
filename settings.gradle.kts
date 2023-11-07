pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven { url=uri("https://maven.aliyun.com/nexus/content/groups/public/") }

        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url=uri("https://maven.aliyun.com/nexus/content/groups/public/") }

    }
}

rootProject.name = "MockLocation"
include(":app")
