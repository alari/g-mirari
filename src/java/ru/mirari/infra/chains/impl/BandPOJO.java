package ru.mirari.infra.chains.impl;

import ru.mirari.infra.ca.Atom;
import ru.mirari.infra.chains.Band;

import java.util.List;

/**
 * @author alari
 * @since 11/19/12 1:29 AM
 */
public class BandPOJO implements Band {
    private String id;
    private String style;
    private String type;
    private List<Atom> atoms;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Atom> getAtoms() {
        return atoms;
    }

    public void setAtoms(List<Atom> atoms) {
        this.atoms = atoms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
