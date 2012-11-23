package ru.mirari.infra.chains

import grails.plugin.spock.IntegrationSpec
import ru.mirari.infra.ca.impl.AtomPOJO
import spock.lang.Stepwise
import ru.mirari.infra.chains.ChainsManager
import ru.mirari.infra.chains.Chain
import ru.mirari.infra.ca.Atom

@Stepwise
class ChainsManagerSpec extends IntegrationSpec {

    ChainsManager chainsManager

    def setup() {
    }

    def cleanup() {
    }

    private Atom.Push getData() {
        new AtomPOJO.Push(
                title: "test text",
                text: "a"
        )
    }

    void "test autowired"() {
        expect:
        chainsManager != null
    }

    void "pushes atom into clean chain"() {
        given:
        Atom.Push data = getData()
        Chain chain
        Atom atom

        when:
        chain = chainsManager.buildChain()
        atom = chainsManager.pushAtom(chain, data)

        then:
        atom != null
        atom.type == "text"
        atom.text == data.text
        chain.bands.size() == 1
        chain.bands[0].atoms[0] == atom
        atom.id != null
        chainsManager.getAtom(chain, atom.id) == atom

        when: "we can remove!"
        chainsManager.removeAtom(chain, atom.id)

        then:
        chain.bands.size() == 0
        chainsManager.getAtom(chain, atom.id) == null
    }
}