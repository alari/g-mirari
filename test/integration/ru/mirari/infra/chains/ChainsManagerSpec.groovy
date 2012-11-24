package ru.mirari.infra.chains

import grails.plugin.spock.IntegrationSpec
import ru.mirari.infra.ca.Atom
import ru.mirari.infra.ca.impl.AtomPOJO
import ru.mirari.infra.chains.ex.NotFoundInChainException
import spock.lang.Stepwise

@Stepwise
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

        when:
        chainsManager.getAtom(chain, atom.id)

        then:
        thrown(NotFoundInChainException)
    }

    void "we may move an atom inside a band"() {
        given:
        Atom.Push p1 = data
        Atom.Push p2 = data
        Atom.Push p3 = data

        Atom.Push r0 = ruData
        Atom.Push r4 = ruData

        Chain chain = chainsManager.buildChain()
        chainsManager.pushAtom(chain, r0)
        chainsManager.pushAtom(chain, p1)
        chainsManager.pushAtom(chain, p2)
        chainsManager.pushAtom(chain, p3)
        chainsManager.pushAtom(chain, r4)

        expect: "chain is prepared correctly"
        chain.bands.size() == 3
        chain.bands[0].atoms.size() == 1
        chain.bands[2].atoms.size() == 1
        chain.bands[1].atoms.size() == 3
        chain.bands[1].atoms[0].id == p1.id
        chain.bands[1].atoms[1].id == p2.id
        chain.bands[1].atoms[2].id == p3.id

        when:
        chainsManager.moveInBand(chain, p1.id, 1)

        then:
        chain.bands[1].atoms[0].id == p2.id
        chain.bands[1].atoms[1].id == p1.id
        chain.bands[1].atoms[2].id == p3.id

        when:
        chainsManager.moveInBand(chain, p1.id, 0)

        then:
        chain.bands[1].atoms[0].id == p1.id
        chain.bands[1].atoms[1].id == p2.id
        chain.bands[1].atoms[2].id == p3.id
    }

    void "we may move bands inside a chain"() {
        given:
        Chain chain = threeBandChain
        String id0 = chain.bands[0].id
        String id1 = chain.bands[1].id
        String id2 = chain.bands[2].id

        expect:
        chain.bands.size() == 3

        when:
        chainsManager.moveBand(chain, id0, 1)

        then:
        chain.bands[1].id == id0
        chain.bands[0].id == id1
        chain.bands[2].id == id2

        when:
        chainsManager.moveBand(chain, id0, 2)

        then:
        chain.bands[2].id == id0
        chain.bands[0].id == id1
        chain.bands[1].id == id2

        when:
        chainsManager.moveBand(chain, id0, 0)

        then:
        chain.bands[0].id == id0
        chain.bands[1].id == id1
        chain.bands[2].id == id2
    }

    void "we may move an atom from band to band of the same type"() {
        given:
        Chain chain = threeBandChain
        String id0 = threeBandChain.bands[0].atoms[0].id
        String id2 = threeBandChain.bands[1].atoms[0].id
        String id1

        when: "pushing an atom to the first band"
        chainsManager.pushAtom(chain, ruData, chain.bands[0].id)

        then:
        chain.bands.size() == 3
        chain.bands[0].atoms.size() == 2
        chain.bands[0].atoms[0].id == id0

        when:
        id1 = chain.bands[0].atoms[1].id
        chainsManager.moveToBand(chain, id0, chain.bands[2].id, 0)

        then:
        chain.bands[0].atoms*.id == [id1]
        chain.bands[2].atoms*.id == [id0, id2]

        when:
        chainsManager.moveToBand(chain, id2, chain.bands[0].id, 1)

        then:
        chain.bands[0].atoms*.id == [id1, id2]
        chain.bands[2].atoms*.id == [id0]

        when:
        chainsManager.moveToBand(chain, id0, chain.bands[0].id)

        then:
        chain.bands[0].atoms*.id == [id1, id2, id0]
        chain.bands.size() == 2

        when:
        chainsManager.moveToBand(chain, id0, chain.bands[0].id, 0)

        then:
        chain.bands[0].atoms*.id == [id0, id1, id2]
    }

    void "we may split a band by moving an atom of another type inside it"() {
        given:
        Chain chain = threeBandChain
        String id0 = chain.bands[0].id
        String id1 = chain.bands[1].id
        String id2 = chain.bands[2].id
        String style0 = chain.bands[0].style
        String style1 = chain.bands[1].style
        String style2 = chain.bands[2].style

        when: "moving atom to split an another typed band"
        chainsManager.moveToBand(chain, chain.bands[0].atoms[0].id, chain.bands[1].id, 1)

        then: "new band is created"
        chain.bands.size() == 4
        chain.bands[0].atoms.size() == 1
        chain.bands[0].id == id1
        chain.bands[1].id == id0
        !(chain.bands[2].id in [id0, id1, id2])
        chain.bands[2].style == style1
        chain.bands[3].id == id2
        chain.bands*.atoms.size() == [1, 1, 2, 1]

        when: "moving an atom into beginning of another typed band, before a band of correct type, uniting bands"
        chainsManager.moveToBand(chain, chain.bands[3].atoms[0].id, chain.bands[0].id, 1)

        then:
        chain.bands.size() == 3
        chain.bands[0].id == id1
        chain.bands[1].id == id0
        chain.bands[2].id == id2
        chain.bands[1].atoms.size() == 2
        chain.bands*.atoms.size() == [1, 2, 2]

        when: "continue uniting bands"
        chainsManager.moveToBand(chain, chain.bands[0].atoms[0].id, chain.bands[2].id)

        then:
        chain.bands.size() == 2
        chain.bands*.atoms.size() == [2, 3]
    }

    void "we may set position of an atom in a chain to move it between bands, split, replace or something"() {
        // TODO: implement test
    }


    private Atom.Push getData() {
        new AtomPOJO.Push(
                title: "test text",
                text: "a"
        )
    }

    private Atom.Push getRuData() {
        new AtomPOJO.Push(
                externalUrl: "http://russia.ru/video/diskurs_12854/"
        )
    }

    private Chain getThreeBandChain() {
        Chain chain = chainsManager.buildChain()
        chainsManager.pushAtom(chain, ruData)
        chainsManager.pushAtom(chain, data)
        chainsManager.pushAtom(chain, data)
        chainsManager.pushAtom(chain, data)
        chainsManager.pushAtom(chain, ruData)
        chain
    }
}