package tiledmap.engineobjects

import structure.EngineComponent
import structure.EngineObject
import tiledmap.engineobjects.model.ObjectProperties

interface TileObjectProcessor<T : EngineComponent> {

    fun processObjectToComponent(tile: EngineObject, objectProperties: ObjectProperties): T
}