plugins {
    `java-gradle-plugin`
    id("maven-publish")
}

group = "dev.jwall64"
version = "0.4.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
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

        var url: String = project.property("pubUrl") as String
        var repo = project.property("pubRepo")

        if(repo != null) {
            if(!url.endsWith("/")) {
                url += "/";
            }
            url += repo
        }

        repositories.maven(url) {
            name = "pub"
            credentials(PasswordCredentials::class.java)
        }
    }
}
