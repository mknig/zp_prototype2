/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class Panel_ImageMonitorFXMLController implements Initializable {

    @FXML
    public static Label lbl_Count_Image_Loaded;
    @FXML
    public Label lbl_Count_Image_Loaded_Ready;
    @FXML
    public Label lbl_Count_Image_IN;
    @FXML
    public Label lbl_Count_Image_OUT;
    @FXML
    public RadioButton radio_ShowOut;
    @FXML
    public Label lbl_image_load;
    @FXML
    public Label lbl_file_in;
    @FXML
    public Label lbl_file_out;
  

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        //bind wallpaper.images...
        lbl_Count_Image_Loaded_Ready.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().countImageLoadedReady);
        lbl_Count_Image_Loaded.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().countImageLoaded);
        lbl_Count_Image_IN.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().countImageLoadedIN);
        lbl_Count_Image_OUT.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().countImageLoadedOUT);
        radio_ShowOut.selectedProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().showOut);

        //+++++++++++++++    
        //i18N
        ZP_Properties.i18NMap.put(lbl_image_load.getText(), lbl_image_load.textProperty());
        ZP_Properties.i18NMap.put(radio_ShowOut.getText(), radio_ShowOut.textProperty());
        ZP_Properties.i18NMap.put(lbl_file_in.getText(), lbl_file_in.textProperty());
        ZP_Properties.i18NMap.put(lbl_file_out.getText(), lbl_file_out.textProperty());

    }

    @FXML
    private void handleShowOut(ActionEvent event) {
        System.out.println("handleShowOUT.clicked");
        ZP_Prototype2.getInstance().getWallpaper().reArrangeImages();
    }
}
