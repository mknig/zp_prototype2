/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.image;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Timeline;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import zp_prototype2.IWallpaper;
import zp_prototype2.MultipleSelectionManager;

import zp_prototype2.ZP_Prototype2;

/**
 *
 * @author Rackor
 */
public class ImageSelection extends Group implements IImageSelection {

    public ArrayList<IImageSelectionListener> listeners;
    ArrayList<ITranslationListener> translationListeners;
    IWallpaperImage wallpaperImage;
    public Shape selectionGraphics;
    FadeTransition fadeTransition;
    boolean isSelected;
    boolean isImageInsideWallpaper;
    double mousePressedXPosition;
    double mousePressedYPosition;
    private static final double STROKE_WIDTH = 5.5;
    //private static final Color STROKE_COLOR = Color.rgb(210, 104, 29, 0.6);//chocolate
    //private static final Color STROKE_COLOR = Color.rgb(210, 104, 29, 1);
    private static final Color STROKE_COLOR = Color.rgb(0, 255, 0, 1);//green
    private static final Color STROKE_COLOR_HOVER = Color.rgb(210, 210, 210, 0.6);
    private boolean wasImageMoved;
    private Point2D originalPositionBeforeTranslation;
    boolean isMousePressed;
    IWallpaper wallpaper;
    double margin = 0;//100;
    int index = 0;

    public ImageSelection(IWallpaperImage image) {
        this(null, image);
    }

    public ImageSelection(IWallpaper wallpaper, IWallpaperImage image) {
        this.wallpaperImage = image;
        this.wallpaper = wallpaper;
        listeners = new ArrayList<>();
        translationListeners = new ArrayList<>();

        selectionGraphics = createSelectionGraphics();
        selectionGraphics.setOpacity(0);

        isSelected = false;
        isMousePressed = false;
        wasImageMoved = false;

        setCursor(Cursor.HAND);

        selectionGraphics.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onMouseEnteredOnImage(mouseEvent);
            }
        });
        selectionGraphics.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onMouseExitedOffImage(mouseEvent);
            }
        });
        selectionGraphics.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse pressed on image selection" + "keyPressed:" + ZP_Prototype2.getInstance().getScene().keypressed_Value);
                onMousePressed(mouseEvent);
            }
        });
        selectionGraphics.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onMouseDragged(mouseEvent);
            }
        });
        selectionGraphics.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse released on image selection");
                onMouseReleased(mouseEvent);
            }
        });
        selectionGraphics.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse clicked on image selection");
//                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//                    if (mouseEvent.getClickCount() == 2) {
                onMouseClicked(mouseEvent);
