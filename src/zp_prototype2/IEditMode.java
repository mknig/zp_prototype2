/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.IWallpaperImage;

/**
 *
 * @author Pedro
 */
public interface IEditMode {

    void imageRotated(IWallpaperImage image);
    
    void imageTranslated(IImageSelection image);
    void imageTranslationEnded(IImageSelection image, double translatedXAmount, double translatedYAmount);
    
    void wallpaperSizeChanged();
    
    String getResourceBundleKey();
}
