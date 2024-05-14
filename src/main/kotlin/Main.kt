import engine.initGame
import engine.runGame
import engine.startGame
import engine.structure.EngineObject
import graphics.Texture
import graphics.rendering.sprite.SpriteRenderer
import graphics.rendering.sprite.createSprite
import io.setCameraPositionInWorldSpace
import org.joml.Vector2f


fun main() {
    initGame(
        windowWidth = 1920,
        windowHeight = 980,
        windowTitle = "Test Game",
        fullScreen = false,
        vsyncEnabled = false
    )

    val sprite = createSprite(Texture("/tile2.png"))

    val sampleObject = EngineObject()
    sampleObject.transform.scale = (Vector2f(128f))
    sampleObject.transform.position = (Vector2f(0f, 0f))
    sampleObject.renderer = SpriteRenderer(sprite = sprite)

    val sampleObject2 = EngineObject()
    sampleObject2.transform.scale = (Vector2f(128f))
    sampleObject2.transform.position = (Vector2f(1f))
    sampleObject2.renderer = SpriteRenderer(sprite = sprite)
    sampleObject2.addComponent(SampleComponent())


    /*val sampleObject3 = EngineObject()
    sampleObject3.transform.scale = (Vector2f(64f))
    sampleObject3.transform.position = Vector2f(0f, 0f)
    sampleObject3.renderer = SpriteRenderer(sprite = sprite)
    sampleObject3.addChild(sampleObject)*/

    startGame()

    runGame()
}