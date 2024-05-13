package io

import org.lwjgl.glfw.GLFW

private val keys = BooleanArray(GLFW.GLFW_KEY_LAST)
private val buttons = BooleanArray(3)

fun initInput() {
    keys.fill(false, GLFW.GLFW_KEY_SPACE, GLFW.GLFW_KEY_LAST)
    buttons.fill(false)
}

fun isKeyDown(key: Int) = GLFW.glfwGetKey(window, key) == 1

fun isMouseButtonDown(button: Int) = GLFW.glfwGetMouseButton(window, button) == 1

fun updateInput() {
    for (i in GLFW.GLFW_KEY_SPACE until GLFW.GLFW_KEY_LAST) keys[i] = isKeyDown(i)
    for (i in buttons.indices) buttons[i] = isMouseButtonDown(i)
}

fun isMouseButtonPressed(button: Int) = isMouseButtonDown(button) && !buttons[button]

fun isKeyPressed(key: Int) = isKeyDown(key) && !keys[key]

fun isMouseButtonReleased(button: Int) = !isMouseButtonDown(button) && buttons[button]

fun isKeyReleased(key: Int) = !isKeyDown(key) && keys[key]