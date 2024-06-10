import engine.initGame
import engine.runGame
import engine.startGame
import graphics.Texture
import graphics.particles.ParticleEmitter
import graphics.rendering.sprite.SpriteRenderer
import graphics.rendering.sprite.createSprite
import org.jbox2d.dynamics.BodyType
import org.joml.Vector2f
import physics.BoxCollider
import physics.setWorldGravity
import structure.EngineObject
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

    val map = createTiledMapFromFile("/levels/testScene")

    map.active = true

    val sprite = createSprite(Texture("/tile2.png"))

    val ground = EngineObject()
    ground.transform.scale = (Vector2f(428f, 32f))
    ground.transform.position = (Vector2f(0f, -480f + 32))
    ground.renderer = SpriteRenderer(sprite = sprite)
    ground.addComponent(
        BoxCollider(size = Vector2f(428f, 32f), bodyType = BodyType.STATIC, isSensor = false)
    )

    val movableObject = EngineObject()
    movableObject.transform.scale = (Vector2f(16f, 16f))
    movableObject.transform.position = Vector2f(0f)
    movableObject.addComponent(
        BoxCollider(size = Vector2f(16f, 16f), bodyType = BodyType.DYNAMIC, isSensor = false)
    )
    movableObject.addComponent(
        ParticleEmitter(
            sprite = sprite,
            maxEnergy = 500,
            maxSize = 10f,
            minSize = 5f,
            minEmission = 10,
            maxEmission = 20
        )
    )
    movableObject.renderer = SpriteRenderer(sprite = sprite)
    movableObject.addComponent(SampleComponent())

    startGame()

    runGame()
}