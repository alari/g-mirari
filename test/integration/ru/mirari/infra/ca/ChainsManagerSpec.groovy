package ru.mirari.infra.ca

import grails.plugin.spock.IntegrationSpec
import sun.reflect.generics.reflectiveObjects.NotImplementedException

class ChainsManagerSpec extends IntegrationSpec {

    ChainsManager chainsManager

    def setup() {
    }

    def cleanup() {
    }

    void "test autowired"() {
        expect:
        chainsManager != null
    }

    void "test to be done"() {
        expect:
        thrown(NotImplementedException)
    }
}