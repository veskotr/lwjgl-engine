package graphics.rendering

import graphics.shaders.createBasicShader
import graphics.shaders.createDebugShader
import graphics.shaders.createParticleShader


val defaultShader = createBasicShader()
val particleShader = createParticleShader()
val debugShader = createDebugShader()

private var renderersMap = mutableMapOf<String, MutableSet<AbstractRenderer>>().toSortedMap()


fun initRendering() {
    defaultShader.bindAttributeLocation(0, "vertices")
    defaultShader.bindAttributeLocation(1, "textures")
}

fun addRenderer(abstractRenderer: AbstractRenderer, layerName: String) {
    if (!renderersMap.containsKey(layerName)) {
        addRenderLayer(layerName)
    }
    renderersMap[layerName]!!.add(abstractRenderer)
}

fun removeRenderer(abstractRenderer: AbstractRenderer, layerName: String) {
    renderersMap[layerName]?.remove(abstractRenderer)
}

fun addRenderLayer(layerName: String) {
    renderersMap[layerName] = mutableSetOf()
}

fun render() {
    renderersMap.forEach {
        it.value.forEach {
            run {
                if (it.parentObject?.active!!) {
                    it.render()
                }
            }
        }
    }
}

fun sortRenderersByYAxis() {
    renderersMap.forEach {
        it.value.sortedBy { it.parentObject?.transform?.position?.y }
    }
}

fun sortRenderersByXAxis() {
    renderersMap.forEach {
        it.value.sortedBy { it.parentObject?.transform?.position?.x }
    }
}

fun sortRenderersByParentIndex() {
    renderersMap.forEach {
        it.value.sortedBy { it.parentObject?.index }
    }
}