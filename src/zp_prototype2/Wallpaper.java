/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import zp_prototype2.image.IImageSelection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import zp_prototype2.image.*;
import static zp_prototype2.panels.Controller_FileManager.wallpaper;
import zp_prototype2.panels.Mode_Grid_FXMLController;
import zp_prototype2.panels.Panel_ImageMonitorFXMLController;
import javafx.scene.effect.Light.Distant;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Rackor
 */
public class Wallpaper extends Group implements IWallpaper, Serializable {
//public class Wallpaper extends Pane implements IWallpaper, Serializable {

    //boolean bestFit_addRows = true;
    boolean bestFit_check = true;
    public int imageIN = 0;
    public int imageOUT = 0;
    boolean loadingFiles = false;
    Mode_Grid_FXMLController gridController;
    ArrayList<IImageSelection> imagesStupid;
    ObservableList<IImageSelection> images;
    ObservableList<IImageSelection> imagesLoadError;
    SelectionManager selectionManager;
    public SimpleDoubleProperty width;
    public SimpleDoubleProperty height;
    double totalImagesLenght;
    double currentRowY;
    int currentRowNumber;
    SimpleObjectProperty<Color> backgroundColor;
    SimpleObjectProperty<Color> borderColor;
    ArrayList<IWallpaperListener> listeners;
    ArrayList<ArrayList<IImageSelection>> imagesByRow;
    ArrayList<IEditMode> editingModes;
    //+++++++++++++++++
    //HACKs
    //avoid gaps in wallpaper caused by scaling...
    double closeGapsHACK = 0;//2
    //+++++++++++++++++
    double indent4ImageOut = 30;
    //++++++++++++++++++++++
    //Background....
    private static final Color BACKGROUND_COLOR = Color.rgb(100, 0, 0, 1);
    private IEditMode editMode;
    private ImageTranslationListener imageTranslationListener;
    private ImagesOrientation imagesOrientation;
    //handling Row/Col Numbers...
    public SimpleStringProperty ssP_RowNumber = new SimpleStringProperty("1");
    private int rowNumber;
    ILibrary library;
    //++++++++++++++++
    //
    // Monitor Loaded, In, Out,etc...
    //
    public SimpleStringProperty price = new SimpleStringProperty("0");
    public SimpleStringProperty countImageLoaded = new SimpleStringProperty();
    public SimpleStringProperty countImageLoadedReady = new SimpleStringProperty("0");
    public SimpleStringProperty countImageLoadedIN = new SimpleStringProperty("0");
    public SimpleStringProperty countImageLoadedOUT = new SimpleStringProperty("0");
    public BooleanProperty showOut = new SimpleBooleanProperty(true);
    public SimpleDoubleProperty border = new SimpleDoubleProperty(0);
    public SimpleStringProperty lbl_dimension_width = new SimpleStringProperty("NA");
    public SimpleStringProperty lbl_dimension_height = new SimpleStringProperty("NA");
    public SimpleStringProperty medium_output = new SimpleStringProperty("NA");
    public SimpleStringProperty lbl_dimension_averrage_output_width = new SimpleStringProperty("NA");
    public SimpleStringProperty lbl_dimension_averrage_output_height = new SimpleStringProperty("NA");

    public Wallpaper(ILibrary library, double width, double height) {

        this.width = new SimpleDoubleProperty();
        this.height = new SimpleDoubleProperty();

        this.backgroundColor = new SimpleObjectProperty<Color>();
        borderColor = new SimpleObjectProperty<>(Color.TRANSPARENT);
        this.library = library;
        imagesOrientation = ImagesOrientation.HORIZONTAL;

        ssP_RowNumber.addListener(
                new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable,
                    String oldValue, String newValue) {
                rowNumber = new Integer(newValue);
                //update Contoller...
                gridController.rowNumberComboBox.getSelectionModel().select("" + rowNumber);

                if (!gridController.checkBox_best_fit.isSelected()) {
                    bestFit_check = false;
                    reArrangeImages();
                    bestFit_check = true;
                }
            }
        });
        //ssP_RowNumber.setValue("1");
        rowNumber = 1;


        //+++++++++++
        //
        // observers 4images..
        //
        imagesStupid = new ArrayList<>();
        images = initNewImageList();


        listeners = new ArrayList<IWallpaperListener>();
        imagesByRow = new ArrayList<ArrayList<IImageSelection>>();
        imagesByRow.add(new ArrayList<IImageSelection>()); // init first row
        editingModes = new ArrayList<>();


        //selectionManager = new SelectionManager(this);
        selectionManager = new MultipleSelectionManager(this);

        imageTranslationListener = new ImageTranslationListener();

        initWallpaperProperties(width, height);
        updatePrice();
        createBackground();
        initEditingModes();
        resetTemporaryParameters();


