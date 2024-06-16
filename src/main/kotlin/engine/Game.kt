package engine

import graphics.rendering.initRendering
import graphics.rendering.render
import graphics.rendering.sortRenderersByYAxis
import graphics.utils.cleanupGraphics
import io.*
import mu.KotlinLogging
import physics.physicsTimeStep
import physics.startPhysics
import physics.stopPhysicsLoop
import physics.updatePhysics

private var timePassed = 0.0
private var frames = 0
var deltaTime = 0.01f
private var lastTime = 0L
lateinit var camera: Camera
var DEBUG_MODE = false

private val logger = KotlinLogging.logger { }

fun initGame(windowWidth: Int, windowHeight: Int, windowTitle: String, fullScreen: Boolean, vsyncEnabled: Boolean) {
    initWindow(windowWidth, windowHeight, windowTitle, fullScreen, vsyncEnabled)
    initInput()
    initRendering()
    camera = Camera.getInstance(windowWidth, windowHeight)
}

fun startGame() {
    startEngineObjects()
    startPhysics()
}

fun runGame() {
    while (!windowShouldClose()) {

        clearWindow()

        pollEvents()

        updateInput()

        physicsTimeStep = deltaTime

        updatePhysics()

        updateEngineObjects()

        sortRenderersByYAxis()

        render()

        updateTime()
    }
    stopPhysicsLoop()
    cleanupGraphics()
    destroyWindow()
}

private fun updateTime() {
    deltaTime = (System.nanoTime() - lastTime) / 1000000000.0f
    timePassed += deltaTime
    frames++
    if (timePassed >= 1) {
        println("FPS: $frames")
        timePassed = 0.0
        frames = 0
    }
    lastTime = System.nanoTime()
}
fun getTime(): Double {
    return System.nanoTime().toDouble() / 1000000000.0
}
