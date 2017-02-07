/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.image;

/**
 *
 * @author Pedro
 */
public interface ITranslationListener {
    void imageTranslated(IImageSelection image);
    void imageTranslationEnded(IImageSelection image, double translateXAmount, double translateYAmount);
}
