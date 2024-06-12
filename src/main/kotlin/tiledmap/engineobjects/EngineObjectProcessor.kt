package tiledmap.engineobjects

import structure.EngineObject
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

abstract class EngineObjectProcessor() {

    abstract fun processEngineObject(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String
    ): EngineObject

}