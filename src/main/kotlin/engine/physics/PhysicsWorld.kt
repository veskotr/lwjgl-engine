package engine.physics


import engine.updateEngineObjects
import org.dyn4j.dynamics.Body
import org.dyn4j.world.World

import org.joml.Vector2f

private val world = World<Body>()
private var physicsActive = true

private var physicsTPS = 60
private var physicsTimeStep = 1f / physicsTPS

var physicsDeltaTime = 0f
private var lastTime = 0L
private var elapsedTime: Float = 0f
private var timePassed = 0f
private var tps = 0
private var running = true

private lateinit var thread: Thread

fun setWorldGravity(gravity: Vector2f) {
    world.gravity.set(gravity.x.toDouble(), gravity.y.toDouble())
}

fun addPhysicsBody(body: Body) {
    world.addBody(body)
}

fun startPhysicsLoop() {
    thread = Thread {
        while (running) {
            if (elapsedTime >= physicsTimeStep) {
                updatePhysics()
                timePassed += physicsTimeStep
                physicsDeltaTime = elapsedTime
                elapsedTime = 0f
                tps++
            }
            if (timePassed >= 1f) {
                println("Ticks per second: $tps")
                tps = 0
                timePassed = 0f
            }
            elapsedTime += (System.currentTimeMillis() - lastTime).toFloat() / 1000f
            lastTime = System.currentTimeMillis()
        }
    }
    thread.start()
}

fun updatePhysics() {
    if (physicsActive) {
        world.update(physicsDeltaTime.toDouble())
        updateEngineObjects()
    }
}

fun setPhysicsActive(active: Boolean) {
    physicsActive = active
}

fun setPhysicsTPS(tps: Int) {
    physicsTPS = tps
    physicsTimeStep = 1f / physicsTPS
}

fun getPhysicsTPS(): Int {
    return physicsTPS
}

fun stopPhysicsLoop() {
    running = false
}


