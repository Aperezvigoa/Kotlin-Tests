import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Color
import java.awt.Image

fun main() {
    // Image creation with size of 256 x 256
    val myCustomImage: BufferedImage = BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB)

    // Iteration for every pixel on the image
    for (i in 0 until myCustomImage.width) {
        for (j in 0 until myCustomImage.height) {

            myCustomImage.setRGB(i, j, Color(255, 0, 255, j).rgb)
        }
    }
    val separator = File.separator
    val outputFile = File("src${separator}alpha.png")
    try {
        ImageIO.write(myCustomImage, "png", outputFile)
        println("Saved on: ${outputFile.absolutePath}")
    } catch (e: Exception) {
        print("The process failed...")
    }
}
