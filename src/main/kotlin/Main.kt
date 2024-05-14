import engine.initGame
import engine.runGame
import engine.startGame
import engine.structure.EngineObject
import graphics.Texture
import graphics.rendering.sprite.SpriteRenderer
import graphics.rendering.sprite.createSprite
import org.joml.Vector2f


fun main() {
    initGame(
        windowWidth = 1280,
        windowHeight = 920,
        windowTitle = "Test Game",
        fullScreen = false,
        vsyncEnabled = false
    )

    val sprite = createSprite(Texture("/tile2.png"))

    val sampleObject = EngineObject()
    sampleObject.transform.scale(Vector2f(32f))
    sampleObject.renderer = SpriteRenderer(sprite = sprite)

    val sampleObject2 = EngineObject()
    sampleObject2.transform.scale(Vector2f(32f,64f))
    sampleObject2.transform.position = Vector2f(128f, 64f)
    sampleObject2.renderer = SpriteRenderer(sprite = sprite)

    startGame()

    runGame()
}