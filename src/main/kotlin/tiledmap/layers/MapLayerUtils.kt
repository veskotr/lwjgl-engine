package tiledmap.layers

import graphics.rendering.SquareModel
import graphics.rendering.sprite.Sprite
import graphics.rendering.sprite.SpriteRenderer
import org.joml.Vector2f
import org.w3c.dom.Element
import structure.EngineObject
import tiledmap.chunks.MapChunk
import tiledmap.chunks.X
import tiledmap.chunks.Y
import tiledmap.chunks.extractChunk
import tiledmap.tilesets.TileSet

private const val OBJECT_GROUP: String = "objectgroup"
private const val DATA = "data"
private const val LAYER = "layer"
private const val CHUNK = "chunk"
private const val ID = "id"
private const val OBJECT = "object"
private const val GID = "gid"
private const val WIDTH = "width"
private const val HEIGHT = "height"

fun extractLayers(mapElement: Element, tileSets: List<TileSet>, tileScale: Vector2f): List<MapLayer> {
    val tiledLayers = extractTiledLayers(mapElement, tileSets, tileScale)
    val objectLayers = extractObjectLayers(mapElement, tileSets)
    return (tiledLayers + objectLayers).sortedBy { it.id }
}

private fun extractObjectLayers(mapElement: Element, tileSets: List<TileSet>): List<MapLayer> {
    val objectLayers = mapElement.getElementsByTagName(OBJECT_GROUP)
    val objectLayersList = mutableListOf<MapLayer>()
    for (i in 0 until objectLayers.length) {
        val objectLayerElement = objectLayers.item(i) as Element
        objectLayersList.add(extractObjectLayer(objectLayerElement, tileSets))
    }
    return objectLayersList
}

private fun extractObjectLayer(objectLayerElement: Element, tileSets: List<TileSet>): ObjectLayer {
    val objectLayerId = objectLayerElement.getAttribute(ID).toInt()
    val objects = extractObjects(objectLayerElement, tileSets)
    return ObjectLayer(id = objectLayerId, objects = objects)
}

fun extractObjects(objectLayerElement: Element, tileSets: List<TileSet>): List<EngineObject> {
    val objectElements = objectLayerElement.getElementsByTagName(OBJECT)
    val objects = mutableListOf<EngineObject>()
    for (i in 0 until objectElements.length) {
        val objectElement = objectElements.item(i) as Element
        objects.add(extractObject(objectElement, tileSets))
    }
    return objects
}

fun extractObject(objectElement: Element, tileSets: List<TileSet>): EngineObject {
    val id = objectElement.getAttribute(ID).toInt()
    val position = Vector2f(
        objectElement.getAttribute(X).toFloat(),
        objectElement.getAttribute(Y).toFloat()
    ).mul(1f, -1f)
    val size = Vector2f(
        objectElement.getAttribute(WIDTH).let { if (it.isNotEmpty()) it.toFloat() else 1.0f },
        objectElement.getAttribute(HEIGHT).let { if (it.isNotEmpty()) it.toFloat() else 1.0f }
    )
    val tileId = objectElement.getAttribute(GID).let { if (it.isNotEmpty()) it.toInt() else null }

    val engineObject = EngineObject(id = id)
    engineObject.setPosition(position)
    engineObject.setScale(size.div(2.0f))

    if (tileId != null) {
        val tileSet = tileSets.firstOrNull { it.firstGrid <= tileId && it.firstGrid + it.tileCount > tileId }
        engineObject.renderer =
            SpriteRenderer(
                sprite = Sprite(
                    tileSet!!.textureAtlas,
                    SquareModel(tileSet.getTileBufferId(tileId))
                )
            )

    }

    return engineObject
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
    val chunks = mutableListOf<MapChunk>()
    for (i in 0 until chunkElements.length) {
        val chunkElement = chunkElements.item(i) as Element
        chunks.add(extractChunk(chunkElement, tileSets, tileScale))
    }
    return TiledLayer(id = layerId, chunks = chunks)
}
