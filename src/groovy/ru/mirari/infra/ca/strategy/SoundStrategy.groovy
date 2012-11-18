package ru.mirari.infra.ca.strategy

import ru.mirari.infra.ca.AtomStrategy
import ru.mirari.infra.ca.Atom

/**
 * @author alari
 * @since 11/18/12 10:36 PM
 */
class SoundStrategy extends AtomStrategy {
    @Override
    boolean isContentSupported(Atom.Push data) {
        return false  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    void setContent(Atom atom, Atom.Push data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
