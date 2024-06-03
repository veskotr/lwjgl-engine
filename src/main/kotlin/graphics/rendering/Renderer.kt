package graphics.rendering

import graphics.shaders.Shader
import structure.IEngineObject

abstract class Renderer<T: IEngineObject>(open val shader: Shader = defaultShader, var parentObject: T? = null) {

    init {
        addRenderer(this)
    }

    abstract fun render()
}