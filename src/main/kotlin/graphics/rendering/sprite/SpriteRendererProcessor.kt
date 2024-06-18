package graphics.rendering.sprite

import graphics.Texture
import graphics.rendering.AbstractRenderer
import graphics.rendering.SquareModel
import engine.EngineObject
import org.joml.Vector2f
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
            squareModel = SquareModel(size = Vector2f(objectProperties.size))
        )

        return SpriteRenderer(
            sprite = sprite,
            layerName = engineObject.layerName,
        )
    }
}