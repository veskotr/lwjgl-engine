package tiledmap.layers

import org.joml.Vector2f
import org.w3c.dom.Element
import structure.EngineObject
import tiledmap.chunks.extractChunk
import tiledmap.engineobjects.extractObjects
import tiledmap.tilesets.TileSet

const val OBJECT_GROUP: String = "objectgroup"
private const val DATA = "data"
private const val LAYER = "layer"
private const val CHUNK = "chunk"
const val ID = "id"


fun extractLayers(mapElement: Element, tileSets: List<TileSet>, tileScale: Vector2f, path: String): List<MapLayer> {
    val tiledLayers = extractTiledLayers(mapElement, tileSets, tileScale)
    val objectLayers = extractObjectLayers(mapElement, tileSets, path)
    return (tiledLayers + objectLayers).sortedBy { it.id }
}

private fun extractObjectLayers(mapElement: Element, tileSets: List<TileSet>, path: String): List<MapLayer> {
    val objectLayers = mapElement.getElementsByTagName(OBJECT_GROUP)
    val objectLayersList = mutableListOf<MapLayer>()
    for (i in 0 until objectLayers.length) {
        val objectLayerElement = objectLayers.item(i) as Element
        objectLayersList.add(extractObjectLayer(objectLayerElement, tileSets, path))
    }
    return objectLayersList
}

private fun extractObjectLayer(objectLayerElement: Element, tileSets: List<TileSet>, path: String): ObjectLayer {
    val objectLayerId = objectLayerElement.getAttribute(ID).toInt()
    val objects = extractObjects(objectLayerElement, tileSets, path = path)
    return ObjectLayer(id = objectLayerId, objects = objects)
}

private fun extractTiledLayers(mapElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): List<MapLayer> {
    val layers = mapElement.getElementsByTagName(LAYER)
    val layersList = mutableListOf<MapLayer>()
    for (i in 0 until layers.length) {
        val layerElement = layers.item(i) as Element
        layersList.add(extractTiledLayer(layerElement, tileSets, tileScale))
    }
    return layersList
}

private fun extractTiledLayer(layerElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): TiledLayer {
    val data = (layerElement.getElementsByTagName(DATA).item(0) as Element)
    val layerId = layerElement.getAttribute(ID).toInt()
    val chunkElements = data.getElementsByTagName(CHUNK)
    val chunks = mutableListOf<EngineObject>()
    for (i in 0 until chunkElements.length) {
        val chunkElement = chunkElements.item(i) as Element
        chunks.add(extractChunk(chunkElement, tileSets, tileScale))
    }
    return TiledLayer(id = layerId, chunks = chunks)
}
