import io.*


private fun loop() {
    while (!windowShouldClose()) {
        clearWindow()

        pollEvents()
        updateInput()
    }
}

fun main(args: Array<String>) {
    initWindow(1280, 920, "Testss")
    initInput()

    loop()
}