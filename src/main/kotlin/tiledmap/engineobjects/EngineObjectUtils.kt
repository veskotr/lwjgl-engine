package tiledmap.engineobjects

import mu.KotlinLogging
import org.joml.Vector2f
import org.joml.Vector4f
import org.w3c.dom.Element
import engine.EngineObject
import tiledmap.chunks.X
import tiledmap.chunks.Y
import tiledmap.engineobjects.model.CustomPropertyType
import tiledmap.engineobjects.model.ObjectCustomProperty
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.layers.ID
import tiledmap.tilesets.TileSet

private const val PROPERTY_TYPE = "propertytype"
const val OBJECT = "object"
private const val GID = "gid"
private const val WIDTH = "width"
private const val HEIGHT = "height"
private const val PROPERTIES = "properties"
private const val PROPERTY = "property"
private const val NAME = "name"
private const val TYPE = "type"
private const val VALUE = "value"
private const val ROTATION: String = "rotation"
private const val TEMPLATE: String = "template"

private val engineComponentProcessors: MutableMap<String, EngineComponentProcessor> = mutableMapOf()

private val rendererComponentProcessors: MutableMap<String, RendererComponentProcessor> = mutableMapOf()

private val logger = KotlinLogging.logger {}

fun extractObjects(
    objectLayerElement: Element,
    tileSets: List<TileSet>,
    path: String,
    layerName: String
): List<EngineObject> {
    val objectElements = objectLayerElement.getElementsByTagName(OBJECT)
    val objects = mutableListOf<EngineObject>()
    for (i in 0 until objectElements.length) {
        val objectElement = objectElements.item(i) as Element
        objects.add(extractObject(objectElement, tileSets, path, layerName))
    }
    return objects
}

fun extractObject(objectElement: Element, tileSets: List<TileSet>, path: String, layerName: String): EngineObject {
    val objectProperties = extractObjectProperties(objectElement, path)

    val engineObject = EngineObject(id = objectProperties.id, layerName = layerName)
    engineObject.setPosition(objectProperties.position)
    engineObject.setRotation(objectProperties.rotation)

    val engineComponents =
        objectProperties.customProperties.filter {
            it.value.propertyType != null && engineComponentProcessors.containsKey(
                it.value.propertyType
            )
        }.map {
            engineComponentProcessors[it.value.propertyType]!!.processEngineComponent(
                engineObject,
                objectProperties,
                tileSets,
                path,
                it.value
            )
        }

    val rendererComponents =
        objectProperties.customProperties.filter {
            it.value.propertyType != null && rendererComponentProcessors.containsKey(
                it.value.propertyType
            )
        }.map {
            rendererComponentProcessors[it.value.propertyType]!!.processRendererComponent(
                engineObject,
                objectProperties,
                tileSets,
                path,
                it.value
            )
        }

    if (rendererComponents.size > 1) {
        logger.warn { "Multiple abstractRenderer components found for object ${objectProperties.name}" }
    }

    if (rendererComponents.isNotEmpty()) {
        engineObject.renderer = rendererComponents.first()
    }

    engineComponents.forEach(engineObject::addComponent)

    return engineObject
}

fun extractObjectProperties(objectElement: Element, path: String): ObjectProperties {
    val id = objectElement.getAttribute(ID).let { it.ifEmpty { "0" } }.toInt()
    val name = objectElement.getAttribute(NAME).let { it.ifEmpty { "Object$id" } }
    val type = objectElement.getAttribute(TYPE).let { it.ifEmpty { "Object" } }
    val position = Vector2f(
        objectElement.getAttribute(X).let { if (it.isNotEmpty()) it.toFloat() else 0f },
        objectElement.getAttribute(Y).let { if (it.isNotEmpty()) it.toFloat() else 0f }
    ).mul(1f, -1f)

    val size = Vector2f(
        objectElement.getAttribute(WIDTH).let { if (it.isNotEmpty()) it.toFloat() else 1.0f },
        objectElement.getAttribute(HEIGHT).let { if (it.isNotEmpty()) it.toFloat() else 1.0f }
    )
    size.mul(0.5f)

    //position.sub(size.mul(Vector2f(1.0f, -1.0f), Vector2f()))

    val rotation = objectElement.getAttribute(ROTATION).let { if (it.isNotEmpty()) it.toFloat() else 0.0f }

    val tileId = objectElement.getAttribute(GID).let { if (it.isNotEmpty()) it.toInt() else null }

    val objectProperties = ObjectProperties(
        id = id,
        name = name,
        type = type,
        position = position,
        rotation = rotation,
        size = size,
        tileId = tileId,
        customProperties = extractObjectCustomProperties(objectElement)
    )
    val templatePath = objectElement.getAttribute(TEMPLATE)

    return if (templatePath.isNotEmpty()) {
        val template = getPropertiesFromTemplate("${path}/$templatePath")
        objectProperties.copy(
            type = template.type,
            size = template.size,
            rotation = template.rotation,
            customProperties = template.customProperties + objectProperties.customProperties,
            position = position
        )
    } else {
        objectProperties
    }
}

private fun extractObjectCustomProperties(objectElement: Element): Map<String, ObjectCustomProperty> {
    val customProperties = mutableMapOf<String, ObjectCustomProperty>()

    val propertiesElement =
        (objectElement.getElementsByTagName(PROPERTIES).item(0) as Element?) ?: return customProperties
    val properties = propertiesElement.getElementsByTagName(PROPERTY)

    for (i in 0 until properties.length) {
        val propertyElement = properties.item(i) as Element
        val name = propertyElement.getAttribute(NAME)
        customProperties[name] = extractCustomProperty(propertyElement, name)
    }

    return customProperties
}

private fun extractCustomProperty(propertyElement: Element, name: String): ObjectCustomProperty {
    val type =
        CustomPropertyType.valueOf((propertyElement.getAttribute(TYPE).let { it.ifEmpty { "string" } }).uppercase())
    val value = propertyElement.getAttribute(VALUE)

    return when (type) {
        CustomPropertyType.INT -> ObjectCustomProperty(name, type, intValue = value.toInt())
        CustomPropertyType.FLOAT -> ObjectCustomProperty(name, type, floatValue = value.toFloat())
        CustomPropertyType.BOOL -> ObjectCustomProperty(name, type, boolValue = value.toBoolean())
        CustomPropertyType.COLOR -> ObjectCustomProperty(name, type, colorValue = value.toColor())
        CustomPropertyType.FILE -> ObjectCustomProperty(name, type, fileValue = value)
        CustomPropertyType.CLASS -> {
            val classProperties = extractObjectCustomProperties(propertyElement)
            ObjectCustomProperty(
                name,
                type,
                classValue = classProperties,
                propertyType = propertyElement.getAttribute(PROPERTY_TYPE)
            )
        }

        else -> {
            ObjectCustomProperty(name, type, stringValue = value)
        }
    }
}

private fun String.toColor(): Vector4f {
    val color = this.substring(1).toLong(16)
    val a = ((color shr 24) and 0xFF).toFloat() / 255.0f
    val r = ((color shr 16) and 0xFF).toFloat() / 255.0f
    val g = ((color shr 8) and 0xFF).toFloat() / 255.0f
    val b = (color and 0xFF).toFloat() / 255.0f
    return Vector4f(r, g, b, a)
}

fun registerEngineComponentProcessor(customType: String, processor: EngineComponentProcessor) {
    engineComponentProcessors[customType] = processor
}

fun registerRendererComponentProcessor(customType: String, processor: RendererComponentProcessor) {
    rendererComponentProcessors[customType] = processor
}