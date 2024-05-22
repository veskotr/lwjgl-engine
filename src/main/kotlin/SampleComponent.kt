import physics.Collider
import physics.ICollisionListener
import structure.EngineComponent

class SampleComponent : EngineComponent(), ICollisionListener {


    override fun start() {
    }

    override fun update() {
    }

    override fun onCollisionEnter(other: Collider) {
        println("Collision detected!")
    }

    override fun onCollisionExit(other: Collider) {
        println("Collision ended!")
    }
}