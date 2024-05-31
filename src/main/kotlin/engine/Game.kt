package engine

import physics.startPhysics
import physics.stopPhysicsLoop
import graphics.rendering.initRendering
import graphics.rendering.render
import graphics.utils.cleanupGraphics
import io.*

private var timePassed = 0.0
private var frames = 0
var deltaTime = 0.01f
private var lastTime = 0L
lateinit var camera: Camera

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
        updateEngineObjects()

        render()

        pollEvents()

        updateInput()
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