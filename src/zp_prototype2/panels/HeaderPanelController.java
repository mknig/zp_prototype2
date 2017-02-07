/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import zp_prototype2.IMouseHeaderListener;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.render.TranslationManager;

/**
 *
 * @author Pedro
 */
public class HeaderPanelController implements Initializable {

//    @FXML
//    private ZoomPanelController zoomPanelController;
    @FXML
    BorderPane borderPane_Header;
    ArrayList<IMouseHeaderListener> listeners;
//    @FXML
//    Button closeButton;
//    @FXML
//    Button hidePane;
//@FXML
//    Button btn_toogle_menu;
    @FXML
    Label zpVersion;
    @FXML
    ComboBox cb_Language;
    @FXML
    Label monitor_dimension_caption;
    @FXML
    public Label lbl_dimension_width;
    @FXML
    public Label lbl_dimension_height;
    @FXML
    public Label monitor_dimension_height;
    @FXML
    public Label monitor_dimension_width;
    @FXML
    public Label lbl_Medium_Output;
    @FXML
    public Label medium_output;
    @FXML
    public Label lbl_dimension_output;
    @FXML
    public Label monitor_dimension_output_height;
    @FXML
    public Label monitor_dimension_output_width;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listeners = new ArrayList<>();
        zpVersion.textProperty().set(ZP_Prototype2.getVersion());


//        //click borderPane_Header...
//        borderPane_Header.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                System.out.println("HeaderPanelController.borderPane_Header clicked");
//                ZP_Prototype2.getInstance().imageSelectedPanelController.handleUnSelectAll();
//            }
//        });


        //+++++++++++++++++++
        //bind


        // how to cast????? lbl_dimension_width.textProperty().bind(ZP_Prototype2.getInstance().getWallpaper().width);
        // lbl_dimension_height.textProperty().bind(ZP_Prototype2.getInstance().getWallpaper().height);

        //bind combo item...HOW TODO
        //lbl_dimension_width.textProperty().bind(ZP_Prototype2.getInstance().mainPanelController.widthComboBox.itemsProperty());
        //lbl_dimension_height.textProperty().bind(ZP_Prototype2.getInstance().mainPanelController.heightComboBox.itemsProperty());

        //bind combo....values are set in beginning but not changed...!!!
        //lbl_dimension_width.textProperty().bind(ZP_Prototype2.getInstance().mainPanelController.widthComboBox.getSelectionModel().selectedItemProperty());
        //lbl_dimension_height.textProperty().bind(ZP_Prototype2.getInstance().mainPanelController.heightComboBox.getSelectionModel().selectedItemProperty());


