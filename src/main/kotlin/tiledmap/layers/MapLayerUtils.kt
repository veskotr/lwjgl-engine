package tiledmap.layers

import graphics.rendering.addRenderLayer
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
private const val NAME = "name"

fun extractLayers(
    mapElement: Element,
    tileSets: List<TileSet>,
    tileScale: Vector2f,
    path: String
): List<TiledMapLayer> {
    val tiledLayers = extractTiledLayers(mapElement, tileSets, tileScale)
    val objectLayers = extractObjectLayers(mapElement, tileSets, path)
    val sortedLayers = (tiledLayers + objectLayers).sortedBy { it.id }

    //sortedLayers.forEach { addRenderLayer(layerName = it.name) }

    return sortedLayers
}

private fun extractObjectLayers(mapElement: Element, tileSets: List<TileSet>, path: String): List<TiledMapLayer> {
    val objectLayers = mapElement.getElementsByTagName(OBJECT_GROUP)
    val objectLayersList = mutableListOf<TiledMapLayer>()
    for (i in 0 until objectLayers.length) {
        val objectLayerElement = objectLayers.item(i) as Element
        objectLayersList.add(extractObjectLayer(objectLayerElement, tileSets, path))
    }
    return objectLayersList
}

private fun extractObjectLayer(objectLayerElement: Element, tileSets: List<TileSet>, path: String): TiledMapLayer {
    val objectLayerId = objectLayerElement.getAttribute(ID).toInt()
    val objectLayerName = objectLayerElement.getAttribute(NAME)
    val objects = extractObjects(objectLayerElement, tileSets, path = path, layerName = objectLayerName)
    return TiledMapLayer(id = objectLayerId, objects = objects.toMutableList(), name = objectLayerName)
}

private fun extractTiledLayers(mapElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): List<TiledMapLayer> {
    val layers = mapElement.getElementsByTagName(LAYER)
    val layersList = mutableListOf<TiledMapLayer>()
    for (i in 0 until layers.length) {
        val layerElement = layers.item(i) as Element
        layersList.add(extractTiledLayer(layerElement, tileSets, tileScale))
    }
    return layersList
}

private fun extractTiledLayer(layerElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): TiledMapLayer {
    val data = (layerElement.getElementsByTagName(DATA).item(0) as Element)
    val layerId = layerElement.getAttribute(ID).toInt()
    val layerName = layerElement.getAttribute(NAME)
    val chunkElements = data.getElementsByTagName(CHUNK)
    val chunks = mutableListOf<EngineObject>()
    for (i in 0 until chunkElements.length) {
        val chunkElement = chunkElements.item(i) as Element
        chunks.add(extractChunk(chunkElement, tileSets, tileScale, layerName))
    }
    return TiledMapLayer(id = layerId, chunks = chunks, name = layerName)
}
