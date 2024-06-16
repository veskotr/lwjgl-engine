package graphics.rendering

import graphics.shaders.Shader
import structure.EngineObject

abstract class AbstractRenderer(open val shader: Shader = defaultShader, var parentObject: EngineObject? = null, var layerName: String) {

    init {
        addRenderer(this, layerName)
    }

    abstract fun render()

    fun setActive(active: Boolean){
        if (active){
            addRenderer(this, layerName)
        } else {
            removeRenderer(this, layerName)
        }
    }

    fun setLayer(layerName: String){
        removeRenderer(this, this.layerName)
        this.layerName = layerName
        addRenderer(this, layerName)
    }

}