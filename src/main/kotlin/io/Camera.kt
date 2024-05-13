package io

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

private var projection: Matrix4f? = null

private var cameraPosition: Vector2f? = null
private var cameraScale: Vector2f? = null

fun initCamera(width: Int, height: Int) {
    cameraPosition = Vector2f(0f)
    projection = Matrix4f().ortho2D(-width / 2.0f, width / 2.0f, -height / 2.0f, height / 2.0f)
    cameraScale = Vector2f(1f)
}

fun getCameraPositionInWorldSpace(): Vector2f {
    return cameraPosition!!
}

fun setCameraPositionInWorldSpace(position: Vector2f) {
    cameraPosition = position
}

fun getProjectionMatrix(): Matrix4f {
    return projection!!.scale(Vector3f(cameraScale, 0f)).translate(Vector3f(cameraPosition, 0f), Matrix4f())
}

fun setCameraScale(scale: Vector2f) {
    cameraScale = scale
}

fun getCameraScale(): Vector2f {
    return cameraScale!!
}