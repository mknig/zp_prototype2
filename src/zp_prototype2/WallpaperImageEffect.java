/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import zp_prototype2.image.WallpaperImage;

/**
 *
 * @author admin
 */
public class WallpaperImageEffect {

    public static Group pixelate(WallpaperImage wpImage, int blockSize) {

        PixelReader pixelReader = wpImage.getImage().getPixelReader();
       
        int width = (int) wpImage.getWidth();//10;//imageView.getFitWidth();//getWidth();
        int height = (int)wpImage.getHeight();//10 ;//imageView.getFitHeight();//getHeight();
        Group newImage = new Group();
        List<Color> colors = new ArrayList<Color>();

        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                Color col = pixelReader.getColor(x, y);
                int newRed = 0;
                int newGreen = 0;
                int newBlue = 0;
                colors.clear();

                for (int blockY = y; blockY < y + blockSize; ++blockY) {
                    for (int blockX = x; blockX < x + blockSize; ++blockX) {
                        if (blockX < 0 || blockX >= width) {
                            colors.add(col);
                            continue;
                        }
                        if (blockY < 0 || blockY >= height) {
                            colors.add(col);
                            continue;
                        }
                       try{
                        colors.add(pixelReader.getColor(blockX, blockY));
                       }catch(Exception e){
                           System.out.println("Error" + e);
                           
                       }
                    }
                }

                for (Color color : colors) {
                    newRed += (int) (color.getRed() * 255) & 0xFF;
                    newGreen += (int) (color.getGreen() * 255) & 0xFF;
                    newBlue += (int) (color.getBlue() * 255) & 0xFF;
                }

                int noOfColors = colors.size();
                newRed /= noOfColors;
                newGreen /= noOfColors;
                newBlue /= noOfColors;


                Rectangle box = new Rectangle(x + blockSize - 1, y + blockSize - 1,
                        blockSize, blockSize);
                box.setFill(Color.rgb(newRed, newGreen, newBlue));
                newImage.getChildren().add(box);
            }
        }
        return newImage;
    }
}
