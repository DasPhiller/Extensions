import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val githubRepo = "DasPhiller/Extensions"

plugins {
    kotlin("jvm") version "1.7.0"
    id("io.papermc.paperweight.userdev") version "1.3.6"
    `maven-publish`
}

group = "de.dasphiller.extensions"
version = "4.0"
description = "Extensions for Paper/KSpigot"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    // PaperMC Dependency
    paperDevBundle("1.19-R0.1-SNAPSHOT")

    // KSpigot dependency
    implementation("net.axay:kspigot:1.19.0")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

publishing {
    repositories {
        maven("https://oss.sonatype.org/service/local/staging/deploy/maven2") {
            name = "ossrh"
            credentials(PasswordCredentials::class)
        }
        publications {
            create<MavenPublication>(project.name) {
                from(components["java"])
                artifact(tasks.jar.get().outputs.files.single())
                this.groupId = project.group.toString()
                this.artifactId = project.name.toLowerCase()
                this.version = project.version.toString()

                pom {
                    name.set(project.name)
                    description.set(project.description)

                    developers {
                        developer {
                            name.set("DasPhiller")
                        }
                    }

                    licenses {
                        license {
                            name.set("GNU General Public License, Version 3")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                        }
                    }

                    url.set("https://github.com/${githubRepo}")

                    scm {
                        connection.set("scm:git:git://github.com/${githubRepo}.git")
                        url.set("https://github.com/${githubRepo}/tree/main")
                    }
                }
            }
        }
    }
}
