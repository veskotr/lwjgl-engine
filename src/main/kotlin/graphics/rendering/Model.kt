package graphics.rendering

abstract class Model {
    protected var vId: Int = 0
    protected var tId: Int = 0
    protected var iId: Int = 0
    protected var count: Int = 0

    abstract fun bindModel()
    abstract fun render()
    abstract fun unbindModel()
}