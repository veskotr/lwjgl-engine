package debug.rendering

import graphics.rendering.AbstractRenderer
import graphics.rendering.debugShader
import graphics.rendering.defaultShader
import graphics.shaders.Shader

abstract class AbstractDebugRenderer(shader: Shader = debugShader) : AbstractRenderer(shader, layerName = "99999999debug") {

}