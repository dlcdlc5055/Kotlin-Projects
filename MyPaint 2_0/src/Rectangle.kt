class Rectangle {
    private var pos:Vect
    private var size:Vect

    constructor(x:Int,y:Int,x_size:Int,y_size:Int){
        this.pos=Vect(x,y)
        this.size=Vect(x_size,y_size)
    }

    fun setPos(x:Int,y:Int){
        this.pos=Vect(x,y)
    }
    fun checkCollision(rect:Rectangle):Boolean{
        for(i in 0..size.x){
            for(j in 0..size.y){
                var x=pos.x+i
                var y=pos.y+j
                if(rect.pos.x<x &&rect.pos.x+rect.size.x>x && rect.pos.y<y &&rect.pos.y+rect.size.y>y){
                    return true
                }
            }
        }
        return false
    }
}