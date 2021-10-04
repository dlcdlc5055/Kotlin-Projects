import com.raylib.Jaylib.*
import com.raylib.Raylib.*

class Circle {
    var size:Float
    var center:Vect
    var color: Color
    var clrId:Int
    constructor(center:Vect,size:Float,color: Color,clrId:Int){
        this.size=size
        this.center=center
        this.color=color
        this.clrId=clrId
    }
    fun draw(){
        DrawCircle(center.x,center.y,size,color)
    }
}