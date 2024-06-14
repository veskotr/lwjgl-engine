package tiledmap.tilesets

import graphics.Texture
import graphics.rendering.SquareModel
import graphics.rendering.sprite.Sprite
import graphics.rendering.sprite.SpriteRenderer
import org.joml.Vector2f
import structure.EngineObject
import tiledmap.engineobjects.model.ObjectProperties

class TileSet(
    private val textureAtlas: Texture,
    val firstGrid: Int,
    private val bufferIds: Map<Int, Int>,
    val tileCount: Int,
    private val tileTemplateProperties: Map<Int, List<ObjectProperties>>
) {
    fun createTile(tileId: Int, position: Vector2f, scale: Vector2f, layerName: String): EngineObject {
        val engineObject = EngineObject(id = tileId, layerName = layerName)
        engineObject.setPosition(position)
        engineObject.setScale(scale)
        engineObject.renderer = SpriteRenderer(
            sprite = Sprite(
                textureAtlas,
                SquareModel(bufferIds[tileId]!!)
            ), layerName = layerName
        )
        return engineObject
    }

    fun getTileBufferId(tileId: Int): Int {
        return bufferIds[tileId]!!
    }

    fun getTileProperties(tileId: Int): List<ObjectProperties> {
        return tileTemplateProperties[tileId] ?: listOf()
    }
}