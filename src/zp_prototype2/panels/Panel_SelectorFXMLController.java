/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import com.jhlabs.image.BorderFilter;
import com.jhlabs.image.ChromeFilter;
import com.jhlabs.image.GlowFilter;
import com.jhlabs.image.PointillizeFilter;
import com.jhlabs.image.WoodFilter;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import zp_prototype2.CursorFactory;
import zp_prototype2.ISelectionManagerListener;
import zp_prototype2.IWallpaper;
import zp_prototype2.MultipleSelectionManager;
import zp_prototype2.Utils;
import zp_prototype2.WallpaperImageEffect;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.image.WallpaperImage;

/**
 *
 * @author Rackor
 */
public class Panel_SelectorFXMLController implements Initializable {

    private IWallpaper wallpaper;
    @FXML
    public Label lbl_select_count;
    @FXML
    public Label lbl_select_count_caption;
    @FXML
    public Label lbl_cmd_deselect_all;
    @FXML
    public Label lbl_select_second;
    @FXML
    Button btn_select_all;
    @FXML
    Label lbl_select_all;

    /**
     * ***********************
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //  listeners = new ArrayList<>();
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();

        //bind...

        lbl_select_count.textProperty().bindBidirectional(((MultipleSelectionManager) ZP_Prototype2.getInstance().getWallpaper().getSelectionManager()).selectedImages_count);

        SimpleStringProperty selectedImages_count = ((MultipleSelectionManager) ZP_Prototype2.getInstance().getWallpaper().getSelectionManager()).selectedImages_count;
        selectedImages_count.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                System.out.println("countSelect.Changed->(ov,t,t1)" + ov + "/" + t + "/" + t1);
                
                int curCount = new Integer(t1).intValue();
                String style = "";
                
                
                 //animation!!!
                    FadeTransition fadeTransition = FadeTransitionBuilder.create()
                            .duration(Duration.seconds(1.0))
                            //.node(lblDialog)
                            .node(lbl_select_count)
                            .fromValue(1)
                            .toValue(0.2)
                            .cycleCount(Timeline.INDEFINITE)
                            .autoReverse(true)
                            .build();
                
                if (curCount != 0) {
                    style = "-fx-text-fill:col_chocolate;";
                    style += " -fx-font-weight: bold;";


                    String lbl_count = style + " -fx-font-size: 16pt;";
                    lbl_select_count.setStyle(lbl_count);


                   
                    fadeTransition.play();


                } else {
                    style = "-fx-text-fill:col_f1;";
                    String lbl_count = style + " -fx-font-size: 12pt;";
                    lbl_select_count.setStyle(lbl_count);
                    
                    fadeTransition.stop();
                }

                lbl_select_count_caption.setStyle(style);
                lbl_cmd_deselect_all.setStyle(style);

                //lbl_cmd_deselect_all.setStyle(t1);

            }
        });

        initI18N();

    }

    public void initI18N() {

        //+++++++++++++++    
        //i18N
        ZP_Properties.i18NMap.put(lbl_select_count_caption.getText(), lbl_select_count_caption.textProperty());
        ZP_Properties.i18NMap.put(lbl_select_all.getText(), lbl_select_all.textProperty());
        ZP_Properties.i18NMap.put(lbl_cmd_deselect_all.getText(), lbl_cmd_deselect_all.textProperty());
        ZP_Properties.i18NMap.put(lbl_select_second.getText(), lbl_select_second.textProperty());
    }

    @FXML
    public void handleSelectROWCOL() {
        System.out.println("handleSelectAll.clicked");
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        wallpaper.getImageAt(2, 2);
    }

    @FXML
    public void handleSelectEachSecond() {
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        ((MultipleSelectionManager) wallpaper.getSelectionManager()).selectEachSecond();
    }

    @FXML
    public void handleSelectAll() {
        System.out.println("handleSelectAll.clicked");
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        ((MultipleSelectionManager) wallpaper.getSelectionManager()).selectAll();
    }

    @FXML
    public void handleUnSelectAll() {
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        ((MultipleSelectionManager) wallpaper.getSelectionManager()).unSelectAll();
    }
//
//
// 
//
//    public void addListener(IImagePanelControllerListener listener) {
//        listeners.add(listener);
//    }
//
//    public void removeListener(IImagePanelControllerListener listener) {
//        listeners.remove(listener);
//    }
}
