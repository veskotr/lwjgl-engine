package graphics.rendering.animations

import engine.EngineComponent

class AnimationsComponent(val animations: MutableMap<String, Animation>) : EngineComponent() {

    var currentAnimation: Animation? = null

    fun play(animationName: String) {
        currentAnimation = animations[animationName]
        currentAnimation!!.start()
    }

    override fun start() {
        if (currentAnimation != null) {
            currentAnimation!!.start()
        }
    }

    override fun update() {
        if (currentAnimation != null) {
            currentAnimation!!.update()
        }
    }
}