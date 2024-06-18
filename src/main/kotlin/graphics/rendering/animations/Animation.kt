package graphics.rendering.animations

import engine.getTime
import engine.IEngineObject

class Animation(
    fps: Int,
    val timesToRun: Int = -1,
    var frames: MutableSet<IAnimationFrame> = mutableSetOf()
) :
    IEngineObject {

    var timesRan = 0

    private var elapsedTime = 0.0
    private var currentTime = 0.0
    private var lastTime = 0.0
    private val FPS = 1.0 / fps
    var running = false
    var currentFrameIndex = 0

    override fun start() {
        currentFrameIndex = 0
        running = true
        timesRan = 0
    }

    override fun update() {
        if (running) {
            currentTime = getTime()
            elapsedTime += currentTime - lastTime
            if (elapsedTime >= FPS) {
                elapsedTime -= FPS
                currentFrameIndex++
            }
            if (currentFrameIndex >= frames.size) {
                currentFrameIndex = 0
                if (timesToRun > -1) {
                    timesRan++
                    if (timesRan >= timesToRun) {
                        running = false
                    }
                }
            }
            frames.elementAt(currentFrameIndex).update()
            lastTime = currentTime
        }
    }

    fun stop() {
        currentFrameIndex = 0
        running = false
        timesRan = 0
    }
}