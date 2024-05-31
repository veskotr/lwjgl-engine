package graphics.rendering

abstract class Model {
    protected var vId: Int = 0
    protected var tId: Int = 0
    protected var iId: Int = 0
    protected var count: Int = 0

    fun bindModel(){
        bindVertexBuffer()
        bindTextureBuffer()
        bindIndexBuffer()
    }
    abstract fun bindVertexBuffer(vId: Int = this.vId)
    abstract fun bindTextureBuffer(tId: Int = this.tId)
    abstract fun bindIndexBuffer()
    abstract fun drawModel()
    fun render(){
        bindModel()
        drawModel()
        unbindModel()
    }
    abstract fun unbindModel()
}