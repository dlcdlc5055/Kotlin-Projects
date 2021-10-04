package com.company
import com.raylib.Jaylib.*
import com.raylib.Raylib

class Main {
    companion object {
        var gradientRed = IntArray(1280)
        var gradientGreen = IntArray(1280)
        var gradientBlue = IntArray(1280)
        var clr1 = IntArray(3)
        var clr2 = IntArray(3)
        var selected = 0

        fun generateGradient(){
            for (i in 0..1279) {
                var weightClr1:Double=i.toDouble()/1279;
                var weightClr2:Double=1-weightClr1;
                gradientRed[i]=((clr1[0].toDouble()*weightClr1)+(clr2[0].toDouble()*weightClr2)).toInt()
                gradientGreen[i]=((clr1[1].toDouble()*weightClr1)+(clr2[1].toDouble()*weightClr2)).toInt()
                gradientBlue[i]=((clr1[2].toDouble()*weightClr1)+(clr2[2].toDouble()*weightClr2)).toInt()
            }
        }

        fun drawGradient() {
            for (i in 0..1279) {
                var color = RAYWHITE
                color.r(gradientRed[i].toByte())
                color.g(gradientGreen[i].toByte())
                color.b(gradientBlue[i].toByte())
                DrawRectangle(1279-i,0,1,1000, color)
            }
        }

        fun processInput(){
            if(IsKeyPressed(KEY_D)){
                selected+=1
                if(selected>5){
                    selected=5;
                }
            }
            if(IsKeyPressed(KEY_A)){
                selected-=1
                if(selected<0){
                    selected=0;
                }
            }
            if(IsKeyDown(KEY_W)){
                if(selected<3){
                    clr1[selected]+=5;
                    if(clr1[selected]>255){
                        clr1[selected]=255
                    }
                }
                if(selected>=3){
                    clr2[selected-3]+=5;
                    if(clr2[selected-3]>255){
                        clr2[selected-3]=255
                    }
                }
                generateGradient()
            }
            if(IsKeyDown(KEY_S)){
                if(selected<3){
                    clr1[selected]-=5;
                    if(clr1[selected]<0){
                        clr1[selected]=0
                    }
                }
                if(selected>=3){
                    clr2[selected-3]-=5;
                    if(clr2[selected-3]<0){
                        clr2[selected-3]=0
                    }
                }
                generateGradient()
            }
        }

        fun drawUI(){
           DrawRectangle(0,0,10000,80, WHITE)
           for(i in 0..2){
               DrawRectangle(10+(120*i),10,110,60, LIGHTGRAY);
               DrawRectangle(920+(120*i),10,110,60, LIGHTGRAY);
               if(selected==i){
                   DrawRectangle(10+(120*i),10,110,60, GREEN);
               }else if(selected>2 && selected-3==i){
                   DrawRectangle(920+(120*i),10,110,60, GREEN);
               }
           }
            for(i in 0..2){
                Raylib.DrawText(clr1[i].toString(),20+(120*i),15,55, BLACK)
                Raylib.DrawText(clr2[i].toString(),930+(120*i),15,55, BLACK)
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            InitWindow(1280, 720, "Gradient Raylib")
            SetTargetFPS(60)
            generateGradient()

            while (!WindowShouldClose()) {
                BeginDrawing()
                ClearBackground(RAYWHITE)
                drawGradient()
                drawUI()
                DrawFPS(5, 5)
                processInput()
                EndDrawing()
            }

            Raylib.CloseWindow()
        }
    }
}