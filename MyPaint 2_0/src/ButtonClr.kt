import com.raylib.Raylib.*
import com.raylib.Jaylib.*

class ButtonClr {
    var selected=false
    var pos:Vect
    var size:Vect
    var color:Color
    var clrID:Int
    var colRect:Rectangle
    constructor(pos:Vect,size:Vect,clr:Color,clrID:Int){
        this.pos=pos
        this.size=size
        this.color=clr
        this.clrID=clrID
        colRect=Rectangle(pos.x,pos.y,size.x,size.y)
    }
    fun getIfClicked():Boolean{
        var mousePos=Vect(GetMousePosition().x().toInt(),GetMousePosition().y().toInt())
        return colRect.checkCollision(Rectangle(mousePos.x-1,mousePos.y-1,3,3))
    }
    fun draw(){
        if(selected)
            DrawRectangle(pos.x,pos.y,size.x,size.y, GRAY)
        DrawRectangle(pos.x+8,pos.y+8,size.x-16,size.y-16,color)
    }
}