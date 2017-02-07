/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.panels.IImagePanelControllerListener;
import zp_prototype2.panels.ImagePanelController;

/**
 *
 * @author Pedro
 */
public class ImagePanelPopup extends Popup
{
    double sceneX;
    double sceneY;
        
    ImagePanelController imagePanelController;
    AnchorPane popupPane;
    
    FadeTransition fadeTransition;
//    ScaleTransition scaleTransition;
//    ParallelTransition animation;
    
    public ImagePanelPopup() throws Exception
    {
//        FXMLLoaderWrapper fxmlLoader = new FXMLLoaderWrapper(FXMLLoaderWrapper.DEFAULT_BUNDLE);
//
//        Pane panel = fxmlLoader.loadFXML("panels/ImagePanel.fxml");
//        popupPane = new AnchorPane();
//        popupPane.getChildren().add(panel);
//        imagePanelController = ((ImagePanelController)fxmlLoader.getController());
//        imagePanelController.setCloseAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent arg0) {
//                hide();
//            }
//        });
//        imagePanelController.getHeaderPane().setOnMousePressed(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                sceneX = mouseEvent.getSceneX();
//                sceneY = mouseEvent.getSceneY();
//            }
//        });
//        imagePanelController.getHeaderPane().setOnMouseDragged(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                setX(mouseEvent.getScreenX() - sceneX);
//                setY(mouseEvent.getScreenY() - sceneY);
//            }
//        });
//        imagePanelController.addListener(new IImagePanelControllerListener() {
//
//            @Override
//            public void imageRemoved() {
//                ImagePanelPopup.this.hide();
//            }
//        });
//                
//        getContent().add(popupPane);
//        fadeTransition = new FadeTransition(Duration.millis(150), popupPane);
//        fadeTransition.setInterpolator(Interpolator.EASE_IN);
//        fadeTransition.setFromValue(0);
//        fadeTransition.setToValue(1);
//        fadeTransition.setCycleCount(1);
//        fadeTransition.setAutoReverse(false);
////        scaleTransition = new ScaleTransition(Duration.millis(100), popupPane);
////        scaleTransition.setFromX(0);
////        scaleTransition.setFromY(0);
////        scaleTransition.setToX(1);
////        scaleTransition.setToY(1);
////        scaleTransition.setCycleCount(1);
////        scaleTransition.setAutoReverse(false);
////        animation = new ParallelTransition(scaleTransition);
//        
//        setAutoHide(true);
  
    
    }
    
    public void setImage(IImageSelection wallpaperImageSelection)
    {
        imagePanelController.setImage(wallpaperImageSelection);
    }
    
    @Override
    public void show(Window stage)
    {
//        this.popupPane.setScaleX(0);
//        this.popupPane.setScaleY(0);
        this.popupPane.setOpacity(0);
        super.show(stage);
//        this.scaleTransition.setsetScaleX(getWidth() / 2);
//        this.popupPane.setScaleY(getHeight() / 2);
        fadeTransition.playFromStart();
    }
}

