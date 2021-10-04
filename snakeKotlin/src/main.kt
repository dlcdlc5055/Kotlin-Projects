import com.raylib.Jaylib
import com.raylib.Raylib

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Raylib.InitWindow(900,950, "SNAKE KOTLIN")
        Raylib.SetTargetFPS(60)
        var game = Game();

        while (!Raylib.WindowShouldClose()) {
            Raylib.BeginDrawing()
            Raylib.ClearBackground(Jaylib.BLACK)
            game.play()
            if (game.gameover && Raylib.IsKeyPressed(Raylib.KEY_SPACE))
                game=Game()
            Raylib.EndDrawing()
        }
        Raylib.CloseWindow()
    }
}