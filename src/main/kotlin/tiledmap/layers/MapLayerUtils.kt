package tiledmap.layers

import org.joml.Vector2f
import org.w3c.dom.Element
import tiledmap.chunks.MapChunk
import tiledmap.chunks.extractChunk
import tiledmap.tilesets.TileSet

private const val DATA = "data"
private const val LAYER = "layer"
private const val CHUNK = "chunk"

fun extractLayers(mapElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): List<MapLayer> {
    val layers = mapElement.getElementsByTagName(LAYER)
    val layersList = mutableListOf<MapLayer>()
    for (i in 0 until layers.length) {
        val layerElement = layers.item(i) as Element
        layersList.add(extractLayer(layerElement, tileSets, tileScale))
    }
    return layersList
}

private fun extractLayer(layerElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): MapLayer {
    val data = (layerElement.getElementsByTagName(DATA).item(0) as Element)
    val chunkElements = data.getElementsByTagName(CHUNK)
    val chunks = mutableListOf<MapChunk>()
    for (i in 0 until chunkElements.length) {
        val chunkElement = chunkElements.item(i) as Element
        chunks.add(extractChunk(chunkElement, tileSets, tileScale))
    }
    return MapLayer(chunks)
}
