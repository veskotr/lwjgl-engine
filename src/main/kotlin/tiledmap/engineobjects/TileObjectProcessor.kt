package tiledmap.engineobjects

import engine.EngineComponent
import engine.EngineObject
import org.joml.Vector2f
import tiledmap.engineobjects.model.ObjectProperties

interface TileObjectProcessor<T : EngineComponent> {

    fun processObjectToComponent(tile: EngineObject, objectProperties: ObjectProperties, tileSize: Vector2f): T
}