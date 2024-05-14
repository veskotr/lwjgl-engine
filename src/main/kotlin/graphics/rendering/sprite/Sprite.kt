package graphics.rendering.sprite

import graphics.Texture
import graphics.rendering.SquareModel

data class Sprite(val texture: Texture, val squareModel: SquareModel) {

    fun bindTexture(sampler: Int) {
        texture.bind(sampler)
    }

    fun renderModel() {
        squareModel.render()
    }
}