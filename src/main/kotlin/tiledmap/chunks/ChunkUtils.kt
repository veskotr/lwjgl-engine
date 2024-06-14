package tiledmap.chunks

import org.joml.Vector2f
import org.joml.Vector2i
import org.w3c.dom.Element
import structure.EngineComponent
import structure.EngineObject
import tiledmap.engineobjects.TileObjectProcessor
import tiledmap.tilesets.TileSet

private const val WIDTH = "width"
private const val HEIGHT = "height"
const val X = "x"
const val Y = "y"

val tileProcessors: MutableMap<String, TileObjectProcessor<*>> = mutableMapOf()

fun extractChunk(chunkElement: Element, tileSets: List<TileSet>, tileScale: Vector2f, layerName: String): EngineObject {
    val chunkSize = Vector2i(chunkElement.getAttribute(WIDTH).toInt(), chunkElement.getAttribute(HEIGHT).toInt())
    val chunkPosition = Vector2i(chunkElement.getAttribute(X).toInt(), chunkElement.getAttribute(Y).toInt())

    val chunkString = chunkElement.textContent

    val tiles = extractChunkTiles(chunkString, chunkSize, Vector2f(chunkPosition), tileScale, tileSets, layerName)

    val chunkWorldSize = calculateChunkWorldSize(chunkSize, tileScale)
    val chunkWorldPosition = calculateChunkWorldPosition(chunkPosition, tileScale, chunkWorldSize)

    val chunkComponent = MapChunkComponent(chunkWorldPosition, chunkWorldSize, tiles)

    val chunk = EngineObject(layerName = layerName)
    chunk.addComponent(chunkComponent)

    return chunk
}

private fun extractChunkTiles(
    chunkCSVString: String,
    chunkSize: Vector2i,
    chunkPosition: Vector2f,
    tileScale: Vector2f,
    tileSets: List<TileSet>,
    layerName: String
): List<List<EngineObject>> {
    val tileRows = chunkCSVString.split("\n").map { it.trim() }.filter { it.isNotEmpty() }

    val tiles = mutableListOf<MutableList<EngineObject>>()

    for (y in 0 until chunkSize.y) {
        val row = mutableListOf<EngineObject>()
        for (x in 0 until chunkSize.x) {
            val tileId = tileRows[y].split(",")[x].toInt()
            val tilePosition = Vector2f(chunkPosition).mul(tileScale.mul(2.0f, Vector2f()))
                .add(Vector2f(x.toFloat(), y.toFloat()).mul(tileScale.mul(2.0f, Vector2f())))
            tilePosition.mul(1.0f, -1.0f)

            row.add(createTile(tileId, tilePosition, tileScale, tileSets, layerName))
        }
        tiles.add(row)
    }

    return tiles
}

private fun createTile(
    tileId: Int,
    position: Vector2f,
    scale: Vector2f,
    tileSets: List<TileSet>,
    layerName: String
): EngineObject {
    val tileSet = tileSets.firstOrNull { it.firstGrid <= tileId && it.firstGrid + it.tileCount > tileId }

    val tile = tileSet?.createTile(tileId, position, scale, layerName) ?: EngineObject(
        tileId,
        position,
        scale,
        layerName = layerName
    )

    if (tileSet == null) {
        return tile
    }

    processTileProperties(tileSet, tileId, tile).forEach { tile.addComponent(it) }

    return tile
}

private fun processTileProperties(tileSet: TileSet, tileId: Int, tile: EngineObject): List<EngineComponent> {
    return tileSet.getTileProperties(tileId).filter { tileProcessors.containsKey(it.type) }
        .map { tileProcessors[it.type]!!.processObjectToComponent(tile = tile, objectProperties = it) }
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

fun registerTileProcessor(processor: TileObjectProcessor<*>, type: String) {
    tileProcessors[type] = processor
}