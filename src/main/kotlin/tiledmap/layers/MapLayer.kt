package tiledmap.layers

import structure.IEngineObject
import tiledmap.chunks.MapChunk

class MapLayer(val chunks: List<MapChunk>): IEngineObject{

    val chunksInView = mutableListOf<MapChunk>()

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }
}