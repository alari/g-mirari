package ru.mirari.infra.ca;

import java.util.List;

/**
 * @author alari
 * @since 11/19/12 1:23 AM
 */
public interface Band {
    String getStyle();
    void setStyle(String style);

    String getType();
    void setType(String type);

    List<Atom> getAtoms();
    void setAtoms(List<Atom> atoms);
}
