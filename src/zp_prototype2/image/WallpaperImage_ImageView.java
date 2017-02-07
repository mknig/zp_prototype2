///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package zp_prototype2.image;
//
//import java.io.Serializable;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Node;
//import javafx.scene.effect.BlurType;
//import javafx.scene.effect.DropShadow;
//import javafx.scene.effect.Effect;
//import javafx.scene.effect.Glow;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.image.ImageViewBuilder;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.transform.Rotate;
//import zp_prototype2.IWallpaper;
//import zp_prototype2.render.MemoryManager;
//import zp_prototype2.render.PerformanceMeasure;
//
///**
// *
// * @author Rackor
// */
//public class WallpaperImage extends ImageView implements IWallpaperImage {
//
// boolean isProportionsConstrained;
//    int rotationAngle;
//    Rotate rotationTransform;
//    List<IWallpaperImageListener> listeners = new ArrayList<IWallpaperImageListener>();
//    ArrayList<IDimensionListener> dimensionListeners = new ArrayList<>();
//    URI fileURI;
//    Rectangle2D customUserViewport;
//    boolean xxlrendered = false;
//
//    public boolean isXxlrendered() {
//        return xxlrendered;
//    }
//
//    public void setXxlrendered(boolean xxlrendered) {
//        this.xxlrendered = xxlrendered;
//    }
//
//    @Override
//    public void addBorder(double horizontalCropAmount, double verticalCropAmount) {
////        borderSizeHorizontal = horizontalCropAmount;
////        borderSizeVertical = verticalCropAmount;
////
////        Rectangle2D viewport = iv.getViewport();
////        double vpPosX = horizontalCropAmount;
////        double vpPosY = verticalCropAmount;
////        double vpWidth = iv.getFitWidth() - horizontalCropAmount * 2;
////        double vpHeight = iv.getFitHeight() - verticalCropAmount * 2;
////        System.out.println("clip:" + vpPosX + "/" + vpPosY + "/" + vpWidth + "/" + vpHeight);
////        clip = new Rectangle(vpPosX, vpPosY, vpWidth, vpHeight);
////        //clip.setArcHeight(20);
////        //clip.setArcWidth(20);
////
////
////        DropShadow shadow = new DropShadow();
////        shadow.setColor(Color.BLACK);
////        //shadow.setOffsetX(10);
////        //shadow.setOffsetY(10);
////        shadow.setRadius(10);
////        shadow.setBlurType(BlurType.GAUSSIAN);
////
////        //S  clip.setEffect(shadow);//kanten transparent...
////        //iv.setEffect(shadow);//nur bei viewPort<imagesize
////
////        //clip.setOpacity(1);
////        setClip(clip);
//    }
//
//    public ImageView getImageView() {
//        return this;
//    }
//
//    public WallpaperImage(IWallpaperImage wallpaperImage) {
//        this(wallpaperImage.getImage(), (int) wallpaperImage.getRotation(), wallpaperImage.getFileURI());
//    }
//
//    public WallpaperImage(Image image, int rotationAngle, URI fileName) {
//        super(image);
//        this.rotationAngle = rotationAngle;
//        isProportionsConstrained = true;
//        rotationTransform = new Rotate(rotationAngle, 0, 0);
//        getTransforms().add(rotationTransform);
//        resetImageViewOrigin();
//        this.fileURI = fileName;
//
//        customUserViewport = null;
//        //customUserViewport = new Rectangle2D(1000, 550, getSourceImageWidth() , getSourceImageHeight());
//        //cropByPercentage(-0.1);
//
//        DropShadow shadow = new DropShadow();
//        shadow.setColor(Color.BLACK);
//        //shadow.setOffsetX(10);
//        //shadow.setOffsetY(10);
//        shadow.setRadius(100);
//        shadow.setBlurType(BlurType.GAUSSIAN);
//        //  this.setEffect(shadow);
//
//
//    }
//
//    public WallpaperImage loadXXL(int sizeThumb, int size, double scaleFactor) throws Exception {
//
//        WallpaperImage ret = new WallpaperImage(this);
//        Image image = new Image(fileURI.toURL().toString(), 0, size, true, true, false);
//
//        System.out.println("delta width: attLoad->" + sizeThumb + "/new->" + size);
//
//
//        ret.setImage(image);
//        ret.setWidth(this.getWidth() * scaleFactor);
//        ret.setHeight(this.getHeight() * scaleFactor);
//        ret.setWidthInLocal(this.getWidthInLocal() * scaleFactor);
//        ret.setHeightInLocal(this.getHeightInLocal() * scaleFactor);
//        ret.setFitWidth(this.getFitWidth() * scaleFactor);
//        ret.setFitHeight(this.getFitHeight() * scaleFactor);
//
//        // ret.setScaleX(this.scaleXProperty().doubleValue() * scaleFactor);
//        // ret.setScaleY(this.scaleYProperty().doubleValue() * scaleFactor);
//
//        //+++++++++++++++++++++++++
//        //viewport..to loaded imagesSize
//        //
//        double viewPort_Scale_XXL = image.getWidth() / getImage().getWidth();// image.getWidth() / getSourceImageWidth();
//        Rectangle2D viewPort_iv = this.getViewport();
//        if (this.getViewport() != null) {
//            Rectangle2D viewPortNEW;
//            viewPortNEW = new Rectangle2D(viewPort_iv.getMinX() * viewPort_Scale_XXL, viewPort_iv.getMinY() * viewPort_Scale_XXL, viewPort_iv.getWidth() * viewPort_Scale_XXL, viewPort_iv.getHeight() * viewPort_Scale_XXL);
//            ret.getImageView().setViewport(viewPortNEW);
//        } else {
//            ret.getImageView().setViewport(null);
//        }
//
//        PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "WallpaperImage.loadXXL_3" + MemoryManager.getMemStat());
//
//
//        return ret;
//
//    }
//
//    @Override
//    public double getWidth() {
//        return getBoundsInParent().getWidth();
//    }
//
//    @Override
//    public double getHeight() {
//        return getBoundsInParent().getHeight();
//    }
//
//    private double getWidthInLocal() {
//        return getBoundsInLocal().getWidth();
//    }
//
//    private double getHeightInLocal() {
//        return getBoundsInLocal().getHeight();
//    }
//
//    private double getSourceImageWidth() {
//        if (customUserViewport != null) {
//            return customUserViewport.getWidth();
//        } else {
//            return getImage().getWidth();
//        }
//    }
//
//    private double getSourceImageHeight() {
//        if (customUserViewport != null) {
//            return customUserViewport.getHeight();
//        } else {
//            return getImage().getHeight();
//        }
//    }
//
//    @Override
//    public void setWidthInLocal(double newWidth) {
//        if (isProportionsConstrained) {
//            double rateOfChange = newWidth / getWidthInLocal();
//            setFitHeight(getHeightInLocal() * rateOfChange);
//        }
//        this.setFitWidth(newWidth);
//        onDimensionsChanged();
//    }
//
//    @Override
//    public void setHeightInLocal(double newHeight) {
//        if (isProportionsConstrained) {
//            double rateOfChange = newHeight / getHeightInLocal();
//            setFitWidth(getWidthInLocal() * rateOfChange);
//        }
//        this.setFitHeight(newHeight);
//        onDimensionsChanged();
//    }
//
//    @Override
//    public void setWidth(double width) {
//        if (isOrientationHorizontal()) {
//            setWidthInLocal(width);
//        } else if (isOrientationVertical()) {
//            setHeightInLocal(width);
//        }
//    }
//
//    @Override
//    public void setHeight(double height) {
//        if (isOrientationHorizontal()) {
//            setHeightInLocal(height);
//        } else if (isOrientationVertical()) {
//            setWidthInLocal(height);
//        }
//    }
//
//    public boolean isOrientationHorizontal() {
//        double rotationValue = getRotation() % 360;
//        return rotationValue == 0 || rotationValue == 180 || rotationValue == -180;
//    }
//
//    public boolean isOrientationVertical() {
//        double rotationValue = getRotation() % 360;
//        return rotationValue == 90 || rotationValue == 270 || rotationValue == -90 || rotationValue == -270;
//    }
//
//    @Override
//    public Node getNodeRepresentation() {
//        return this;
//    }
//
//    public void setIsProportionsConstrained(boolean isContrained) {
//        isProportionsConstrained = isContrained;
//    }
//
//    public boolean getIsProportionsConstrained() {
//        return isProportionsConstrained;
//    }
//
//    @Override
//    public void rotateCW() {
//        rotationAngle += 90;
//        rotationTransform.angleProperty().set(rotationAngle);
//        onRotated();
//    }
//
//    @Override
//    public void rotateCCW() {
//        rotationAngle -= 90;
//        rotationTransform.angleProperty().set(rotationAngle);
//        onRotated();
//    }
//
//    @Override
//    public double getRotation() {
//        return rotationAngle;
//    }
//
//    @Override
//    public void setTranslate(double x, double y) {
//        setTranslateX(x);
//        setTranslateY(y);
//    }
//
//    @Override
//    public void setXTranslation(double x) {
//        setTranslateX(x);
//    }
//
//    @Override
//    public void setYTranslation(double y) {
//        setTranslateY(y);
//    }
//
//    @Override
//    public void resetViewPortAndSize() {
//        setViewport(customUserViewport);
//        setFitHeight(0);
//        setFitWidth(0);
//    }
//
//    @Override
//    public void addListener(IWallpaperImageListener listener) {
//        listeners.add(listener);
//    }
//
//    @Override
//    public void removeListener(IWallpaperImageListener listener) {
//        listeners.remove(listener);
//    }
//
//    @Override
//    public void addDimensionListener(IDimensionListener dimensionListener) {
//        dimensionListeners.add(dimensionListener);
//    }
//
//    @Override
//    public void removeDimensionListener(IDimensionListener dimensionListener) {
//        dimensionListeners.remove(dimensionListener);
//    }
//
//    private void onDimensionsChanged() {
//        resetImageViewOrigin();
//        for (IDimensionListener listener : dimensionListeners) {
//            listener.dimensionsChanged(this);
//        }
//    }
//
//    protected void onRotated() {
//        resetImageViewOrigin();
//        for (IWallpaperImageListener listener : listeners) {
//            listener.imageRotated(this);
//        }
//    }
//
//    private void resetImageViewOrigin() {
//        double rotationValue = getRotation() % 360;
//
//        if (rotationValue == 0) {
//            getNodeRepresentation().setLayoutX(0);
//            getNodeRepresentation().setLayoutY(0);
//        } else if (rotationValue == 270 || rotationValue == -90) {
//            getNodeRepresentation().setLayoutX(0);
//            getNodeRepresentation().setLayoutY(getHeight());
//        } else if (rotationValue == 180 || rotationValue == -180) {
//            getNodeRepresentation().setLayoutX(getWidth());
//            getNodeRepresentation().setLayoutY(getHeight());
//        } else if (rotationValue == 90 || rotationValue == -270) {
//            getNodeRepresentation().setLayoutX(getWidth());
//            getNodeRepresentation().setLayoutY(0);
//        }
//    }
//
//    @Override
//    public void cropHorizontally(double cropSize) {
//        if (isOrientationVertical()) {
//            internalCropVertically(cropSize);
//        } else {
//            internalCropHorizontal(cropSize);
//        }
//        resetImageViewOrigin();
//    }
//
//    private void internalCropHorizontal(double cropSize) {
//
//        double sourceImageWidth = getSourceImageWidth();
//        double ratio = sourceImageWidth / getWidthInLocal();
//        double amountToCrop = cropSize * ratio / 2d;
//
//        Rectangle2D newViewport;
//        if (customUserViewport != null) {
//            newViewport = new Rectangle2D(customUserViewport.getMinX() + Math.round(amountToCrop), customUserViewport.getMinY(), Math.round(sourceImageWidth - amountToCrop * 2), getSourceImageHeight());
//        } else {
//            newViewport = new Rectangle2D(Math.round(amountToCrop), 0, Math.round(sourceImageWidth - amountToCrop * 2), getSourceImageHeight());
//        }
//
//        setViewport(newViewport);
//        setFitWidth(getWidthInLocal() - cropSize);
//
//    }
//
//    @Override
//    public void cropVertically(double cropSize) {
//        if (isOrientationVertical()) {
//            internalCropHorizontal(cropSize);
//        } else {
//            internalCropVertically(cropSize);
//        }
//        resetImageViewOrigin();
//    }
//
//    private void internalCropVertically(double cropSize) {
//        double sourceImageHeight = getSourceImageHeight();
//        double ratio = sourceImageHeight / getHeightInLocal();
//
//        double amountToCrop = cropSize * ratio / 2d;
//
//        Rectangle2D newViewport;
//        if (customUserViewport != null) {
//            newViewport = new Rectangle2D(customUserViewport.getMinX(), customUserViewport.getMinY() + Math.round(amountToCrop), getSourceImageWidth(), Math.round(sourceImageHeight - amountToCrop * 2));
//        } else {
//            newViewport = new Rectangle2D(0, Math.round(amountToCrop), getSourceImageWidth(), Math.round(sourceImageHeight - amountToCrop * 2));
//        }
//
//        setViewport(newViewport);
//        setFitHeight(getHeightInLocal() - cropSize);
//    }
//
//    @Override
//    public void cropByPercentage(double percentage) {
//        crop(percentage * getImage().getWidth(), percentage * getImage().getHeight());
//    }
//
//    @Override
//    public void crop(double horizontalCropAmount, double verticalCropAmount) {
//        Rectangle2D viewport = getViewport();
//        double currentHorizontalCrop;
//        double currentVerticalCrop;
//        double viewportWidth;
//        double viewportHeight;
//
//        if (viewport == null) {
//            currentHorizontalCrop = 0;
//            currentVerticalCrop = 0;
//            viewportWidth = getImage().getWidth();
//            viewportHeight = getImage().getHeight();
//        } else {
//            currentHorizontalCrop = getImage().getWidth() - getViewport().getWidth();
//            currentVerticalCrop = getImage().getHeight() - getViewport().getHeight();
//            viewportWidth = getViewport().getWidth();
//            viewportHeight = getViewport().getHeight();
//        }
//
//
//        viewport = new Rectangle2D(Math.round(currentHorizontalCrop / 2d) + Math.round(horizontalCropAmount / 2d), Math.round(currentVerticalCrop / 2d) + Math.round(verticalCropAmount / 2d), viewportWidth - horizontalCropAmount, viewportHeight - verticalCropAmount);
//        setViewport(viewport);
//    }
//
//    @Override
//    public void setCustomUserViewport(Rectangle2D viewport) {
//        customUserViewport = viewport;
//        setViewport(customUserViewport);
//    }
//
//    @Override
//    public void moveViewport(double x, double y) {
//        Rectangle2D currentViewport = getViewport();
//        if (currentViewport == null) {
//            return;
//        }
//
//        double imageWidth = getImage().getWidth();
//        double imageHeight = getImage().getHeight();
//        double viewportWidth = currentViewport.getWidth();
//        double viewportHeight = currentViewport.getHeight();
//
//        double newViewportX = currentViewport.getMinX() + x;
//        double newViewportY = currentViewport.getMinY() + y;
//        if (newViewportX + viewportWidth > imageWidth) {
//            newViewportX = imageWidth - viewportWidth;
//        } else if (newViewportX < 0) {
//            newViewportX = 0;
//        }
//        if (newViewportY + viewportHeight > imageHeight) {
//            newViewportY = imageHeight - viewportHeight;
//        } else if (newViewportY < 0) {
//            newViewportY = 0;
//        }
//
//
//        Rectangle2D newViewport = new Rectangle2D(newViewportX, newViewportY, viewportWidth, viewportHeight);
//        setViewport(newViewport);
//
//    }
//
//    public URI getFileURI() {
//        return fileURI;
//    }
//}
