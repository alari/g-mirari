package mirari

import grails.plugin.spock.IntegrationSpec
import infra.ca.Atom
import infra.ca.AtomFactory
import infra.ca.AtomPush
import infra.ca.AtomsManager
import org.springframework.beans.factory.annotation.Autowired

class BlocksAtomSpec extends IntegrationSpec {
    AtomFactory atomFactory
    @Autowired
    AtomsManager atomsManager

    def "atom factory is autowired correctly"(){
        expect:
        atomFactory != null
        atomFactory instanceof BlockFactory
        atomsManager != null
    }

    def "text block is a block"() {
        given:
        AtomPush data = atomFactory.buildPushAtom(
                title: "test text",
                text: "<p>a</p>",
                type: "html"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "html"
        atom.title == data.title
        atom.text == "<p>a</p>"
        atom instanceof Block
        atom.id != null
        when:
        atomsManager.forRender(atom)
        then:
        atom.text == "<p>a</p>"
    }
}
