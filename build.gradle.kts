val KGraphQLVersion: String by project

val exposedVersion: String by project
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.20"
                id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21"
}

group = "com.ndambuki"
version = "0.0.1"
application {
    mainClass.set("com.ndambuki.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-freemarker:$ktor_version")
    implementation("io.ktor:ktor-mustache:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
    implementation("io.ktor:ktor-html-builder:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")


    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
//    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.h2database:h2:1.4.200")
    implementation("com.apurebase:kgraphql:$KGraphQLVersion")
    implementation("com.apurebase:kgraphql-ktor:$KGraphQLVersion")
    implementation("org.postgresql:postgresql:42.2.23.jre7")


}