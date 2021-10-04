import com.raylib.Jaylib
import com.raylib.Jaylib.*
import com.raylib.Raylib
import com.raylib.Raylib.*
import java.util.*

class Program {
    var canvas_width = 1500
    var canvas_height = 800
    var canvas = Array(canvas_width) {
        Array(canvas_height){
            IntArray(3)
        }
    }
    var canvas_buffer = Array(canvas_width) {
        IntArray(
            canvas_height
        )
    }
    var sprite: Texture2D? =null
    var sizes=Vector<Float>()
    var colors=Vector<Color>()
    var shapes=Vector<Circle>()
    var selectedClr=1
    var selectedSize=1
    var timebuffer=0f
    var shapesBuget:Int=4000
    var clrButtons=Vector<ButtonClr>()
    var txtButtons=Vector<BtnText>()
    var saveFile=SaveFile()
    var loadFile=LoadFile()
    constructor(){
        for(i in 0..canvas_width-1){
            for(j in 0..canvas_height-1){
                canvas_buffer[i][j]=0
                canvas[i][j][0]=255
                canvas[i][j][1]=255
                canvas[i][j][2]=255
            }
        }
        colors.add(WHITE)
        colors.add(BLACK)
        colors.add(RED)
        colors.add(GREEN)
        colors.add(BLUE)
        colors.add(YELLOW)
        colors.add(PURPLE)
        colors.add(ORANGE)
        colors.add(BROWN)
        for(i in 0..5){
            sizes.add((20+i*20).toFloat())
        }
        var count=0
        for(color in colors){
            clrButtons.addElement(ButtonClr(Vect(1500,0+count*(800/9).toInt()),Vect(100,(800/9).toInt()),color,count))
            count+=1
        }
        clrButtons[1].selected=true
        txtButtons.addElement(BtnText("CLEAR",Vect(1600-200,800),Vect(200,100),40,Vect(30,14)))
        txtButtons.addElement(BtnText("-",Vect(0,800),Vect(100,100),40,Vect(40,14)))
        txtButtons.addElement(BtnText("+",Vect(200,800),Vect(100,100),40,Vect(40,14)))
        txtButtons.addElement(BtnText("SAVE",Vect(1600/2-200-10,800),Vect(200,100),40,Vect(40,14)))
        txtButtons.addElement(BtnText("LOAD",Vect(1600/2+10,800),Vect(200,100),40,Vect(40,14)))
    }
    fun clear(){
        for(i in 0..canvas_width-1){
            for(j in 0..canvas_height-1){
                canvas_buffer[i][j]=0
                for(t in 0..2){
                    canvas[i][j][t]=255
                }
            }
        }
        for(i in 0..shapes.size-1)
            shapes.removeElementAt(0)
        sprite=null
    }
    fun update(){
        var mousePos=Vect(GetMousePosition().x().toInt(),GetMousePosition().y().toInt())
        var can=true
        if(shapes.size>0 && getDistance(mousePos.x,mousePos.y,shapes[shapes.size-1].center.x,shapes[shapes.size-1].center.y) < 6)
            can=false
        if(IsMouseButtonDown(MOUSE_LEFT_BUTTON) && mousePos.x<canvas_width&&mousePos.y<canvas_height&& mousePos.x>0&&mousePos.y>0 && can){
            canvas_buffer[mousePos.x][mousePos.y]=selectedClr
            shapes.add(Circle(mousePos,sizes[selectedSize],colors[selectedClr],selectedClr))
        }
        if(IsMouseButtonDown(MOUSE_RIGHT_BUTTON) && mousePos.x<canvas_width&&mousePos.y<canvas_height&& mousePos.x>0&&mousePos.y>0&& can){
            canvas_buffer[mousePos.x][mousePos.y]=0
            shapes.add(Circle(mousePos,sizes[selectedSize],colors[0],0))
        }
        timebuffer+= GetFrameTime()
        if(timebuffer>.999) {
            clearInvizibleShapes()
            timebuffer=0f
        }
        if(timebuffer>.333)
            limitShapes()
        for(btn in clrButtons){
            if(btn.getIfClicked() && IsMouseButtonPressed(MOUSE_LEFT_BUTTON)){
                for(i in clrButtons)
                    i.selected=false
                btn.selected=true
                selectedClr=btn.clrID
            }
        }
        if(txtButtons[0].getIfClicked() && IsMouseButtonPressed(MOUSE_LEFT_BUTTON))
            clear()
        if(txtButtons[1].getIfClicked() && IsMouseButtonPressed(MOUSE_LEFT_BUTTON))
            this.selectedSize-=1
        if(txtButtons[2].getIfClicked() && IsMouseButtonPressed(MOUSE_LEFT_BUTTON))
            this.selectedSize+=1
        if(txtButtons[3].getIfClicked() && IsMouseButtonPressed(MOUSE_LEFT_BUTTON))
            save()
        if(txtButtons[4].getIfClicked() && IsMouseButtonPressed(MOUSE_LEFT_BUTTON))
            load()
        if(selectedSize<1)
            selectedSize=1
        if(selectedSize>5)
            selectedSize=5
    }
    fun getDistance(x1:Int,y1:Int,x2:Int,y2:Int):Int{
        val dis: Double
        dis = Math.sqrt(((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toDouble())
        return dis.toInt()
    }
    fun clearInvizibleShapes(){
        for(i in 0..shapes.size-1){
            try{
                if(shapes[i].clrId==shapes[i
                ].clrId&&shapes[i].center.x==shapes[i+1].center.x&&shapes[i].center.y==shapes[i+1].center.y && shapes[i].size==shapes[1+i].size) {
                    shapes.removeElementAt(i + 1)
                    break
                }
            }catch (e:Exception){}
        }
    }
    fun limitShapes(){
        var fps= GetFPS()
        if(fps>60 && shapesBuget<50000)
            shapesBuget+=500
        if(fps<60 && shapesBuget>4500)
            shapesBuget-=500
        if(shapes.size>shapesBuget) {
            for (i in 0..shapes.size - shapesBuget) {
                shapes.removeElementAt(i)
            }
        }
    }
    fun save(){
        val img = Array(1500) {
            Array(900) {
                IntArray(3)
            }
        }
        for(i in 0..1500-1){
            for(j in 0..800-1){
                img[i][j][0]=canvas[i][j][0]
                img[i][j][1]=canvas[i][j][1]
                img[i][j][2]=canvas[i][j][2]
            }
        }
        for(shape in shapes){
            var shapeSize=shape.size
            for (i in 0..shapeSize.toInt()*2){
                for (j in 0..shapeSize.toInt()*2){
                    var clrId=shape.clrId
                    var clr=colors[clrId]
                    var red=clr.r()
                    var green=clr.g()
                    var blue=clr.b()
                    var center=shape.center
                    var point=Vect((center.x-shapeSize+i).toInt(),(center.y-shapeSize+j).toInt())
                    var dist=getDistance(center.x,center.y,point.x,point.y)
                    if(dist<shapeSize){
                        try {
                            img[point.x][point.y][0] = (red).toInt()
                            img[point.x][point.y][1] = (green).toInt()
                            img[point.x][point.y][2] = (blue).toInt()
                        }catch (e:Exception){}
                    }
                }
            }
        }
        saveFile.save(img)
    }
    fun load(){
        clear()
        var os=System.getProperty("os.name")
        var data=loadFile.load()
        val currentPath = System.getProperty("user.dir")
        if(os =="Linux")
            sprite= Raylib.LoadTexture(currentPath+"/img.png")
        else
            sprite= Raylib.LoadTexture(currentPath+"\\img.png")
        if(data!=null){
            for(i in 0..1500-1){
                for(j in 0..800-1){
                    try {
                        canvas[i][j][0] = data[i][j][0]
                        canvas[i][j][1] = data[i][j][1]
                        canvas[i][j][2] = data[i][j][2]
                    }catch (e:Exception){
                        canvas[i][j][0] = 255
                        canvas[i][j][1] = 255
                        canvas[i][j][2] = 255
                    }
                }
            }
        }
    }
    fun draw(){
        Raylib.BeginDrawing()
        ClearBackground(LIGHTGRAY)
        DrawRectangle(0,0,canvas_width,canvas_height, WHITE)
        if(sprite!=null)
            DrawTexture(sprite,0,0,Jaylib.WHITE)
        for(shape in shapes)
            shape.draw()
        DrawRectangle(0,800,10000,10000, LIGHTGRAY)
        DrawRectangle(1500,0,10000,10000,LIGHTGRAY)
        for(btn in clrButtons)
            btn.draw()
        for(btn in txtButtons)
            btn.draw()
        Raylib.DrawText((selectedSize).toString(),100+40,800+14   ,40,Jaylib.WHITE)
        Raylib.DrawFPS(20, 20)
        Raylib.EndDrawing()
    }
}