package graphics.rendering.animations

import engine.EngineObject

interface IAnimationFrame {
    val parentObject: EngineObject
    fun update()
}