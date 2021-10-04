import java.util.*
import com.raylib.Jaylib
import com.raylib.Raylib
import com.raylib.Raylib.*


class Game {
    private var bordSize=20;
    private var food = Vect(0,0)
    private var snake=Vector<Vect>();
    private var timeBuffer:Float=0.0f;
    private var snakeSpeed:Float=0.13f;
    private var snakeDir=0
    private var moveBuffer=snakeDir
    private var add=false
    var gameover=true
    var start=false
    private var score=0
    constructor(){
        generateSnake()
        generateFood()
        gameover=true
    }
    private fun draw(){
        drawFood()
        drawSnake()
        drawGrid()
        drawScore()
    }
    private fun drawScore(){
        DrawRectangle(0,902,1000,1000,Jaylib.BLACK)
        DrawText("Score: "+score.toString(),10,902+5,43,Jaylib.WHITE)
    }
    private fun drawGrid(){
        for (i in 1..(bordSize-1)) {
            DrawRectangle(i*(900/bordSize)-1,0,3,1000,Jaylib.DARKGRAY);
        }
        for (i in 1..bordSize) {
            DrawRectangle(0, i * (900 / bordSize) - 1, 1000, 3, Jaylib.DARKGRAY);
        }
    }
    private fun drawFood(){
        DrawRectangle(food.x*(900/bordSize),food.y*(900/bordSize),(900/bordSize),(900/bordSize),Jaylib.GREEN)
    }
    private fun drawSnake(){
        for (i in 0..snake.size-1){
            if(i!=snake.size-1){
                DrawRectangle(snake[i].x*(900/bordSize),snake[i].y*(900/bordSize),(900/bordSize),(900/bordSize),Jaylib.BLUE)
            }else{
                DrawRectangle(snake[i].x*(900/bordSize),snake[i].y*(900/bordSize),(900/bordSize),(900/bordSize),Jaylib.SKYBLUE)
            }
        }
    }
    private fun generateFood(){
        this.food=Vect((Math.random() * (bordSize-2)+1).toInt(), (Math.random() * (bordSize-2)+1).toInt())
        while (true){
            var count = 0
            for(i in 0..snake.size-1){
                if(snake[i].x!=food.x && snake[i].y!=food.y){
                    count+=1
                }
            }
            if(count==snake.size){
                break
            }else{
                this.food=Vect((Math.random() * (bordSize-2)+1).toInt(), (Math.random() * (bordSize-2)+1).toInt())
            }
        }
    }
    private fun processIfFoodPickup(){
        var buffer=false
        for(i in 0..snake.size-1){
            if(snake[i].x==food.x && snake[i].y==food.y){
                buffer=true
            }
        }
        if(buffer){
            add=true
            generateFood()
        }
    }
    private fun generateSnake(){
        snake.add(Vect(10,7+3))
        snake.add(Vect(10,6+3))
        snake.add(Vect(10,5+3))
    }
    private fun updateSnake(){
            if(snakeDir==0){
                if(!add)
                    snake.remove(snake[0])
                snake.add(Vect(snake[snake.size-1].x,snake[snake.size-1].y-1))
            }
            if(snakeDir==1){
                if(!add)
                    snake.remove(snake[0])
                snake.add(Vect(snake[snake.size-1].x,snake[snake.size-1].y+1))
            }
            if(snakeDir==2){
                if(!add)
                    snake.remove(snake[0])
                snake.add(Vect(snake[snake.size-1].x-1,snake[snake.size-1].y))
            }
            if(snakeDir==3){
                if(!add)
                    snake.remove(snake[0])
                snake.add(Vect(snake[snake.size-1].x+1,snake[snake.size-1].y))
            }
    }
    private fun processControls(){
        if(IsKeyPressed(KEY_W) && snakeDir!=1){
            moveBuffer=0
            start=true
        }
        if(IsKeyPressed(KEY_S)&& snakeDir!=0){
            moveBuffer=1
            start=true
        }
        if(IsKeyPressed(KEY_A)&& snakeDir!=3){
            moveBuffer=2
            start=true
        }
        if(IsKeyPressed(KEY_D)&& snakeDir!=2){
            moveBuffer=3
            start=true
        }
    }
    private fun getNextPos():Vect{
        var nextPos:Vect=Vect(snake[snake.size-1].x,snake[snake.size-1].y)
        if(snakeDir==0){
            nextPos.y-=1
        }
        if(snakeDir==1){
            nextPos.y+=1
        }
        if(snakeDir==2){
            nextPos.x-=1
        }
        if(snakeDir==3){
            nextPos.x+=1
        }
        return nextPos
    }
    private fun checkIfGameOver():Boolean{
        var nextPos=getNextPos()
        if(nextPos.x<0||nextPos.y<0||nextPos.x>bordSize-1||nextPos.y>bordSize-1)
            return true
        else
            for(i in 0..snake.size-1){
                if(nextPos.x==snake[i].x&&nextPos.y==snake[i].y)
                    return true
            }
        return false
    }
    private fun update(){
        processControls()
        this.gameover=checkIfGameOver()
        if(!gameover && start) {
            timeBuffer += GetFrameTime();
            if (timeBuffer > snakeSpeed) {
                timeBuffer = 0.0f;
                snakeDir = moveBuffer
                processIfFoodPickup()
                updateSnake()
                if (add) {
                    score += 100
                    add = false
                }
            }
        }else{

        }
    }
    fun play(){
        update();
        draw();
    }
}