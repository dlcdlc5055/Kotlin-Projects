import com.raylib.Jaylib
import com.raylib.Raylib
import java.util.*

class Enemy {
    var pos:Vect
    private var size:Vect
    var colRect:Rectangle
    private val spriteType_2:Raylib.Texture2D
    private val spritesType_1:Vector<Raylib.Texture2D>
    private val explosionSprite:Raylib.Texture2D
    var type:Int
    var dying=false
    var alive=true
    var phase=true
    private var time_buffer=0f
    private var move_count=0
    val lasers=Vector<Laser>()
    var canShoot=true
    private var timeBuffer=0.0f
    private val fireDelay=0.8f
    var movesBuffer=0
    var side=false
    private val movesPerRow=3

    constructor(x:Int,y:Int,type:Int,side:Boolean,spriteType_2:Raylib.Texture2D,spriteType_1:Vector<Raylib.Texture2D>,explosionSprite:Raylib.Texture2D){
        this.spriteType_2=spriteType_2
        this.spritesType_1=spriteType_1
        this.explosionSprite=explosionSprite
        this.side=side
        pos=Vect(x,y)
        this.type=type
        if(type==1){
            size=Vect(105,76)
        }else{
            size=Vect(105,70)
        }
        size=Vect(105,70)
        colRect=Rectangle(pos.x,pos.y,size.x,size.y)
    }
    fun draw(){
        if(pos.y>-200) {
            if (!dying) {
                if (type == 1) {
                    if (this.phase) {
                        Raylib.DrawTexture(spritesType_1[0], pos.x, pos.y, Jaylib.WHITE)
                    } else {
                        Raylib.DrawTexture(spritesType_1[1], pos.x, pos.y, Jaylib.WHITE)
                    }
                } else {
                    Raylib.DrawTexture(spriteType_2, pos.x, pos.y, Jaylib.WHITE)
                }
            } else {
                Raylib.DrawTexture(explosionSprite, pos.x, pos.y, Jaylib.WHITE)
            }
        }
            for (i in 0..lasers.size - 1) {
                lasers[i].draw()
            }
    }
    fun update(){
        time_buffer+=Raylib.GetFrameTime()
        this.colRect=Rectangle(pos.x,pos.y,size.x,size.y)
        if(dying){
            time_buffer+=Raylib.GetFrameTime()
            if(time_buffer>.3){
                alive=false
            }
        }
        for (i in 0..lasers.size-1){
            lasers[i].update()
        }
        if(timeBuffer>fireDelay){
            time_buffer=0f
            canShoot=true
        }
    }
    fun checkIfHit(rect:Rectangle):Boolean{
        if (colRect.checkCollision(rect)==true)
            return true
        else
            return false
    }
    fun move(){
        if(move_count<3) {
            val moveSpeed = 161
            if (side) {
                pos.x += moveSpeed
            } else {
                pos.x -= moveSpeed
            }
            move_count += 1
        }else{
            move_count=0
            lower()
        }
        phase=!phase
    }
    fun lower(){
        pos.y+=95
        side=!side
    }
    fun fire(){
       lasers.add(Laser(pos.x+(105/2-3).toInt(),pos.y,true,Jaylib.WHITE))
    }
}