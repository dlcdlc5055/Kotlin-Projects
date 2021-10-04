import com.raylib.Jaylib
import com.raylib.Raylib

    fun main(args: Array<String>) {
        Raylib.InitWindow(1600, 860, "MyPaint 2.0")
        var prg=Program()
        while (!Raylib.WindowShouldClose()) {
            prg.update()
            prg.draw()
        }
        Raylib.CloseWindow()
    }
