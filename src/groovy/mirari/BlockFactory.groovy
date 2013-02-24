package mirari

import infra.ca.AtomFactory
import infra.ca.impl.AtomPOJOPush

/**
 * @author alari
 * @since 2/24/13 11:21 PM
 */
class BlockFactory implements AtomFactory<Block,AtomPOJOPush> {
    @Override
    AtomPOJOPush buildPushAtom() {
        new AtomPOJOPush()
    }

    @Override
    AtomPOJOPush buildPushAtom(Map props) {
        new AtomPOJOPush(props)
    }

    @Override
    Block buildAtom() {
        Block b = new Block()
        b.save(failOnError: true)
        b
    }

    @Override
    Block buildAtom(Map props) {
        Block b = new Block(props)
        b.save()
        b
    }
}
