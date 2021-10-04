import java.awt.Color.red
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class LoadFile {
    fun load(): Array<Array<IntArray>>? {
        var os=System.getProperty("os.name")
        var img:Image
        val currentPath = System.getProperty("user.dir")
        try {
            var path= File(currentPath+"/img.png")
            if(os!="Linux")
                path= File(currentPath+"\\img.png")
            var img:BufferedImage
            img=ImageIO.read(path)
            var height=img.height
            var width=img.width
            val bufferImg = Array(width) {
                Array(height) {
                    IntArray(3)
                }
            }
            for(i in 0..width-1){
                for(j in 0..height-1){
                    var buffer=img.getRGB(i,j)
                    var red = (buffer shr 16) and 0xff
                    var green = (buffer shr 8) and 0xff
                    var blue = buffer  and 0xff
                    bufferImg[i][j][0]=red
                    bufferImg[i][j][1]=green
                    bufferImg[i][j][2]=blue
                }
            }
            return bufferImg
        }catch (E:Exception){}
        return null
    }
}