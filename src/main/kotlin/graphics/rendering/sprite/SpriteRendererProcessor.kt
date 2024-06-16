package graphics.rendering.sprite

import graphics.Texture
import graphics.rendering.AbstractRenderer
import graphics.rendering.SquareModel
import structure.EngineObject
import tiledmap.engineobjects.RendererComponentProcessor
import tiledmap.engineobjects.model.ObjectCustomProperty
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

class SpriteRendererProcessor : RendererComponentProcessor {

    companion object {
        private const val SPRITE = "sprite"
    }

    override fun processRendererComponent(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String,
        customProperty: ObjectCustomProperty
    ): AbstractRenderer {
        val sprite = Sprite(
            texture = Texture("/" + customProperty.classValue!![SPRITE]!!.fileValue!!),
            squareModel = SquareModel()
        )

        return SpriteAbstractRenderer(
            sprite = sprite,
            layerName = engineObject.layerName,
        )
    }
}