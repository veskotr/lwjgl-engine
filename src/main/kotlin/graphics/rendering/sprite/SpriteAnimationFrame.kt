package graphics.rendering.sprite

import engine.EngineObject
import graphics.rendering.animations.IAnimationFrame

class SpriteAnimationFrame(override val parentObject: EngineObject, val sprite: Sprite) : IAnimationFrame {

    override fun update() {
        val renderer = parentObject.renderers.firstOrNull { it is SpriteRenderer } as SpriteRenderer?
        if (renderer != null) {
            renderer.sprite = sprite
        } else {
            throw Exception("Parent object does not have a SpriteRenderer")
        }
    }

}