//        
//        setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                System.out.println("ImagePanel ->mouse exited..");
//                 ((MultipleSelectionManager)selectionManager).unSelectAll();
//            }
//        });



        //++++++++++++++++++++
        //
        // width CHANGED LISTENER
        // width CHANGED LISTENER
        // width CHANGED LISTENER
        //
        //
        this.width.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {


//                //set cursor...
//                ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().getScene();
//                Cursor curCursor = zpScene.getCursor();
//                //zpScene.switchCursor(Cursor.WAIT);
//                zpScene.setCursor(Cursor.WAIT);

                rowNumber = 1;

                editMode.wallpaperSizeChanged();
                updatePrice();


//                TitledPane titled_measure = ZP_Prototype2.getInstance().menuController.getTitled_measure();
//                titled_measure.setWrapText(true);
//                titled_measure.setTextOverrun(OverrunStyle.CENTER_WORD_ELLIPSIS);
//                titled_measure.setText("args1/args2/args0\n" + arg1 + "/" + arg2 + "/" + arg0);

//                zpScene.switchCursor(curCursor);

                //++++++++++++++++++++++
                //
                // current Dimension..
                //
                lbl_dimension_width.setValue("" + WallpaperDimensions.convertPixelToCM(getWidth()));
            }
        });


        this.height.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

//                //set cursor...
//                ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().getScene();
//                Cursor curCursor = zpScene.getCursor();
//               // zpScene.switchCursor(Cursor.WAIT);
//                zpScene.setCursor(Cursor.WAIT);

                rowNumber = 1;

                editMode.wallpaperSizeChanged();
                updatePrice();

                // zpScene.switchCursor(curCursor);

                //++++++++++++++++++++++
                //
                // current Dimension..
                //
                lbl_dimension_height.setValue("" + WallpaperDimensions.convertPixelToCM(getHeight()));

            }
        });



