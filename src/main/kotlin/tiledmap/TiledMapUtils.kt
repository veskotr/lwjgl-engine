package tiledmap

import graphics.Texture
import graphics.utils.createVbo
import org.joml.Vector2f
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

private const val TILE_WIDTH = "tilewidth"
private const val TILE_HEIGHT = "tileheight"
private const val TILE_SET = "tileset"
private const val IMAGE = "image"
private const val SOURCE = "source"
private const val TILE_COUNT = "tilecount"
private const val FIRST_GID = "firstgid"
private const val COLUMNS = "columns"

private const val MAP_FILE = "map.tmx"

fun readTiledMap(path: String) {
    val inputStream =
        Any::class.java.getResourceAsStream("$path/$MAP_FILE") ?: error("Could not find map file $MAP_FILE in $path")
    val document = getMapDocument(inputStream)
    val mapElement = document.documentElement

    val tileScale =
        Vector2f(mapElement.getAttribute(TILE_WIDTH).toFloat(), mapElement.getAttribute(TILE_HEIGHT).toFloat())
    val tileSets = extractTileSets(mapElement, path)
    val layers = extractLayers(mapElement, tileSets, tileScale)

}

fun extractLayers(mapElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): List<MapLayer> {

}

private fun getMapDocument(inputStream: InputStream): Document {
    val factory = DocumentBuilderFactory.newInstance()
    factory.isValidating = false
    val builder = factory.newDocumentBuilder()
    return builder.parse(inputStream)
}

private fun extractTileSets(mapElement: Element, path: String): List<TileSet> {
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

    return TileSet(textureAtlas, firstGid, bufferIds)
}

private fun sliceTileSet(firstGrid: Int, rows: Int, columns: Int, glWith: Float, glHeight: Float): Map<Int, Int> {
    val bufferIds = mutableMapOf<Int, Int>()
    for (row in 0 until rows) {
        for (column in 0 until columns) {
            val textureCoordinates = floatArrayOf(
                column * glWith, row * glHeight,
                column * glWith + glWith, row * glHeight,
                column * glWith + glWith, row * glHeight + glHeight,
                column * glWith, row * glHeight + glHeight
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


