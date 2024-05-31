package tiledmap

import graphics.Texture
import graphics.utils.createVbo
import org.joml.Vector2f
import org.joml.Vector2i
import org.w3c.dom.Document
import org.w3c.dom.Element
import tiledmap.tiles.EmptyTile
import tiledmap.tiles.TexturedTile
import tiledmap.tiles.Tile
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
private const val TILE = "tile"
private const val DATA = "data"
private const val LAYER = "layer"
private const val CHUNK = "chunk"
private const val WIDTH = "width"
private const val HEIGHT = "height"
private const val X = "x"
private const val Y = "y"

private const val MAP_FILE = "map.tmx"

fun createTiledMapFromFile(path: String): TiledMap {
    val inputStream = object {}.javaClass.getResourceAsStream("$path/$MAP_FILE")
        ?: error("Could not find map file $MAP_FILE in $path")
    val document = getMapDocument(inputStream)
    val mapElement = document.documentElement

    val tileScale =
        Vector2f(mapElement.getAttribute(TILE_WIDTH).toFloat(), mapElement.getAttribute(TILE_HEIGHT).toFloat())
    val tileSets = extractTileSets(mapElement, path)
    val layers = extractLayers(mapElement, tileSets, tileScale)

    return TiledMap(layers)
}

fun extractLayers(mapElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): List<MapLayer> {
    val layers = mapElement.getElementsByTagName(LAYER)
    val layersList = mutableListOf<MapLayer>()
    for (i in 0 until layers.length) {
        val layerElement = layers.item(i) as Element
        layersList.add(extractLayer(layerElement, tileSets, tileScale))
    }
    return layersList
}

fun extractLayer(layerElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): MapLayer {
    val data = (layerElement.getElementsByTagName(DATA).item(0) as Element)
    val chunkElements = data.getElementsByTagName(CHUNK)
    val chunks = mutableListOf<MapChunk>()
    for (i in 0 until chunkElements.length) {
        val chunkElement = chunkElements.item(i) as Element
        chunks.add(extractChunk(chunkElement, tileSets, tileScale))
    }
    return MapLayer(chunks)
}

fun extractChunk(chunkElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): MapChunk {
    val chunkSize = Vector2i(chunkElement.getAttribute(WIDTH).toInt(), chunkElement.getAttribute(HEIGHT).toInt())
    val chunkPosition = Vector2i(chunkElement.getAttribute(X).toInt(), chunkElement.getAttribute(Y).toInt())

    val tiles = mutableListOf<MutableList<Tile>>()

    val chunkString = chunkElement.textContent

    val tileRows = chunkString.split("\n").map { it.trim() }.filter { it.isNotEmpty() }

    for (y in 0 until chunkSize.y) {
        val row = mutableListOf<Tile>()
        for (x in 0 until chunkSize.x) {
            val tileId = tileRows[y].split(",")[x].toInt()
            val tileSet = tileSets.find { it.firstGrid <= tileId && (it.firstGrid + it.tileCount) >= tileId }
                ?: continue
            val position = Vector2f(chunkPosition).mul(tileScale.mul(2.0f, Vector2f()))
                .add(Vector2f(x.toFloat(), y.toFloat()).mul(tileScale.mul(2.0f, Vector2f())))
            position.mul(1.0f, -1.0f)
            val scale = Vector2f(tileScale)
            row.add(tileSet.createTile(tileId, position, scale))
        }
        tiles.add(row)
    }
    return MapChunk(Vector2f(chunkPosition), Vector2f(chunkSize), tiles)
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


