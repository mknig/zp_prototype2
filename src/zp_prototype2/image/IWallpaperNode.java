/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.image;

import javafx.scene.Node;

/**
 *
 * @author Pedro
 */
public interface IWallpaperNode {
    Node getNodeRepresentation();    
    
    double getWidth();
    double getHeight();
        
    void setXTranslation(double x);   
    void setYTranslation(double y);
    void setTranslate(double x, double y);
}
