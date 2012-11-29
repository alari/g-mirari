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

    void "we may move an atom after another band"() {
        given:
        Chain chain = threeBandChain
        List<List<String>> ids = chain.bands*.atoms.id
        List<String> bands = chain.bands*.id

        expect:
        chain.bands[1].atoms.size() == 3

        when:
        chainsManager.moveToBand(chain, ids[0][0], bands[0])

        then:
        ids == chain.bands*.atoms.id

        when:
        chainsManager.moveToBand(chain, ids[0][0], bands[1])

        then:
        chain.bands.size() == 2
        chain.bands[1].atoms*.id == [ids[0][0], ids[2][0]]

        when:
        chainsManager.moveToBand(chain, ids[1][0], bands[2])

        then:
        chain.bands.size() == 3
        chain.bands[2].atoms*.id == [ids[1][0]]
        chain.bands[0].atoms.size() == 2

        when:
        chainsManager.moveToBand(chain, ids[1][1], bands[1])

        then:
        chain.bands.size() == 3
        chain.bands[0].atoms*.id == [ids[1][2], ids[1][1]]
    }

    void "we may push an atom to a band"() {
        given:
        Chain chain = threeBandChain
        String id0 = chain.bands[0].atoms[0].id
        String id1

        expect:
        chain.bands.size() == 3
        chain.bands[0].atoms.size() == 1

        when:
        def rd = ruData
        chainsManager.pushAtom(chain, rd, chain.bands[0].id)
        id1 = rd.id

        then:
        chain.bands.size() == 3
        chain.bands[0].atoms.size() == 2
        chain.bands[0].atoms*.id == [id0, id1]
    }

    void "we may move an atom from band to band of the same type"() {
        given:
        Chain chain = threeBandChain
        String id0 = chain.bands[0].atoms[0].id
        String id2 = chain.bands[2].atoms[0].id
        String id1

        expect:
        chain.bands.size() == 3
        chain.bands[0].atoms.size() == 1
        chain.bands[2].atoms*.id == [id2]

        when: "pushing an atom to the first band"
        def rd = ruData
        chainsManager.pushAtom(chain, rd, chain.bands[0].id)
        id1 = rd.id

        then:
        chain.bands.size() == 3
        chain.bands[0].atoms.size() == 2
        chain.bands[0].atoms*.id == [id0, id1]

        when: "moving a non-singular atom from a band to a band of the same type"
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

    void "we may move an atom before or after another band"() {
        given:
        Chain chain = threeBandChain
        List<List<String>> atoms = chain.bands*.atoms.id
        List<String> bands = chain.bands*.id

        expect:
        chain.bands.size() == 3
        chain.bands*.atoms*.size() == [1, 3, 1]

        when:
        chainsManager.moveToBand(chain, atoms[0][0], bands[1], 0)

        then:
        chain.bands*.atoms.id == atoms

        when:
        chainsManager.moveToBand(chain, atoms[0][0], bands[1], 3)

        then:
        chain.bands*.atoms*.size() == [3, 2]
        chain.bands[1].atoms*.id == [atoms[0][0], atoms[2][0]]

        when:
        chainsManager.moveToBand(chain, atoms[2][0], bands[1], 0)

        then:
        chain.bands*.atoms*.size() == [1, 3, 1]
        chain.bands[0].atoms*.id == [atoms[2][0]]
        chain.bands[2].atoms*.id == [atoms[0][0]]

        when:
        chainsManager.moveToBand(chain, atoms[1][0], bands[1], 2)

        then:
        chain.bands*.atoms*.size() == [1, 3, 1]
        chain.bands[1].atoms*.id == [atoms[1][1], atoms[1][2], atoms[1][0]]
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
        chain.bands*.atoms*.size() == [1, 1, 2, 1]
        !(chain.bands[2].id in [id0, id1, id2])
        chain.bands*.id == [id1, id0, chain.bands[2].id, id2]
        chain.bands[2].style == style1

        when: "moving an atom into beginning of another typed band, after a band of correct type, uniting bands"
        id2 = chain.bands[2].id
        chainsManager.moveToBand(chain, chain.bands[3].atoms[0].id, chain.bands[0].id, 1)

        then:
        chain.bands*.id == [id1, id0, id2]
        chain.bands[1].atoms.size() == 2
        chain.bands*.atoms*.size() == [1, 2, 2]

        when: "continue uniting bands"
        chainsManager.moveToBand(chain, chain.bands[0].atoms[0].id, chain.bands[2].id)

        then:
        chain.bands.size() == 2
        chain.bands*.atoms*.size() == [2, 3]
    }

    void "we may set position of an atom in a chain to move it between bands, split, replace or something"() {
        given:
        Chain chain = threeBandChain
        List<List<String>> atoms = chain.bands*.atoms.id

        when: "not moving an atom"
        chainsManager.moveAtom(chain, atoms[0][0], 0)

        then:
        chain.bands*.atoms.id == atoms

        when: "moving to the end"
        chainsManager.moveAtom(chain, atoms[0][0], 10)

        then:
        chain.bands*.atoms.id == [atoms[1], [atoms[2][0], atoms[0][0]]]

        when: "moving to split"
        chainsManager.moveAtom(chain, atoms[0][0], 1)

        then:
        chain.bands*.atoms.id == [[atoms[1][0]], [atoms[0][0]], [atoms[1][1], atoms[1][2]], [atoms[2][0]]]

        when: "moving inside a band"
        chainsManager.moveAtom(chain, atoms[1][1], 3)

        then:
        chain.bands*.atoms.id == [[atoms[1][0]], [atoms[0][0]], [atoms[1][2], atoms[1][1]], [atoms[2][0]]]

        when: "looking to unexistent source"
        chainsManager.moveAtom(chain, atoms[1][1] + "x", 3)

        then:
        thrown(NotFoundInChainException)
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