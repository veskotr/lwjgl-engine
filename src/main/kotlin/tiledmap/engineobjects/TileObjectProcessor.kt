package tiledmap.engineobjects

import structure.EngineComponent
import tiledmap.engineobjects.model.ObjectProperties

interface TileObjectProcessor<T: EngineComponent> {

    fun processObjectToComponent(objectProperties: ObjectProperties): T
}