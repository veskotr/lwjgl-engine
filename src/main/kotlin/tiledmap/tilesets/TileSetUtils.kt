package tiledmap.tilesets

import graphics.Texture
import graphics.utils.createVbo
import org.joml.Vector2f
import org.w3c.dom.Element
import tiledmap.*

private const val TILE_SET = "tileset"
private const val IMAGE = "image"
private const val SOURCE = "source"
private const val TILE_COUNT = "tilecount"
private const val FIRST_GID = "firstgid"
private const val COLUMNS = "columns"

fun extractTileSets(mapElement: Element, path: String): List<TileSet> {
    val tileSets = mapElement.getElementsByTagName(TILE_SET)
    val tileSetsList = mutableListOf<TileSet>()
    for (i in 0 until tileSets.length) {
        val tileSetElement = tileSets.item(i) as Element
        tileSetsList.add(extractTileSet(tileSetElement, path))
    }
    return tileSetsList
}

private fun extractTileSet(tileSetElement: Element, path: String): TileSet {
    val textureAtlas = extractTileSetTextureAtlas(tileSetElement, path)
    val tileSize = Vector2f(
        tileSetElement.getAttribute(TILE_WIDTH).toFloat(),
        tileSetElement.getAttribute(TILE_HEIGHT).toFloat()
    )
    val tileCount = tileSetElement.getAttribute(TILE_COUNT).toInt()
    val firstGid = tileSetElement.getAttribute(FIRST_GID).toInt()
    val columns = tileSetElement.getAttribute(COLUMNS).toInt()
    val rows = tileCount / columns

    val glWith = tileSize.x / textureAtlas.width
    val glHeight = tileSize.y / textureAtlas.height

    val bufferIds = sliceTileSet(firstGid, rows, columns, glWith, glHeight)

    return TileSet(textureAtlas, firstGid, bufferIds, tileCount)
}

private fun sliceTileSet(firstGrid: Int, rows: Int, columns: Int, glWith: Float, glHeight: Float): Map<Int, Int> {
    val bufferIds = mutableMapOf<Int, Int>()
    for (row in 0 until rows) {
        for (column in 0 until columns) {
            val textureCoordinates = floatArrayOf(
                column * glWith, row * glHeight,
                column * glWith + glWith, row * glHeight,
                column * glWith, row * glHeight + glHeight,
                column * glWith + glWith, row * glHeight + glHeight
            )
            val tileId = firstGrid + row * columns + column
            bufferIds[tileId] = createVbo(textureCoordinates)
        }
    }
    return bufferIds
}


private fun extractTileSetTextureAtlas(tileSetElement: Element, path: String): Texture {
    val tileSetImageSrc = (tileSetElement.getElementsByTagName(IMAGE).item(0) as Element).getAttribute(SOURCE)
    return Texture("$path/$tileSetImageSrc")
}