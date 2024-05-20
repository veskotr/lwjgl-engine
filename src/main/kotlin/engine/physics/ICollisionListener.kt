package engine.physics

interface ICollisionListener {

    fun onCollisionEnter(other: Collider)

    fun onCollisionExit(other: Collider)
}