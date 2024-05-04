plugins {
    `java-gradle-plugin`
    id("maven-publish")
}

group = "dev.jwall64"
version = "0.3.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

gradlePlugin {
    val multiVersion by plugins.creating {
        id = "dev.jwall64.gradle-multi-version"
        implementationClass = "dev.jwall64.gradle.mv.MultiVersionPlugin"
    }
}

publishing {

    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }

    if (project.hasProperty("pubUrl")) {

        val url: String = project.properties["pubUrl"] as String
        repositories.maven(url) {
            name = "pub"
            credentials(PasswordCredentials::class.java)
        }
    }

}