        // set by listener in wallpaper...values are only set if combobox used!
        lbl_dimension_width.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().lbl_dimension_width);
        lbl_dimension_height.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().lbl_dimension_height);
        medium_output.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().medium_output);

        monitor_dimension_output_width.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().lbl_dimension_averrage_output_width);
        monitor_dimension_output_height.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().lbl_dimension_averrage_output_height);

        //+++++++++++++++++++
        //i18N
        // ZP_Properties.i18NMap.put(btn_toogle_menu.getText(), btn_toogle_menu.textProperty());
        ZP_Properties.i18NMap.put(monitor_dimension_caption.getText(), monitor_dimension_caption.textProperty());
        //ZP_Properties.i18NMap.put(monitor_dimension_width.getText(), monitor_dimension_width.textProperty());
        //ZP_Properties.i18NMap.put(monitor_dimension_height.getText(), monitor_dimension_height.textProperty());
        ZP_Properties.i18NMap.put(lbl_Medium_Output.getText(), lbl_Medium_Output.textProperty());
        ZP_Properties.i18NMap.put(lbl_dimension_output.getText(), lbl_dimension_output.textProperty());


    }

    @FXML
    private void handleLogoClick(MouseEvent event) {
        String dic_intro_URL = TranslationManager.translate("dic_intro_URL");
        String starterUrl = ZP_Properties.zpServer + dic_intro_URL;
        ZPScene zpScene = ZP_Prototype2.getInstance().getScene();
        zpScene.getDialogPaneController().setWebViewUrl(starterUrl);
        zpScene.showDialog();
    }

    @FXML
    private void handleShowOut(ActionEvent event) {
        System.out.println("handleShowOUT.clicked");
        ZP_Prototype2.getInstance().getWallpaper().reArrangeImages();
    }

    @FXML
    private void handleCloseAction() {
        System.exit(0);
    }

    @FXML
    private void handleLanguageAction(ActionEvent event) {
//     try {
//
//            if (ZP_Properties.getLang().equals("de")) {
//                ZP_Properties.setLang("en");
//            } else {
//                ZP_Properties.setLang("de");
//            }
//            ZP_Prototype2.getInstance().mainPanel = ZP_Prototype2.getInstance().createMainPanel(ZP_Prototype2.getInstance().getWallpaper(), null);
//            //ZP_Prototype2.getInstance().getContentPanel().ad (ZP_Prototype2.getInstance().getMainPanel());
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        ZP_Properties.updateLanguage("en");


    }

    @FXML
    private void handleUpdate(MouseEvent mouseEvent) throws Exception {
        String zp_update_url = TranslationManager.translate("zp_update_url");
        java.awt.Desktop.getDesktop().browse(new URI(zp_update_url));
        //mouseEvent.consume();
    }

    @FXML
    private void handleCB_LanguageAction(ActionEvent event) throws Exception {
        ZP_Properties.setLang((String) cb_Language.getValue());
        ZP_Properties.updateLanguage((String) cb_Language.getValue());
    }

    @FXML
    private void handleMouseDragged(MouseEvent mouseEvent) {
        for (IMouseHeaderListener listener : listeners) {
            listener.mouseDragged(mouseEvent);
        }
    }

    @FXML
    private void handleMousePressed(MouseEvent mouseEvent) {
        for (IMouseHeaderListener listener : listeners) {
            listener.mousePressed(mouseEvent);
        }
    }

    public void addMouseDraggedListener(IMouseHeaderListener listener) {
        listeners.add(listener);
    }

    public void removeMouseDraggedListener(IMouseHeaderListener listener) {
        listeners.remove(listener);
    }

    @FXML
    public void handleHideAction(ActionEvent event) {

        System.out.println("handleHideAction clicked");


        //border according...
        if (ZP_Properties.getState().equals("design")) {

            if (((BorderPane) ZP_Prototype2.getInstance().getContentPanel()).getRight() == null) {
                //ZP_Prototype2.getInstance().menuAccodion.setVisible(false);
                ((BorderPane) ZP_Prototype2.getInstance().getContentPanel()).setRight(ZP_Prototype2.getInstance().vBoxMenu);//menuAccodion);
            } else {
                //ZP_Prototype2.getInstance().menuAccodion.setVisible(true);
                ((BorderPane) ZP_Prototype2.getInstance().getContentPanel()).setRight(null);
            }
        } else {

//          ZP_Prototype2.getInstance().getWorkspace().setVisible(true);
//          ZP_Prototype2.getInstance().getMainPanel().setVisible(true);
//          ZP_Prototype2.getInstance().getDonePanel().setVisible(true);
            ZP_Properties.setState("design");
        }


//        //stack
//        if (ZP_Properties.getState().equals("design")) {
//            
//            if (ZP_Prototype2.getInstance().getMainPanel().isVisible()) {
//                ZP_Prototype2.getInstance().getMainPanel().setVisible(false);
//                ZP_Prototype2.getInstance().getDonePanel().setVisible(false);
//            } else {
//                ZP_Prototype2.getInstance().getMainPanel().setVisible(true);
//                ZP_Prototype2.getInstance().getDonePanel().setVisible(true);
//            }
//        } else {
//            
//          ZP_Prototype2.getInstance().getWorkspace().setVisible(true);
//          ZP_Prototype2.getInstance().getMainPanel().setVisible(true);
//          ZP_Prototype2.getInstance().getDonePanel().setVisible(true);
//            ZP_Properties.setState("design");
//        }



        //borderPane...
//        if (ZP_Properties.getState().equals("design")) {
//            
//            if (ZP_Prototype2.getInstance().getContentPanel().getLeft() == null) {
//                ZP_Prototype2.getInstance().getContentPanel().setLeft(ZP_Prototype2.getInstance().getMainPanel());
//                ZP_Prototype2.getInstance().getContentPanel().setRight(ZP_Prototype2.getInstance().getDonePanel());
//            } else {
//                ZP_Prototype2.getInstance().getContentPanel().setLeft(null);
//                ZP_Prototype2.getInstance().getContentPanel().setRight(null);
//            }
//        } else {
//            
//            ZP_Prototype2.getInstance().getContentPanel().setCenter(ZP_Prototype2.getInstance().getWorkspace());
//            ZP_Prototype2.getInstance().getContentPanel().setLeft(ZP_Prototype2.getInstance().getMainPanel());
//            ZP_Prototype2.getInstance().getContentPanel().setRight(ZP_Prototype2.getInstance().getDonePanel());
//            ZP_Properties.setState("design");
//        }
    }
}
