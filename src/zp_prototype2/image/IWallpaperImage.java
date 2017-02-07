/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.image;

import java.net.URI;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Rackor
 */
public interface IWallpaperImage extends IWallpaperNode{
    
    public ImageView getImageView();
    
    
    
    void setIsProportionsConstrained(boolean isConstrained);
    boolean getIsProportionsConstrained();
    
    Image getImage();
    
    void setWidthInLocal(double width);
    void setHeightInLocal(double height);
    void setWidth(double width);
    void setHeight(double height);
    void setFitWidth(double width);
    void setFitHeight(double height);
    public double getWidth_IV() ;
    public double getHeight_IV() ;
    public Point2D getPosition() ;
    
    
    ObjectProperty<Rectangle2D> viewportProperty();
    void setViewport(Rectangle2D viewport);
    Rectangle2D getViewport();
    
    
    boolean isOrientationHorizontal();
    boolean isOrientationVertical();

    public URI getFileURI();
  
    
    void cropByPercentage(double percentage);
    void crop(double horizontalCropAmount, double verticalCropAmount);
    
    void cropHorizontally(double cropSize);
    void cropVertically(double cropSize);
    
    void moveViewport(double x, double y);
    void setCustomUserViewport(Rectangle2D viewport);
    
    void resetViewPortAndSize();
    
   //void addBorder(double horizontalCropAmount, double verticalCropAmount);
    public void addBorder(double horizontalBorder, double verticalBorder,double arcHeight,double arcWidth);
    public void addBorderX();
    public double getBorderSizeHorizontal();
    public double getBorderSizeVertical();

   
    
   
    
    void rotateCW();
    void rotateCCW();
    double getRotation();
    
    void flipX();
    void flipY();
    
    void addListener(IWallpaperImageListener listener);
    void removeListener(IWallpaperImageListener listener);
    void addDimensionListener(IDimensionListener dimensionListener);
    void removeDimensionListener(IDimensionListener dimensionListener);
}
