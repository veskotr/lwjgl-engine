package engine

private var engineObjects: MutableList<IEngineObject> = mutableListOf()

fun addEngineObject(engineObject: IEngineObject) {
    engineObjects.add(engineObject)
}

fun startEngineObjects() {
    engineObjects.forEach { it.start() }
}

fun updateEngineObjects() {
    engineObjects.forEach { it.update()  }
}

fun removeEngineObject(engineObject: IEngineObject) {
    engineObjects.remove(engineObject)
}