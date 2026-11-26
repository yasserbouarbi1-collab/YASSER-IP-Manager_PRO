pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradleCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "YASSER-IP-Manager"
include(":app")
