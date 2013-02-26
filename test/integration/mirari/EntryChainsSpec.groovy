package mirari

import grails.plugin.spock.IntegrationSpec
import infra.ca.Atom
import infra.ca.AtomPush
import infra.ca.impl.AtomPOJOPush
import infra.chains.Chain
import infra.chains.ChainsManager
import infra.chains.ex.NotFoundInChainException

class EntryChainsSpec extends IntegrationSpec {
    ChainsManager chainsManager

    void "test autowired"() {
        expect:
        chainsManager != null
        chainsManager.chainFactory instanceof EntryFactory
    }

    void "pushes atom into clean chain"() {
        given:
        AtomPush data = getData()
        Chain chain
        Atom atom

        when:
        chain = chainsManager.buildChain()
        atom = chainsManager.pushAtom(chain, data)

        then:
        chain instanceof Entry
        atom != null
        atom instanceof Block
        atom.type == "markdown"
        atom.text == data.text
        chain.bands.size() == 1
        chain.bands[0] instanceof BlockDiapason
        chain.bands[0].atoms[0] == atom
        atom.id != null
        chainsManager.getAtom(chain, atom.id) == atom

        when: "we can remove!"
        chainsManager.removeAtom(chain, atom.id)

        then:
        chain.bands.size() == 0

        when:
        chainsManager.getAtom(chain, atom.id)

        then:
        thrown(NotFoundInChainException)
    }

    private AtomPush getData() {
        new AtomPOJOPush(
                title: "test text",
                text: "a"
        )
    }

    private AtomPush getRuData() {
        new AtomPOJOPush(
                externalUrl: "http://russia.ru/video/diskurs_12854/"
        )
    }
}
