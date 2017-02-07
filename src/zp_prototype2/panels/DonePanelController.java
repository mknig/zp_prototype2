/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.FreeMode;
import zp_prototype2.GridMode;
import zp_prototype2.ZP_Properties;
import zp_prototype2.render.BuildZoopraxiController;

/**
 *
 * @author Michael
 */
public class DonePanelController extends ZP_Pane_Controller implements Initializable {

    @FXML
    AnchorPane pane_Done;
    @FXML
    Accordion accordionModes;
    @FXML
    Label price;
    @FXML
    Label lbl_checkout_caption;
    @FXML
    Label lbl_checkout_text;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

       
        //ZP_Pane_Controller rootController = new ZP_Pane_Controller();
        //rootController.setDrag(pane_Done);


        //listeners = new ArrayList<>();
        //price.setText(""+ZP_Prototype2.getInstance().getWallpaper().price.getValue());
        price.textProperty().bindBidirectional(ZP_Prototype2.getInstance().getWallpaper().price);


        //+++++++++++++++++++
        //i18N
        ZP_Properties.i18NMap.put(lbl_checkout_caption.getText(), lbl_checkout_caption.textProperty());
        ZP_Properties.i18NMap.put(lbl_checkout_text.getText(), lbl_checkout_text.textProperty());


    }

    @FXML
    private void handleDoneAction(ActionEvent event) {
        BuildZoopraxiController.buildZoopraxi();
    }

    @FXML
    private void handleTestAction(ActionEvent event) {
//        XStream xstream = new XStream();
//        String xml = xstream.toXML(ZP_Prototype2.getInstance().getWallpaper());
//        System.out.println(xml);
//        XMLEncoder enc = null;
//        try {
//            enc = new XMLEncoder(new FileOutputStream("x:/tmp/zp_serialzied.xml"));
//           // enc.writeObject("Today");
//           // enc.writeObject(new Date());
//            enc.writeObject(ZP_Prototype2.getInstance().getWallpaper());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (enc != null) {
//                enc.close();
//            }
//        }
//        try {
//            FileOutputStream file = new FileOutputStream("x:/tmp/zp_serialzied.data");
//            ObjectOutputStream o = new ObjectOutputStream(file);
//           o.writeObject(ZP_Prototype2.getInstance().getWallpaper());
//            // o.writeObject("Today");
//           // o.writeObject(new Date());
//            o.close();
//        } catch (IOException e) {
//            System.err.println(e);
//        }
////        Task task1 = new Task<Void>() {
////            @Override
////            public Void call() {
////                Platform.runLater(new Runnable() {
////                    public void run() {
//        try {
//
//            if (ZP_Properties.getLang().equals("de")) {
//                ZP_Properties.setLang("en");
//            } else {
//                ZP_Properties.setLang("de");
//            }
//            ZP_Prototype2.getInstance().mainPanel = ZP_Prototype2.getInstance().createMainPanel(ZP_Prototype2.getInstance().getWallpaper(), null);
//            ZP_Prototype2.getInstance().getContentPanel().setLeft(ZP_Prototype2.getInstance().getMainPanel());
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
////                    }
////                });
////                return null;
////            }
////        };//end TASK
////         new Thread(task1).start();
    }

    public Label getPrice() {
        return price;
    }

    public void setPrice(Label price) {
        this.price = price;
    }
}//end Class
