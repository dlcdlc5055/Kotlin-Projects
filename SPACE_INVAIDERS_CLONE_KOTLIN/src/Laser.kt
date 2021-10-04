import com.raylib.Jaylib
import com.raylib.Raylib

class Laser {
    var pos=Vect(0,0)
    private val laserSpeed=1500
    var colisionRect:Rectangle
    val side:Boolean
    val clr:Raylib.Color
    constructor(x:Int,y:Int,side:Boolean,clr:Raylib.Color){
        this.clr=clr
        this.pos.x=x
        this.pos.y=y
        this.side=side
        colisionRect=Rectangle(pos.x,pos.y,6,69)
    }
    fun update(){
        var frameTime= Raylib.GetFrameTime()
        if(!side) {
            pos.y -= (frameTime * laserSpeed).toInt()
        }else{
            pos.y += (frameTime * laserSpeed).toInt()
        }
        colisionRect=Rectangle(pos.x,pos.y,6,34)
    }
    fun draw(){
        Raylib.DrawRectangle(pos.x,pos.y,6,34,clr)
    }
}