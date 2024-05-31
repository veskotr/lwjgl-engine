package tiledmap

import org.joml.Vector2f
import tiledmap.tiles.Tile

class MapChunk(private val position: Vector2f, private val size: Vector2f, val tiles: List<List<Tile>>) {

}
