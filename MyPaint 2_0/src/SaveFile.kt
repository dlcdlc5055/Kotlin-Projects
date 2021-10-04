import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class SaveFile {
    fun save(data: Array<Array<IntArray>>){
        var os=System.getProperty("os.name")
        val currentPath = System.getProperty("user.dir")
        var image=BufferedImage(1500, 800, BufferedImage.TYPE_INT_RGB)
        for(i in 0..1500-1){
            for(j in 0..800-1){
                var red=data[i][j][0] and 0xff
                var green=data[i][j][1] and 0xff
                var blue=data[i][j][2] and 0xff
                var alpha =255
                val rgb: Int = (alpha shl 24) or (red shl 16) or (green shl 8) or blue
                image.setRGB(i,j,rgb)
            }
        }
        if(os=="Linux") {
            var file = File(currentPath + "/img.png")
            ImageIO.write(image, "png", file)
        }else{
            var file = File(currentPath + "\\img.png")
            ImageIO.write(image, "png", file)
        }
    }
}