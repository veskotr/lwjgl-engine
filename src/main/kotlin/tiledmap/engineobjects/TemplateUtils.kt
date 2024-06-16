package tiledmap.engineobjects

import org.w3c.dom.Element
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.getXmlDocument

private val objectPropertiesMap: MutableMap<String, ObjectProperties> = mutableMapOf()

fun getPropertiesFromTemplate(path: String): ObjectProperties {
    if (!objectPropertiesMap.containsKey(path)) {
        val objectProperties = extractObjectPropertiesFromTemplateFile(path)
        objectPropertiesMap[path] = objectProperties
    }
    return objectPropertiesMap[path]!!
}

private fun extractObjectPropertiesFromTemplateFile(path: String): ObjectProperties {
    val inputStream = object {}.javaClass.getResourceAsStream(path)
        ?: error("Could not find template file $path")
    val document = getXmlDocument(inputStream)
    val templateElement = document.documentElement
    val objectElement = templateElement.getElementsByTagName(OBJECT).item(0) as Element

    return extractObjectProperties(objectElement, path)

}