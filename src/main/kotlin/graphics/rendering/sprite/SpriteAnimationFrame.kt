package graphics.rendering.sprite

import graphics.rendering.animations.IAnimationFrame
import structure.EngineObject

class SpriteAnimationFrame(override val parentObject: EngineObject, val sprite: Sprite): IAnimationFrame {

    override fun update() {
        val renderer = parentObject.renderer
        if (renderer is SpriteRenderer){
            renderer.sprite = sprite
        }else{
            throw Exception("Parent object does not have a SpriteRenderer")
        }
    }

}