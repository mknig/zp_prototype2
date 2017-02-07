/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.image;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import zp_prototype2.IWallpaper;

/**
 *
 * @author Rackor
 */
public interface IImageSelection extends IWallpaperNode {

    void select();

    void unSelect();

    boolean isSelected();
    
    int getIndex();
    void setIndex(int idx);
    
    
    void addListener(IImageSelectionListener listener);

    void removeListener(IImageSelectionListener listener);

    void addTranslationListener(ITranslationListener translationListener);

    void removeTranslationListener(ITranslationListener translationListener);

    void removeFromWallpaper();

    IWallpaperImage getWallpaperImage();

    Point2D getPosition();

    boolean isImageIN();

    void setImageIN(boolean imageIN);

    void setIsHighlighted(boolean isHighlighted);

    boolean getIsHighlighted();

    IWallpaper getWallpaper();

    void setWallpaper(IWallpaper wallpaper);

    String getSnapShotKey(double posX, double posY, double width,double height );

    public String getXXLKey();
}
