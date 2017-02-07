/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Pedro
 */
public enum ImagesOrientation {
    HORIZONTAL("Horizontal_Orientation_Choice"), 
    VERTICAL("Vertical_Orientation_Choice");
    
    private String resourceBundleKey;
    
    static Locale Locale = new Locale("en", "GB");
    static ResourceBundle Bundle = ResourceBundle.getBundle("zp_prototype2/resource/text", Locale);
            
    ImagesOrientation(String resourceBundleKey)
    {
        this.resourceBundleKey = resourceBundleKey;
        
    }
    
    public String toString() {
        return Bundle.getString(resourceBundleKey);
    } 
    
    public static ImagesOrientation getImagesOrientationFromString(String stringRepresentation)
    {
        for(ImagesOrientation imageOrientation : values())
            if(imageOrientation.toString().equals(stringRepresentation)) return imageOrientation;
        return null;
    }
}
