import com.raylib.Jaylib
import com.raylib.Raylib
import com.raylib.Raylib.*

class BtnText {
    var txt:String
    var pos:Vect
    var size:Vect
    var fontSize:Int
    var colRect:Rectangle
    var TextOffset:Vect
    var active=false
    var timeBuffer=0f
    constructor(txt:String,pos:Vect,size:Vect,fontSize:Int,TextOffset:Vect){
        this.txt=txt
        this.pos=pos
        this.size=size
        this.fontSize=fontSize
        this.TextOffset=TextOffset
        colRect= Rectangle(pos.x, pos.y, size.x, size.y)
    }
    fun draw(){
        if(active)
            timeBuffer+= GetFrameTime()
        if(timeBuffer>1.5){
            timeBuffer=0f
            active=false
        }
        if(IsMouseButtonPressed(MOUSE_LEFT_BUTTON) && getIfClicked())
            active=true
        else if(IsMouseButtonReleased(MOUSE_LEFT_BUTTON) && getIfClicked())
            active=false
        if(active)
            DrawRectangle(pos.x,pos.y,size.x,size.y,Jaylib.DARKGRAY)
        else
            DrawRectangle(pos.x,pos.y,size.x,size.y,Jaylib.GRAY)
        DrawText(txt,pos.x+TextOffset.x,pos.y+TextOffset.y,fontSize,Jaylib.WHITE)
    }
    fun getIfClicked():Boolean{
        var mousePos=Vect(GetMousePosition().x().toInt(), GetMousePosition().y().toInt())
        return colRect.checkCollision(Rectangle(mousePos.x - 1, mousePos.y - 1, 3, 3))
    }
}