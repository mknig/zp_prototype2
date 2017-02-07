/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.util.List;
import javafx.beans.property.ReadOnlyDoubleProperty;
import zp_prototype2.image.IImageSelection;
import javafx.scene.Node;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.panels.Mode_Grid_FXMLController;

/**
 *
 * @author Rackor
 */
public interface IWallpaper {

    void set_Grid_Controller(Mode_Grid_FXMLController controller);

    Mode_Grid_FXMLController getGridController();

    boolean isBestFit_check();

    void setBestFit_check(boolean bestFit);

    boolean isLoadingFiles();

    void setLoadingFiles(boolean bestFit);

    public boolean getIsLoadingImageReady();

    void addBorder();

    ReadOnlyDoubleProperty widthProperty();

    ReadOnlyDoubleProperty heightProperty();

    double getWidth();

    void setWidth(double width);

    double getHeight();

    void setHeight(double height);

    IImageSelection addImage(IImageSelection image);

    IImageSelection addImage(IWallpaperImage image);

    void copyImage(IImageSelection image);
    
    void removeImage(IImageSelection image);

    void removeImageAtIndex(int index);

    void removeErrorImages();

    void resetImages();

    IImageSelection getImageAt(int index);

    public IImageSelection getImageAt(int col,int row) ;
    
    int getImageIndex(IImageSelection imageSelection);

    void putImageAt(IImageSelection imageSelection, int index);

    List<IImageSelection> getImages();

    SelectionManager getSelectionManager();

    void addListener(IWallpaperListener listener);

    void removeListener(IWallpaperListener listener);

    void reArrangeImages();

    IEditMode getEditMode();

    void setEditMode(IEditMode editMode);

    List<IEditMode> getEditingModes();

    IImageSelection checkBiggestColision(IImageSelection image);

    ImagesOrientation getImagesOrientation();

    void setImagesOrientation(ImagesOrientation orientation);

    int getRowNumber();

    void setRowNumber(int rowNumber);

    double getCurrentImageHeight();

    double getCurrentImageWidth();

    Node getNodeRepresentation();
}
