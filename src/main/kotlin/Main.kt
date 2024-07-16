import engine.DEBUG_MODE
import engine.DEBUG_OCCLUDERS
import engine.EngineObject
import engine.initGame
import engine.runGame
import engine.startGame
import org.joml.Vector2f
import physics.setWorldGravity
import tiledmap.createTiledMapFromFile
import tiledmap.engineobjects.registerEngineComponentProcessor

fun main() {

    DEBUG_MODE = true
    DEBUG_OCCLUDERS = true

    initGame(
        windowWidth = 1920,
        windowHeight = 980,
        windowTitle = "Test Game",
        fullScreen = false,
        vsyncEnabled = false
    )

    setWorldGravity(Vector2f(0f, 0f))

    val engineObject: EngineObject = EngineObject(987, layerName = "layer1")
    engineObject.addComponent(TestComponent())

    registerEngineComponentProcessor(customType = "SampleComponent", processor = SampleComponentProcessor())

    val map = createTiledMapFromFile("/levels/realisticScene")

    startGame()

    runGame()
}