package mirari

import infra.ca.Atom
import infra.chains.Band
import infra.chains.Chain

class Entry implements Chain {

    String id
    List<BlockDiapason> diapasons

    @Override
    List<Band> getBands() {
        diapasons
    }

    @Override
    void setBands(List<Band> bands) {
        diapasons = (List<BlockDiapason>) bands
    }

    static mapWith = "mongo"
    static embedded = ['diapasons']
    static hasMany = [diapasons: BlockDiapason]
    static transients = ['bands', 'diapasons.atoms']

    static constraints = {
    }
}

class BlockDiapason implements Band {
    String id
    Map<String, String> styles
    String type

    static transients = ['atoms']

    @Override
    List<Atom> getAtoms() {
        blocks
    }

    @Override
    void setAtoms(List<Atom> atoms) {
        blocks = (List<Block>) atoms
    }

    List<Block> blocks
}