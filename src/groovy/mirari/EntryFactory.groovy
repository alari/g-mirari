package mirari

import infra.chains.Chain
import infra.chains.ChainFactory

/**
 * @author alari
 * @since 2/26/13 11:26 PM
 */
class EntryFactory implements ChainFactory {
    @Override
    Chain buildChain() {
        new Entry()
    }
}
