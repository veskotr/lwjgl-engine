package engine.structure

abstract class EngineComponent : IEngineObject {
    var active = false

    var parentObject: EngineObject? = null

}