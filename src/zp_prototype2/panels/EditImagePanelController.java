/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.image.WallpaperImage;

/**
 *
 * @author Pedro
 */
public class EditImagePanelController implements Initializable{
    private final static double CROP_PERCENTAGE = 0.1d;
    private IWallpaperImage currentImage;
    private IWallpaperImage originalImage;
    
    private double mousePressedXPosition;
    private double mousePressedYPosition;
    
    private Rectangle2D originalViewport;
    
    private Stage stage;
    
    @FXML private HBox imagePane;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
    @FXML private void handleZoomOut()
    {
        currentImage.cropByPercentage(-CROP_PERCENTAGE);
    }
    
    @FXML private void handleZoomIn()
    {
        currentImage.cropByPercentage(CROP_PERCENTAGE);
    }
    
    @FXML private void handleCancel()
    {
        onCancel();
    }
    
    @FXML private void handleOK()
    {
        stage.close();
    }
    
    private void onCancel()
    {
        originalImage.setViewport(originalViewport);
        stage.close();
    }
    
    public void setImage(IWallpaperImage image)
    {
        currentImage = new WallpaperImage(image);
        double prefHeight = imagePane.getPrefHeight();
        currentImage.setHeight(prefHeight);
        imagePane.setAlignment(Pos.CENTER);
        
        Node imageNode = currentImage.getNodeRepresentation();
        imageNode.getStyleClass().add("image");
        imagePane.getChildren().add(currentImage.getNodeRepresentation());
        
        imageNode.setCursor(Cursor.OPEN_HAND);
        setupEventHandlers();
        bindImageToOriginal();
        
        originalImage = image;
        originalViewport = image.getViewport();
    }
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {
                onCancel();
            }
        });
    }
    
    private void setupEventHandlers()
    {
        currentImage.getNodeRepresentation().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePressedOnImage(mouseEvent);
            }
        });
        currentImage.getNodeRepresentation().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseDraggedOnImage(mouseEvent);
            }
        });
    }
    
    private void bindImageToOriginal()
    {
        currentImage.viewportProperty().addListener(new ChangeListener<Rectangle2D>() {
            @Override
            public void changed(ObservableValue<? extends Rectangle2D> arg0, Rectangle2D arg1, Rectangle2D arg2) {
                originalImage.setViewport(currentImage.getViewport());
            }
        });
    }
    
    protected void mouseDraggedOnImage(MouseEvent mouseEvent)
    {
        double dragX = mouseEvent.getX() - mousePressedXPosition;
        double dragY = mouseEvent.getY() - mousePressedYPosition;
        currentImage.moveViewport(-dragX, -dragY);
        mousePressedXPosition = mouseEvent.getX();
        mousePressedYPosition = mouseEvent.getY();
        
        
    }
    
    protected void mousePressedOnImage(MouseEvent mouseEvent)
    {
        mousePressedXPosition = mouseEvent.getX();
        mousePressedYPosition = mouseEvent.getY();
        
        currentImage.getNodeRepresentation().setCursor(Cursor.CLOSED_HAND);
    }
    
    protected void mouseReleasedOnImage(MouseEvent mouseEvent)
    {
        currentImage.getNodeRepresentation().setCursor(Cursor.OPEN_HAND);
    }
}
