package physics

interface ICollisionListener {

    fun onCollisionEnter(other: Collider)

    fun onCollisionExit(other: Collider)
}