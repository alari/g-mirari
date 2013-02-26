package mirari

import infra.chains.Band
import infra.chains.BandFactory

/**
 * @author alari
 * @since 2/26/13 11:25 PM
 */
class BlockDiapasonFactory implements BandFactory {
    @Override
    Band buildBand() {
        new BlockDiapason()
    }
}
