package tiledmap

import graphics.Texture
import org.joml.Vector2f
import tiledmap.tiles.TexturedTile

class TileSet(
    private val textureAtlas: Texture,
    val firstGrid: Int,
    private val bufferIds: Map<Int, Int>,
    val tileCount: Int
) {
    fun createTile(tileId: Int, position: Vector2f, scale: Vector2f): TexturedTile {
        println(tileId)
        return TexturedTile(position, scale, bufferIds[tileId]!!, textureAtlas)
    }
}