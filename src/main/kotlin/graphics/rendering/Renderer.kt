package graphics.rendering

import structure.EngineObject
import graphics.shaders.Shader

abstract class Renderer(open val shader: Shader = defaultShader, var parentObject: EngineObject? = null) {

    init {
        addRenderer(this)
    }

    abstract fun render()
}