package ru.mirari.infra.ca;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import ru.mirari.infra.ca.ex.CreativeAtomException;
import ru.mirari.infra.ca.ex.NoTypeStrategyFound;
import ru.mirari.infra.ca.impl.AtomPOJO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author alari
 * @since 11/13/12 11:41 PM
 */
@Service
public class AtomsManager implements ApplicationContextAware {
    private Class<? extends Atom> atomClass = AtomPOJO.class;
    private Class<? extends Atom.Push> atomPushClass = AtomPOJO.Push.class;

    private Map<String, AtomStrategy> strategies = new TreeMap<String, AtomStrategy>();
    private LinkedList<AtomStrategy> strategyDiscoverySequence = new LinkedList<AtomStrategy>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        for (Map.Entry<String, AtomStrategy> e : applicationContext.getBeansOfType(AtomStrategy.class).entrySet()) {
            AtomStrategy strategy = e.getValue();
            strategies.put(strategy.getName(), strategy);
            if (strategy.checkLast()) strategyDiscoverySequence.addLast(strategy);
            else strategyDiscoverySequence.addFirst(strategy);
        }
    }

    public Atom build(Atom.Push data) throws IllegalAccessException, InstantiationException, CreativeAtomException {
        if (data == null) {
            throw new NullPointerException();
        }
        AtomStrategy strategy = null;
        if (data.getType() != null) strategy = strategies.get(data.getType());
        Atom atom = atomClass.newInstance();

        prepareExtendedInfo(data);

        if (strategy == null) {
            for (AtomStrategy s : strategyDiscoverySequence) {
                if (s.isContentSupported(data)) {
                    strategy = s;
                    atom.setType(s.getName());
                    break;
                }
            }
            if (strategy == null) {
                throw new NoTypeStrategyFound();
            }
        }
        atom.setDateCreated(new Date());

        strategy.update(atom, data);
        return atom;
    }

    private void prepareExtendedInfo(Atom.Push data) {
        if (data.getExternalUrl() != null && !data.getExternalUrl().isEmpty()) {
            try {
                data.setUrl(new URL(data.getExternalUrl()));
            } catch (MalformedURLException e) {
                data.setExternalUrl(null);
            }
        }
        if (data.getFile() != null) {
            try {
                data.setFileType(Files.probeContentType(data.getFile().toPath()));
            } catch (IOException e) {
                data.setFile(null);
            }
        }
    }

    private AtomStrategy getStrategy(final Atom atom) throws NoTypeStrategyFound {
        AtomStrategy strategy = strategies.get(atom.getType());
        if (strategy == null) {
            throw new NoTypeStrategyFound();
        }
        return strategy;
    }

    public void update(Atom atom, Atom.Push data) throws CreativeAtomException {
        prepareExtendedInfo(data);
        getStrategy(atom).update(atom, data);
    }

    public void forUpdate(Atom atom) throws CreativeAtomException {
        getStrategy(atom).forUpdate(atom);
    }

    public void forRender(Atom atom) throws CreativeAtomException {
        getStrategy(atom).forRender(atom);
    }

    public void delete(Atom atom) throws CreativeAtomException {
        getStrategy(atom).delete(atom);
    }
}
