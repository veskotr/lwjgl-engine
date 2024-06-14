import engine.initGame
import engine.runGame
import engine.startGame
import org.joml.Vector2f
import physics.TileBoxColliderProcessor
import physics.setWorldGravity
import tiledmap.chunks.registerTileProcessor
import tiledmap.createTiledMapFromFile

fun main() {
    initGame(
        windowWidth = 1920,
        windowHeight = 980,
        windowTitle = "Test Game",
        fullScreen = false,
        vsyncEnabled = false
    )

    setWorldGravity(Vector2f(0f, 0f))

    registerTileProcessor(type = "BoxCollider", processor = TileBoxColliderProcessor())

    val map = createTiledMapFromFile("/levels/testScene")

    startGame()

    runGame()
}