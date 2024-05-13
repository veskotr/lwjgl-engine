package engine.structure

import engine.addEngineObject
import engine.geometry.Transform
import engine.removeEngineObject
import org.joml.Quaternionf
import org.joml.Vector2f
import java.util.function.Consumer

class EngineObject() : IEngineObject {

    private val transform: Transform = Transform(this)

    private val components: MutableSet<EngineComponent> = mutableSetOf()

    private val children: MutableSet<EngineObject> = mutableSetOf()

    var parent: EngineObject? = null

    var active = false

    init {
        addEngineObject(this)
        active = true
    }

    override fun start() {
        components.forEach(IEngineObject::start)

        children.forEach(IEngineObject::start)
    }

    override fun update() {
        transform.position.add(transform.frameVelocity)
        components.forEach(IEngineObject::update)
        children.forEach(IEngineObject::update)
        transform.frameVelocity = Vector2f()
        if (parent != null) {
            transform.rotation = Quaternionf(parent!!.transform.rotation)
            transform.position = Vector2f(parent!!.transform.position)
            transform.scale = Vector2f(parent!!.transform.scale)
        }
    }

    fun getTransform(): Transform? {
        return transform
    }

    fun addComponent(component: EngineComponent): EngineComponent? {
        component.setParentObject(this)
        this.components.add(component)
        return component
    }

    fun addChild(engineObject: EngineObject) {
        removeEngineObject(engineObject)
        engineObject.parent = this
        children.add(engineObject)
    }

    fun getChild(id: Int): EngineObject? {
        return if (id > children.size) {
            null
        } else children.elementAt(id)
    }

    fun setActive(active: Boolean) {
        components.forEach { it.setActive(active) }

        children.forEach { it.setActive(active) }
        this.active = active
    }
}