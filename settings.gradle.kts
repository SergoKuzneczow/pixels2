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
    }
}

rootProject.name = "Pixels2"
include(":app")
include(":core:ui")
include(":core:models")
include(":core:network")
include(":core:database")
include(":core:data")
include(":core:datastore_picture")
include(":core:utilities")
include(":feature:home")
include(":feature:settings")
include(":core:domain")
include(":feature:suitable_pictures")
include(":feature:splash")
include(":feature:selected_picture")
include(":feature:main_menu")
