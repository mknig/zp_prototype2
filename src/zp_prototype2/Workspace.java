/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.render.LocateJRE;

/**
 *
 * @author Rackor
 */
public class Workspace extends ScrollPane {

    public Workspace me = null;
    IWallpaper wallpaper;
    // StackPane contentGroup;
    Group contentGroup;
    Group zoomGroup;
    Scale scaleTransform;
    private boolean isUpdatingZoom;
    private boolean isFirstLayout;
    private double minimumScale;
    private double maximumScale;
    ArrayList<IWorkspaceListener> listeners;
    ImageView bgImage = null;
    double wallHeight = 0;
    Timeline timeline = new Timeline();
    // public boolean panMode=false;
    public final BooleanProperty dragModeActiveProperty =
            new SimpleBooleanProperty(this, "dragModeActive", true);

    public Workspace(Wallpaper wallpaper) {

        this.wallpaper = wallpaper;
        me = this;

        //TODO: set on/off disable drop/drag for wallpapaerimage.
        //TODO: set on/off disable drop/drag for wallpapaerimage.
        //TODO: set on/off disable drop/drag for wallpapaerimage.


        dragModeActiveProperty.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal,
                    Object newVal) {

                setPannable((boolean) newVal);

                System.out.println("dragModeActiveProperty changed old/new" + oldVal + "/" + newVal);
            }
        });

        //++++++++++
        // boden...
        // bgImage = new ImageView(new Image(this.getClass().getResourceAsStream("resource/bg_boden_2.jpg")));
        bgImage = new ImageView(new Image(this.getClass().getResourceAsStream("resource/bg_boden_3.jpg")));
        bgImage.setFitWidth(2000);// fit bg  image dimension to workspace
        if (WallpaperDimensions.convertPixelToM(wallpaper.getHeight()) < 2) {
            wallHeight = 700;
        }

        //for big width...
        // bgImage = new ImageView(new Image(this.getClass().getResourceAsStream("resource/bg_boden_4.jpg")));
        // bgImage.setFitWidth(4500);// fit bg  image dimension to workspace

        //++++++++++++++
        //stones
        //bgImage = new ImageView(new Image(this.getClass().getResourceAsStream("resource/bg_medium/Fotolia_51237146_X.jpg")));
        //bgImage.setFitWidth(2000);// fit bg  image dimension to workspace
        //if (WallpaperDimensions.convertPixelToM(wallpaper.getHeight()) < 2) {
        //    wallHeight = -400;
        //}

        //++++++++++++++
        //facebook
        // bgImage = new ImageView(new Image(this.getClass().getResourceAsStream("resource/facebook.JPG")));
        // bgImage.setFitWidth(2000);// fit bg  image dimension to workspace

        bgImage.setPreserveRatio(true);

        isUpdatingZoom = false;
        isFirstLayout = true;
        maximumScale = 10;//5;//0.2;


        //init Nodes
        contentGroup = new Group();
        zoomGroup = new Group();
        Node wallpaperNode = wallpaper.getNodeRepresentation();

        //++++++++++++++++++++++
        // stick togehter...TODO:refactor


        // what to zoom?
        //zoomGroup.getChildren().addAll(wallpaperNode);
        zoomGroup.getChildren().addAll(bgImage, wallpaperNode);

        // why the same? refactor
        contentGroup.getChildren().addAll(zoomGroup);

        //Text msg= setMessage();
        //contentGroup.getChildren().addAll(zoomGroup, msg);
        setContent(contentGroup);


        //+++++++++++++++++++++
        // set Style
        getStyleClass().add("css_workspace");


        //+++++++++++++++++++++
        // add Listeners...
        wallpaper.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                onWallpaperSizeChanged();
            }
        });
        wallpaper.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                onWallpaperSizeChanged();
            }
        });
        //??????
        listeners = new ArrayList<>();

//        //++++++++++++++++++++++++++++++++++
//        // addScrollPane-ViewportListener..
//        viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
//            @Override
//            public void changed(ObservableValue<? extends Bounds> bounds, Bounds oldBounds, Bounds newBounds) {
//                if (timeline.getStatus() == Animation.Status.RUNNING) {
//                    //center crollbar...
//                    me.setHvalue(0.5);
//                    //    System.out.println("isUpdatingZoom-TRUE");
//                }
//            }
//        });


    }

