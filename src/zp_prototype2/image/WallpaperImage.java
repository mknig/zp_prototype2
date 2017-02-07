/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.image;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import zp_prototype2.WallpaperImageEffect;
import zp_prototype2.ZPScene;
import zp_prototype2.render.MemoryManager;
import zp_prototype2.render.PerformanceMeasure;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.Wallpaper;
import zp_prototype2.ZP_Properties;
import static zp_prototype2.panels.Controller_FileManager.wallpaper;

/**
 *
 * @author Rackor
 */
public class WallpaperImage extends Group implements IWallpaperImage {

    boolean fullyLoadedXXL = false;
    boolean isProportionsConstrained;
    int rotationAngle;
    Rotate rotationTransform;
    List<IWallpaperImageListener> listeners = new ArrayList<IWallpaperImageListener>();
    ArrayList<IDimensionListener> dimensionListeners = new ArrayList<>();
    URI fileURI;
    Rectangle2D customUserViewport;
    boolean xxlrendered = false;
    Rectangle clip;
    Rectangle recShadow;
    double borderSizeHorizontal = 0;
    double borderSizeVertical = 0;
    double borderArcHorizontal = 0;
    double borderArcVertical = 0;

    public WallpaperImage(IWallpaperImage wallpaperImage) {
        this(wallpaperImage.getImage(), (int) wallpaperImage.getRotation(), wallpaperImage.getFileURI());
    }

    public WallpaperImage(Image image, int rotationAngle, URI fileName) {

        //super(image);
        imageView = new ImageView(image);

        this.rotationAngle = rotationAngle;
        isProportionsConstrained = true;
        //rotationTransform = new Rotate(rotationAngle, 0, 0);
        rotationTransform = new Rotate(rotationAngle);
        getTransforms().add(rotationTransform);

        resetImageViewOrigin();

        this.fileURI = fileName;
        customUserViewport = null;

        //setCache(false);
//        iv.setCache(false);
//        iv.setSmooth(true);
//        setCacheHint(CacheHint.QUALITY);
//        iv.setCacheHint(CacheHint.QUALITY);
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.CADETBLUE);
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        shadow.setRadius(10);
        shadow.setBlurType(BlurType.GAUSSIAN);

        // imageView.setScaleX(0.9);
        //  imageView.setScaleY(0.9);
        //  imageView.setEffect(shadow);
        this.getChildren().addAll(imageView);

