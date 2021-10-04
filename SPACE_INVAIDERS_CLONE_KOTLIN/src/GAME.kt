import com.raylib.Jaylib
import com.raylib.Raylib
import java.util.*

class GAME {
    private val player=Player((1600/2-113/2).toInt(),900-105)
    private var score=0
    private val blockers= kotlin.arrayOfNulls<Bloker>(5)
    private val enemys=Vector<Enemy>()
    private val aliensPerRow=12
    private var timeBuffer=0.0f
    private val enemyFireWaitDelay=.2f
    private var EnemyMoveTimeBuffer=0f
    private val enemyMoveWaitTime=1.25f
    private val spriteType_2:Raylib.Texture2D
    private val spritesType_1=Vector<Raylib.Texture2D>()
    var dead=false
    private var killBuffer=0
    private val explosionSprite:Raylib.Texture2D

    constructor(){
        val currentPath = System.getProperty("user.dir")
        for(i in 0..4){
            blockers[i]=Bloker(60+i*320,690)
        }
        for (i in 0..5000){
            generateRowEnemy(1, -100 - (i * 100), true)
        }
        spriteType_2=Raylib.LoadTexture(currentPath+"\\assets\\alien_type_2.png")
        explosionSprite=Raylib.LoadTexture(currentPath+"\\assets\\explosion.png")
        spritesType_1.add(Raylib.LoadTexture(currentPath+"\\assets\\alien_type1_phase1.png"))
        spritesType_1.add(Raylib.LoadTexture(currentPath+"\\assets\\alien_type1_phase2.png"))
    }

    fun generateRowEnemy(type:Int,y:Int,side:Boolean){
        for(i in 0..8){
            if(side) {
                enemys.add(Enemy(5 + i * 125, y, type, side,spriteType_2,spritesType_1,explosionSprite))
            }else{
                enemys.add(Enemy(1600-(5 + i * 125)-116, y, type, side,spriteType_2,spritesType_1,explosionSprite))
            }
        }
    }

    fun moveAllEnemys(){
        for(i in 0..enemys.size-1){
            enemys[i].move()
        }
    }

    fun update() {
        if(!dead){
            if(killBuffer>=15){
                killBuffer=0
                player.addLife()
            }
            if(player.lives<=0)
                dead=true
            for(i in 0..enemys.size-1){
                var c_E=enemys[i].colRect
                if(enemys[i].pos.y>0)
                    for(j in 0..blockers.size-1)
                        blockers[j]?.checkIfHitLaser(c_E)
            }
            for(i in 0..enemys.size-1)
                if(enemys[i].pos.y>800)
                    dead=true
            EnemyMoveTimeBuffer+=Raylib.GetFrameTime()
            if(EnemyMoveTimeBuffer>enemyMoveWaitTime){
                moveAllEnemys()
                EnemyMoveTimeBuffer=0f
            }
            timeBuffer+=Raylib.GetFrameTime()
            clearDeadEnemy()
            player.processInput()
            for(i in 0..player.lasers.size-1){
                val col_P=player.lasers[i].colisionRect
                var _break=false
                for(j in 0..4){
                    var buffer=blockers[j]?.checkIfHitLaser(col_P)
                    if(buffer==true){
                        player.lasers.remove(player.lasers[i])
                        _break=true
                        break
                    }
                }
                if(_break){break}
                for(j in 0..enemys.size-1){
                    if(enemys[j].pos.y>-80 && enemys[j].checkIfHit(player.lasers[i].colisionRect) && !enemys[j].dying){
                        player.lasers.remove(player.lasers[i])
                        killBuffer+=1
                        enemys[j].dying=true
                        score+=100
                        _break=true
                        break
                    }
                }
                if(_break){break}
            }
            for(i in 0..enemys.size-1){
                var _break=false
                enemys[i].update()
                if(_break){break}
                for(j in 0..enemys[i].lasers.size-1){
                    if(_break){break}
                    for(t in 0..blockers.size-1){
                        val col_E=enemys[i].lasers[j].colisionRect
                        if(_break){break}
                        if(blockers[t]?.checkIfHitLaser(col_E) == true){
                            enemys[i].lasers.removeAt(j)
                            _break=true
                            break
                        }
                    }
                }
            }
            if(timeBuffer>enemyFireWaitDelay){
                var i=0
                timeBuffer=0f
                while (true && i<10000 && !enemys[0].phase){
                    i+=1
                    var enemyID=(Math.random()*enemys.size).toInt()
                    if(enemys[enemyID].canShoot && !enemys[enemyID].dying &&enemys[enemyID].pos.y>-80) {
                        enemys[enemyID].fire()
                        break
                    }
                }
            }
            for(i in  0..enemys.size-1){
                for (j in 0..enemys[i].lasers.size-1){
                    if(player.colisionRect.checkCollision(enemys[i].lasers[j].colisionRect)){
                        enemys[i].lasers.removeAt(j)
                        player.lives-=1
                        break
                    }
                }
            }
        }
    }

    fun clearDeadEnemy(){
        for(i in 0..enemys.size-1){
            if(!enemys[i].alive){
                enemys.remove(enemys[i])
                break
            }
        }
    }

    fun draw() {
        if(!dead) {
            for (i in 0..enemys.size - 1)
                enemys[i].draw()
            for (i in 0..4)
                blockers[i]?.draw()
            player.draw()
            Raylib.DrawText("SCORE: " + score.toString(), 145, 900 - 32, 30, Jaylib.WHITE)
        }else{
            Raylib.DrawText("GAME OVER",10,10,50,Jaylib.WHITE)
            Raylib.DrawText("SCORE: "+score.toString(),10,80,50,Jaylib.WHITE)
            Raylib.DrawText("PRESS SPACE TO RESET!!",10,80+70,50,Jaylib.WHITE)
        }
    }
}