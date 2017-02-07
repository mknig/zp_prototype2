/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.panels.Panel_ImageMonitorFXMLController;
import zp_prototype2.panels.Panel_SelectorFXMLController;

/**
 *
 * @author Pedro
 */
public class MultipleSelectionManager extends SelectionManager {

    //ArrayList<IImageSelection> selectedImages;
    public static ObservableList<IImageSelection> selectedImages;
    public SimpleStringProperty selectedImages_count = new SimpleStringProperty("0");

    public MultipleSelectionManager(IWallpaper wallpaper) {
        super(wallpaper);
        initNewImageList();// new ArrayList<>();
        //selectedImages =(ArrayList<IImageSelection>)Collections.synchronizedList(new ArrayList());
    }

    public ObservableList initNewImageList() {
        selectedImages = FXCollections.observableList(Collections.synchronizedList(new ArrayList()));
        selectedImages_count.setValue("" + selectedImages.size());
        selectedImages.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                // Panel_ImageMonitorFXMLController.lbl_Count_Image_Loaded.setText("" + selectedImages.size());
                selectedImages_count.setValue("" + selectedImages.size());
                if (selectedImages.size() != 0) {
                    ZP_Prototype2.getInstance().imageSelectedPanelController.lbl_noImageSelected.visibleProperty().set(false);
                } else {
                    ZP_Prototype2.getInstance().imageSelectedPanelController.lbl_noImageSelected.visibleProperty().set(true);
                }
            }
        });

        return selectedImages;

    }

    public void selectAll() {
        unselectAllSelected();
        for (IImageSelection imageSelection : wallpaper.getImages()) {
            internalSelect(imageSelection, true);
        }
    }

     public void selectEachSecond() {
        unselectAllSelected();
        int count=0;
        for (IImageSelection imageSelection : wallpaper.getImages()) {
            count++;
            if(count%2==0){
                internalSelect(imageSelection, true);
            }
        }
    }
     
    
    
    public void unSelectAll() {
        unselectAllSelected();
        onSelectionChanged(null);
    }

    @Override
    protected void handleMouseReleasedOnImage(IImageSelection image, boolean wasImageMoved, MouseEvent mouseEvent) {
        internalSelect(image, checkIsMultipleSelection(mouseEvent));
    }

    private void internalSelect(IImageSelection image, boolean isMultipleSelection) {

        //deselect if already selected
        if (image.isSelected()) {
            image.unSelect();
            selectedImages.remove(image);
            onSelectionChanged(null);
            return;
        }

        // if (!isMultipleSelection) {
        //     unselectAllSelected();
        // }

        addToSelectionList(image);
        image.select();
        onSelectionChanged(image);
    }

    
    private boolean checkIsMultipleSelection(MouseEvent mouseEvent) {
        return (mouseEvent.getButton() == MouseButton.PRIMARY 
                && mouseEvent.isControlDown()) 
                || mouseEvent.getButton() == MouseButton.SECONDARY;
    }

    private void addToSelectionList(IImageSelection image) {
        System.out.println("add 2 selection->" + image.getWallpaperImage().getFileURI());
        selectedImages.add(image);
    }

    private void unselectAllSelected() {
        for (IImageSelection imageSelection : selectedImages) {
            imageSelection.unSelect();
            //selectedImages.remove(imageSelection);
        }
        selectedImages = initNewImageList();//new ArrayList<>();
    }

    public ObservableList<IImageSelection> getSelectedImages() {
        // public ArrayList<IImageSelection> getSelectedImages() {
        return selectedImages;
    }
}
