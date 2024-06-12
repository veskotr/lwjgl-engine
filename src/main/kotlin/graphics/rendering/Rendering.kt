package graphics.rendering

import graphics.shaders.createBasicShader
import graphics.shaders.createParticleShader


val defaultShader = createBasicShader()
val particleShader = createParticleShader()


private var renderers = mutableListOf<Renderer>()

fun initRendering() {
    defaultShader.bindAttributeLocation(0, "vertices")
    defaultShader.bindAttributeLocation(1, "textures")
}

fun addRenderer(renderer: Renderer) {
    renderers.add(renderer)
}

fun removeRenderer(renderer: Renderer) {
    renderers.remove(renderer)
}

fun render() {
    renderers.forEach {
        run {
            if (it.parentObject?.active!!) {
                it.render()
            }
        }
    }
}