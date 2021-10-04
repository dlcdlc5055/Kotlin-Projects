import com.raylib.Jaylib
import com.raylib.Raylib


fun main(args: Array<String>) {
        Raylib.InitWindow(800*2, 450*2, "SPACE INVAIDERS CLONE")
        Raylib.SetTargetFPS(60)
        var game=GAME()
        while (!Raylib.WindowShouldClose()) {
            Raylib.BeginDrawing()
            Raylib.ClearBackground(Jaylib.BLACK)
            //Raylib.DrawFPS(20, 20)
            game.update()
            game.draw()
            if (game.dead)
                if(Raylib.IsKeyPressed(Jaylib.KEY_SPACE))
                    game=GAME()
            Raylib.EndDrawing()
        }
        Raylib.CloseWindow()
}
