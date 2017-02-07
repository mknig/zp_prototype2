/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import zp_prototype2.image.IImageSelectionListener;
import zp_prototype2.image.IImageSelection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import javafx.scene.input.MouseEvent;
import zp_prototype2.image.ImageSelectionListenerAdapter;

/**
 *
 * @author Pedro
 */
public class SelectionManager {
    private IImageSelection currentSelectedImage;
    private SelectableImageListener imageListener;
    private ArrayList<ISelectionManagerListener> listeners;

    IWallpaper wallpaper;
    
    
    public SelectionManager(IWallpaper wallpaper)
    {
        this.wallpaper = wallpaper;
        listeners = new ArrayList<ISelectionManagerListener>();
        
        imageListener = new SelectableImageListener();
        wallpaper.addListener(new IWallpaperListener() {

            @Override
            public void imageAdded(IImageSelection image) {
                image.addListener(imageListener);
            }
        });
    }
    
    protected void handleMousePressedOnImage(IImageSelection image, MouseEvent mouseEvent)
    {
       //set Image to Menu...
       //CAUSE DRAGDRROP ERROR???
        // ZP_Prototype2.getInstance().vBoxMenu.getChildren().add(ZP_Prototype2.getInstance().imageSelectedPanel);
    }
    
    protected void handleMouseReleasedOnImage(IImageSelection image, boolean wasImageMoved, MouseEvent mouseEvent) {
        if (!wasImageMoved)
            internalSelect(image);
        fireMouseReleasedOnImage(image, wasImageMoved, mouseEvent);
    }
    
    protected void handleMouseClickedOnImage(IImageSelection image, MouseEvent mouseEvent) {
    }
    
    public void onSelectionChanged(IImageSelection lastSelectedImage)
    {
        for(ISelectionManagerListener listener : listeners)
            listener.selectionChanged(lastSelectedImage);
        
    }
    
    private void handleImageDeleted(IImageSelection image) {
        if (currentSelectedImage == image)
        {
            currentSelectedImage = null;
            onSelectionChanged(currentSelectedImage);
        }
    }
    
    private void internalSelect(IImageSelection image)
    {
        if (currentSelectedImage != null)        
            currentSelectedImage.unSelect();
        currentSelectedImage = image;
        currentSelectedImage.select();
        onSelectionChanged(currentSelectedImage);
    }
    
    protected void fireMouseReleasedOnImage(IImageSelection image, boolean wasImageMoved, MouseEvent mouseEvent)
    {
        for(ISelectionManagerListener listener : listeners)
            listener.mouseReleasedOnImage(image, wasImageMoved);
    }
    
    public void addListener(ISelectionManagerListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeListener(ISelectionManagerListener listener)
    {
        listeners.remove(listener);
    }
    
    
    
    class SelectableImageListener extends ImageSelectionListenerAdapter
    {
        @Override
        public void mousePressed(IImageSelection image, MouseEvent mouseEvent) {
            SelectionManager.this.handleMousePressedOnImage(image, mouseEvent);
        }

        @Override
        public void deleted(IImageSelection image) {
            SelectionManager.this.handleImageDeleted(image);
        }
        
        @Override 
        public void mouseReleased(IImageSelection image, boolean wasImageMoved, MouseEvent mouseEvent)
        {
            SelectionManager.this.handleMouseReleasedOnImage(image, wasImageMoved, mouseEvent);
        }
        
        @Override
        public void mouseClicked(IImageSelection image, MouseEvent mouseEvent)
        {
            SelectionManager.this.handleMouseClickedOnImage(image, mouseEvent);
        }
        
    }
    
    
}
