package tiledmap.tilesets

import graphics.Texture
import org.joml.Vector2f
import tiledmap.tiles.TexturedTile

class TileSet(
    val textureAtlas: Texture,
    val firstGrid: Int,
    private val bufferIds: Map<Int, Int>,
    val tileCount: Int
) {
    fun createTile(tileId: Int, position: Vector2f, scale: Vector2f): TexturedTile {
        return TexturedTile(position, scale, bufferIds[tileId]!!, textureAtlas)
    }

    fun getTileBufferId(tileId: Int): Int {
        return bufferIds[tileId]!!
    }
}