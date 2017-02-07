/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotResult;
import javafx.scene.image.WritableImage;
import javafx.util.Callback;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.ImageSelection;
import zp_prototype2.image.WallpaperImage;

/**
 *
 * @author admin
 */
public class BuildZP_RunLaterV2 implements Runnable {

    IImageSelection wpSelection;
    WallpaperImage wpImage;
    WallpaperImage wpImage_XXL;
    double scaleFactor;
    double x;
    double y;
    double width;
    double height;

    BuildZP_RunLaterV2(IImageSelection wpSelection, WallpaperImage wpImage, WallpaperImage wpImage_XXL, double x, double y, double width, double height) {
        this.wpSelection = wpSelection;
        this.wpImage = wpImage;
        this.wpImage_XXL = wpImage_XXL;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
//        this.x = Math.round(x);
//        this.y = Math.round(y);
//        this.width = Math.round(width);
//        this.height = Math.round(height);
    }

//    public void onException(Exception e) {
//        System.out.println("Exception SnapshotResult");
//        e.printStackTrace();
//    }
    
    public void run() {

        //++++++++++++++
        //excute snapshot and save...
        Callback<SnapshotResult, Void> processSnapshotCallback = new Callback<SnapshotResult, Void>() {
            @Override
            public Void call(SnapshotResult result) throws RuntimeException {
                try {

                     ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().primaryStage.getScene();
                    
                    //System.out.println("SnapshotResult x0.1");
                    final WritableImage nodeImage = result.getImage();

                    //display...
                    //show loading images...
                    //zpScene.getDialogPaneController().getDiag_ImageView().visibleProperty().set(true);
                    //zpScene.getDialogPaneController().getDiag_ImageView().setImage(nodeImage);



                    // System.out.println("SnapshotResult x0.2");
                    String hashKey = "" + wpImage.getFileURI().hashCode() + "-" + (int) x + "-" + (int) y + "-" + (int) width + "-" + (int) height;
                    //Thread.sleep(100);
                    BufferedImage bfImage = SwingFXUtils.fromFXImage(nodeImage, null);
                   //bfImage = SwingFXUtils.fromFXImage(nodeImage, null);
                   // BufferedImage bfImage = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
                   // SwingFXUtils.fromFXImage(nodeImage, bfImage);
                    
                    
                    // System.out.println("SnapshotResult x0.3 ->bfImage: " +bfImage.getWidth()+"-"+bfImage.getHeight());

                    //measure
                    //PerformanceMeasure.measurePoint("" + wpImage.getFileURI().hashCode(), "snapshot_>bfImage" + hashKey);

                    String filePathAndName = "snapShotURL";
                    String zpPath = ZP_Properties.getZoopraxiLocalPath();
                    filePathAndName = zpPath + wpSelection.getSnapShotKey(x, y, width, height) + ".png";

                    //###################
                    //save image 

//                    try {
//                        File outFile = new File(filePathAndName);
//                        ImageIO.write(bfImage, "png", outFile);
//                        //System.out.println("saveImage: " + filePathAndName);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    //measure
                    PerformanceMeasure.measurePoint("" + wpImage.getFileURI().hashCode(), "snapshot_>save " + hashKey);

                    //                    //
//                    // END save image
//                    //###################

                    //###################
                    //putCache
                    ZP_Properties.getReadyImages().put(hashKey, bfImage);
                    ZP_Properties.getReadyImages();
                    wpImage.setXxlrendered(true);



                    //+++++++++++++++++++++++
                    //progess..
                    //progess..

                   

                    int i = ZP_Properties.getImages2prepare();
                    int ready = ZP_Properties.getImages2prepareProcessed() + 1;
                    ZP_Properties.setImages2prepareProcessed(ready);
                    String message = "(" + ready + "/" + i + ") saved ->" + filePathAndName;

                    double curProgress = ZP_Properties.getPt2renderProcessed() / ZP_Properties.getPt2render();
                    message = "ZPID:" + ZP_Properties.getZpID() + " - Progess: " + curProgress + " - " + message;

                    zpScene.getDialogPaneController().getProgressBar().setProgress(curProgress);
                    zpScene.getDialogPaneController().getMessageBox().setText(message);

                    //display...
                    //show loading images...
                    //zpScene.getDialogPaneController().getDiag_ImageView().visibleProperty().set(true);
                    //zpScene.getDialogPaneController().getDiag_ImageView().setImage(nodeImage);



                    //just updateable in runLater...
                    zpScene.getDialogPaneController().getLblDialog().setText("build Zoopraxi ID:" + ZP_Properties.getZpID());

                    //mesure
                    //PerformanceMeasure.measurePoint("" + wpImage.getFileURI().hashCode(), "snapshot_>end " + hashKey);
                    //BuildZoopraxiController.lock.release();

                    ZP_Properties.runLater = false;
                    return null;

                } catch (Exception e) {
                    ZP_Properties.runLater = false;
                    e.printStackTrace();
                    throw new RuntimeException("Exception SnapShot", e);
                }

            }
        };


        //++++++++++++++
        //setup Params...
        SnapshotParameters paramsSnapShot = new SnapshotParameters();
        paramsSnapShot.setFill(javafx.scene.paint.Color.TRANSPARENT);
       
        //paramsSnapShot.setFill(javafx.scene.paint.Color.RED);

        //System.out.println("ParamSnapshot.Rectangle" + x + "/" + y + "-" + width + "/" + height);
        //Rectangle2D rectange = new Rectangle2D(x, y, width, height);
        Rectangle2D rectange = new Rectangle2D(x, y, width, height);
        paramsSnapShot.setViewport(rectange);

        //++++++++++++++
        //do it
        // System.out.println("SnapshotResult x0");
        try {
            //Thread.sleep(20);
            wpImage_XXL.snapshot(processSnapshotCallback, paramsSnapShot, null);

        } catch (Exception e) {
            System.out.println("Exception SnapshotResult");
            e.printStackTrace();
        }
        //  System.out.println("SnapshotResult x1 ");
        //  BuildZoopraxiController.lock.release();
        // PerformanceMeasure.measurePoint("" + wpImage.getFileURI().hashCode(), "snapshot>Take");

    }
}
