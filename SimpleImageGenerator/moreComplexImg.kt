import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

fun drawCircles(): BufferedImage {
  // Add your code here
    val image: BufferedImage = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)

    for (i in 0 until image.width) {
        for (j in 0 until image.height) {
            image.setRGB(0, 0, 0)
        }
    }

    val graphics = image.createGraphics()
    graphics.color = Color.RED
    graphics.drawOval(50, 50, 100, 100)
    graphics.color =  Color.YELLOW
    graphics.drawOval(50, 75, 100, 100)
    graphics.color = Color.GREEN
    graphics.drawOval(75, 50, 100, 100)
    graphics.color = Color.BLUE
    graphics.drawOval(75, 75, 100, 100)

    return image
}

fun main() {
    val outputForImage = File("YOURPATH")
    val image = drawCircles()

    ImageIO.write(image, "png", outputForImage)
}