//                    }
//                }
            }
        });


        Node nodeWallpaperImg = wallpaperImage.getNodeRepresentation();

        getChildren().addAll(nodeWallpaperImg, selectionGraphics);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int idx) {
        index = idx;
    }

    @Override
    public String getSnapShotKey(double posX, double posY, double width, double height) {
        return "" + getWallpaperImage().getFileURI().hashCode() + "-" + (int) posX + "-" + (int) posY + "-" + (int) width + "-" + (int) height;
    }

    public String getXXLKey() {
        return "" + getWallpaperImage().getFileURI().hashCode() + "_" + getIndex();
    }

    private void handleMousePressed(MouseEvent mouseEvent) {

        mousePressedXPosition = mouseEvent.getX() - wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinX();
        mousePressedYPosition = mouseEvent.getY() - wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinY();

        originalPositionBeforeTranslation = getPosition();

        for (IImageSelectionListener listener : listeners) {


            //KEY´s???
            boolean selRange = ZP_Prototype2.getInstance().getScene().keypressed;

            if (selRange) {
                //select Image till previous selected...
                // 
                List<IImageSelection> imagesSelection = ZP_Prototype2.getInstance().getWallpaper().getImages();
                int idxSelected = 0;
                int prvSelectedIDX = 0;
                for (IImageSelection iImageSelection : imagesSelection) {

                    //get previous selected index...
                    if (iImageSelection.isSelected()) {
                        prvSelectedIDX = idxSelected;
                    }

                    //that´s me...
                    if (iImageSelection.equals(this)) {
                        System.out.println("Gotcha");
                        break;
                    };
                    idxSelected++;
                }

                //select range between previous and selected index...

                int idxCur = 0;
                for (IImageSelection curImageSelection : imagesSelection) {

                    if (prvSelectedIDX < idxCur && idxSelected > idxCur) {

                        for (IImageSelectionListener listenerX : ((ImageSelection) curImageSelection).listeners) {
                            System.out.println("select:" + curImageSelection.getWallpaperImage().getFileURI());
                            //listenerX.mousePressed(curImageSelection, mouseEvent);
                            //iImageSelection.select();
                            //mouseRelease call select()
                            listenerX.mouseReleased(curImageSelection, false, mouseEvent);

                        }
                    }

                    //break after gotcha...
                    if (idxCur > idxSelected) {
                        break;
                    }
                    idxCur++;
                }


            } else {
                //TODO REFACTORdo nothing...check...select called by mouseRelease!!!
                listener.mousePressed(this, mouseEvent);
            }

        }
        toFront();
    }

    private void moveImage(MouseEvent mouseEvent) {
        Point2D mousePosInWallpaper = localToParent(mouseEvent.getX(), mouseEvent.getY());
        double translateXValue = mousePosInWallpaper.getX() - mousePressedXPosition;
        double translateYValue = mousePosInWallpaper.getY() - mousePressedYPosition;

        if (getTranslateX() - translateXValue == 0 && getTranslateY() - translateYValue == 0) {
            return;
        }

        translateXValue = constrainTranslateXToWallpaper(translateXValue);
        translateYValue = constrainTranslateYToWallpaper(translateYValue);
        setXTranslation(translateXValue);
        setYTranslation(translateYValue);

        wasImageMoved = true;

        onTranslated();
    }

    private double constrainTranslateXToWallpaper(double translateX) {
        if (translateX + getWidth() > getWallpaper().getWidth()) {
            translateX = getWallpaper().getWidth() - getWidth();
        }
        if (translateX < 0) {
            translateX = 0;
        }
        return translateX;
    }

    private double constrainTranslateYToWallpaper(double translateY) {
        if (translateY + getHeight() > getWallpaper().getHeight()) {
            translateY = getWallpaper().getHeight() - getHeight();
        }
        if (translateY < 0) {
            translateY = 0;
        }
        return translateY;
    }

    private Shape createSelectionGraphics() {

        Rectangle rectangle = new Rectangle();
        //rectangle.setArcHeight(5);
        //rectangle.setArcWidth(5);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);
        rectangle.setStrokeType(StrokeType.INSIDE);

        //animation!!!
        fadeTransition = FadeTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                //.node(lblDialog)
                .node(rectangle)
                .fromValue(1)
                .toValue(0.2)
                .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();


        rectangle.xProperty().bind(new DoubleBinding() {
            {
                super.bind(wallpaperImage.getNodeRepresentation().boundsInParentProperty());
            }

            @Override
            protected double computeValue() {
                return wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinX() - wallpaperImage.getBorderSizeHorizontal();
            }
        });

        rectangle.yProperty().bind(new DoubleBinding() {
            {
                super.bind(wallpaperImage.getNodeRepresentation().boundsInParentProperty());
            }

            @Override
            protected double computeValue() {
                return wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinY() - wallpaperImage.getBorderSizeVertical();
            }
        });

        rectangle.widthProperty().bind(new DoubleBinding() {
            {
                super.bind(wallpaperImage.getNodeRepresentation().boundsInParentProperty());
            }

            @Override
            protected double computeValue() {
                return wallpaperImage.getNodeRepresentation().getBoundsInParent().getMaxX() - wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinX() + wallpaperImage.getBorderSizeHorizontal() * 2;
            }
        });

        rectangle.heightProperty().bind(new DoubleBinding() {
            {
                super.bind(wallpaperImage.getNodeRepresentation().boundsInParentProperty());
            }

            @Override
            protected double computeValue() {
                return wallpaperImage.getNodeRepresentation().getBoundsInParent().getMaxY() - wallpaperImage.getNodeRepresentation().getBoundsInParent().getMinY() + wallpaperImage.getBorderSizeVertical() * 2;
            }
        });

        return rectangle;
    }

    @Override
    public void removeFromWallpaper() {
        if (wallpaper == null) {
            return;
        }
        wallpaper.removeImage(this);
        onDeleted();
    }

    protected void onDeleted() {
        for (IImageSelectionListener listener : listeners) {
            listener.deleted(this);
        }
    }

    @Override
    public void addListener(IImageSelectionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IImageSelectionListener listener) {
        listeners.remove(listener);
    }

    protected void onTranslated() {
        for (ITranslationListener listener : translationListeners) {
            listener.imageTranslated(this);
        }
    }

    protected void onTranslationEnded() {
        Point2D currentPosition = getPosition();
        double translatedXAmount = currentPosition.getX() - originalPositionBeforeTranslation.getX();
        double translatedYAmount = currentPosition.getY() - originalPositionBeforeTranslation.getY();

        for (ITranslationListener listener : translationListeners) {
            listener.imageTranslationEnded(this, translatedXAmount, translatedYAmount);
        }
    }

    public Point2D getPosition() {
        return new Point2D(translateXProperty().get(), translateYProperty().get());
    }

    @Override
    public void addTranslationListener(ITranslationListener translationListener) {
        translationListeners.add(translationListener);
    }

    @Override
    public void removeTranslationListener(ITranslationListener translationListener) {
        translationListeners.remove(translationListener);
    }

    //+++++++++++++++++++++
    // select, higlight...
    @Override
    public void select() {
        isSelected = true;
        selectionGraphics.setOpacity(1);
        selectionGraphics.setStroke(STROKE_COLOR);

        //start translation...
        fadeTransition.play();
    }

    @Override
    public void unSelect() {
        isSelected = false;

        selectionGraphics.setOpacity(0);
        //stop translation!
        fadeTransition.stop();
    }

    @Override
    public void setIsHighlighted(boolean isHighlighted) {

        //resizeStokeWidth
        //double strokeWidth = this.getWallpaperImage().getWidth() * 0.05 / ZP_Prototype2.getInstance().getWorkspace().getCurrentZoom();
        //selectionGraphics.setStrokeWidth(strokeWidth);

        if (isHighlighted) {
            //selectionGraphics.setOpacity(isHighlighted ? 0.5 : 0);
            if (!isSelected) {
                selectionGraphics.setOpacity(1);
                selectionGraphics.setStroke(STROKE_COLOR_HOVER);
            }
        } else {
            if (!isSelected) {
                selectionGraphics.setOpacity(0);
                selectionGraphics.setStroke(STROKE_COLOR);
            }
        }
    }

    //***************
    // events..
    // events..
    // events.. 
    // events..
    // events..
    // events..
    private void onMousePressed(MouseEvent mouseEvent) {
        isMousePressed = true;
        handleMousePressed(mouseEvent);
    }

    private void onMouseDragged(MouseEvent mouseEvent) {


        if (!ZP_Prototype2.getInstance().getWorkspace().dragModeActiveProperty.getValue()) {

            if (isMousePressed == false) // This happens if a popup is active
            {
                handleMousePressed(mouseEvent);
            }
            selectionGraphics.setOpacity(0);
            wallpaperImage.getNodeRepresentation().setOpacity(0.6);
            moveImage(mouseEvent);

        }

    }

    private void onMouseClicked(MouseEvent mouseEvent) {

        // if (mouseEvent.getClickCount() == 2) {
        System.out.println("Double clicked");
        for (IImageSelectionListener listener : listeners) {
            listener.mouseClicked(this, mouseEvent);
        }
        //  }


    }

    private void onMouseReleased(MouseEvent mouseEvent) {

        selectionGraphics.setOpacity(1);
        wallpaperImage.getNodeRepresentation().setOpacity(1);
        if (wasImageMoved) {
            onTranslationEnded();

        } //no selection on drag&drop actions
        //no selection on drag&drop actions
        else {

            //dbl-click???
            if (mouseEvent.getClickCount() == 2) {
                System.out.println("Double clicked");
                
                //deselect all other just this...
                //refactor!!!!
               ((MultipleSelectionManager)ZP_Prototype2.getInstance().getWallpaper().getSelectionManager()).unSelectAll();
                
                //select...
                for (IImageSelectionListener listener : listeners) {
                    listener.mouseReleased(this, wasImageMoved, mouseEvent);
                }
                
            } else {

                for (IImageSelectionListener listener : listeners) {
                    listener.mouseReleased(this, wasImageMoved, mouseEvent);
                }
            }
        }
        wasImageMoved = false;
        isMousePressed = false;
    }

    private void onMouseEnteredOnImage(MouseEvent mouseEvent) {
        setIsHighlighted(true);
        // zp_prototype2.ZP_Prototype2.instance.imageSelectedPanelController.setImage(this);
    }

    private void onMouseExitedOffImage(MouseEvent mouseEvent) {
        if (!isSelected) {
            setIsHighlighted(false);
        }
    }

    //******************
    // stupidos...
    // stupidos...
    // stupidos...
    // stupidos...
    // stupidos...
    // stupidos...
    @Override
    public double getWidth() {
        // return wallpaperImage.getWidth();
        return getBoundsInParent().getWidth();

    }

    @Override
    public double getHeight() {
        //return wallpaperImage.getHeight();
        return getBoundsInParent().getHeight();

    }

    @Override
    public Node getNodeRepresentation() {
        return this;
    }

    @Override
    public void setTranslate(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public void setXTranslation(double x) {
        setTranslateX(x);
    }

    @Override
    public void setYTranslation(double y) {
        setTranslateY(y);
    }

    @Override
    public IWallpaperImage getWallpaperImage() {
        return wallpaperImage;
    }

    @Override
    public IWallpaper getWallpaper() {
        return wallpaper;
    }

    @Override
    public void setWallpaper(IWallpaper wallpaper) {
        this.wallpaper = wallpaper;
    }

    @Override
    public boolean getIsHighlighted() {
        return selectionGraphics.getOpacity() == 1;
    }

    //
    // END select, higlight...
    public boolean isImageIN() {
        return isImageInsideWallpaper;
    }

    public void setImageIN(boolean imageIN) {
        this.isImageInsideWallpaper = imageIN;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
