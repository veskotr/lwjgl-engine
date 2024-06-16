import engine.DEBUG_MODE
import engine.initGame
import engine.runGame
import engine.startGame
import graphics.rendering.sprite.SpriteRendererProcessor
import org.joml.Vector2f
import physics.BoxColliderComponentProcessor
import physics.TileBoxColliderProcessor
import physics.setWorldGravity
import tiledmap.chunks.registerTileProcessor
import tiledmap.createTiledMapFromFile
import tiledmap.engineobjects.registerEngineComponentProcessor
import tiledmap.engineobjects.registerRendererComponentProcessor

fun main() {

    DEBUG_MODE = true

    initGame(
        windowWidth = 1920,
        windowHeight = 980,
        windowTitle = "Test Game",
        fullScreen = false,
        vsyncEnabled = false
    )

    setWorldGravity(Vector2f(0f, 0f))

    registerTileProcessor(type = "BoxCollider", processor = TileBoxColliderProcessor())

    registerEngineComponentProcessor(customType = "BoxCollider", processor = BoxColliderComponentProcessor())

    registerEngineComponentProcessor(customType = "SampleComponent", processor = SampleComponentProcessor())

    registerRendererComponentProcessor(customType = "SpriteRenderer", processor = SpriteRendererProcessor())

    val map = createTiledMapFromFile("/levels/realisticScene")

    startGame()

    runGame()
}