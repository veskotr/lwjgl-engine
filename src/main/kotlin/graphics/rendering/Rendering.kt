package graphics.rendering

import graphics.shaders.createBasicShader
import graphics.shaders.createParticleShader


val defaultShader = createBasicShader()
val particleShader = createParticleShader()


private var renderersMap = mutableMapOf<String, MutableSet<Renderer>>()

fun initRendering() {
    defaultShader.bindAttributeLocation(0, "vertices")
    defaultShader.bindAttributeLocation(1, "textures")
}

fun addRenderer(renderer: Renderer, layerName: String) {
    renderersMap[layerName]?.add(renderer)
}

fun removeRenderer(renderer: Renderer, layerName: String) {
    renderersMap[layerName]?.remove(renderer)
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