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
public interface IImageSelectionListener {
    void mousePressed(IImageSelection image, MouseEvent mouseEvent);
    void mouseReleased(IImageSelection image, boolean wasImageMoved, MouseEvent mouseEvent);
    void mouseClicked(IImageSelection image, MouseEvent mouseEvent);
    void deleted(IImageSelection image);
}