        // this.setScaleX(0.9);
        // this.setScaleY(0.9);
        // this.setEffect(shadow);
        //getChildren().add(clip);    
//        String style_inner = "-fx-border-color: red;"
//                + "-fx-border-width: 12;"
//               // + "-fx-border-style: dotted;"
//                + "";
//        setStyle(style_inner);
    }

    public WallpaperImage loadXXL(int sizeThumb, int size, double scaleFactor) throws Exception {

        try {

            PerformanceMeasure.setStartPoint("loadXXL" + getFileURI().hashCode());
            // PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "sizeThumb/size/scaleFactor/" + sizeThumb + "-" + size + "-" + scaleFactor);

            //++++++++++++++++++++++++++
            //load
            //set loadSize by free memory....
            Image image = null;
            double freeAll = MemoryManager.getFreeAll();
            double loadSize = 2500;
            Wallpaper wallpaper = (Wallpaper) ZP_Prototype2.getInstance().getWallpaper();

            //+++++++++++++++
            // 
//            if (wallpaper.imageIN < 2) {
//
//                //load best...
//                PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "WpImg.loadXXL_0 -->sizeLoad - FULL / " + MemoryManager.getMemStat());
//                image = new Image(fileURI.toURL().toString(), 0, 0, true, true, true);
//
//            } else {
            //load 
            //loadSize = (int) freeAll * i;//*10 ;//6;//3;//hangs at luis 10;// 3;    
            loadSize = this.getImageView().getFitWidth() * scaleFactor;

            double loadSize_height = 0;
            if (this.getImageView().getFitWidth() / this.getImageView().getFitHeight() >= 0) {
                loadSize_height = loadSize / (this.getImageView().getFitWidth() / this.getImageView().getFitHeight());
            } else {
                loadSize_height = loadSize / (this.getImageView().getFitHeight() / this.getImageView().getFitWidth());
            }

            //System.out.println("check: loadsize ->" + loadSize + "/" + loadSize_height);
            double i = 1;
            double step = loadSize / 100;
            while (!MemoryManager.isEnoughMemory(loadSize, loadSize_height, 0)) {

//cache disabled in buildZPV3            
//                if (ZP_Properties.getReadyImagesXXL().size() > 0) {
//                    ZP_Properties.getReadyImagesXXL().remove(ZP_Properties.getReadyImagesXXL().keySet().iterator().next());
//                    System.out.println("lessMem: clear cache imageReady size" + ZP_Properties.getReadyImagesXXL().size());
//                    // System.gc();
//                }
                if (i == 1) {
                    //System.gc();
                    //dont do it!!! cause stuck 
                } else {
                    loadSize = loadSize - step;
                    if (this.getImageView().getFitWidth() / this.getImageView().getFitHeight() >= 0) {
                        loadSize_height = loadSize / (this.getImage().getWidth() / this.getImage().getHeight());
                    } else {
                        loadSize_height = loadSize / (this.getImage().getHeight() / this.getImage().getWidth());
                    }
                    PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "iterate4loadsize -> " + loadSize + "/" + loadSize_height + " / Mem->" + MemoryManager.getMemStat());

//                
//                if (loadSize < 1500) {
//                    loadSize = (int) 1500;
//                    break;
//                }
                   
                    
                }
                i++;
            }

            //load image...
            image = new Image(fileURI.toURL().toString(), loadSize, 0, true, true, true);


            //progess loading...wait while() loading
            image.progressProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue ov,
                        Number old_val, Number new_val) {
                    if (new_val.doubleValue() == 1.0) {
                        fullyLoadedXXL = true;
                    }
                }
            });
            while (!fullyLoadedXXL) {
                Thread.sleep(10);
            }
            //reset for next...
            fullyLoadedXXL = false;
            PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "loadXXL_0->loaded(w/h/l)-" +image.getWidth() + "/" + image.getHeight() + "/" + (int)loadSize + "/"+(int)loadSize_height + "/"+MemoryManager.getMemStat());

            //++++++++++++++++++++++++++++
            //init...
            WallpaperImage ret = new WallpaperImage(image, this.rotationAngle, this.fileURI);
            //ret.setCache(false);
            //ret.setCacheHint(CacheHint.QUALITY);

            //++++++++++++++++++++++++++++
            //set Size....
