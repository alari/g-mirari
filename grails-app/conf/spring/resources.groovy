import mirari.BlockDiapasonFactory
import mirari.BlockFactory
import mirari.EntryFactory

// Place your Spring DSL code here
beans = {
    atomFactory(BlockFactory)
    bandFactory(BlockDiapasonFactory)
    chainFactory(EntryFactory)
}
