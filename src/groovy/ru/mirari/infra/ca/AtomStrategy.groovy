package ru.mirari.infra.ca

/**
 * @author alari
 * @since 11/13/12 11:42 PM
 */
abstract class AtomStrategy {
    abstract boolean isContentSupported(final Atom.Push data);

    private volatile String name;

    final public String getName() {
        if(name == null) {
            synchronized (this) {
                if(name == null) {
                    name = getClass().simpleName - "Strategy"
                    name = name.charAt(0).toLowerCase().toString() + name.substring(1)
                }
            }
        }
        name
    }

    public boolean checkLast() {
        false
    }

    final void update(Atom atom, final Atom.Push data) {
        if(data.id) atom.id = data.id
        atom.title = data.title
        atom.lastUpdated = new Date()
        setContent(atom, data)
    }
    abstract void setContent(Atom atom, final Atom.Push data);

    void forUpdate(Atom atom) {}
    void forRender(Atom atom) {}
    void delete(Atom atom) {}
}
