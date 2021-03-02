plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("dev.ligature.slonky.kotlin-common-conventions")

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

dependencies {
    implementation("io.vertx:vertx-web:4.0.2")
    implementation(project(":ligature-in-memory"))
//    testImplementation("io.vertx:vertx-junit5:4.0.2")
    testImplementation("io.vertx:vertx-web-client:4.0.2")
    testImplementation("io.kotest:kotest-runner-junit5:4.4.1")
    testImplementation("io.kotest:kotest-assertions-core:4.4.1")
}

application {
    mainClass.set("dev.ligature.slonky.AppKt")
}
