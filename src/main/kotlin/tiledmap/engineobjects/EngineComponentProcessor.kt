package tiledmap.engineobjects

import engine.EngineComponent
import engine.EngineObject
import tiledmap.engineobjects.model.ObjectCustomProperty
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

interface EngineComponentProcessor {

    fun processEngineComponent(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String,
        customProperty: ObjectCustomProperty
    ): EngineComponent

}