import java.io.File                   // Import the File class for file handling
import javax.imageio.ImageIO          // Import the ImageIO class for reading and writing images
import java.awt.image.BufferedImage   // BufferedImage Class
import java.awt.Color                 // Color class

fun main() {
    // Image creation with size of 256 x 256
    val myCustomImage: BufferedImage = BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB)

    // Iteration for every pixel on the image
    for (i in 0 until myCustomImage.width) {
        for (j in 0 until myCustomImage.height) {

            myCustomImage.setRGB(i, j, Color(0 + i, 0 + j, 50, j).rgb)
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
