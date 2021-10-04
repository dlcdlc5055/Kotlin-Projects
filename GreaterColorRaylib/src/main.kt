package com.company
import com.raylib.Jaylib.*
import com.raylib.Raylib
import kotlin.random.Random

class Main {
    companion object {
        var bord = Array(8) { IntArray(8) }
        var count_red=0;
        var count_green=0;
        var score=0;
        var state=false

        fun init(){
            bord = Array(8) { IntArray(8) }
            count_red=0;
            count_green=0;
            score=0;
            state=false
            generateBord()
        }

        fun generateBord(){
            for (i in 0..7){
                for (j in 0..7){
                    var buffer = Random.nextBoolean();

                    if(buffer){
                        count_red+=1
                        bord[i][j]=1
                    }else{
                        count_green+=1
                        bord[i][j]=2
                    }
                }
            }
        }

        fun drawBord(){
            DrawRectangle(0,0,720,720, GRAY)
            DrawText("Score: "+score.toString(),720+10,10,35, BLACK)
            for (i in 0..7){
                for (j in 0..7){
                    var buffer=bord[i][j];

                    if(buffer==1){
                        DrawRectangle(5+i*(720/8),5+j*(720/8),720/8-10,720/8-10, RED)
                    }else if(buffer==2){
                        DrawRectangle(5+i*(720/8),5+j*(720/8),720/8-10,720/8-10, GREEN)
                    }
                }
            }
        }

        fun processInput(){
            if(IsKeyReleased(KEY_W)){
                if(count_red> count_green){
                    score+=1;
                }else{
                    score-=1;
                }
                generateBord()
            }
            if(IsKeyReleased(KEY_S)){
                if(count_red> count_green){
                    score-=1;
                }else{
                    score+=1;
                }
                generateBord()
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            InitWindow(920, 720, "Greater Color Raylib")
            SetTargetFPS(60)
            init()

            while (!WindowShouldClose()) {
                BeginDrawing()
                ClearBackground(LIGHTGRAY)
                drawBord()
                processInput()
                EndDrawing()
            }

            Raylib.CloseWindow()
        }
    }
}