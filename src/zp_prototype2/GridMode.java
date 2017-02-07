/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.IWallpaperImage;

/**
 *
 * @author Pedro
 */
public class GridMode implements IEditMode{
    IWallpaper wallpaper;
    
    IImageSelection biggestCollisionImage;
    
    public GridMode(IWallpaper wallpaper)
    {
        this.wallpaper = wallpaper;
    }
    
    
    @Override
    public void imageRotated(IWallpaperImage image)
    {
        wallpaper.reArrangeImages();
    }
    
    @Override
    public void imageTranslated(IImageSelection image)
    {
        IImageSelection currentBiggestCollisionImage = wallpaper.checkBiggestColision(image);
        if (currentBiggestCollisionImage != null)
        {
            if (currentBiggestCollisionImage != biggestCollisionImage)
            {
                if (biggestCollisionImage != null) biggestCollisionImage.setIsHighlighted(false);
                biggestCollisionImage = currentBiggestCollisionImage;
                biggestCollisionImage.setIsHighlighted(true);
            }
        }
    }
    
    @Override
    public void imageTranslationEnded(IImageSelection image, double translatedXAmount, double translatedYAmount)
    {
        double absTranslatedXAmount = Math.abs(translatedXAmount);
        double absTranslatedYAmount = Math.abs(translatedYAmount);
        if (absTranslatedXAmount < image.getWidth() / 2d && absTranslatedYAmount < image.getHeight() / 2d)
        {
            Point2D currentPosition = image.getPosition();
            image.setTranslate(currentPosition.getX() - translatedXAmount, currentPosition.getY() - translatedYAmount);
        }
        else
        {
            if (biggestCollisionImage != null)
            {
                int imageIndex = wallpaper.getImageIndex(image);
                int currentBiggestCollisionIndex = wallpaper.getImageIndex(biggestCollisionImage);
                wallpaper.putImageAt(image, currentBiggestCollisionIndex);
                
                if (imageIndex < currentBiggestCollisionIndex)
                {
                    int i = currentBiggestCollisionIndex - 1;
                    IImageSelection currentImage = biggestCollisionImage;
                    while(i >= imageIndex)
                    {
                        IImageSelection tempImage = wallpaper.getImageAt(i);
                        wallpaper.putImageAt(currentImage, i);
                        currentImage = tempImage;
                        --i;
                    }
                }
                else
                {
                    int i = currentBiggestCollisionIndex + 1;
                    IImageSelection currentImage = biggestCollisionImage;
                    while(i <= imageIndex)
                    {
                        IImageSelection tempImage = wallpaper.getImageAt(i);
                        wallpaper.putImageAt(currentImage, i);
                        currentImage = tempImage;
                        ++i;
                    }
                }
                wallpaper.reArrangeImages();
                biggestCollisionImage.setIsHighlighted(false);
            }
            else
            {
                Point2D currentPosition = image.getPosition();
                image.setTranslate(currentPosition.getX() - translatedXAmount, currentPosition.getY() - translatedYAmount);
            }
        }
        
        
    }
    
    @Override
    public String getResourceBundleKey() {
        return "Grid_Mode_Choice";
    }

    @Override
    public void wallpaperSizeChanged() {
        wallpaper.reArrangeImages();
        
    }
    
}