//        setOnDragEntered(new EventHandler<DragEvent>() {
//            public void handle(DragEvent event) {
//                if (event.getGestureSource() != Wallpaper.this //                         && event.getDragboard().hasImage()
//                        ) {
//                    Wallpaper.this.borderColor.set(Color.web("#db8694"));
//                }
//                event.consume();
//            }
//        });
//        setOnDragExited(new EventHandler<DragEvent>() {
//            @Override
//            public void handle(DragEvent event) {
//                Wallpaper.this.borderColor.set(Color.TRANSPARENT);
//                event.consume();
//            }
//        });
//        setOnDragOver(new EventHandler<DragEvent>() {
//            @Override
//            public void handle(DragEvent event) {
//                if (event.getGestureSource() != Wallpaper.this //                        && event.getDragboard().hasString()
//                        ) {
//                    /* allow for both copying and moving, whatever user chooses */
//                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                }
//                event.consume();
//            }
//        });
//        setOnDragDropped(new EventHandler<DragEvent>() {
//            @Override
//            public void handle(DragEvent event) {
//                boolean success = false;
//                IWallpaperImage dragContent = Wallpaper.this.library.getDragAndDropContent();
//                if (dragContent != null) {
//                    Wallpaper.this.addImage(dragContent);
//                    success = true;
//                    Wallpaper.this.library.dragAndDropFinished(null);
//                }
//                event.setDropCompleted(success);
//                event.consume();
//            } 
//        });


    }

    public boolean isLoadingFiles() {
        return loadingFiles;
    }

    public void setLoadingFiles(boolean loadingFiles) {
        this.loadingFiles = loadingFiles;
    }

    public boolean getIsLoadingImageReady() {

        for (IImageSelection imageToAdd : images) {
            if (imageToAdd.getWallpaperImage().getImageView().getImage().getProgress() != 1) {
                return false;
            }
        }


        reArrangeImages();


        //close loading...
        //show loading...
        ZPScene scene = ZP_Prototype2.getInstance().getScene();
        scene.closeDialog();

        return true;




    }

    public void set_Grid_Controller(Mode_Grid_FXMLController controller) {
        this.gridController = controller;
    }

    public Mode_Grid_FXMLController getGridController() {
        return gridController;
    }

    public double updatePrice() {
        //double squareMeter=getWidth()*getHeight();  
        //double ret = WallpaperDimensions.getPrice();

        double ret = 0;
        double priceSquareMeter = ZP_MediumManager.getInstance().getSelectedMedium().getPriceSquareMeter();

        double widthCM = WallpaperDimensions.convertPixelToCM(getWidth());
        double heightCM = WallpaperDimensions.convertPixelToCM(getHeight());

        ret = widthCM / 100 * heightCM / 100 * priceSquareMeter;

        System.out.println("updtePrice: " + ret + "width/height in cm " + widthCM + "/" + heightCM);

        //runden, format...
        DecimalFormat twoDForm = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        String formatString = twoDForm.format(ret);
        price.setValue(formatString);
        return Double.valueOf(formatString);

    }

    private void initWallpaperProperties(double width, double height) {
        this.width.set(width);
        this.height.set(height);

        //update binded StringProperties..
        lbl_dimension_height.setValue("" + WallpaperDimensions.convertPixelToCM(getHeight()));
        lbl_dimension_width.setValue("" + WallpaperDimensions.convertPixelToCM(getWidth()));

    }

    private void resetTemporaryParameters() {

        totalImagesLenght = 0;
        currentRowY = 0;
        currentRowNumber = 0;

    }

    private void createBackground() {

        Rectangle background = new Rectangle();
        background.widthProperty().bind(width);
        background.heightProperty().bind(height);

        //#####################
        //color
//        background.fillProperty().bind(backgroundColor);
//        background.strokeProperty().bind(borderColor);


        //#####################
        //texture
        //http://docs.oracle.com/javafx/2/api/javafx/scene/paint/ImagePattern.html
        Image bgPattern = new Image(this.getClass().getResourceAsStream("/zp_prototype2/resource/textures/paper_@2X.png"), 1000, 1000, true, true);
        background.setFill(new ImagePattern(bgPattern, 0, 0, 400, 400, false));

        //add...
        getChildren().addAll(background);

    }

    @Override
    public double getCurrentImageHeight() {
        return this.height.get() / rowNumber;
    }

    @Override
    public double getCurrentImageWidth() {
        return this.width.get() / rowNumber;
    }
    //++++++++++
    //
    // ADD IMAGE!!!
    // ADD IMAGE!!!// ADD IMAGE!!!// ADD IMAGE!!!
    // ADD IMAGE!!!
    //
    //

    //++++++++++++++++++++++++++
    //add WallpaperImages...
    @Override
    public IImageSelection addImage(IWallpaperImage wallpaperImage) {
        final IImageSelection imageToAdd = new ImageSelection(wallpaperImage);
        return addImage(imageToAdd);
    }

    //add ImageSelection and add listiner to NODE !!!!!
    @Override
    public IImageSelection addImage(final IImageSelection imageToAdd) {

        //final Node imageNode = imageToAdd.getNodeRepresentation();

        if (imagesOrientation == ImagesOrientation.HORIZONTAL) {
            imageToAdd.getWallpaperImage().setHeight(getCurrentImageHeight());
            //System.out.println("Going to add image.. ImageSelection/wallpaperimg width = " + imageToAdd.getWidth()+"/"+ imageToAdd.getWallpaperImage().getWidth());
            return addImage(imageToAdd, imageToAdd.getWallpaperImage().getWidth(), width.get());
            //return addImage(imageToAdd, imageToAdd.getWidth(), width.get());


        } else // imagesOrientation == ImagesOrientation.VERTICAL
        {
            imageToAdd.getWallpaperImage().setWidth(getCurrentImageWidth());
            return addImage(imageToAdd, imageToAdd.getWallpaperImage().getHeight(), height.get());
            //return addImage(imageToAdd, imageToAdd.getHeight(), height.get());
        }
    }
    //+++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++

    public void bestFit() {

        //set to false...otherwise recursion...
        bestFit_check = false;

        rowNumber = this.rowNumber;//1;
        //rowNumber = 1 in wallpaper resize size...;

        System.out.println("--->do bestFit for Rows/IN/Out:" + rowNumber + "/" + imageIN + "/" + imageOUT);


        //reArrangeImages();
        while (imageOUT > 0) {
            rowNumber++;
            System.out.println("--->do bestFit for Rows/IN/Out ROW++:" + rowNumber + "/" + imageIN + "/" + imageOUT);
            reArrangeImages();

        }

        //TODO:space?
        if (rowNumber > 1) {
            rowNumber--;
            System.out.println("--->do bestFit for Rows/IN/Out ROW--:" + rowNumber + "/" + imageIN + "/" + imageOUT);
            reArrangeImages();

        }

        ssP_RowNumber.setValue("" + (rowNumber));
        bestFit_check = true;


    }

    @Override
    public void reArrangeImages() {


        //TODO-MIN square find squreSize
        //double lcd=Utils.getLeastCommonDenominator((int)Math.round(getWidth()),(int)Math.round(getHeight()));
        //System.out.println("leastCommonDenominator: " + lcd);
        //END


        //let´s go... 
        lastImageAdded = false;
        removeImagesFromView();
        resetTemporaryParameters();

        int index = 0;
        for (IImageSelection imageToAdd : images) {
            //??? imageToAdd.getNodeRepresentation().layoutXProperty().setValue(0);
            imageToAdd.getNodeRepresentation().setOpacity(1);
            imageToAdd.getWallpaperImage().resetViewPortAndSize();
            addImage(imageToAdd);

            //setIndex
            imageToAdd.setIndex(index++);
        }

        //++++++++++++++++++++++++
        //monitor in out...
        imageIN = 0;
        imageOUT = 0;

        double averageHeight = 0;
        double averageWidth = 0;

        for (IImageSelection imageToAdd : images) {

            if (imageToAdd.isImageIN()) {
                imageIN++;
                averageHeight += imageToAdd.getHeight();
                averageWidth += imageToAdd.getWidth();
            } else {
                //System.out.println("reArrangeImages.out-->:" + imageToAdd.getWallpaperImage().getFileURI());
                imageOUT++;
            }
        }

        //update stringProperties...
        countImageLoadedIN.setValue("" + imageIN);
        countImageLoadedOUT.setValue("" + imageOUT);

        averageHeight = WallpaperDimensions.convertPixelToCM(averageHeight / imageIN);
        averageWidth = WallpaperDimensions.convertPixelToCM(averageWidth / imageIN);
        DecimalFormat df = new DecimalFormat("###.##");

        lbl_dimension_averrage_output_height.setValue("" + df.format(averageHeight));
        lbl_dimension_averrage_output_width.setValue("" + df.format(averageWidth));


        //do bestFit?
        if (bestFit_check && gridController.checkBox_best_fit.isSelected()) {
            bestFit();
        }

        //addBorder..
        addBorder();

    }

    public void addBorder() {
        double borderArc = 0;//border.doubleValue();

        //+++++++++++++++++++++++++
        //addBorder...
        for (IImageSelection curImageSelection : images) {
            curImageSelection.getWallpaperImage().addBorder(border.doubleValue(), border.doubleValue(), borderArc, borderArc);
            //curImageSelection.getWallpaperImage().addBorderX();
        }
    }
    //+++++++++++++++++++++++++++++++++++++
    //add ImageSelection and arrange!!!!!
    //add ImageSelection and arrange!!!!!
    //add ImageSelection and arrange!!!!!
    //add ImageSelection and arrange!!!!!
    //add ImageSelection and arrange!!!!!
    private boolean lastImageAdded = false; //set to true in reArrangeImages
    private double opacityNotInDefault = 0.5;//set to true in reArrangeImages
    static double bf_step_reduce = 0;

    private IImageSelection addImage(IImageSelection imageToAdd, double imageLenght, double wallpaperLenght) {

        //+++++++++++++++
        //Gap for non onZP_Images...

        double opacityNotIn = opacityNotInDefault;
        if (!showOut.getValue()) {
            opacityNotIn = 0;
        } else {
            opacityNotIn = opacityNotInDefault;
        }

        if (totalImagesLenght + imageLenght >= wallpaperLenght) {

            imageToAdd.setImageIN(true);
            cropExistingImagesAndAddAnother(imageToAdd);
            addRow();
            imageToAdd.setWallpaper(this);

            //new row/col....
            if (isRowNumberLimitReached()) {
                if (lastImageAdded) {
                    if (imagesOrientation == ImagesOrientation.HORIZONTAL) {
                        imageToAdd.setYTranslation(imageToAdd.getNodeRepresentation().getTranslateY() + indent4ImageOut);
                        // imageToAdd.setYTranslation(imageToAdd.getNodeRepresentation().getLayoutY() + indent4ImageOut); 
                    } else {
                        imageToAdd.setXTranslation(imageToAdd.getNodeRepresentation().getTranslateX() + indent4ImageOut);
                        // imageToAdd.setXTranslation(imageToAdd.getNodeRepresentation().getLayoutX()+ indent4ImageOut);
                    }
                    imageToAdd.setImageIN(false);
                    imageToAdd.getNodeRepresentation().setOpacity(opacityNotIn);
                }

                //...loading...
                //...loading...
                //...loading...
                //...loading...
                if (isLoadingFiles()) {
                    // rowNumber++;
                    ssP_RowNumber.setValue("" + (rowNumber + 1));
                } else {
                    lastImageAdded = true;
                }
                //lastImageAdded = true;
            }
            return imageToAdd;


        } else {

            if (isRowNumberLimitReached()) {

                //++++++++++++++++++++++    
                //image out Zoopraxi
                imageToAdd.setImageIN(false);
                imageToAdd.getNodeRepresentation().setOpacity(opacityNotIn);
                if (imagesOrientation == ImagesOrientation.HORIZONTAL) {
                    imageToAdd.setTranslate(totalImagesLenght, currentRowY + indent4ImageOut);
                } else if (imagesOrientation == ImagesOrientation.VERTICAL) {
                    imageToAdd.setTranslate(currentRowY + indent4ImageOut, totalImagesLenght);
                }

            } else {

                //++++++++++++++++++++++    
                //image in Zoopraxi
                imageToAdd.setImageIN(true);
                if (imagesOrientation == ImagesOrientation.HORIZONTAL) {
                    imageToAdd.setTranslate(totalImagesLenght, currentRowY);
                } else if (imagesOrientation == ImagesOrientation.VERTICAL) {
                    imageToAdd.setTranslate(currentRowY, totalImagesLenght);
                }
            }

            internalAddImage(imageToAdd);
            totalImagesLenght += imageLenght;
            imageToAdd.setWallpaper(this);
            return imageToAdd;
        }
    }

    private void addRow() {

        if (imagesOrientation == ImagesOrientation.HORIZONTAL) {
            currentRowY += getCurrentImageHeight() - closeGapsHACK;
        } else if (imagesOrientation == ImagesOrientation.VERTICAL) {
            currentRowY += getCurrentImageWidth() - closeGapsHACK;
        }

        ++currentRowNumber;
        imagesByRow.add(new ArrayList<IImageSelection>());
        totalImagesLenght = 0;
    }

    /* Returns false when there is not enough space to add another row */
    private boolean isRowNumberLimitReached() {

        if (imagesOrientation == ImagesOrientation.HORIZONTAL) {
            if (currentRowY + closeGapsHACK * rowNumber >= getHeight()) {
                return true;
            }

        } else if (imagesOrientation == ImagesOrientation.VERTICAL) {
            if (currentRowY + closeGapsHACK * rowNumber >= getWidth()) {
                return true;
            }
        }
        return false;
    }

    private void cropExistingImagesAndAddAnother(IImageSelection imageToAdd) {


        if (imagesOrientation == ImagesOrientation.HORIZONTAL) {

            double widthDifference = totalImagesLenght + imageToAdd.getWallpaperImage().getWidth() - width.get();
            double cropSize = widthDifference / (imagesByRow.get(currentRowNumber).size() + 1);
           // double cropSize =100 ;//widthDifference / (imagesByRow.get(currentRowNumber).size() + 1);
            
            
            
            
            
            cropSize = cropSize - closeGapsHACK;
            //iterate and crop images except last...
            totalImagesLenght = 0;
            for (int i = 0; i < imagesByRow.get(currentRowNumber).size(); ++i) {
                IImageSelection currentImage = imagesByRow.get(currentRowNumber).get(i);
                currentImage.getWallpaperImage().cropHorizontally(cropSize);
                currentImage.setXTranslation(totalImagesLenght);
                totalImagesLenght += currentImage.getWallpaperImage().getWidth() - closeGapsHACK;
            }
            imageToAdd.getWallpaperImage().cropHorizontally(cropSize);
            // System.out.println("horizontal...add Image...X:" + totalImagesLenght + " - Y:" + currentRowY + " crop:" + cropSize);
            imageToAdd.setTranslate(totalImagesLenght, currentRowY);


            
        } else if (imagesOrientation == ImagesOrientation.VERTICAL) {

            double widthDifference = totalImagesLenght + imageToAdd.getWallpaperImage().getHeight() - height.get();
            double cropSize = widthDifference / (imagesByRow.get(currentRowNumber).size() + 1);
            cropSize = cropSize - closeGapsHACK;
            totalImagesLenght = 0;
            for (int i = 0; i < imagesByRow.get(currentRowNumber).size(); ++i) {
                IImageSelection currImage = imagesByRow.get(currentRowNumber).get(i);
                currImage.getWallpaperImage().cropVertically(cropSize);
                currImage.setYTranslation(totalImagesLenght);
                totalImagesLenght += currImage.getWallpaperImage().getHeight() - closeGapsHACK;
            }
            imageToAdd.getWallpaperImage().cropVertically(cropSize);
            //System.out.println("vertical add Image...X:" + currentRowY + " - Y:" + totalImagesLenght);
            imageToAdd.setTranslate(currentRowY, totalImagesLenght);

        }
        internalAddImage(imageToAdd);

    }

    private void internalAddImage(IImageSelection image) {
        if (images.indexOf(image) == -1) // image didn't exist
        {
            handleNewImageAdded(image);
        }
        imagesByRow.get(currentRowNumber).add(image);
        getChildren().add(image.getNodeRepresentation());
    }

    private void handleNewImageAdded(IImageSelection image) {

        image.addTranslationListener(imageTranslationListener);
        image.getWallpaperImage().addListener(imageTranslationListener);

        //add image to collection!!!
        images.add(image);
        //observe_Images.add(image);
        onNewImageAdded(image);
    }

    @Override
    public IImageSelection getImageAt(int index) {
        return images.get(index);
    }

    public void arrangeMirror() {

        for (int i = 0; i < imagesByRow.size(); i++) {
            ArrayList<IImageSelection> curCol = imagesByRow.get(i);
            for (int j = 0; j < curCol.size(); j++) {
                IImageSelection selImg = imagesByRow.get(i).get(j);



                if (i % 2 == 0 && j % 2 == 0) {
                }
                if (i % 2 == 0 && j % 2 == 1) {
                    selImg.getWallpaperImage().flipX();
                }
                if (i % 2 == 1 && j % 2 == 0) {
                    selImg.getWallpaperImage().flipY();
                }
                if (i % 2 == 1 && j % 2 == 1) {
                    selImg.getWallpaperImage().flipX();
                    selImg.getWallpaperImage().flipY();
                }



            }
        }
    }

    public void transformPerspectiv() {

        for (int i = 0; i < imagesByRow.size(); i++) {
            ArrayList<IImageSelection> curCol = imagesByRow.get(i);
            for (int j = 0; j < curCol.size(); j++) {
                IImageSelection selImg = imagesByRow.get(i).get(j);



                double pers = 10;
                if (j % 2 == 0) {

                    PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
                    perspectiveTrasform.setUlx(0);
                    perspectiveTrasform.setUly(0);
                    perspectiveTrasform.setLlx(0);
                    perspectiveTrasform.setLly(selImg.getHeight());


                    perspectiveTrasform.setUrx(selImg.getWidth() - pers);
                    perspectiveTrasform.setUry(+pers);
                    perspectiveTrasform.setLrx(selImg.getWidth());
                    perspectiveTrasform.setLry(selImg.getHeight() - pers);

                    ((Group) selImg.getWallpaperImage()).setEffect(perspectiveTrasform);

                } else {

                    PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();

                    perspectiveTrasform.setUlx(pers * 2);
                    perspectiveTrasform.setUly(pers);
                    perspectiveTrasform.setLlx(pers);
                    perspectiveTrasform.setLly(selImg.getHeight() - pers);

                    perspectiveTrasform.setUrx(selImg.getWidth());
                    perspectiveTrasform.setUry(0);
                    perspectiveTrasform.setLrx(selImg.getWidth());
                    perspectiveTrasform.setLry(selImg.getHeight());

                    ((Group) selImg.getWallpaperImage()).setEffect(perspectiveTrasform);
                }
            }
        }
    }

    public void effectInnerShadow() {

        for (int i = 0; i < imagesByRow.size(); i++) {
            ArrayList<IImageSelection> curCol = imagesByRow.get(i);
            for (int j = 0; j < curCol.size(); j++) {
                IImageSelection selImg = imagesByRow.get(i).get(j);


                InnerShadow innerShadow = new InnerShadow();
                innerShadow.setOffsetX(4);
                innerShadow.setOffsetY(0);
                innerShadow.setColor(Color.web("0x3b596d"));
                ((Group) selImg.getWallpaperImage()).setEffect(innerShadow);


//                Light.Distant light = new Distant();
//                light.setAzimuth(-135.0f);
//                Lighting l = new Lighting();
//                l.setLight(light);
//                l.setSurfaceScale(5.0f);
//                ((Group) selImg.getWallpaperImage()).setEffect(l);


            }
        }



    }

    @Override
    public IImageSelection getImageAt(int col, int row) {

        for (int i = 0; i < imagesByRow.size(); i++) {
            ArrayList<IImageSelection> curCol = imagesByRow.get(i);
            for (int j = 0; j < curCol.size(); j++) {
                IImageSelection selImg = imagesByRow.get(i).get(j);
                if (i == row && j == col) {
                    return selImg;
                }

            }
        }

        return null;

    }

    @Override
    public void putImageAt(IImageSelection imageSelection, int index) {
        images.set(index, imageSelection);
    }

    @Override
    public int getImageIndex(IImageSelection imageSelection) {
        return images.indexOf(imageSelection);
    }

    @Override
    public void copyImage(IImageSelection image) {
        IWallpaperImage img2Copy = image.getWallpaperImage();
        WallpaperImage wallpaperImage = new WallpaperImage(img2Copy.getImage(), (int) img2Copy.getRotation(), img2Copy.getFileURI());

        //add with rearrange..
        IImageSelection imageSelection = wallpaper.addImage(wallpaperImage);
        //onImageCopied();
        reArrangeImages();
    }

    public void copyImageALL(List<IImageSelection> images) {

        List tmp = new ArrayList<IImageSelection>();


        //sort images by Index...
        Collections.sort(images, new Comparator<IImageSelection>() {
            @Override
            public int compare(IImageSelection o1, IImageSelection o2) {
                int ret = o1.getIndex() - o2.getIndex();
                return ret;
                //reverse->  return ret*-1;
            }
        });



        for (IImageSelection image : images) {
            IWallpaperImage img2Copy = image.getWallpaperImage();
            WallpaperImage wallpaperImage = new WallpaperImage(img2Copy.getImage(), (int) img2Copy.getRotation(), img2Copy.getFileURI());
            System.out.println("copy Image..." + image.getIndex());

            //add listeners...TOD research,refactor copyImage calls handlenewimg...
            IImageSelection newImgSel = new ImageSelection(wallpaperImage);
            newImgSel.addTranslationListener(imageTranslationListener);
            newImgSel.getWallpaperImage().addListener(imageTranslationListener);
            onNewImageAdded(newImgSel);


            tmp.add(newImgSel);



        }

        this.images.addAll(tmp);
    }

