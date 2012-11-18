package ru.mirari.infra.ca

import grails.plugin.spock.IntegrationSpec
import spock.lang.Stepwise
import ru.mirari.infra.ca.impl.AtomPOJO

@Stepwise
class RussiaRuSpec extends IntegrationSpec {

    AtomsManager atomsManager

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        given:
        Atom.Push data = new AtomPOJO.Push(
                externalUrl: "http://russia.ru/video/diskurs_12854/"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "russiaRu"
        atom.externalId == "diskurs_12854"
    }
}