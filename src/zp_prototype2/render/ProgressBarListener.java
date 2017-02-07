/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Prototype2;

public class ProgressBarListener {

    private long fileSize = 5;
    private int transferedMegaBytes = 0;
    private ProgressIndicator progressIndicatorUpload = null;

    public ProgressBarListener() {
        super();
    }

    public ProgressBarListener(ProgressIndicator progressIndicatorUpload, long fileSize) {
        this();
        this.fileSize = fileSize;
        this.progressIndicatorUpload = progressIndicatorUpload;
    }

    public void updateTransferred(long transferedBytes) {
        transferedMegaBytes = (int) (transferedBytes / 1048576);

        final ProgressIndicator pi = progressIndicatorUpload;
        final long transfered = transferedBytes;
        Platform.runLater(new Runnable() {
            public void run() {
                ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().primaryStage.getScene();
               
                TextField messageBox = zpScene.getDialogPaneController().getMessageBox();

                double transferedMegaBytes_double = transfered;
                double fileSize_double = fileSize;
                double value = transferedMegaBytes_double / fileSize_double;
             
                String msg = "upload: " + value + " / " + fileSize_double;
                messageBox.setText(msg);
                
                zpScene.getDialogPaneController().getProgressBar().setProgress(value);
               // pi.setProgress(value);
                
            }
        });
        //System.out.println("Transferred: " + transferedMegaBytes + " Megabytes.-->");


    }
}