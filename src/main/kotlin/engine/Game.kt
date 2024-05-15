package engine

import engine.physics.updatePhysics
import graphics.rendering.initRendering
import graphics.rendering.render
import graphics.utils.cleanupGraphics
import io.clearWindow
import io.destroyWindow
import io.initCamera
import io.initInput
import io.initWindow
import io.pollEvents
import io.updateInput
import io.windowShouldClose

private var timePassed = 0.0
private var frames = 0
var deltaTime = 0.01f
private var lastTime = 0L

fun initGame(windowWidth: Int, windowHeight: Int, windowTitle: String, fullScreen: Boolean, vsyncEnabled: Boolean) {
    initWindow(windowWidth, windowHeight, windowTitle, fullScreen, vsyncEnabled)
    initInput()
    initRendering()
    initCamera(windowWidth, windowHeight)
}

fun startGame() {
    startEngineObjects()

}

fun runGame() {
    while (!windowShouldClose()) {

        clearWindow()

        render()

        pollEvents()

        updateInput()
        updateTime()
        updatePhysics()
    }

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