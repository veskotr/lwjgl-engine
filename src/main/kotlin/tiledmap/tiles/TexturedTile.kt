package tiledmap.tiles

import graphics.Texture
import org.joml.Vector2f

class TexturedTile(position: Vector2f, scale: Vector2f, val textureBufferId: Int, val textureAtlas: Texture) :
    Tile(position, scale) {

}