package tiledmap.chunks

import org.joml.Vector2f
import tiledmap.tiles.Tile

class MapChunk(private val worldPosition: Vector2f, private val worldSize: Vector2f, val tiles: List<List<Tile>>) {

}
