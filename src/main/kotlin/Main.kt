import engine.initGame
import engine.physics.RigidBody
import engine.physics.setWorldGravity
import engine.runGame
import engine.startGame
import engine.structure.EngineObject
import graphics.Texture
import graphics.rendering.sprite.SpriteRenderer
import graphics.rendering.sprite.createSprite
import org.dyn4j.geometry.Geometry
import org.dyn4j.geometry.Mass
import org.dyn4j.geometry.Vector2
import org.joml.Vector2f


fun main() {
    initGame(
        windowWidth = 1920,
        windowHeight = 980,
        windowTitle = "Test Game",
        fullScreen = false,
        vsyncEnabled = false
    )

    setWorldGravity(Vector2f(0f, -100f))

    val sprite = createSprite(Texture("/tile2.png"))
    val sprite2 = createSprite(Texture("/icon32.png"))

    val ground = EngineObject()
    ground.transform.scale = (Vector2f(428f, 32f))
    ground.transform.position = (Vector2f(0f, -480f + 32))
    ground.renderer = SpriteRenderer(sprite = sprite)
    ground.addComponent(
        RigidBody(
            Geometry.createRectangle(856.0, 64.0),
            static = true
        )
    )


    for (i in 0 until 14) {
        val movableObject = EngineObject()
        movableObject.transform.scale = (Vector2f(32f, 32f))
        movableObject.transform.position = Vector2f(-426 + (64 + 2f) * i, 0f)
        movableObject.renderer = SpriteRenderer(sprite = sprite2)
        movableObject.addComponent(
            RigidBody(Geometry.createSquare(64.0), static = false)
        )
    }
    for (i in 0 until 14) {
        val movableObject = EngineObject()
        movableObject.transform.scale = (Vector2f(32f, 32f))
        movableObject.transform.position = Vector2f(-426 + (64 + 2f) * i, 128f)
        movableObject.renderer = SpriteRenderer(sprite = sprite)
        movableObject.addComponent(
            RigidBody(Geometry.createSquare(64.0), static = false)
        )
    }
    for (i in 0 until 14) {
        val movableObject = EngineObject()
        movableObject.transform.scale = (Vector2f(32f, 32f))
        movableObject.transform.position = Vector2f(-426 + (64 + 2f) * i, 256f)
        movableObject.renderer = SpriteRenderer(sprite = sprite)
        movableObject.addComponent(
            RigidBody(Geometry.createSquare(64.0), static = false)
        )
    }
    //movableObject.addComponent(SampleComponent())

    startGame()

    runGame()
}