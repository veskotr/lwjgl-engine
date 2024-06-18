package tiledmap.engineobjects

import graphics.rendering.AbstractRenderer
import engine.EngineObject
import tiledmap.engineobjects.model.ObjectCustomProperty
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

interface RendererComponentProcessor {

    fun processRendererComponent(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String,
        customProperty: ObjectCustomProperty
    ): AbstractRenderer


}