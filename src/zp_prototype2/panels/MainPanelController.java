/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import zp_prototype2.IWallpaper;
import zp_prototype2.WallpaperDimensions;
import zp_prototype2.ZP_Medium;
import zp_prototype2.ZP_MediumManager;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;

/**
 *
 * @author Rackor
 */
public class MainPanelController implements Initializable {

    public static IWallpaper wallpaper;
    private ZP_MediumManager mediumMGR = ZP_MediumManager.getInstance();
    private final File DEFAULT_FILECHOOSER_DIR = new JFileChooser().getFileSystemView().getDefaultDirectory();
    private final int DEFAULT_TARGET_HEIGHT = 1739;
    private ResourceBundle resourceBundle;
    List<File> lastChoosenFiles;
    File lastChoosenDir;
    Stage stage;
    boolean isInitializing = false;
    @FXML
    AnchorPane pane_Main;
    @FXML
    public ComboBox cb_Medium;
    @FXML
    public Pane pane_width_height;
    @FXML
    public ComboBox widthComboBox;
    @FXML
    public ComboBox heightComboBox;
    @FXML
    public Pane pane_fixedsize;
    @FXML
    public ComboBox fixedsizeComboBox;
    @FXML
    Label lbl_caption_measure;
    @FXML
    Label lbl_dimension_text;
    @FXML
    Label lbl_medium;
    @FXML
    Label lbl_width;
    @FXML
    Label lbl_height;
    @FXML
    Label lbl_fixedsize_text;
    @FXML
    Label lbl_price_caption;
    @FXML
    Label lbl_price;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.resourceBundle = resourceBundle;

        isInitializing = true;//avoid setWallpaperSize ERROR at init...


        //medium
        ZP_MediumManager mediumMGR = ZP_MediumManager.getInstance();

        List<String> mediums = mediumMGR.getNameMediums();
        cb_Medium.getItems().addAll(mediums);

        //width
        widthComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                handleWidthComboBoxAction(null);
            }
        });

        //height
        heightComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                handleHeightComboBoxAction(null);
            }
        });

        //height
        fixedsizeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                handleFixedSizeBoxAction(null);
            }
        });

        //set default...call handleCBAction...
        cb_Medium.getSelectionModel().select(mediumMGR.getDefaultMedium().getName());

        //bind price
        lbl_price.textProperty().bind(ZP_Prototype2.getInstance().getWallpaper().price);


        isInitializing = false;


//        //????
//        ZP_Pane_Controller rootController = new ZP_Pane_Controller();
//        rootController.setDrag(pane_Main);


        //+++++++++++++++++++
        //i18N
        ZP_Properties.i18NMap.put(lbl_caption_measure.getText(), lbl_caption_measure.textProperty());
        ZP_Properties.i18NMap.put(lbl_dimension_text.getText(), lbl_dimension_text.textProperty());
        ZP_Properties.i18NMap.put(lbl_medium.getText(), lbl_medium.textProperty());
        ZP_Properties.i18NMap.put(lbl_width.getText(), lbl_width.textProperty());
        ZP_Properties.i18NMap.put(lbl_height.getText(), lbl_height.textProperty());
        ZP_Properties.i18NMap.put(lbl_fixedsize_text.getText(), lbl_fixedsize_text.textProperty());

        ZP_Properties.i18NMap.put(lbl_price_caption.getText(), lbl_price_caption.textProperty());


    }

    @FXML
    private void handleMediumAction(ActionEvent event) {



//            //marshall defaultObject....
//            ZP_Medium mediumMarshall = new ZP_Medium();
//            try {
//                mediumMarshall.marshall();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


        //mediumMGR
        ZP_Medium medium = mediumMGR.getMedium(cb_Medium.getSelectionModel().getSelectedItem());
        ZP_MediumManager.getInstance().setSelectedMedium(medium);

        //calc sizes...
        List sizes = new ArrayList();
        if (!medium.isSizeFixed()) {

            pane_fixedsize.visibleProperty().set(false);
            pane_width_height.visibleProperty().set(true);
            pane_fixedsize.toFront();


            double minSquare = medium.getMin_square();
            for (int i = 1; i < 10; i++) {
                double x = minSquare * i;
                sizes.add("" + x);
            }

            widthComboBox.getItems().setAll(sizes);
            heightComboBox.getItems().setAll(sizes);

            //++++++++END

            //sets current size...not 0!!!
            heightComboBox.getSelectionModel().select("" + medium.getDefaultHeight());
            widthComboBox.getSelectionModel().select("" + medium.getDefaultWidth());

            //handleAction...
            handleWidthComboBoxAction(null);
            handleHeightComboBoxAction(null);

        } else {

            pane_width_height.visibleProperty().set(false);
            pane_fixedsize.visibleProperty().set(true);
            pane_fixedsize.toFront();

            Iterator it = medium.getFixedSizes().keySet().iterator();
            while (it.hasNext()) {
                Object object = it.next();
                String format = "" + medium.getFixedSizes().get(object);
                sizes.add(format);
//                String[] widht_height = format.split(Pattern.quote("*"));
//                System.out.println(Arrays.toString(widht_height));
//                //get Medium.XStream ->unmarshall to Object...
//                for (int i = 0; i < widht_height.length; i++) {
//                    try {
//                        //sizes.add(widht_height[i].trim());
//                        sizes.add(format);
//                    } catch (Exception e) {
//                        //mediums.add("NA");
//                    }
//                }

            } 

            fixedsizeComboBox.getItems().setAll(sizes);
            //fixedsizeComboBox.getSelectionModel().select("" + medium.getDefaultHeight());
            fixedsizeComboBox.getSelectionModel().select("" + "50*50");

            handleFixedSizeBoxAction(null);
        }

        ZP_Prototype2.getInstance().getWallpaper().medium_output.setValue((String) cb_Medium.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void handleWidthComboBoxAction(ActionEvent event) {
        if (!isInitializing) {
            double widthInPixels = WallpaperDimensions.convertToPixels((String) widthComboBox.getSelectionModel().getSelectedItem());
            wallpaper.setWidth(widthInPixels);
        }
    }

    @FXML
    private void handleHeightComboBoxAction(ActionEvent event) {
        if (!isInitializing) {
            double heightInPixels = WallpaperDimensions.convertToPixels((String) heightComboBox.getSelectionModel().getSelectedItem());
            wallpaper.setHeight(heightInPixels);

        }
    }

    @FXML
    private void handleFixedSizeBoxAction(ActionEvent event) {
        if (!isInitializing) {
            String format = "" + (String) fixedsizeComboBox.getSelectionModel().getSelectedItem();
            String[] widht_height = format.split(Pattern.quote("*"));
            System.out.println(Arrays.toString(widht_height));
            double heightInPixels = WallpaperDimensions.convertToPixels((String) widht_height[0]);
            double widthInPixels = WallpaperDimensions.convertToPixels((String) widht_height[1]);
            wallpaper.setWidth(widthInPixels);
            wallpaper.setHeight(heightInPixels);
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setWallpaper(IWallpaper wallpaper) {
        this.wallpaper = wallpaper;

    }
}
