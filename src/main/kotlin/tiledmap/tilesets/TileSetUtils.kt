package tiledmap.tilesets

import graphics.Texture
import graphics.utils.createVbo
import org.joml.Vector2f
import org.w3c.dom.Element
import tiledmap.TILE_HEIGHT
import tiledmap.TILE_WIDTH
import tiledmap.engineobjects.OBJECT
import tiledmap.engineobjects.extractObjectProperties
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.layers.ID
import tiledmap.layers.OBJECT_GROUP

private const val TILE_SET = "tileset"
private const val IMAGE = "image"
private const val SOURCE = "source"
private const val TILE_COUNT = "tilecount"
private const val FIRST_GID = "firstgid"
private const val COLUMNS = "columns"
private const val TILE = "tile"

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

    val glWidth = tileSize.x / textureAtlas.width
    val glHeight = tileSize.y / textureAtlas.height

    val bufferIds = sliceTileSet(firstGid, rows, columns, glWidth, glHeight)

    return TileSet(textureAtlas, firstGid, bufferIds, tileCount, extractTileSetObjectProperties(tileSetElement, path, firstGid))
}

fun sliceTileSet(firstGrid: Int, rows: Int, columns: Int, glWidth: Float, glHeight: Float): Map<Int, Int> {
    val bufferIds = mutableMapOf<Int, Int>()
    for (row in 0 until rows) {
        for (column in 0 until columns) {
            val textureCoordinates = floatArrayOf(
                column * glWidth, row * glHeight,
                column * glWidth + glWidth, row * glHeight,
                column * glWidth, row * glHeight + glHeight,
                column * glWidth + glWidth, row * glHeight + glHeight
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

private fun extractTileSetObjectProperties(
    tileSetElement: Element,
    path: String,
    firstGid: Int
): Map<Int, MutableList<ObjectProperties>> {
    val tileElements = tileSetElement.getElementsByTagName(TILE).let { if (it.length == 0) return emptyMap() else it }

    val objectPropertiesMap = mutableMapOf<Int, MutableList<ObjectProperties>>()

    for (i in 0 until tileElements.length) {
        val tileElement = tileElements.item(i) as Element
        val tileId = tileElement.getAttribute(ID).toInt() + firstGid
        val objectGroupElement = tileElement.getElementsByTagName(OBJECT_GROUP).item(0) as Element
        val objectElements = objectGroupElement.getElementsByTagName(OBJECT)
        val objectPropertiesList = mutableListOf<ObjectProperties>()
        for (j in 0 until objectElements.length) {
            val objectElement = objectElements.item(j) as Element
            val objectProperties = extractObjectProperties(objectElement, path)
            objectPropertiesList.add(objectProperties)
        }
        objectPropertiesMap[tileId] = objectPropertiesList
    }

    return objectPropertiesMap
}