package structure

import engine.addEngineObject
import engine.removeEngineObject
import geometry.Transform
import graphics.rendering.Renderer
import org.joml.Vector2f
import physics.ICollisionListener
import kotlin.reflect.KClass

class EngineObject(val id: Int? = null, position: Vector2f = Vector2f(), scale: Vector2f = Vector2f()) : IEngineObject {

    val transform: Transform = Transform(this)

    private val components: MutableSet<EngineComponent> = mutableSetOf()

    private val children: MutableSet<EngineObject> = mutableSetOf()

    var renderer: Renderer? = null
        set(value) {
            field = value
            field!!.parentObject = this
        }

    var parent: EngineObject? = null

    var active = false
        set(value) {
            components.forEach { it.active = value }

            children.forEach { it.active = value }

            field = value
        }

    init {
        addEngineObject(this)
        active = true
        transform.position = position
        transform.scale = scale
    }

    override fun start() {
        components.forEach(IEngineObject::start)

        children.forEach(IEngineObject::start)
    }

    override fun update() {
        if (!active) return
        components.forEach(IEngineObject::update)
        children.forEach(IEngineObject::update)
    }

    fun addComponent(component: EngineComponent): EngineComponent {
        component.parentObject = this
        this.components.add(component)
        return component
    }

    fun addChild(engineObject: EngineObject) {
        removeEngineObject(engineObject)
        engineObject.parent = this
        children.add(engineObject)
    }

    fun createChild(): EngineObject {
        val child = EngineObject()
        addChild(child)
        return child
    }

    fun getChild(id: Int): EngineObject? {
        return if (id > children.size) {
            null
        } else children.elementAt(id)
    }

    fun setScale(scale: Vector2f) {
        transform.scale = scale
    }

    fun getScale(): Vector2f {
        return transform.scale
    }

    fun getPosition(): Vector2f {
        return transform.position
    }

    fun setPosition(position: Vector2f) {
        transform.position = position
    }

    fun getRotation(): Float {
        return transform.rotation
    }

    fun setRotation(rotation: Float) {
        transform.rotation = rotation
    }

    fun getCollisionListeners(): Set<ICollisionListener> {
        return components.filterIsInstance<ICollisionListener>().toSet()
    }

    fun getComponent(kClass: KClass<out EngineComponent>): Any? {
        return components.firstOrNull() { kClass.isInstance(it) }
    }

}