//    public void setPanMode(boolean panMode){
//        this.panMode=panMode; 
//        setPannable(panMode);
//    }
    private Text setMessage() {
        //+++++++++++++++++
        //
        // Message
        //
        String whereIam = "whereIam: no idea";

        try {
            File classFile = LocateJRE.getClassLocation(String.class);
            whereIam = "\njre:" + classFile.getAbsolutePath();
        } catch (IOException e) {
            whereIam = e.getMessage();
        }
        //geht gar nicht!!! whereIam+="\n"+ZP_Properties.getZoopraxiLocalPath();
        try {
            whereIam += "\nuser.dir:" + System.getProperty("user.dir");
        } catch (Exception e) {
            e.printStackTrace();
            whereIam += "\nuser.dir:" + e.toString();
            whereIam += "\nuser.dir:" + e.getMessage();
        }

        try {
            whereIam += "\nuser.home:" + System.getProperty("user.home");
        } catch (Exception e) {
            e.printStackTrace();
            whereIam += "\nuser.home:" + e.toString();
            whereIam += "\nuser.home:" + e.getMessage();
        }

        try {
            whereIam += "\nJFileChooser" + (new JFileChooser().getFileSystemView().getDefaultDirectory()).getAbsolutePath();
        } catch (Exception ee) {
            ee.printStackTrace();
            whereIam += "\nJFileChooser:" + ee.toString();
            whereIam += "\nJFileChooser:" + ee.getMessage();
        }

        Text msg = new Text(100, 100, "Hello Jessi:\n" + whereIam);
        msg.setFont(new Font(20));

        return msg;

    }

    private void updateMinimumZoom() {
        double scale = getScaleToFitWallpaperSize();
        //minimumScale = scale;
        // minimumScale = scale/2;
        minimumScale = scale / 1.8;//1.5;
    }

    public void addListener(IWorkspaceListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IWorkspaceListener listener) {
        listeners.remove(listener);
    }

    public void initScale() {

        updateMinimumZoom();
        scaleTransform = new Scale(minimumScale, minimumScale);
        zoomGroup.getTransforms().add(scaleTransform);

        scaleTransform.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                onZoomChanged();
            }
        });
        scaleTransform.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                onZoomChanged();
            }
        });
        onMinimumZoomChanged();
        onZoomChanged();
    }

    public void zoom(double zoomStep) {
        double currentScale = scaleTransform.getX();
        currentScale += zoomStep;
        internalZoomTo(currentScale);
    }

    public void zoomTo(double zoomValue) {
        if (isUpdatingZoom) {
            return;
        }
        System.out.println("zooming in. is updating zoom = " + isUpdatingZoom);
        isUpdatingZoom = true;
        internalZoomTo(zoomValue);

        //updatePosition..
        positionWorkspace(zoomValue);
    }

    private void internalZoomTo(double zoomValue) {
        if (zoomValue < minimumScale) {
            zoomValue = minimumScale;
        } else if (zoomValue > maximumScale) {
            zoomValue = maximumScale;
        }
        animateScaleToValue(zoomValue, false);
        System.out.print("scroll.hvalue: " + this.getHvalue());
    }

    public final double getScaleToFitWallpaperSize() {

        //find scale..
        double xScale = getWidth() / wallpaper.getWidth();
        double yScale = getHeight() / wallpaper.getHeight();
        double scale = Math.min(xScale, yScale);

        //add some padding...
        //  scale = scale - scale * 0.05;//;0.20;


        //setBackground
        double bgImageWidth = 1.3;
        scale = scale * bgImageWidth;
        positionWorkspace(scale);

        return scale;
        // return 1;//0.5;

    }

    //+++++++++++++++++++++++++++++++++++
    //setPosition of ZP on Wall....
    public final void positionWorkspace(double scale) {


        //setScrollPanel
        me.setVvalue(0);
        me.setHvalue(0.5);

        //resize/set bgImage
        System.out.println("positionWorkspace for scale:" + scale);


        double centerWorkspace = me.getWidth() / scale / 2;

        //wallpaper on Horizont...
        double shiftBottom = wallpaper.getHeight();



        wallHeight = 0;
        if (WallpaperDimensions.convertPixelToM(wallpaper.getHeight()) < 2) {
            wallHeight = 700;
        }

        double shift = shiftBottom + wallHeight;
        bgImage.setTranslateY(shift);
        double centerBG = bgImage.getFitWidth() / 2;
        double bg_shiftY = (centerWorkspace - centerBG);
        bgImage.setTranslateX(bg_shiftY);


        //center Wallpaper on bgImage
        // double centerWorkspace = me.getWidth() * 1 / scale / 2 / WallpaperDimensions.convertPixelToM(wallpaper.getWidth()) * 2;
        double centerWallpaper = wallpaper.getWidth() / 2;
        double wp_ShiftX = (centerWorkspace - centerWallpaper);
        wallpaper.getNodeRepresentation().setTranslateX(wp_ShiftX);


        contentGroup.setTranslateY(50);
        //center contentGroup...
        if (bgImage.getFitWidth() * scale < me.getWidth()) {
            double centerBGX = bgImage.getFitWidth() * scale / 2;
            double centerMe = me.getWidth() / 2;
            contentGroup.setTranslateX(centerMe - centerBGX);
        } else {
            contentGroup.setTranslateX(0);
        }
    }

    private void animateScaleToValue(double value, boolean isDelayed) {

        //me.setHvalue(0.5);

        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);

