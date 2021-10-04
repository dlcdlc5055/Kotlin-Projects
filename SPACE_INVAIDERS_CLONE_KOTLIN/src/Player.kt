import com.raylib.Jaylib
import com.raylib.Raylib
import sun.java2d.loops.DrawRect
import java.util.*

class Player {
    private var pos:Vect
    val colisionRect:Rectangle
    private val size:Vect=Vect(113,69)
    private val sprite:Raylib.Texture2D
    private val health_sprite:Raylib.Texture2D
    private val moveSpeed=750
    private var canFire=true
    private var timeBuffer:Float=0.0f
    var lives=3
    val lasers=Vector<Laser>()

    constructor(x:Int,y:Int){
        val currentPath = System.getProperty("user.dir")
        pos=Vect(x,y)
        sprite=Raylib.LoadTexture(currentPath+"\\assets\\Player.png")
        health_sprite=Raylib.LoadTexture(currentPath+"\\assets\\health_show.png")
        colisionRect=Rectangle(pos.x,pos.y,size.x,size.y)
    }
    fun draw(){
        for(i in 0..lasers.size-1){
            lasers[i].draw()
        }
        Raylib.DrawTexture(sprite,pos.x,pos.y,Jaylib.WHITE)
        Raylib.DrawRectangle(0,900-35,10000,1000,Jaylib.DARKGRAY)
        for(i in 0..lives-1){
            Raylib.DrawTexture(health_sprite,10+i*45,900-27,Jaylib.WHITE)
        }
    }
    fun addLife(){
        lives+=1
        if(lives>3){
            lives=3
        }
    }
    fun processInput(){
        var frameTime=Raylib.GetFrameTime()

        for(i in 0..lasers.size-1){
            if (lasers[i].pos.y<-300){
                lasers.remove(lasers[i])
                break
            }
        }

        if(timeBuffer>.35f){
            timeBuffer=0.0f
            canFire=true
        }else{
            timeBuffer+=frameTime
        }

        for(i in 0..lasers.size-1){
            lasers[i].update()
        }

        if(Raylib.IsKeyDown(Jaylib.KEY_A)){
            pos.x-=(moveSpeed*frameTime).toInt()
            if(pos.x<0){
                pos.x=0
            }
            colisionRect.setPos(pos.x,pos.y)
        }

        if(Raylib.IsKeyDown(Jaylib.KEY_D)){
            pos.x+=(moveSpeed*frameTime).toInt()
            if(pos.x>1600-113){
                pos.x=1600-113
            }
            colisionRect.setPos(pos.x,pos.y)
        }

        if(Raylib.IsKeyPressed(Jaylib.KEY_SPACE) && canFire){
            lasers.add(Laser(pos.x+(113/2-3).toInt(),pos.y-(69-34),false,Jaylib.GREEN))
            canFire=false
        }
    }
}