/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import zp_prototype2.IWallpaper;
import zp_prototype2.ImagesOrientation;
import zp_prototype2.Wallpaper;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.image.IImageSelection;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class Mode_Grid_FXMLController implements Initializable {

    public static IWallpaper wallpaper;
    boolean isUpdatingSlider;
    @FXML
    ComboBox<String> orientationComboBox;
    @FXML
    public ComboBox<String> rowNumberComboBox;
    @FXML
    Slider sliderBorder;
    @FXML
    Slider sliderCrop;
    @FXML
    public CheckBox checkBox_best_fit;
    @FXML
    Label lbl_number_row_col;
    @FXML
    Label lbl_orientation;
    @FXML
    Label lbl_frame;
    @FXML
    Label lbl_layout_caption;
    @FXML
    Label lbl_crop_all;
    @FXML
    Label lbl_orientation_text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // this.resourceBundle = resourceBundle;
//        isInitializing = true;
//        widthComboBox.getItems().addAll(Arrays.asList(WallpaperDimensions.DIMENSIONS));
//        widthComboBox.getSelectionModel().select(WallpaperDimensions.DEFAULT_WIDTH);
//        heightComboBox.getItems().addAll(Arrays.asList(WallpaperDimensions.DIMENSIONS));
//        heightComboBox.getSelectionModel().select(WallpaperDimensions.DEFAULT_HEIGHT);
//        isInitializing = false;

        setWallpaper(ZP_Prototype2.getInstance().getWallpaper());
        wallpaper.set_Grid_Controller(this);


        //orientation...
        for (ImagesOrientation imagesOrientation : ImagesOrientation.values()) {
            orientationComboBox.getItems().add(imagesOrientation.toString());
        }

        //++++++++++++++++++++++
        //rows/cols
        rowNumberComboBox.getSelectionModel().select(ZP_Prototype2.getInstance().getWallpaper().ssP_RowNumber.getValue());

        //+++++++++++++++++
        //sliderBorder
        //isUpdatingSlider = false;
        //numberOfZoomSteps = 15;
        sliderBorder.setSnapToTicks(true);
//        sliderBorder.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
//                // wallpaper.reArrangeImages();
//                wallpaper.addBorder();
//                if (!newValue && !isUpdatingSlider) {
//                    System.out.println("valueChangingProperty()...->" + sliderBorder.getValue());
//                }
//            }
//        });

        
        
        
        
        sliderBorder.setSnapToTicks(true);
        sliderBorder.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {         // wallpaper.reArrangeImages();
                wallpaper.addBorder();
            }
        });
       //bind...
        sliderBorder.valueProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().border);
       
        
        
        
        //++++++++++++++++++++++
        //slider crop            
        sliderCrop.setSnapToTicks(true);
        sliderCrop.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
                if (!newValue && !isUpdatingSlider) {
                    System.out.println("valueChangingProperty()...->" + sliderCrop.getValue());
                }
            }
        });
        sliderCrop.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {

                if (!sliderCrop.isValueChanging() && !isUpdatingSlider) {
                    System.out.println("valueProperty...->" + sliderCrop.getValue());
                }

                List<IImageSelection> listImageSelection = wallpaper.getImages();
                for (IImageSelection curImageSelection : listImageSelection) {

                    //  curImageSelection.setBorder(sliderBorder.getValue());

                    //TEST BORDER!!!
                    //curImageSelection.getWallpaperImage().resetViewPortAndSize();
                    //double value=sliderBorder.getValue()/-100;

                    double value = new Double("" + oldValue).doubleValue() - new Double("" + newValue).doubleValue();//newValue -oldValue;
                    //System.out.println("crop...->" + value); 
                    curImageSelection.getWallpaperImage().crop(value, value);
                    //curImageSelection.getWallpaperImage().crop(new Double(""+newValue).doubleValue(), new Double(""+newValue).doubleValue());
                    //curImageSelection.getWallpaperImage().addBorder(new Double(""+newValue).doubleValue(), new Double(""+newValue).doubleValue());

                }
            }
        });


        //+++++++++++++++++++
        //i18N
        ZP_Properties.i18NMap.put(lbl_number_row_col.getText(), lbl_number_row_col.textProperty());
        ZP_Properties.i18NMap.put(lbl_frame.getText(), lbl_frame.textProperty());
        ZP_Properties.i18NMap.put(lbl_layout_caption.getText(), lbl_layout_caption.textProperty());
        ZP_Properties.i18NMap.put(checkBox_best_fit.getText(), checkBox_best_fit.textProperty());
        ZP_Properties.i18NMap.put(lbl_crop_all.getText(), lbl_crop_all.textProperty());
        ZP_Properties.i18NMap.put(lbl_orientation.getText(), lbl_orientation.textProperty());
        ZP_Properties.i18NMap.put(lbl_orientation_text.getText(), lbl_orientation_text.textProperty());
  
    }

    @FXML
    private void handleOrientationComboBoxAction(ActionEvent event) {
        wallpaper.setImagesOrientation(ImagesOrientation.getImagesOrientationFromString(orientationComboBox.getValue()));
    }

    @FXML
    private void handleRowNumberComboBoxAction(ActionEvent event) {
        //  wallpaper.setRowNumber(Integer.valueOf(rowNumberComboBox.getValue()));
        ((Wallpaper) wallpaper).ssP_RowNumber.setValue(rowNumberComboBox.getValue());
    }

    @FXML
    private void handleRowNumberComboBoxAction_MouseClicked(MouseEvent event) {
        checkBox_best_fit.selectedProperty().setValue(false);
    }

    @FXML
    private void handleCheckBox_Best_FitAction(ActionEvent event) {
        if (checkBox_best_fit.isSelected()) {
            ((Wallpaper) wallpaper).reArrangeImages();
        }

    }

    public void setWallpaper(IWallpaper wallpaper) {
        this.wallpaper = wallpaper;
        orientationComboBox.getSelectionModel().select(wallpaper.getImagesOrientation().toString());
        // rowNumberComboBox.getSelectionModel().select("" + wallpaper.getRowNumber());
    }
}
