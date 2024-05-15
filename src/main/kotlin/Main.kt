import engine.initGame
import engine.physics.RigidBody
import engine.physics.createSquareShape
import engine.runGame
import engine.startGame
import engine.structure.EngineObject
import graphics.Texture
import graphics.rendering.sprite.SpriteRenderer
import graphics.rendering.sprite.createSprite
import org.joml.Vector2f


fun main() {
    initGame(
        windowWidth = 1920,
        windowHeight = 980,
        windowTitle = "Test Game",
        fullScreen = false,
        vsyncEnabled = true
    )

    val sprite = createSprite(Texture("/tile2.png"))

    val ground = EngineObject()
    ground.transform.scale = (Vector2f(1920f,32f))
    ground.transform.position = (Vector2f(0f, -480f))
    ground.renderer = SpriteRenderer(sprite = sprite)
    ground.addComponent(RigidBody(shape = createSquareShape(1920f, 32f), bodyType = org.jbox2d.dynamics.BodyType.STATIC))

    val movableObject = EngineObject()
    movableObject.transform.scale = (Vector2f(32f))
    movableObject.transform.position = Vector2f()
    movableObject.renderer = SpriteRenderer(sprite = sprite)
    movableObject.addComponent(RigidBody(shape = createSquareShape(32f, 32f)))

    startGame()

    runGame()
}