//        if (isDelayed) {
//            timeline.setDelay(new Duration(700));
//        }

        //avoid fly out?
        timeline.setDelay(new Duration(10));
        
        
        //scale...
        final KeyValue keyValueX = new KeyValue(scaleTransform.xProperty(), value, Interpolator.EASE_BOTH);
        final KeyValue keyValueY = new KeyValue(scaleTransform.yProperty(), value, Interpolator.EASE_BOTH);
        final KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), keyValueX, keyValueY);
        timeline.getKeyFrames().add(keyFrame);

        final KeyValue scrollPaneHValueX = new KeyValue(me.hvalueProperty(), 0.5, Interpolator.EASE_BOTH);
        //final KeyValue scrollPaneHValueY = new KeyValue(me.vvalueProperty(), 0, Interpolator.EASE_BOTH);
        //final KeyFrame keyFrameValue = new KeyFrame(Duration.millis(1000), scrollPaneHValueX, scrollPaneHValueY);
        final KeyFrame keyFrameValue = new KeyFrame(Duration.millis(1000), scrollPaneHValueX);
        timeline.getKeyFrames().add(keyFrameValue);
        
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                //me.setHvalue(0.5);
                isUpdatingZoom = false;
                onZoomChanged();
            }
        });


        timeline.play();

    }

    public boolean isMinimumZoom() {
        return Math.abs(scaleTransform.getX() - minimumScale) < 0.0000000001;
    }

    public boolean isMaximumZoom() {
        return Math.abs(scaleTransform.getX() - maximumScale) < 0.0000000001;
    }

    public double getMinimumZoom() {
        return minimumScale;
    }

    public double getMaximumZoom() {
        return maximumScale;
    }

    public double getCurrentZoom() {
        return scaleTransform.getX();
    }

    public boolean isUpdatingZoom() {
        return isUpdatingZoom;
    }

    @Override
    protected void layoutChildren() {

        super.layoutChildren();
        if (isFirstLayout) {

            initScale();

            //center ScrollPane 
            me.setHvalue(0.5);
            isFirstLayout = false;
        }
    }

    protected void onZoomChanged() {
        for (IWorkspaceListener listener : listeners) {
            listener.zoomChanged();
        }
    }

    protected void onWallpaperSizeChanged() {
        updateMinimumZoom();
        animateScaleToValue(minimumScale, true);
        fireWallpaperSizeChangedEvent();
        onMinimumZoomChanged();
    }

    protected void onMinimumZoomChanged() {
        for (IWorkspaceListener listener : listeners) {
            listener.minimumZoomChanged();
        }
    }

    private void fireWallpaperSizeChangedEvent() {
        for (IWorkspaceListener listener : listeners) {
            listener.wallpaperSizeChanged();
        }
    }
}
