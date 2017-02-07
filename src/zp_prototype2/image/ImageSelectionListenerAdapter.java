/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.image;

import javafx.scene.input.MouseEvent;

/**
 *
 * @author Rackor
 */
public class ImageSelectionListenerAdapter implements IImageSelectionListener{

    @Override
    public void mousePressed(IImageSelection image, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(IImageSelection image, boolean wasImageMoved, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseClicked(IImageSelection image, MouseEvent mouseEvent) {
    }
    
    @Override
    public void deleted(IImageSelection image) {
    }

}
