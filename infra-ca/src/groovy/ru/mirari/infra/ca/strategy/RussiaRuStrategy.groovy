package ru.mirari.infra.ca.strategy

import ru.mirari.infra.ca.Atom

import org.springframework.stereotype.Component
import ru.mirari.infra.ca.AtomStrategy

/**
 * @author alari
 * @since 11/18/12 12:54 AM
 */
@Component
class RussiaRuStrategy extends AtomStrategy {
    @Override
    boolean isContentSupported(Atom.Push data) {
        (data.url && data.url.host in ["russia.ru", "tv.russia.ru", "www.russia.ru"])
    }

    @Override
    void setContent(Atom atom, Atom.Push data) {
        //http://russia.ru/video/diskurs_12854/
        // TODO: validate characters in external id!
        atom.externalId = data.url.path.substring(7, data.url.path.size() - 1)
    }
}
