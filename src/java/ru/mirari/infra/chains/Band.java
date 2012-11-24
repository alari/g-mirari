package ru.mirari.infra.chains;

import ru.mirari.infra.ca.Atom;
import ru.mirari.infra.ca.StringIdContainer;

import java.util.List;

/**
 * @author alari
 * @since 11/19/12 1:23 AM
 */
public interface Band extends StringIdContainer {
    String getStyle();

    void setStyle(String style);

    String getType();

    void setType(String type);

    List<Atom> getAtoms();

    void setAtoms(List<Atom> atoms);
}
