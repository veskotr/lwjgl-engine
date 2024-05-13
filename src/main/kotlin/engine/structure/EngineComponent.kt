package engine.structure

abstract class EngineComponent : IEngineObject {
    protected var active = false

    protected var parentObject: EngineObject? = null

    open fun setParentObject(engineObject: EngineObject?) {
        parentObject = engineObject
    }

    open fun getParentObject(): EngineObject? {
        return parentObject
    }

    open fun isActive(): Boolean {
        return active
    }

    open fun setActive(active: Boolean) {
        this.active = active
    }

    abstract fun destroy()
}