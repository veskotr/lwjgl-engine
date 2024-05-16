import engine.initGame
import engine.physics.RigidBody
import engine.physics.createSquareShape
import engine.runGame
import engine.startGame
import engine.structure.EngineObject
import graphics.Texture
import graphics.rendering.sprite.SpriteRenderer
import graphics.rendering.sprite.createSprite
import org.jbox2d.dynamics.BodyType
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

    val ground = EngineObject()
    ground.transform.scale = (Vector2f(428f, 32f))
    ground.transform.position = (Vector2f(0f, -480f + 32))
    ground.transform.rotation = 50f
    ground.renderer = SpriteRenderer(sprite = sprite)
    ground.addComponent(
        RigidBody(
            shape = createSquareShape(428f, 32f),
            bodyType = BodyType.KINEMATIC
        )
    )


    val movableObject = EngineObject()
    movableObject.transform.scale = (Vector2f(32f, 32f))
    movableObject.transform.position = Vector2f(-426 + (64 + 2f), 0f)
    movableObject.renderer = SpriteRenderer(sprite = sprite)
    movableObject.addComponent(
        RigidBody(shape = createSquareShape(32.0f, 32.0f), bodyType = BodyType.DYNAMIC)
    )

    startGame()

    runGame()
}