//    protected void onImageCopied() {
//        reArrangeImages();
//    }
    @Override
    public void removeImage(IImageSelection image) {

        //remove from images
        images.remove(image);
        for (int i = 0; i < imagesByRow.size(); ++i) {
            int index = imagesByRow.get(i).indexOf(image);
            if (index != -1) {
                imagesByRow.get(i).remove(image);
                break;
            }
        }
        getChildren().remove(image.getNodeRepresentation());
        image.removeTranslationListener(imageTranslationListener);
        onImageRemoved();
    }

    public void removeImageALL(List<IImageSelection> images) {

        List tmp = new ArrayList<IImageSelection>();
        for (IImageSelection image : images) {

            for (int i = 0; i < imagesByRow.size(); ++i) {
                int index = imagesByRow.get(i).indexOf(image);
                if (index != -1) {
                    imagesByRow.get(i).remove(image);
                    break;
                }
            }
            getChildren().remove(image.getNodeRepresentation());
            image.removeTranslationListener(imageTranslationListener);

            tmp.add(image);
        }

        this.images.removeAll(tmp);
        onImageRemoved();
    }

    protected void onImageRemoved() {

        this.rowNumber = 1;
        reArrangeImages();

        // update Monitor...
        Integer curLoaded = new Integer(countImageLoadedReady.getValue());
        curLoaded--;
        countImageLoadedReady.setValue("" + curLoaded);


    }

    public void resetImages() {

        images = initNewImageList();
        ((MultipleSelectionManager) selectionManager).initNewImageList();
        reArrangeImages();

        //resetZoom
        ZP_Prototype2.getInstance().getWorkspace().zoomTo(ZP_Prototype2.getInstance().getWorkspace().getMinimumZoom());
    }

    public ObservableList initNewImageList() {

        //ObservableList ret = FXCollections.observableList(new ArrayList<>());
        ObservableList ret = FXCollections.observableList(Collections.synchronizedList(new ArrayList()));
        ret.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                Panel_ImageMonitorFXMLController.lbl_Count_Image_Loaded.setText("" + images.size());
                System.out.println("wallpaper.observe_Images: Detected a change! " + images.size());
                countImageLoaded.setValue("" + images.size());
            }
        });
        countImageLoaded.setValue("0");
        countImageLoadedReady.setValue("0");
        return ret;
    }

    public void removeImageAtIndex(int index) {
        getChildren().remove(index);
        //image.removeTranslationListener(imageTranslationListener);
        //onImageRemoved();
    }

    public void removeErrorImages() {

        List<IImageSelection> toRemoveImages = new ArrayList<IImageSelection>();

        for (IImageSelection curImage : images) {
            if (curImage.getWallpaperImage().getImage().isError()) {
                toRemoveImages.add(curImage);
            }
        }

        for (IImageSelection curImage : toRemoveImages) {
            System.out.println("remove Image / error on loading for : " + curImage.getWallpaperImage().getFileURI());
            //images.remove(curImage);
            removeImage(curImage);
        }


    }

    @Override
    public SimpleDoubleProperty widthProperty() {
        return width;
    }

    @Override
    public SimpleDoubleProperty heightProperty() {
        return height;
    }

    @Override
    public void setWidth(double width) {
        this.width.set(width);
    }

    @Override
    public double getWidth() {
        return this.width.get();
    }

    @Override
    public void setHeight(double height) {
        this.height.set(height);
    }

    @Override
    public double getHeight() {
        return this.height.get();
    }

    protected void onNewImageAdded(IImageSelection image) {
        for (IWallpaperListener listener : listeners) {
            listener.imageAdded(image);
        }
    }

    @Override
    public void addListener(IWallpaperListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IWallpaperListener listener) {
        listeners.remove(listener);
    }

    @Override
    public SelectionManager getSelectionManager() {
        return selectionManager;
    }

    private void removeImagesFromView() {
        // Don't want to remove the first child - it is the background
        getChildren().remove(1, getChildren().size());
        imagesByRow.clear();
        imagesByRow.add(new ArrayList<IImageSelection>());
    }

    @Override
    public IEditMode getEditMode() {
        return editMode;
    }

    @Override
    public void setEditMode(IEditMode editMode) {
        this.editMode = editMode;
    }

    private void initEditingModes() {

        GridMode gridMode = new GridMode(this);
        editingModes.add(gridMode);
        FreeMode freeMode = new FreeMode();
        editingModes.add(freeMode);

        editMode = gridMode;
        // editMode = freeMode;
    }

    @Override
    public List<IEditMode> getEditingModes() {
        return editingModes;
    }

    @Override
    public IImageSelection checkBiggestColision(IImageSelection image) {
        double collisionArea = 0;
        Bounds imageBounds = image.getNodeRepresentation().localToParent(image.getWallpaperImage().getNodeRepresentation().getBoundsInParent());
        IImageSelection biggestCollisionImage = null;
        for (IImageSelection currentImage : images) {
            if (currentImage == image) {
                continue;
            }
            Bounds currentImageBounds = currentImage.getNodeRepresentation().localToParent(currentImage.getWallpaperImage().getNodeRepresentation().getBoundsInParent());
            double currCollisionArea = Utils.getCollisionArea(currentImageBounds, imageBounds);
            if (currCollisionArea > collisionArea) {
                collisionArea = currCollisionArea;
                biggestCollisionImage = currentImage;
            }
        }

        return biggestCollisionImage;
    }

    public List<IImageSelection> getImages() {
        return images;
    }

    public void setImages(ObservableList<IImageSelection> images) {
        this.images = images;
    }

    @Override
    public ImagesOrientation getImagesOrientation() {
        return imagesOrientation;
    }

    @Override
    public void setImagesOrientation(ImagesOrientation orientation) {
        if (orientation == this.imagesOrientation) {
            return;
        }
        this.imagesOrientation = orientation;

        //reset rowNumber..for bestFit
        setRowNumber(1);

        reArrangeImages();

    }

    @Override
    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    public void setRowNumber(int rowNumber) {
        if (rowNumber == this.rowNumber) {
            return;
        }
        this.rowNumber = rowNumber;
        reArrangeImages();
    }

    @Override
    public Node getNodeRepresentation() {
        return this;
    }

    @Override
    public boolean isBestFit_check() {
        return bestFit_check;
    }

    @Override
    public void setBestFit_check(boolean bestFit_check) {
        this.bestFit_check = bestFit_check;
    }

    private class ImageTranslationListener implements ITranslationListener, IWallpaperImageListener {

        @Override
        public void imageTranslated(IImageSelection image) {
            editMode.imageTranslated(image);
        }

        @Override
        public void imageTranslationEnded(IImageSelection image, double translatedXAmount, double translatedYAmount) {
            editMode.imageTranslationEnded(image, translatedXAmount, translatedYAmount);
        }

        @Override
        public void imageRotated(IWallpaperImage image) {
            editMode.imageRotated(image);
        }
    }
}
