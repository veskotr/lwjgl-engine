package tiledmap.chunks

import engine.camera
import org.joml.Vector2f
import engine.EngineComponent
import engine.EngineObject

class MapChunkComponent(val worldPosition: Vector2f, val worldSize: Vector2f, val tiles: List<List<EngineObject>>) :
    EngineComponent() {
    private var wasInView = true

    override fun start() {
        setTilesActive(wasInView)
        parentObject.setPosition(worldPosition)
        parentObject.setScale(worldSize)
    }

    override fun update() {
        val isInView = camera.isInView(parentObject)
        if (isInView != wasInView) {
            setTilesActive(isInView)
            wasInView = isInView
        }
    }

    private fun setTilesActive(active: Boolean) {
        for (row in tiles) {
            for (tile in row) {
                tile.active = active
            }
        }
    }
}
