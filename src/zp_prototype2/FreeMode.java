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
public class FreeMode implements IEditMode{

    @Override
    public void imageRotated(IWallpaperImage image)
    {
        
    }

    @Override
    public void imageTranslated(IImageSelection image)
    {
        
    }
    
    @Override
    public void imageTranslationEnded(IImageSelection image, double translatedXAmount, double translatedYAmount){
    }
    
    @Override
    public void wallpaperSizeChanged() {
        
    }
    
    @Override
    public String getResourceBundleKey() {
        return "Free_Mode_Choice";
    }
}
