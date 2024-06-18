package tiledmap.layers

import engine.EngineObject

class TiledMapLayer(
    val id: Int,
    val name: String,
    val chunks: MutableList<EngineObject> = mutableListOf(),
    val objects: MutableList<EngineObject> = mutableListOf()
)