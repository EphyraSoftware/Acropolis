plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "org.ephyra.acropolis.cli.Main"
}

dependencies {
    compile(kotlin("stdlib"))
}
