package tiledmap

import structure.IEngineObject
import tiledmap.layers.MapLayer
import tiledmap.rendering.TiledMapRenderer

class TiledMap(val layers: List<MapLayer>): IEngineObject {

    val renderer = TiledMapRenderer(this)

    var active: Boolean = false
        set(value) {
            field = value

        }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }
}