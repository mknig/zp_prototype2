/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import zp_prototype2.image.IImageSelection;

/**
 *
 * @author Rackor
 */
public interface ISelectionManagerListener {
    void selectionChanged(IImageSelection lastSelectedImage);
    void mouseReleasedOnImage(IImageSelection image, boolean wasMoved);
}
