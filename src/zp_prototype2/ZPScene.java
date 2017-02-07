/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import zp_prototype2.panels.Dialog_Workspace_FXMLController;

/**
 *
 * @author Pedro
 */
public class ZPScene extends Scene {

    Parent root;
    Pane dialogPane;
    Dialog_Workspace_FXMLController dialogController;
    //GlassPane glassPane;
    BoxBlur boxBlur = new BoxBlur();
    //+++++++++++++++++
    // keyHandling
    public boolean keypressed = false;
    public String keypressed_Value = "";

    public ZPScene(Parent root) {

        super(new StackPane(), Color.TRANSPARENT);
        this.root = root;
        StackPane rootStackPane = (StackPane) getRoot();


        //glass...
        //glassPane = new GlassPane();

        try {
            Pane panel = ZP_Prototype2.getInstance().fxmlLoader.loadFXML("panels/Dialog_Workspace_FXML.fxml");
            dialogPane = panel;//new DialogPane();
            dialogController = (Dialog_Workspace_FXMLController) ZP_Prototype2.getInstance().fxmlLoader.getController();

        } catch (Exception e) {
            System.out.println(e);
        }
        //rootStackPane.getChildren().addAll(root, glassPane, dialogPane);

        //setSize by SCALE!!! Ilike 
        dialogPane.setScaleX(0.8);
        dialogPane.setScaleY(0.8);

        rootStackPane.getChildren().addAll(root, dialogPane);

        rootStackPane.getStyleClass().add("rootPane");
        boxBlur.setWidth(5);
        boxBlur.setHeight(5);
        boxBlur.setIterations(3);

        //keyEvents...
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                keypressed = true;
                keypressed_Value = ke.getText();
                //System.out.println("Key Pressed:" + keypressed + "->" + keypressed_Value);
            }
        });

        //keyEvents...
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                keypressed = false;
                keypressed_Value = "";
                //System.out.println("Key Released:" + keypressed + "->" + ke.getText());
            }
        });


        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Scene Width: " + newSceneWidth);
                //ZP_Prototype2.getInstance().getWorkspace().zoomTo(ZP_Prototype2.getInstance().getWorkspace().getMinimumZoom());
                //ZP_Prototype2.getInstance().getWallpaper().setWidth(ZP_Prototype2.getInstance().getWallpaper().getWidth());
                //ZP_Prototype2.getInstance().getWallpaper().reArrangeImages();
                //ZP_Prototype2.getInstance().getWorkspace().zoomTo(ZP_Prototype2.getInstance().getWorkspace().getMinimumZoom());

            }
        });
        this.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Scene Height: " + newSceneHeight);
                // ZP_Prototype2.getInstance().getWallpaper().setWidth(ZP_Prototype2.getInstance().getWallpaper().getWidth());
            }
        });


    }
//    public GlassPane getGlassPane() {
//        return glassPane;
//    }
//
//    public void setBlockUIAndShowProgress(boolean block) {
//        if (block) {
//            root.setEffect(boxBlur);
//        } else {
//            root.setEffect(null);
//        }
//        //  glassPane.setBlockUIAndShowProgress(block);
//    }
    Cursor previousMouseCursor;

    public void setBlockUI(boolean block) {
        if (block) {
            root.setEffect(boxBlur);
            dialogPane.setMouseTransparent(false);
            //previousMouseCursor = getCursor();
            //setCursor(Cursor.WAIT);

        } else {
            root.setEffect(null);
            dialogPane.setMouseTransparent(true);
            //setCursor(previousMouseCursor);
        }

    }

    public void switchCursor(Cursor cursor) {
        final Cursor curCursor = cursor;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("switch cursor->" + curCursor);
                ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().getScene();
                zpScene.setCursor(curCursor);
                // Update the text node with calculated results
            }
        });

    }

    public void showDialog() {
        setBlockUI(true);
        dialogPane.setVisible(true);
    }

    public void closeDialog() {
        setBlockUI(false);
        dialogPane.setVisible(false);
    }

    public Pane getDialogPane() {
        return dialogPane;
    }

    public Dialog_Workspace_FXMLController getDialogPaneController() {
        return dialogController;
    }

    public void setDialogPane(Pane dialogPane) {
        this.dialogPane = dialogPane;
    }
}
