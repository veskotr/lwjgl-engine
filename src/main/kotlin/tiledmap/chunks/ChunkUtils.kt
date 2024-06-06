package tiledmap.chunks

import org.joml.Vector2f
import org.joml.Vector2i
import org.w3c.dom.Element
import tiledmap.tiles.EmptyTile
import tiledmap.tiles.Tile
import tiledmap.tilesets.TileSet

private const val WIDTH = "width"
private const val HEIGHT = "height"
private const val X = "x"
private const val Y = "y"


fun extractChunk(chunkElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): MapChunk {
    val chunkSize = Vector2i(chunkElement.getAttribute(WIDTH).toInt(), chunkElement.getAttribute(HEIGHT).toInt())
    val chunkPosition = Vector2i(chunkElement.getAttribute(X).toInt(), chunkElement.getAttribute(Y).toInt())

    val chunkString = chunkElement.textContent

    val tiles = extractChunkTiles(chunkString, chunkSize, Vector2f(chunkPosition), tileScale, tileSets)

    val chunkWorldSize = calculateChunkWorldSize(chunkSize, tileScale)
    val chunkWorldPosition = calculateChunkWorldPosition(chunkPosition, tileScale, chunkWorldSize)

    return MapChunk(chunkWorldPosition, chunkWorldSize, tiles)
}

private fun extractChunkTiles(
    chunkCSVString: String,
    chunkSize: Vector2i,
    chunkPosition: Vector2f,
    tileScale: Vector2f,
    tileSets: List<TileSet>
): List<List<Tile>> {
    val tileRows = chunkCSVString.split("\n").map { it.trim() }.filter { it.isNotEmpty() }

    val tiles = mutableListOf<MutableList<Tile>>()

    for (y in 0 until chunkSize.y) {
        val row = mutableListOf<Tile>()
        for (x in 0 until chunkSize.x) {
            val tileId = tileRows[y].split(",")[x].toInt()
            val tilePosition = Vector2f(chunkPosition).mul(tileScale.mul(2.0f, Vector2f()))
                .add(Vector2f(x.toFloat(), y.toFloat()).mul(tileScale.mul(2.0f, Vector2f())))
            tilePosition.mul(1.0f, -1.0f)

            row.add(createTile(tileId, tilePosition, tileScale, tileSets))
        }
        tiles.add(row)
    }

    return tiles
}

private fun createTile(tileId: Int, position: Vector2f, scale: Vector2f, tileSets: List<TileSet>): Tile {
    val tileSet = tileSets.firstOrNull { it.firstGrid <= tileId && it.firstGrid + it.tileCount > tileId }

    return tileSet?.createTile(tileId, position, scale) ?: EmptyTile(position, scale)
}

private fun calculateChunkWorldSize(chunkSize: Vector2i, tileScale: Vector2f): Vector2f {
    return Vector2f(chunkSize).mul(tileScale.mul(2.0f, Vector2f()))
}

private fun calculateChunkWorldPosition(
    chunkPosition: Vector2i,
    tileScale: Vector2f,
    chunkWorldSize: Vector2f
): Vector2f {
    return Vector2f(chunkPosition).mul(tileScale.mul(2.0f, Vector2f())).add(chunkWorldSize.mul(0.5f, Vector2f()))
}