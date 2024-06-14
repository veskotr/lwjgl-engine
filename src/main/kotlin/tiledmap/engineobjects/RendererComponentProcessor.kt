package tiledmap.engineobjects

import graphics.rendering.Renderer
import structure.EngineObject
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

interface RendererComponentProcessor {

    fun processRendererComponent(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String
    ): Renderer


}