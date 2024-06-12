package graphics.rendering

import graphics.shaders.Shader
import structure.EngineObject

abstract class Renderer(open val shader: Shader = defaultShader, var parentObject: EngineObject? = null) {

    init {
        addRenderer(this)
    }

    abstract fun render()
}