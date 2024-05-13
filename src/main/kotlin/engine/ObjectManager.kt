package engine

import engine.structure.EngineObject

private var engineObjects: MutableList<EngineObject> = mutableListOf()

fun addEngineObject(engineObject: EngineObject) {
    engineObjects.add(engineObject)
}

fun startEngineObjects() {
    engineObjects.forEach { it.start() }
}

fun updateEngineObjects() {
    engineObjects.forEach { it.update()  }
}

fun removeEngineObject(engineObject: EngineObject) {
    engineObjects.remove(engineObject)
}