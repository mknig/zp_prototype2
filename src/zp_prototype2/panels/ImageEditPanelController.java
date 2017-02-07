/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import zp_prototype2.CursorFactory;
import zp_prototype2.ISelectionManagerListener;
import zp_prototype2.IWallpaper;
import zp_prototype2.MultipleSelectionManager;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.image.WallpaperImage;

/**
 *
 * @author Pedro
 */
public class ImageEditPanelController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private StackPane imagePane;
    @FXML
    private Button deleteButton;
    @FXML
    private Label headerLabel;
    @FXML
    private Button rotateCCWButton;
    @FXML
    private Button rotateCWButton;
    @FXML
    private Button resetViewportButton;
    

    private final static double CROP_PERCENTAGE = 0.05d;
    
    private IWallpaperImage currentImage;
    private IWallpaperImage originalImage;
    
    private double mousePressedXPosition;
    private double mousePressedYPosition;
    private Rectangle2D originalImageViewport;
    
    private MultipleSelectionManager selectionManager;
    
    private ArrayList<IImagePanelControllerListener> listeners;
    
    private IWallpaper wallpaper;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void setWallpaper(IWallpaper wallpaper)
    {
        wallpaper.getSelectionManager().addListener(new ISelectionManagerListener() {

            @Override
            public void selectionChanged(IImageSelection lastSelectedImage) {
                setImage(lastSelectedImage);
            }

            @Override
            public void mouseReleasedOnImage(IImageSelection image, boolean wasMoved) {
            }
        });
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        this.wallpaper = wallpaper;
    }
    
    @FXML
    private void handleDeleteAction() {
        for (IImageSelection image : selectionManager.getSelectedImages())
            wallpaper.removeImage(image);
        onImageRemoved();
    }

    @FXML
    private void handleRotateCWAction() {
        for (IImageSelection image : selectionManager.getSelectedImages())
            image.getWallpaperImage().rotateCW();
    }

    @FXML
    private void handleRotateCCWAction() {
        for (IImageSelection image : selectionManager.getSelectedImages())
            image.getWallpaperImage().rotateCCW();
    }

    @FXML
    private void handleZoomOutAction() {
        currentImage.cropByPercentage(-CROP_PERCENTAGE);
    }

    @FXML
    private void handleZoomInAction() {
        currentImage.cropByPercentage(CROP_PERCENTAGE);
    }

    @FXML
    private void handleResetViewportAction() {
        currentImage.setViewport(null);
    }

    public void setImage(IImageSelection selectedImage) {
        if (currentImage != null) {
            imagePane.getChildren().remove(currentImage.getNodeRepresentation());
        }
        IWallpaperImage selectedWallpaperImage = selectedImage.getWallpaperImage();
        
        originalImage = selectedWallpaperImage;
        originalImageViewport = originalImage.getViewport();

        internalSetImage(selectedWallpaperImage);

        setupEventHandlers();
        bindImageToOriginal();
    }
    
    private void internalSetImage(IWallpaperImage wallpaperImage) {
        currentImage = new WallpaperImage(wallpaperImage);
        
        
        //currentImage.setRotation(0);
        
        
        resizeImageBeforeAddingToPane(currentImage);
        currentImage.setViewport(originalImageViewport);
        currentImage.getImageView().getStyleClass().add("image");
        Node imageNode = currentImage.getNodeRepresentation();
        imagePane.getChildren().add(currentImage.getNodeRepresentation());

        imageNode.setCursor(CursorFactory.getPanCursor());
        updateResetViewportButtonState();
    }

    private void resizeImageBeforeAddingToPane(IWallpaperImage image)
    {
        double prefHeight = imagePane.getPrefHeight();
        double prefWidth = imagePane.getPrefWidth();
        image.setIsProportionsConstrained(true);
        image.setHeight(prefHeight);
        if (image.getWidth() > prefWidth) {
            image.setWidth(prefWidth);
        }
    }

    private void updateResetViewportButtonState() {
        Rectangle2D currentViewport = currentImage.getViewport();
        if (currentViewport == null) {
            resetViewportButton.setDisable(true);
            return;
        }

        Image fxImage = currentImage.getImage();
        if (currentViewport.getMinX() == 0 && currentViewport.getMinY() == 0 && currentViewport.getWidth() >= fxImage.getWidth() && currentViewport.getHeight() >= fxImage.getHeight()) {
            resetViewportButton.setDisable(true);
        } else {
            resetViewportButton.setDisable(false);
        }
    }

    private void setupEventHandlers() {
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
        currentImage.getNodeRepresentation().setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseReleasedOnImage(mouseEvent);
            }
        });
    }

    private void bindImageToOriginal() {
        currentImage.viewportProperty().addListener(new ChangeListener<Rectangle2D>() {
            @Override
            public void changed(ObservableValue<? extends Rectangle2D> arg0, Rectangle2D arg1, Rectangle2D arg2) {
                originalImage.setCustomUserViewport(currentImage.getViewport());
                updateResetViewportButtonState();
            }
        });
    }

    protected void mouseDraggedOnImage(MouseEvent mouseEvent) {
        double dragX = mouseEvent.getX() - mousePressedXPosition;
        double dragY = mouseEvent.getY() - mousePressedYPosition;
        currentImage.moveViewport(-dragX, -dragY);
        mousePressedXPosition = mouseEvent.getX();
        mousePressedYPosition = mouseEvent.getY();
    }

    protected void mousePressedOnImage(MouseEvent mouseEvent) {

        mousePressedXPosition = mouseEvent.getX();
        mousePressedYPosition = mouseEvent.getY();

        currentImage.getNodeRepresentation().setCursor(CursorFactory.getPanningCursor());
    }

    protected void mouseReleasedOnImage(MouseEvent mouseEvent) {
        currentImage.getNodeRepresentation().setCursor(CursorFactory.getPanCursor());
    }

    protected void onImageRemoved()
    {
        for(IImagePanelControllerListener listener : listeners)
            listener.imageRemoved();
    }
    
    public void addListener(IImagePanelControllerListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeListener(IImagePanelControllerListener listener)
    {
        listeners.remove(listener);
    }
}
