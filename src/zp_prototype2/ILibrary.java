/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import javafx.scene.image.Image;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.image.WallpaperImage;

/**
 *
 * @author Pedro
 */
public interface ILibrary {
    void addImage(WallpaperImage image);
    
    void removeDraggedImage();
    IWallpaperImage getDragAndDropContent();
    void dragAndDropFinished(IWallpaperImage switchedImage);
}
