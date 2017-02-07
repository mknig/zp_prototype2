/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import zp_prototype2.ILibrary;
import zp_prototype2.LibraryImageSelection;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.image.WallpaperImage;

/**
 *
 * @author Pedro
 */
public class LibraryPanelController implements Initializable, ILibrary{
    private double imageDefaultWidth;
    private double imageDefaultHeight;
    
    @FXML private FlowPane contentPane;
    
    private IWallpaperImage dragContent;
    private LibraryImageSelection currentlyDraggedImage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageDefaultWidth = contentPane.getPrefWidth() / 5;
        imageDefaultHeight = contentPane.getPrefHeight() / 4;
    }
    
    @Override
    public void addImage(final WallpaperImage wallpaperImage)
    {
        wallpaperImage.setFitWidth(imageDefaultWidth);
        wallpaperImage.setFitHeight(imageDefaultHeight);
        final LibraryImageSelection selection = new LibraryImageSelection(wallpaperImage);
        selection.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Dragboard dragBoard = wallpaperImage.startDragAndDrop(TransferMode.ANY);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString("drag-and-drop marker");
                dragBoard.setContent(clipboardContent);
                dragContent = wallpaperImage;
                selection.setOpacity(0.2);
                currentlyDraggedImage = selection;
                event.consume();
            }
        });
        
        contentPane.getChildren().add(selection);
    }

    @Override
    public IWallpaperImage getDragAndDropContent() {
        return dragContent;
    }


    @Override
    public void dragAndDropFinished(final IWallpaperImage switchedImage) {
        if (switchedImage != null)
        {
            switchedImage.setFitWidth(imageDefaultWidth);
            switchedImage.setFitHeight(imageDefaultHeight);
            final LibraryImageSelection selection = new LibraryImageSelection(switchedImage);
            selection.setOnDragDetected(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Dragboard dragBoard = switchedImage.getNodeRepresentation().startDragAndDrop(TransferMode.ANY);
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString("drag-and-drop marker");
                    dragBoard.setContent(clipboardContent);
                    dragContent = switchedImage;
                    selection.setOpacity(0.2);
                    currentlyDraggedImage = selection;
                    event.consume();
                }
            });
            
            contentPane.getChildren().add(selection);
        }
        dragContent = null;
    }

    @Override
    public void removeDraggedImage()
    {
        currentlyDraggedImage.setOpacity(1);
        contentPane.getChildren().remove(currentlyDraggedImage);
    }
}
