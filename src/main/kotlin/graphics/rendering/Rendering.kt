package graphics.rendering

import graphics.shaders.createBasicShader
import graphics.shaders.createParticleShader


val defaultShader = createBasicShader()
val particleShader = createParticleShader()


private var renderers = mutableListOf<Renderer>()

var boundModel: Model? = null

fun initRendering() {
    defaultShader.bindAttributeLocation(0, "vertices")
    defaultShader.bindAttributeLocation(1, "textures")
}

fun addRenderer(renderer: Renderer) {
    renderers.add(renderer)
}

fun render() {
    renderers.forEach { it.render() }
}