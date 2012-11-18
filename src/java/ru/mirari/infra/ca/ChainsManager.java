package ru.mirari.infra.ca;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirari.infra.ca.ex.CreativeAtomException;
import ru.mirari.infra.ca.impl.BandPOJO;
import ru.mirari.infra.ca.impl.ChainPOJO;

import java.io.IOException;
import java.util.LinkedList;

/**
 * @author alari
 * @since 11/19/12 1:26 AM
 */
@Service
public class ChainsManager {
    private Class<? extends Chain> chainClass = ChainPOJO.class;
    private Class<? extends Band> bandClass = BandPOJO.class;

    private AtomsManager atomsManager;

    final private ObjectMapper mapper = new ObjectMapper();

    public Chain forJson(String json) throws IOException{
        return mapper.readValue(json, chainClass);
    }

    public String toJson(Chain chain) throws IOException{
        return mapper.writeValueAsString(chain);
    }

    public void addAtom(Chain chain, Atom atom) throws IllegalAccessException, InstantiationException {
        if(chain.getBands() == null) {
            chain.setBands(new LinkedList<Band>());
        }
        Band band = null;
        if(chain.getBands().size()>0) {
            band = chain.getBands().get(chain.getBands().size()-1);
            if(!band.getType().equals(atom.getType())) {
                band = null;
            }
        }
        if(band == null) {
            band = bandClass.newInstance();
            band.setType(atom.getType());
            chain.getBands().add(band);
        }
        if(band.getAtoms() == null) {
            band.setAtoms(new LinkedList<Atom>());
        }
        band.getAtoms().add(atom);
    }

    public void pushAtom(Chain chain, Atom.Push data) throws CreativeAtomException, InstantiationException, IllegalAccessException {
        Atom atom = atomsManager.build(data);
        addAtom(chain, atom);
    }

    public Atom getAtom(Chain chain, String id) {
        for(Band b : chain.getBands()) for(Atom a : b.getAtoms()) if(a.getId().equals(id)) return a;
        return null;
    }

    public void removeAtom(Chain chain, String id) {
        for(Band b : chain.getBands()) for(Atom a : b.getAtoms()) if(a.getId().equals(id)) {
            b.getAtoms().remove(a);
            if(b.getAtoms().size() == 0) {
                chain.getBands().remove(b);
            }
            return;
        }
    }
}
