plugins {
    id("java")
    id("war")
}

group = "space.nerfthis"
version = ""

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly("javax:javaee-api:8.0")
    compileOnly("javax.faces:javax.faces-api:2.3")
    implementation("org.hibernate.orm:hibernate-core:6.6.1.Final")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.hibernate:hibernate-core:5.6.14.Final")
    implementation("org.hibernate:hibernate-entitymanager:5.6.14.Final")
}

tasks.test {
    useJUnitPlatform()
}


tasks.register("deployToServer") {
    group = "deployment"
    description = "Builds the project, transfers .war to server, restarts WildFly"

    dependsOn("build")

    doLast {
        val remoteUser = System.getenv("DEPLOYUSER")
        val remoteHost = System.getenv("DEPLOYHOST")
        val remotePath = "wildfly-20.0.1.Final/standalone/deployments/"
        val warFile = layout.buildDirectory.file("libs/lab3web.war").get().asFile.absolutePath
        val wildFlyStopCommand = "pkill -f wildfly"
        val wildFlyStartCommand = "nohup wildfly-20.0.1.Final/bin/standalone.sh -b 0.0.0.0&"
        val password = System.getenv("DEPLOYPWD")

        exec {
            commandLine("pscp", "-pw", password, warFile, "$remoteUser@$remoteHost:$remotePath")
        }

        exec {
            commandLine("plink", "-batch", "-pw", password, "$remoteUser@$remoteHost", wildFlyStopCommand)
            isIgnoreExitValue = true

        }

        exec {
            commandLine("plink", "-batch", "-pw", password, "$remoteUser@$remoteHost", wildFlyStartCommand, "exit")
            isIgnoreExitValue = true
        }
    }
}