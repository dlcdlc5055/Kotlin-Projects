import com.raylib.Jaylib
import com.raylib.Raylib

class Bloker {
    private val pos:Vect
    private val blockerSize=(17/.6).toInt()
    private val blockerRez=Vect((15/2).toInt(),(6/2).toInt())
    private var health = Array(blockerRez.x) { BooleanArray(blockerRez.y)}
    private var ColisionRects = Array(blockerRez.x) {
        arrayOfNulls<Rectangle>(
            blockerRez.y
        )
    }

    constructor(x:Int,y:Int){
        this.pos=Vect(x,y)
        for(i in 0..blockerRez.x-1){
            for(j in 0..blockerRez.y-1){
                this.health[i][j]=true;
                this.ColisionRects[i][j]=Rectangle(pos.x+(i*blockerSize),pos.y+(j*blockerSize),blockerSize,blockerSize)
            }
        }
    }

    fun draw(){
        for(i in 0..blockerRez.x-1){
            for(j in 0..blockerRez.y-1){
                if(this.health[i][j]){
                    val x=pos.x+(i*blockerSize)
                    val y=pos.y+(j*blockerSize)
                    Raylib.DrawRectangle(x,y,blockerSize,blockerSize,Jaylib.GREEN)
                }
            }
        }
    }
    fun checkIfHitLaser(col_rect:Rectangle):Boolean{
        var ret=false
        var x_lock=-1
        for(i in 0..blockerRez.x-1){
            for(j in 0..blockerRez.y-1){
                if(ColisionRects[i][j]?.let { col_rect.checkCollision(it) } == true && health[i][j]==true){
                    if(x_lock==-1) {
                        health[i][j] = false
                        x_lock=i
                    }else if(i==x_lock){
                        health[i][j] = false
                    }
                    ret=true
                }
            }
        }
        return ret
    }
}