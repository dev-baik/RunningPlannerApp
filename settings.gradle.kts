pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            // KakaoSDK repository
            url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
    }
}

rootProject.name = "RunningPlannerApp"
include(":app")
include(":data")
include(":domain")
include(":presentation")
