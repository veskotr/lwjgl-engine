package tiledmap.engineobjects

import structure.EngineComponent
import structure.EngineObject
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

interface EngineComponentProcessor {

    fun processEngineComponent(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String
    ): EngineComponent

}