package tiledmap

import org.joml.Vector2f
import org.w3c.dom.Document
import tiledmap.layers.extractLayers
import tiledmap.tilesets.extractTileSets
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

const val TILE_WIDTH = "tilewidth"
const val TILE_HEIGHT = "tileheight"
private const val MAP_FILE = "map.tmx"

fun createTiledMapFromFile(path: String, wantedTileSize: Vector2f? = null): TiledMap {
    val inputStream = object {}.javaClass.getResourceAsStream("$path/$MAP_FILE")
        ?: error("Could not find map file $MAP_FILE in $path")
    val document = getXmlDocument(inputStream)
    val mapElement = document.documentElement

    val mapTileSize = wantedTileSize
        ?: Vector2f(
            mapElement.getAttribute(TILE_WIDTH).toFloat() / 2.0f,
            mapElement.getAttribute(TILE_HEIGHT).toFloat() / 2.0f
        )
    val tileSets = extractTileSets(mapElement, path)

    return TiledMap(extractLayers(mapElement, tileSets, mapTileSize!!, path))
}

fun getXmlDocument(inputStream: InputStream): Document {
    val factory = DocumentBuilderFactory.newInstance()
    factory.isValidating = false
    val builder = factory.newDocumentBuilder()
    return builder.parse(inputStream)
}




