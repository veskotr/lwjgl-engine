package graphics.rendering.animations

import structure.EngineObject

interface IAnimationFrame {
    val parentObject: EngineObject
    fun update()
}