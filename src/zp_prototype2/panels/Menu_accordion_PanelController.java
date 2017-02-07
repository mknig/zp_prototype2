/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import zp_prototype2.MultipleSelectionManager;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class Menu_accordion_PanelController implements Initializable {

    @FXML
    Accordion accordion_menu;
    @FXML
    Pane acc_mainPanel;
    @FXML
    TitledPane titled_file;
    @FXML
    TitledPane titled_measure;
    @FXML
    TitledPane titled_layout;
    @FXML
    TitledPane titled_checkout;
    @FXML
    public TitledPane titled_imageEdit;
    @FXML
    ScrollPane imageEdit_ScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
        
        ZP_Prototype2.getInstance().mainPanel = acc_mainPanel;

        accordion_menu.setExpandedPane(null);
        
        
//        accordion_menu.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                System.out.println("ImagePanel ->mouse exited..");
//                ((MultipleSelectionManager) ZP_Prototype2.getInstance().getWallpaper().getSelectionManager()).unSelectAll();
//            }
//        });
        
        
        //+++++++++++++++++++
        //step1
        Node open1 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step1_o).build();

        Node closed1 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step1_c).build();

        titled_file.graphicProperty().bind(
                Bindings
                .when(titled_file.expandedProperty())
                .then(open1)
                .otherwise(closed1));

//    no effect?    titled_file.getContent().setLayoutX(10);
//        titled_file.getContent().setLayoutY(10);
        
        

        //+++++++++++++++++++
        //step2
        Node open2 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step2_o).build();

        Node closed2 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step2_c).build();

        titled_measure.graphicProperty().bind(
                Bindings
                .when(titled_measure.expandedProperty())
                .then(open2)
                .otherwise(closed2));

        //+++++++++++++++++++
        //step3
        Node open3 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step3_o).build();

        Node closed3 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step3_c).build();

        titled_layout.graphicProperty().bind(
                Bindings
                .when(titled_layout.expandedProperty())
                .then(open3)
                .otherwise(closed3));


        //+++++++++++++++++++
        //step4
        Node open5 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step4_o).build();

        Node closed5 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step4_c).build();

        titled_imageEdit.graphicProperty().bind(
                Bindings
                .when(titled_imageEdit.expandedProperty())
                .then(open5)
                .otherwise(closed5));


        //+++++++++++++++++++
        //step5
        Node open4 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step5_o).build();

        Node closed4 = HBoxBuilder.create().spacing(5).children(
                ZP_Properties.getInstance().step5_c).build();

        titled_checkout.graphicProperty().bind(
                Bindings
                .when(titled_checkout.expandedProperty())
                .then(open4)
                .otherwise(closed4));


        //+++++++++++++++    
        //i18N
        ZP_Properties.i18NMap.put(titled_measure.getText(), titled_measure.textProperty());
        ZP_Properties.i18NMap.put(titled_file.getText(), titled_file.textProperty());
        ZP_Properties.i18NMap.put(titled_layout.getText(), titled_layout.textProperty());
        ZP_Properties.i18NMap.put(titled_checkout.getText(), titled_checkout.textProperty());
        ZP_Properties.i18NMap.put(titled_imageEdit.getText(), titled_imageEdit.textProperty());

        //+++++++++++++++    
        //setStyle for titledPane
       // titled_file.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
       //add extra css... titled_file.getStyleClass().add("connectivity");

    }

    public ScrollPane getImageEdit_ScrollPane() {
        return imageEdit_ScrollPane;
    }

    public void setImageEdit_ScrollPane(Pane imageEdit_Pane) {



        this.imageEdit_ScrollPane.setContent(imageEdit_Pane);
    }

    public Accordion getAccordion_menu() {
        return accordion_menu;
    }

    public Pane getAcc_mainPanel() {
        return acc_mainPanel;
    }

    public void setAcc_mainPanel(Pane acc_mainPanel) {
        this.acc_mainPanel = acc_mainPanel;
    }

    public TitledPane getTitled_file() {
        return titled_file;
    }

    public void setTitled_file(TitledPane titled_file) {
        this.titled_file = titled_file;
    }

    public TitledPane getTitled_measure() {
        return titled_measure;
    }

    public void setTitled_measure(TitledPane titled_measure) {
        this.titled_measure = titled_measure;
    }

    public TitledPane getTitled_layout() {
        return titled_layout;
    }

    public void setTitled_layout(TitledPane titled_layout) {
        this.titled_layout = titled_layout;
    }

    public TitledPane getTitled_checkout() {
        return titled_checkout;
    }

    public void setTitled_checkout(TitledPane titled_checkout) {
        this.titled_checkout = titled_checkout;
    }

    public TitledPane getTitled_imageEdit() {
        return titled_imageEdit;
    }

    public void setTitled_imageEdit(TitledPane titled_imageEdit) {
        this.titled_imageEdit = titled_imageEdit;
    }
}
