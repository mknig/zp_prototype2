/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import zp_prototype2.image.IWallpaperImage;

/**
 *
 * @author Pedro
 */
public class LibraryImageSelection extends Group{
    private static final int STROKE_WIDTH = 3;
    
    IWallpaperImage wallpaperImage;
    Shape selection;
    
    public LibraryImageSelection(final IWallpaperImage wallpaperImage)
    {
        this.wallpaperImage = wallpaperImage;
        
        selection = createSelectionGraphics();
        
        selection.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onMouseEnteredOnImage(mouseEvent);
            }
        });
        selection.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onMouseExitedOffImage(mouseEvent);
            }
        });
        
        selection.setOpacity(0);
        selection.setCursor(Cursor.HAND);
        getChildren().addAll(wallpaperImage.getNodeRepresentation(), selection);
    }
    
    private Shape createSelectionGraphics()
    {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(new Color(0.4, 0.4, 0.8, 1));
        rectangle.setStrokeWidth(STROKE_WIDTH);
        rectangle.setStrokeType(StrokeType.INSIDE);
        rectangle.xProperty().bind(new DoubleBinding(){
            {
                super.bind(wallpaperImage.getNodeRepresentation().boundsInParentProperty());
            }
            @Override
            protected double computeValue() {
                return wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinX();
            }
        });
        rectangle.yProperty().bind(new DoubleBinding(){
            {
                super.bind(wallpaperImage.getNodeRepresentation().boundsInParentProperty());
            }
            @Override
            protected double computeValue() {
                return wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinY();
            }
        });
        rectangle.widthProperty().bind(new DoubleBinding(){
            {
                super.bind(wallpaperImage.getNodeRepresentation().boundsInParentProperty());
            }
            @Override
            protected double computeValue() {
                return wallpaperImage.getNodeRepresentation().getBoundsInParent().getMaxX() - wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinX();
            }
        });
        rectangle.heightProperty().bind(new DoubleBinding(){
            {
                super.bind(wallpaperImage.getNodeRepresentation().boundsInParentProperty());
            }
            @Override
            protected double computeValue() {
                return wallpaperImage.getNodeRepresentation().getBoundsInParent().getMaxY() - wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinY();
            }
        });    
        
        return rectangle;
    }
    
    protected void onMouseEnteredOnImage(MouseEvent mouseEvent)
    {
        selection.setOpacity(1);
    }
    
    protected void onMouseExitedOffImage(MouseEvent mouseEvent)
    {
        selection.setOpacity(0);
    }
    
    
    
}
