package ru.mirari.infra.ca

import grails.plugin.spock.IntegrationSpec
import ru.mirari.infra.ca.impl.AtomPOJO

class YouTubeSpec extends IntegrationSpec {

    AtomsManager atomsManager

    def setup() {
    }

    def cleanup() {
    }

    void "short link works ok"() {
        given:
        Atom.Push data = new AtomPOJO.Push(
                externalUrl: "http://youtu.be/zi3AqicZgEk"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "youTube"
        atom.externalId == "zi3AqicZgEk"
    }

    void "long link works ok"() {
        given:
        Atom.Push data = new AtomPOJO.Push(
                externalUrl: "http://www.youtube.com/watch?v=zi3AqicZgEk&feature=g-logo&context=G2e33cabFOAAAAAAABAA"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "youTube"
        atom.externalId == "zi3AqicZgEk"
    }
}