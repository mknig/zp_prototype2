///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package zp_prototype2.image;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import javafx.beans.property.ObjectProperty;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.effect.BlurType;
//import javafx.scene.effect.DropShadow;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.transform.Rotate;
//import zp_prototype2.render.MemoryManager;
//import zp_prototype2.render.PerformanceMeasure;
//
///**
// *
// * @author Rackor
// */
//public class WallpaperImage_Group extends Group implements IWallpaperImage {
//
//    boolean isProportionsConstrained;
//    int rotationAngle;
//    Rotate rotationTransform;
//    List<IWallpaperImageListener> listeners = new ArrayList<IWallpaperImageListener>();
//    ArrayList<IDimensionListener> dimensionListeners = new ArrayList<>();
//    URI fileURI;
//    Rectangle2D customUserViewport;
//    boolean xxlrendered = false;
//    Rectangle clip;
//    double borderSizeHorizontal = 0;
//    double borderSizeVertical = 0;
//    //++++++++++++++
//    // Start Delegate ImageView
//    //
//    ImageView iv;
//
//    public ImageView getImageView() {
//        return iv;
//    }
//
//    public Image getImage() {
//        return iv.getImage();
//    }
//
//    public Rectangle2D getViewport() {
//        return iv.getViewport();
//    }
//
//    public void setViewport(Rectangle2D rec) {
//        iv.setViewport(rec);
//    }
//
//    public ObjectProperty<Rectangle2D> viewportProperty() {
//        return iv.viewportProperty();
//    }
//
//    public void setFitWidth(double width) {
//        iv.setFitWidth(width);
//    }
//
//    public void setFitHeight(double height) {
//        iv.setFitHeight(height);
//    }
//
//    //
//    // END Delegate ImageView
//    //++++++++++++++++
//    public WallpaperImage_Group(IWallpaperImage wallpaperImage) {
//        this(wallpaperImage.getImage(), (int) wallpaperImage.getRotation(), wallpaperImage.getFileURI());
//    }
//
//    public WallpaperImage_Group(Image image, int rotationAngle, URI fileName) {
//
//        //super(image);
//        iv = new ImageView(image);
//
//        this.rotationAngle = rotationAngle;
//        isProportionsConstrained = true;
//        rotationTransform = new Rotate(rotationAngle, 0, 0);
//        getTransforms().add(rotationTransform);
//        resetImageViewOrigin();
//        this.fileURI = fileName;
//        customUserViewport = null;
//        setCache(true);
//
//
//        clip = new Rectangle(0, 0, 0, 0);
//
////        DropShadow shadow = new DropShadow();
////        shadow.setColor(Color.BLACK);
////        //shadow.setOffsetX(10);
////        //shadow.setOffsetY(10);
////        shadow.setRadius(10);
////        shadow.setBlurType(BlurType.GAUSSIAN);
////        clip.setEffect(shadow);
//
//
//        this.getChildren().addAll(iv);
//        //getChildren().add(clip);    
//
//
//    }
//
//    public WallpaperImage_Group loadXXL(int sizeThumb, int size, double scaleFactor) throws Exception {
//
//
//        PerformanceMeasure.setStartPoint("loadXXL" + getFileURI().hashCode());
//
//        // System.out.println("load ImageXXL: size" + size +" ->" + getFileURI());
//        PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "WallpaperImage.loadXXL_Start" + MemoryManager.getMemStat());
//
//        //  final Image image = new Image(fileUrl, 0, imgSize, true, true, true);
//        Image image = new Image(fileURI.toURL().toString(), 0, size, true, true, false);
//        //WallpaperImage ret = new WallpaperImage(this);
//        WallpaperImage_Group ret = new WallpaperImage_Group(image, this.rotationAngle, this.fileURI);
//
//        PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "WallpaperImage.loadXXL_1" + MemoryManager.getMemStat());
//
//        //ret.getImageView().setImage(image);
//        ret.setWidth(this.getWidth() * scaleFactor);
//        ret.setHeight(this.getHeight() * scaleFactor);
//
//        ret.getImageView().setFitWidth(this.getImageView().getFitWidth() * scaleFactor);
//        ret.getImageView().setFitHeight(this.getImageView().getFitHeight() * scaleFactor);
//
//
////        ret.setWidthInLocal(this.getWidthInLocal() * scaleFactor);
////       ret.setHeightInLocal(this.getHeightInLocal() * scaleFactor);
//
//
//        PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "WallpaperImage.loadXXL_2" + MemoryManager.getMemStat());
//
//        //+++++++++++++++++++++++++
//        //viewport..to loaded imagesSize
//        //
//
//        double viewPort_Scale_XXL =image.getWidth() / iv.getImage().getWidth();// image.getWidth() / getSourceImageWidth();
//        Rectangle2D viewPort_iv = this.iv.getViewport();
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
//        //+++++++++++++++++++++++++
//        //border by nodeClip...
//        Rectangle clip = (Rectangle) this.getClip();
//        if (clip != null) {
//            Rectangle clipXXL = new Rectangle(clip.getX() * scaleFactor, clip.getY() * scaleFactor, clip.getWidth() * scaleFactor, clip.getHeight() * scaleFactor);
//
//            clipXXL.setArcHeight(clip.getArcHeight() * scaleFactor);
//            clipXXL.setArcWidth(clip.getArcWidth() * scaleFactor);
//
//            DropShadow shadow = new DropShadow();
//            shadow.setColor(Color.BLACK);
//            //shadow.setOffsetX(10);
//            //shadow.setOffsetY(10);
//            shadow.setRadius(10 * scaleFactor);
//            shadow.setBlurType(BlurType.GAUSSIAN);
//            // clipXXL.setEffect(shadow);
//            ret.setClip(clipXXL);
//        }
//
//        PerformanceMeasure.measurePoint("loadXXL" + getFileURI().hashCode(), "WallpaperImage.loadXXL_END" + MemoryManager.getMemStat());
//        return ret;
//
//    }
//
//    private double getSourceImageWidth() {
//        if (customUserViewport != null) {
//            return customUserViewport.getWidth();
//        } else {
//            return iv.getImage().getWidth();
//        }
//    }
//
//    private double getSourceImageHeight() {
//        if (customUserViewport != null) {
//            return customUserViewport.getHeight();
//        } else {
//            return iv.getImage().getHeight();
//        }
//    }
//
//    @Override
//    public void setWidthInLocal(double newWidth) {
//        if (isProportionsConstrained) {
//            double rateOfChange = newWidth / getWidthInLocal();
//            iv.setFitHeight(getHeightInLocal() * rateOfChange);
//        }
//        iv.setFitWidth(newWidth);
//        onDimensionsChanged();
//    }
//
//    @Override
//    public void setHeightInLocal(double newHeight) {
//        if (isProportionsConstrained) {
//            double rateOfChange = newHeight / getHeightInLocal();
//            iv.setFitWidth(getWidthInLocal() * rateOfChange);
//        }
//        iv.setFitHeight(newHeight);
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
//    public void resetViewPortAndSize() {
//        iv.setViewport(customUserViewport);
//        iv.setFitHeight(0);
//        iv.setFitWidth(0);
//
//        //remove clip...
//        setClip(null);
//
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
//
//        //       double amountToCrop = Math.round(cropSize) * Math.round(ratio / 2d);
//        double amountToCrop = cropSize * ratio / 2d;
//
//
////        System.out.println("crop amounttoCrop->" + amountToCrop);
////        amountToCrop = Math.round(amountTogetSourceImageWidthCrop);
////        System.out.println("crop amounttoCrop->" + amountToCrop + "rounded");
//
//        Rectangle2D newViewport;
//        if (customUserViewport != null) {
//            newViewport = new Rectangle2D(customUserViewport.getMinX() + Math.round(amountToCrop), customUserViewport.getMinY(), Math.round(sourceImageWidth - amountToCrop * 2), getSourceImageHeight());
//            //newViewport = new Rectangle2D(customUserViewport.getMinX() + amountToCrop, customUserViewport.getMinY(), sourceImageWidth - amountToCrop * 2, getSourceImageHeight());
//        } else {
//            newViewport = new Rectangle2D(Math.round(amountToCrop), 0, Math.round(sourceImageWidth - amountToCrop * 2), getSourceImageHeight());
//            //newViewport = new Rectangle2D(amountToCrop, 0, sourceImageWidth - amountToCrop * 2, getSourceImageHeight());
//
//        }
//
//        iv.setViewport(newViewport);
//        iv.setFitWidth(getWidthInLocal() - cropSize);
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
//            //  newViewport = new Rectangle2D(customUserViewport.getMinX(), customUserViewport.getMinY() + Math.round(amountToCrop), getSourceImageWidth(), Math.round(sourceImageHeight - amountToCrop * 2));
//            newViewport = new Rectangle2D(customUserViewport.getMinX(), customUserViewport.getMinY() + amountToCrop, getSourceImageWidth(), sourceImageHeight - amountToCrop * 2);
//        } else {
//            //  newViewport = new Rectangle2D(0, Math.round(amountToCrop), getSourceImageWidth(), Math.round(sourceImageHeight - amountToCrop * 2));
//            newViewport = new Rectangle2D(0, amountToCrop, getSourceImageWidth(), sourceImageHeight - amountToCrop * 2);
//        }
//
//        iv.setViewport(newViewport);
//        iv.setFitHeight(getHeightInLocal() - cropSize);
//    }
//
//    @Override
//    public void cropByPercentage(double percentage) {
//        crop(percentage * iv.getImage().getWidth(), percentage * iv.getImage().getHeight());
//    }
//
//    @Override
//    public void crop(double horizontalCropAmount, double verticalCropAmount) {
//        Rectangle2D viewport = iv.getViewport();
//        double currentHorizontalCrop;
//        double currentVerticalCrop;
//        double viewportWidth;
//        double viewportHeight;
//
//        if (viewport == null) {
//            currentHorizontalCrop = 0;
//            currentVerticalCrop = 0;
//            viewportWidth = iv.getImage().getWidth();
//            viewportHeight = iv.getImage().getHeight();
//        } else {
//            currentHorizontalCrop = iv.getImage().getWidth() - iv.getViewport().getWidth();
//            currentVerticalCrop = iv.getImage().getHeight() - iv.getViewport().getHeight();
//            viewportWidth = iv.getViewport().getWidth();
//            viewportHeight = iv.getViewport().getHeight();
//        }
//
////        double vpPosX = Math.round(currentHorizontalCrop / 2d) + Math.round(horizontalCropAmount / 2d);
////        double vpPosY = Math.round(currentVerticalCrop / 2d) + Math.round(verticalCropAmount / 2d);
//        double vpPosX = currentHorizontalCrop / 2d + horizontalCropAmount / 2d;
//        double vpPosY = currentVerticalCrop / 2d + verticalCropAmount / 2d;
//        double vpWidth = viewportWidth - horizontalCropAmount;
//        double vpHeight = viewportHeight - verticalCropAmount;
//
//
//        System.out.println("setViewport:" + vpPosX + "/" + vpPosY + "/" + vpWidth + "/" + vpHeight);
//        viewport = new Rectangle2D(vpPosX, vpPosY, vpWidth, vpHeight);
//        // setPreserveRatio(false);
//        iv.setViewport(viewport);
//        // fitWidthProperty().set(100);
//
//    }
//
//    @Override
//    public void addBorder(double horizontalCropAmount, double verticalCropAmount) {
//
//        borderSizeHorizontal = horizontalCropAmount;
//        borderSizeVertical = verticalCropAmount;
//
//        Rectangle2D viewport = iv.getViewport();
//        double vpPosX = horizontalCropAmount;
//        double vpPosY = verticalCropAmount;
//        double vpWidth = iv.getFitWidth() - horizontalCropAmount * 2;
//        double vpHeight = iv.getFitHeight() - verticalCropAmount * 2;
//        System.out.println("clip:" + vpPosX + "/" + vpPosY + "/" + vpWidth + "/" + vpHeight);
//        clip = new Rectangle(vpPosX, vpPosY, vpWidth, vpHeight);
//        //clip.setArcHeight(20);
//        //clip.setArcWidth(20);
//
//
//        DropShadow shadow = new DropShadow();
//        shadow.setColor(Color.BLACK);
//        //shadow.setOffsetX(10);
//        //shadow.setOffsetY(10);
//        shadow.setRadius(10);
//        shadow.setBlurType(BlurType.GAUSSIAN);
//
//        //S  clip.setEffect(shadow);//kanten transparent...
//        //iv.setEffect(shadow);//nur bei viewPort<imagesize
//
//        //clip.setOpacity(1);
//        setClip(clip);
//
//    }
//
//    @Override
//    public void setCustomUserViewport(Rectangle2D viewport) {
//        customUserViewport = viewport;
//        iv.setViewport(customUserViewport);
//    }
//
//    @Override
//    public void moveViewport(double x, double y) {
//        Rectangle2D currentViewport = iv.getViewport();
//        if (currentViewport == null) {
//            return;
//        }
//
//        double imageWidth = iv.getImage().getWidth();
//        double imageHeight = iv.getImage().getHeight();
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
//        iv.setViewport(newViewport);
//
//    }
//
//    //+++++++++++++++
//    // stupidos
//    public URI getFileURI() {
//        return fileURI;
//    }
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
//    public double getBorderSizeHorizontal() {
//        return borderSizeHorizontal;
//    }
//
//    public void setBorderSizeHorizontal(double borderSizeHorizontal) {
//        this.borderSizeHorizontal = borderSizeHorizontal;
//    }
//
//    public double getBorderSizeVertical() {
//        return borderSizeVertical;
//    }
//
//    public void setBorderSizeVertical(double borderSizeVertical) {
//        this.borderSizeVertical = borderSizeVertical;
//    }
//}
