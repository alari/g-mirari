grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

//grails.plugin.location.'infra-ui-ca' = "infra-ui-ca"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
        excludes 'hibernate'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsCentral()
        mavenCentral()

        mavenRepo "http://mvn.quonb.org/repo"
        grailsRepo "http://mvn.quonb.org/repo", "quonb"
    }
    dependencies {
        compile "com.fasterxml.jackson.core:jackson-databind:latest.release"
        compile "com.fasterxml.jackson.core:jackson-annotations:latest.release"
        compile "com.fasterxml.jackson.core:jackson-core:latest.release"

        test("org.spockframework:spock-grails-support:0.7-groovy-2.0")
    }

    plugins {
        build ":tomcat:$grailsVersion"

        //runtime ":resources:1.2.RC2"

        compile ":infra-ca:0.1-SNAPSHOT"

        compile ":mongodb:1.1.0.GA"

        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
    }
}
