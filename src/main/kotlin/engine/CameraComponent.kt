package engine

import physics.Collider
import physics.ICollisionListener
import structure.EngineComponent

class CameraComponent: EngineComponent(), ICollisionListener{

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun onCollisionEnter(other: Collider) {
        TODO("Not yet implemented")
    }

    override fun onCollisionExit(other: Collider) {
        TODO("Not yet implemented")
    }
}