//            System.out.println("loadXXL:node.sizeInLocal->" + this.getWidthInLocal() + "/" + this.getHeightInLocal());
//            System.out.println("loadXXL:node.getSize->" + this.getWidth() + "/" + this.getHeight());
//
//            System.out.println("loadXXL:node.boundsLayout->" + this.getLayoutBounds());
//            System.out.println("loadXXL:node.boundsParent->" + this.getBoundsInParent());
//            System.out.println("loadXXL:node.boundsLocal->" + this.getBoundsInLocal());
//
//
//            System.out.println("loadXXL:iv.fitSize->" + this.getImageView().getFitWidth() + "/" + this.getImageView().getFitHeight());
//            System.out.println("loadXXL:iv.boundsLayout->" + imageView.getLayoutBounds());
//            System.out.println("loadXXL:iv.boundsParent->" + imageView.getBoundsInParent());
//            System.out.println("loadXXL:iv.boundsLocal->" + imageView.getBoundsInLocal());
            if (true //false
                    //clip == null
                    ) {

                //ret.setWidth(this.getWidth() * scaleFactor);
                //ret.setHeight(this.getHeight() * scaleFactor);
                ret.setWidth(this.getImageView().getFitWidth() * scaleFactor);
                ret.setHeight(this.getImageView().getFitHeight() * scaleFactor);

                ret.getImageView().setFitWidth(this.getImageView().getFitWidth() * scaleFactor);
                ret.getImageView().setFitHeight(this.getImageView().getFitHeight() * scaleFactor);
                ret.resetImageViewOrigin();

                double closeGap = 2;
                double scaledWidth = this.getImageView().getFitWidth() * scaleFactor + closeGap;
                double scaledHeight = this.getImageView().getFitHeight() * scaleFactor + closeGap;

                ret.setWidthInLocal(scaledWidth);
                ret.setHeightInLocal(scaledHeight);

                // System.out.println("loadXXL: setSize width/height->" + scaledWidth + "/" + scaledHeight);
            } else {
                //++++++++++++++++++++++++++++
                //set Size....
                double closeGap = 0;
                double scaledWidth = this.getWidthInLocal() * scaleFactor + closeGap;
                double scaledHeight = this.getHeightInLocal() * scaleFactor + closeGap;

                //set size
                ret.setWidthInLocal(scaledWidth);
                ret.setHeightInLocal(scaledHeight);

                ret.setWidth(scaledWidth);
                ret.setHeight(scaledHeight);

                //ask in addBorder
                ret.getImageView().setFitWidth(scaledWidth);
                ret.getImageView().setFitHeight(scaledHeight);
                ret.resetImageViewOrigin();
            }
            //PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "wpImg.loadXXL_2" + MemoryManager.getMemStat());

            //+++++++++++++++++++++++++
            //set viewport..to loaded imagesSize
            double viewPort_Scale_XXL = image.getWidth() / imageView.getImage().getWidth();
            Rectangle2D viewPort_iv = this.imageView.getViewport();
            if (this.getViewport() != null) {
                Rectangle2D viewPortNEW;
                viewPortNEW = new Rectangle2D(
                        viewPort_iv.getMinX() * viewPort_Scale_XXL,
                        viewPort_iv.getMinY() * viewPort_Scale_XXL,
                        viewPort_iv.getWidth() * viewPort_Scale_XXL,
                        viewPort_iv.getHeight() * viewPort_Scale_XXL);

                ret.getImageView().setViewport(viewPortNEW);
            } else {
                ret.getImageView().setViewport(null);
            }
            //PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "wpImg.loadXXL_3" + MemoryManager.getMemStat());

            //+++++++++++++++++++++++++++++++
            //add Border...if Border set needs shift..
            //border
            if (clip != null) {
                ret.addBorder(borderSizeHorizontal * scaleFactor, borderSizeVertical * scaleFactor, borderArcHorizontal * scaleFactor, borderArcVertical * scaleFactor);
                //ret.setTranslateX(this.getTranslateX() * scaleFactor + borderSizeHorizontal * scaleFactor);
                //ret.setTranslateY(this.getTranslateY() * scaleFactor + borderSizeVertical * scaleFactor);
                //ret.setLayoutX(this.getLayoutX() * scaleFactor + borderSizeHorizontal * scaleFactor);
                //ret.setLayoutY(this.getLayoutY() * scaleFactor + borderSizeVertical * scaleFactor);
            }

            //+++++++++++++++++++++++++++++++
            // flip
            if (imageView.getScaleX() < 1) {
                ret.getImageView().setScaleX(ret.getImageView().getScaleX() * -1);
            }

            if (imageView.getScaleY() < 1) {
                ret.getImageView().setScaleY(ret.getImageView().getScaleY() * -1);
            }

            //++++++++++++++++++++++++++
            //SetEffect...
            Effect effect = this.imageView.getEffect();
            if (effect instanceof DropShadow) {
                DropShadow dropShadow = (DropShadow) effect;
                // dropShadow.setRadius(dropShadow.getRadius()*scaleFactor);
                dropShadow.setRadius(dropShadow.getRadius() * scaleFactor * scaleFactor);
                ret.getImageView().setEffect(dropShadow);

            }

            if (this.getEffect() instanceof PerspectiveTransform) {
                PerspectiveTransform t = (PerspectiveTransform) this.getEffect();
                PerspectiveTransform transXXL = new PerspectiveTransform();
                transXXL.setUlx(t.getUlx() * scaleFactor);
                transXXL.setUly(t.getUly() * scaleFactor);
                transXXL.setLlx(t.getLlx() * scaleFactor);
                transXXL.setLly(t.getLly() * scaleFactor);
                transXXL.setUrx(t.getUrx() * scaleFactor);
                transXXL.setUry(t.getUry() * scaleFactor);
                transXXL.setLrx(t.getLrx() * scaleFactor);
                transXXL.setLry(t.getLry() * scaleFactor);
                ret.setEffect(transXXL);
            }

            //PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "wpImg.loadXXL_END: loadSize" + loadSize + "Mem:" + MemoryManager.getMemStat());
            return ret;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    //++++++++++++++
    // Start Delegate ImageView
    //
    ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public Image getImage() {
        return imageView.getImage();
    }

    public Rectangle2D getViewport() {
        return imageView.getViewport();
    }

    public void setViewport(Rectangle2D rec) {
        imageView.setViewport(rec);
    }

    public ObjectProperty<Rectangle2D> viewportProperty() {
        return imageView.viewportProperty();
    }

    public void setFitWidth(double width) {
        imageView.setFitWidth(width);
    }

    public void setFitHeight(double height) {
        imageView.setFitHeight(height);
    }

    public double getWidth_IV() {
        //return getBoundsInParent().getWidth();
        //return iv.getFitWidth();
        return imageView.getBoundsInParent().getWidth();
    }

    public double getHeight_IV() {
        //return getBoundsInParent().getHeight();
        // return iv.getFitHeight();
        return imageView.getBoundsInParent().getHeight();
    }

    //
    // END Delegate ImageView
    //++++++++++++++++
    public Point2D getPosition() {
        return new Point2D(translateXProperty().get(), translateYProperty().get());
    }

    @Override
    public void addBorderX() {
        addBorder(borderSizeHorizontal, borderSizeVertical, borderArcHorizontal, borderArcVertical);
    }

    @Override
    public void addBorder(double horizontalBorder, double verticalBorder, double arcHeight, double arcWidth) {

        if (horizontalBorder < 0.1 && horizontalBorder < 0.1) {
            // if (horizontalBorder == 0 && horizontalBorder == 0) {
            borderSizeHorizontal = 0;
            borderSizeVertical = 0;
            borderArcHorizontal = 0;
            borderArcVertical = 0;
            setClip(null);
            return;
        }

        borderSizeHorizontal = horizontalBorder;
        borderSizeVertical = verticalBorder;
        borderArcHorizontal = arcHeight;
        borderArcVertical = arcWidth;

        double vpPosX = horizontalBorder;
        double vpPosY = verticalBorder;

        //double vpWidth = Math.round(imageView.getFitWidth() - horizontalBorder * 2);
        //double vpHeight = Math.round(imageView.getFitHeight() - verticalBorder * 2);
        double vpWidth = imageView.getFitWidth() - horizontalBorder * 2;
        double vpHeight = imageView.getFitHeight() - verticalBorder * 2;

        //double vpWidth = this.getWidth() - horizontalBorder * 2;
        //double vpHeight = this.getHeight()- verticalBorder * 2;
        System.out.println("BorderClip.imageView:" + imageView.getFitWidth() + "/" + imageView.getFitHeight());
        System.out.println("BorderClip:" + vpPosX + "/" + vpPosY + "/" + vpWidth + "/" + vpHeight);
        clip = new Rectangle(vpPosX, vpPosY, vpWidth, vpHeight);

        if (arcHeight > 0 || arcWidth > 0) {
            clip.setArcHeight(arcHeight);
            clip.setArcWidth(arcWidth);
        }

//        DropShadow shadow = new DropShadow();
//        shadow.setColor(Color.BLACK);
//        //shadow.setOffsetX(10);
//        //shadow.setOffsetY(10);
//        shadow.setRadius(10);
//        shadow.setBlurType(BlurType.GAUSSIAN);
//
//        clip.setEffect(shadow);//kanten transparent...
//        iv.setEffect(shadow);//nur bei viewPort<imagesize
        clip.setOpacity(1);

        //clip 2 Imageview
        //imageView.setClip(clip);
        setClip(clip);

    }

    private double getSourceImageWidth() {
        if (customUserViewport != null) {
            return customUserViewport.getWidth();
        } else {
            return imageView.getImage().getWidth();
        }
    }

    private double getSourceImageHeight() {
        if (customUserViewport != null) {
            return customUserViewport.getHeight();
        } else {
            return imageView.getImage().getHeight();
        }
    }

    @Override
    public void setWidthInLocal(double newWidth) {
        if (isProportionsConstrained) {
            double rateOfChange = newWidth / getWidthInLocal();
            imageView.setFitHeight(getHeightInLocal() * rateOfChange);
        }
        imageView.setFitWidth(newWidth);
        onDimensionsChanged();
    }

    @Override
    public void setHeightInLocal(double newHeight) {
        if (isProportionsConstrained) {
            double rateOfChange = newHeight / getHeightInLocal();
            imageView.setFitWidth(getWidthInLocal() * rateOfChange);
        }
        imageView.setFitHeight(newHeight);
        onDimensionsChanged();
    }

    @Override
    public void setWidth(double width) {
        if (isOrientationHorizontal()) {
            setWidthInLocal(width);
        } else if (isOrientationVertical()) {
            setHeightInLocal(width);
        }
    }

    @Override
    public void setHeight(double height) {
        if (isOrientationHorizontal()) {
            setHeightInLocal(height);
        } else if (isOrientationVertical()) {
            setWidthInLocal(height);
        }
    }

    public boolean isOrientationHorizontal() {
        double rotationValue = getRotation() % 360;
        return rotationValue == 0 || rotationValue == 180 || rotationValue == -180;
    }

    public boolean isOrientationVertical() {
        double rotationValue = getRotation() % 360;
        return rotationValue == 90 || rotationValue == 270 || rotationValue == -90 || rotationValue == -270;
    }

    @Override
    public Node getNodeRepresentation() {
        return this;
    }

    public void setIsProportionsConstrained(boolean isContrained) {
        isProportionsConstrained = isContrained;
    }

    public boolean getIsProportionsConstrained() {
        return isProportionsConstrained;
    }

    @Override
    public void rotateCW() {

        ZP_Prototype2.getInstance().getWallpaper().setBestFit_check(false);

        rotationAngle = rotationAngle % 360;
        rotationAngle += 90;

        System.out.println("rotateCW:" + rotationAngle + "/getRotation:" + getRotation());

        if (rotationAngle != 360) {
            resetImageViewOrigin();
        }

        rotationTransform.angleProperty().set(rotationAngle);
        onRotated();

        ZP_Prototype2.getInstance().getWallpaper().setBestFit_check(true);

    }

    @Override
    public void flipX() {
        double filp_scale = getImageView().getScaleX() * -1;
        getImageView().scaleXProperty().set(filp_scale);
    }

    @Override
    public void flipY() {
        double filp_scale = getImageView().getScaleY() * -1;
        getImageView().scaleYProperty().set(filp_scale);
    }

    @Override
    public void rotateCCW() {

        rotateCW();

//        System.out.println("rotateCW:" + rotationAngle);
//        rotationAngle = rotationAngle % 360;
//        rotationAngle -= 90;
//
//       if(rotationAngle!=360){ 
//        resetImageViewOrigin();
//        }
//
//        rotationTransform.angleProperty().set(rotationAngle);
//        onRotated();
    }

    protected void onRotated() {
        // resetImageViewOrigin();
        for (IWallpaperImageListener listener : listeners) {
            //cause rearrangeImages...
            listener.imageRotated(this);
        }

        //update Popup...
        ZP_Prototype2.getInstance().imageSelectedPanelController.setImage(this);

    }

    private void resetImageViewOrigin() {
        double rotationValue = getRotation() % 360;

        if (rotationValue == 0) {
            getNodeRepresentation().setLayoutX(0);
            getNodeRepresentation().setLayoutY(0);

        } else if (rotationValue == 270 || rotationValue == -90) {
            getNodeRepresentation().setLayoutX(0);
            getNodeRepresentation().setLayoutY(getHeight());

        } else if (rotationValue == 180 || rotationValue == -180) {
            getNodeRepresentation().setLayoutX(getWidth());
            getNodeRepresentation().setLayoutY(getHeight());

        } else if (rotationValue == 90 || rotationValue == -270) {
            getNodeRepresentation().setLayoutX(getWidth());
            getNodeRepresentation().setLayoutY(0);
        }
    }

    @Override
    public void resetViewPortAndSize() {

        imageView.setViewport(customUserViewport);

        imageView.setFitHeight(0);
        imageView.setFitWidth(0);

        //remove clip...
        setClip(null);
    }

    private void onDimensionsChanged() {
        resetImageViewOrigin();
        for (IDimensionListener listener : dimensionListeners) {
            listener.dimensionsChanged(this);
        }
    }

    @Override
    public void cropHorizontally(double cropSize) {
        if (isOrientationVertical()) {
            internalCropVertically(cropSize);
        } else {
            internalCropHorizontal(cropSize);
        }
        resetImageViewOrigin();
    }

    private void internalCropHorizontal(double cropSize) {
        try {
            double sourceImageWidth = getSourceImageWidth();
            double ratio = sourceImageWidth / getWidthInLocal();
            double amountToCrop = cropSize * ratio / 2d;

            Rectangle2D newViewport;
            if (customUserViewport != null) {
                // newViewport = new Rectangle2D(customUserViewport.getMinX() + Math.round(amountToCrop), customUserViewport.getMinY(), Math.round(sourceImageWidth - amountToCrop * 2), getSourceImageHeight());
                newViewport = new Rectangle2D(customUserViewport.getMinX() + amountToCrop, customUserViewport.getMinY(), sourceImageWidth - amountToCrop * 2, getSourceImageHeight());
            } else {
                //newViewport = new Rectangle2D(Math.round(amountToCrop), 0, Math.round(sourceImageWidth - amountToCrop * 2), getSourceImageHeight());
                double viewPortWidth = sourceImageWidth - amountToCrop * 2;
                if (viewPortWidth < 0) {
                    return;
                    //viewPortWidth=sourceImageWidth;
                }
                double viewPortHeight = getSourceImageHeight();
                // System.out.println("internalCropH amount/width/height: " +amountToCrop+"/"+viewPortWidth+"/"+viewPortHeight);
                newViewport = new Rectangle2D(amountToCrop, 0, viewPortWidth, viewPortHeight);

            }

            imageView.setViewport(newViewport);
            imageView.setFitWidth(getWidthInLocal() - cropSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cropVertically(double cropSize) {
        if (isOrientationVertical()) {
            internalCropHorizontal(cropSize);
        } else {
            internalCropVertically(cropSize);
        }
        resetImageViewOrigin();
    }

    private void internalCropVertically(double cropSize) {
        try {
            double sourceImageHeight = getSourceImageHeight();
            double ratio = sourceImageHeight / getHeightInLocal();

            double amountToCrop = cropSize * ratio / 2d;

            Rectangle2D newViewport;
            if (customUserViewport != null) {
                // newViewport = new Rectangle2D(customUserViewport.getMinX(), customUserViewport.getMinY() + Math.round(amountToCrop), getSourceImageWidth(), Math.round(sourceImageHeight - amountToCrop * 2));
                newViewport = new Rectangle2D(customUserViewport.getMinX(), customUserViewport.getMinY() + amountToCrop, getSourceImageWidth(), sourceImageHeight - amountToCrop * 2);

            } else {
                //newViewport = new Rectangle2D(0, Math.round(amountToCrop), getSourceImageWidth(), Math.round(sourceImageHeight - amountToCrop * 2));
                newViewport = new Rectangle2D(0, amountToCrop, getSourceImageWidth(), sourceImageHeight - amountToCrop * 2);

            }

            imageView.setViewport(newViewport);
            imageView.setFitHeight(getHeightInLocal() - cropSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cropByPercentage(double percentage) {
        crop(percentage * imageView.getImage().getWidth(), percentage * imageView.getImage().getHeight());
    }

    @Override
    public void crop(double horizontalCropAmount, double verticalCropAmount) {
        Rectangle2D viewport = imageView.getViewport();
        double currentHorizontalCrop;
        double currentVerticalCrop;
        double viewportWidth;
        double viewportHeight;

        if (viewport == null) {
            currentHorizontalCrop = 0;
            currentVerticalCrop = 0;
            viewportWidth = imageView.getImage().getWidth();
            viewportHeight = imageView.getImage().getHeight();
        } else {
            currentHorizontalCrop = imageView.getImage().getWidth() - imageView.getViewport().getWidth();
            currentVerticalCrop = imageView.getImage().getHeight() - imageView.getViewport().getHeight();
            viewportWidth = imageView.getViewport().getWidth();
            viewportHeight = imageView.getViewport().getHeight();
        }

        double vpPosX = currentHorizontalCrop / 2d + horizontalCropAmount / 2d;
        double vpPosY = currentVerticalCrop / 2d + verticalCropAmount / 2d;
        double vpWidth = viewportWidth - horizontalCropAmount;
        double vpHeight = viewportHeight - verticalCropAmount;

        System.out.println("setViewport:" + vpPosX + "/" + vpPosY + "/" + vpWidth + "/" + vpHeight);
        viewport = new Rectangle2D(vpPosX, vpPosY, vpWidth, vpHeight);
        // setPreserveRatio(false);
        imageView.setViewport(viewport);
        // fitWidthProperty().set(100);

    }

    @Override
    public void setCustomUserViewport(Rectangle2D viewport) {
        customUserViewport = viewport;
        imageView.setViewport(customUserViewport);
    }

    @Override
    public void moveViewport(double x, double y) {
        Rectangle2D currentViewport = imageView.getViewport();
        if (currentViewport == null) {
            return;
        }

        double imageWidth = imageView.getImage().getWidth();
        double imageHeight = imageView.getImage().getHeight();
        double viewportWidth = currentViewport.getWidth();
        double viewportHeight = currentViewport.getHeight();

        double newViewportX = currentViewport.getMinX() + x;
        double newViewportY = currentViewport.getMinY() + y;
        if (newViewportX + viewportWidth > imageWidth) {
            newViewportX = imageWidth - viewportWidth;
        } else if (newViewportX < 0) {
            newViewportX = 0;
        }
        if (newViewportY + viewportHeight > imageHeight) {
            newViewportY = imageHeight - viewportHeight;
        } else if (newViewportY < 0) {
            newViewportY = 0;
        }

        Rectangle2D newViewport = new Rectangle2D(newViewportX, newViewportY, viewportWidth, viewportHeight);
        imageView.setViewport(newViewport);

    }

    //+++++++++++++++
    // stupidos
    public URI getFileURI() {
        return fileURI;
    }

    public boolean isXxlrendered() {
        return xxlrendered;
    }

    public void setXxlrendered(boolean xxlrendered) {
        this.xxlrendered = xxlrendered;
    }

    @Override
    public void addListener(IWallpaperImageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IWallpaperImageListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void addDimensionListener(IDimensionListener dimensionListener) {
        dimensionListeners.add(dimensionListener);
    }

    @Override
    public void removeDimensionListener(IDimensionListener dimensionListener) {
        dimensionListeners.remove(dimensionListener);
    }

    @Override
    public double getRotation() {
        return rotationAngle;
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
    public double getWidth() {
        return getBoundsInParent().getWidth();
        //return getWidth_IV();
        //return getBoundsInLocal().getWidth();
    }

    @Override
    public double getHeight() {
        return getBoundsInParent().getHeight();
        // return getHeight_IV();
        // return getBoundsInLocal().getHeight();
    }

    private double getWidthInLocal() {
        return getBoundsInLocal().getWidth();

    }

    private double getHeightInLocal() {
        return getBoundsInLocal().getHeight();
    }

    public double getBorderSizeHorizontal() {
        return borderSizeHorizontal;
    }

    public void setBorderSizeHorizontal(double borderSizeHorizontal) {
        this.borderSizeHorizontal = borderSizeHorizontal;
    }

    public double getBorderSizeVertical() {
        return borderSizeVertical;
    }

    public void setBorderSizeVertical(double borderSizeVertical) {
        this.borderSizeVertical = borderSizeVertical;
    }
}
