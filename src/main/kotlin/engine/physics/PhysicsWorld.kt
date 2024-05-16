package engine.physics


import engine.updateEngineObjects
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.World
import org.joml.Vector2f

private val world = World(Vec2(0f, -1000f))

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
    world.gravity = Vec2(gravity.x, gravity.y)
}

fun createPhysicsBody(bodyDef: BodyDef): Body {
    return world.createBody(bodyDef)
}

fun startPhysicsLoop() {
    thread = Thread {
        while (running) {
            if (elapsedTime >= physicsTimeStep) {
                updatePhysics()
                timePassed += physicsTimeStep
                physicsDeltaTime = physicsTimeStep
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

fun createSquareShape(width: Float, height: Float): PolygonShape {
    val shape = PolygonShape()
    shape.setAsBox(width, height)
    return shape
}

fun updatePhysics() {
    if (physicsActive) {
        world.step(physicsTimeStep, 8, 3)
        updateEngineObjects()
        //println(elapsedTime)
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


