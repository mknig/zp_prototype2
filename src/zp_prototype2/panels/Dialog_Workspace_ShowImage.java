/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import zp_prototype2.Workspace;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Prototype2;

/**
 *
 * @author admin
 */
public class Dialog_Workspace_ShowImage implements Runnable {

    Image image = null;
    String fileName = "";

    public Dialog_Workspace_ShowImage(Image img, String filename) {
        this.image = img;
        this.fileName = filename;
    }

    public Dialog_Workspace_ShowImage(BufferedImage img, String filename) {
        this.image = SwingFXUtils.toFXImage(img, null);
        this.fileName = filename;
    }

    @Override
    public void run() {
        System.out.println("Dialog_Workspace_ShowImage.run->show Image...");

        //  this.image=new Image("http://static.tapetenagentur.de/rendered/jpg/height-443/width-400/scaletype-3/400xNx1155326723.jpg.pagespeed.ic.M3fAtFV61k.jpg");
        ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().primaryStage.getScene();
        zpScene.getDialogPaneController().getDiag_ImageView().visibleProperty().set(true);
        zpScene.getDialogPaneController().getDiag_ImageView().setImage(image);

        //ZP_Prototype2.getInstance().fileController.loadImage(fileName);

    }
}
