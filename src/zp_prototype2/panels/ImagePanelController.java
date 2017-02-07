/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import com.jhlabs.image.PointillizeFilter;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import zp_prototype2.CursorFactory;
import zp_prototype2.ISelectionManagerListener;
import zp_prototype2.IWallpaper;
import zp_prototype2.MultipleSelectionManager;
import zp_prototype2.Utils;
import zp_prototype2.Wallpaper;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.image.WallpaperImage;
import zp_prototype2.resource.fonts.AwesomeDude;
import zp_prototype2.resource.fonts.AwesomeIcons;

/**
 *
 * @author Rackor
 */
public class ImagePanelController implements Initializable {

//    @FXML
//    Label lbl_imagedit_caption;
//    @FXML
//    Label lbl_select_all;
//    @FXML
//    Label lbl_select_unselectall;
    @FXML
    public Label lbl_noImageSelected;
    /**
     * ***********************
     */
    @FXML
    private AnchorPane root;
    @FXML
    private TabPane tabs;
    @FXML
    private Tab tab_edit_color;
    @FXML
    private Tab tab_edit_effect;
    @FXML
    private Tab tab_edit_arrange;
    @FXML
    private AnchorPane header;
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
    @FXML
    private Button closeButton;
    @FXML
    private Button copyButton;
    @FXML
    private Button btn_arrange_mirror;
    @FXML
    private Slider sliderEffect_Sepia;
    @FXML
    private Slider sliderEffect_DropShadow_Radius;
    ColorAdjust colorAdjust = new ColorAdjust();
    @FXML
    private Slider sliderEffect_Color_Contrast;
    @FXML
    private Slider sliderEffect_Color_Hue;
    @FXML
    private Slider sliderEffect_Color_Brightness;
    @FXML
    private Slider sliderEffect_Color_Saturation;
    @FXML
    private Slider sliderEffect_pixalte;
    @FXML
    private Tab tab_edit_image;
    @FXML
    private Label lbl_rotate_caption;
    @FXML
    private Label lbl_edit_flip;
    @FXML
    private Label lbl_edit_zoom_caption;
    @FXML
    private Label lbl_delete_caption;
    @FXML
    private Label lbl_copy_caption;
    @FXML
    private Tab tab_edit_frame;
    @FXML
    private Label lbl_frame;
    @FXML
    private Slider sliderBorder;
    @FXML
    private Slider sliderBorder_Arc;
    @FXML
    private Label lbl_frame_arc;
    @FXML
    private Label lbl_imgedit_selected;
    @FXML
    private FlowPane fp_ImagesSelected;
    @FXML
    private Label lbl_arrangeMirror;
    //+++++++++++++++
    private IImageSelection selectedImage;
    private final static double CROP_PERCENTAGE = 0.05d;
    private IWallpaperImage currentImage;
    private IWallpaperImage originalImage;
    private double mousePressedXPosition;
    private double mousePressedYPosition;
    private Rectangle2D originalImageViewport;
    private EventHandler<ActionEvent> closeAction;
    private ArrayList<IImagePanelControllerListener> listeners;
    private IWallpaper wallpaper;
    private MultipleSelectionManager selectionManager;
    @FXML
    private Button btn_mirror_horizontal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listeners = new ArrayList<>();

        //http://fortawesome.github.io/Font-Awesome/  
        //Label pictureLabel = AwesomeDude.createIconLabel(AwesomeIcons.ICON_PICTURE, 32);
        //Button resetViewportButtonX = AwesomeDude.createIconButton(AwesomeIcons.ICON_REFRESH, "");
        //imagePane.getChildren().add(resetViewportButtonX);
        // btn_mirror_horizontal.setGraphic(AwesomeDude.createIconButton(AwesomeIcons.ICON_RESIZE_HORIZONTAL, ""));


//        root.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                System.out.println("ImagePanel ->mouse entered..");
//            }
//        });
//
//     
//        root.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//               
//                System.out.println("ImagePanel ->mouse exited..");
//                selectionManager.unSelectAll();
//                
//                // e.consume();
//            }
//        });



        //sliderBorder.setSnapToTicks(true);
        sliderBorder.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {         // wallpaper.reArrangeImages();

                double sliderValueDouble = Double.parseDouble("" + newValue);
                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    //all.wallpaper.addBorder(); 
                    image.getWallpaperImage().addBorder(sliderValueDouble, sliderValueDouble, sliderBorder_Arc.getValue(), sliderBorder_Arc.getValue());
                }

            }
        });

        sliderBorder_Arc.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {         // wallpaper.reArrangeImages();

                double sliderValueDouble = Double.parseDouble("" + newValue);
                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    //all.wallpaper.addBorder(); 
                    image.getWallpaperImage().addBorder(sliderBorder.getValue(), sliderBorder.getValue(), sliderValueDouble, sliderValueDouble);
                }

            }
        });


        //SEPIA
        sliderEffect_Sepia.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {

                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    SepiaTone sepiaTone = new SepiaTone();
                    sepiaTone.setLevel(sliderEffect_Sepia.valueProperty().doubleValue());
                    image.getWallpaperImage().getImageView().setEffect(sepiaTone);
                }
            }
        });


        //Shadow
        sliderEffect_DropShadow_Radius.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {

                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    DropShadow shadow = new DropShadow();
                    shadow.setColor(Color.GREEN);
                    //shadow.setOffsetX(10);
                    //shadow.setOffsetY(10);
                    shadow.setRadius(sliderEffect_DropShadow_Radius.valueProperty().doubleValue());
                    shadow.setBlurType(BlurType.GAUSSIAN);
                    image.getWallpaperImage().getImageView().setEffect(shadow);

                }
            }
        });

        //Shadow
        sliderEffect_DropShadow_Radius.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {

                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    DropShadow shadow = new DropShadow();
                    shadow.setColor(Color.GREEN);
                    //shadow.setOffsetX(10);
                    //shadow.setOffsetY(10);
                    shadow.setRadius(sliderEffect_DropShadow_Radius.valueProperty().doubleValue());
                    shadow.setBlurType(BlurType.GAUSSIAN);
                    image.getWallpaperImage().getImageView().setEffect(shadow);

                }
            }
        });


//        //Pixalte
//        sliderEffect_pixalte.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
//                for (IImageSelection image : selectionManager.getSelectedImages()) {
//                    Group ret=WallpaperImageEffect.pixelate((WallpaperImage)image.getWallpaperImage(), ((int)sliderEffect_pixalte.valueProperty().doubleValue()));
//                    ((WallpaperImage)image.getWallpaperImage()).getChildren().clear();
//                    ((WallpaperImage)image.getWallpaperImage()).getChildren().add(ret);
//                    //image.getWallpaperImage().
//                }
//            }
//        });

        //  JH LABS Filter
        sliderEffect_pixalte.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                for (IImageSelection image : selectionManager.getSelectedImages()) {

//                    ChromeFilter chromeFilter = new ChromeFilter();
//                    Image fxImage = ((WallpaperImage) image.getWallpaperImage()).getImageView().getImage();
//                    BufferedImage bfImage = Utils.convertToAwtImage(fxImage);
//                    chromeFilter.filter(bfImage, bfImage);
//                    fxImage = Utils.convertToFxImage(bfImage);
//                    ((WallpaperImage) image.getWallpaperImage()).getImageView().imageProperty().set(fxImage);


//                    BorderFilter filter = new BorderFilter();
//                    filter.setLeftBorder(100);
//                    filter.setBottomBorder(50);
//                    
//                    Image fxImage = ((WallpaperImage) image.getWallpaperImage()).getImageView().getImage();
//                    BufferedImage bfImage = Utils.convertToAwtImage(fxImage);
//                    filter.filter(bfImage, bfImage);
//                    fxImage = Utils.convertToFxImage(bfImage);
//                    ((WallpaperImage) image.getWallpaperImage()).getImageView().imageProperty().set(fxImage);

                    PointillizeFilter filter = new PointillizeFilter();
                    filter.setScale(8);

                    // WoodFilter filter = new WoodFilter();
                    // GlowFilter filter = new GlowFilter();

                    Image fxImage = ((WallpaperImage) image.getWallpaperImage()).getImageView().getImage();
                    BufferedImage bfImage = Utils.convertToAwtImage(fxImage);
                    filter.filter(bfImage, bfImage);
                    fxImage = Utils.convertToFxImage(bfImage);
                    ((WallpaperImage) image.getWallpaperImage()).getImageView().imageProperty().set(fxImage);
                }
            }
        });



        sliderEffect_Color_Contrast.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    colorAdjust.setContrast(sliderEffect_Color_Contrast.valueProperty().doubleValue());
                    image.getWallpaperImage().getImageView().setEffect(colorAdjust);
                }
            }
        });

        sliderEffect_Color_Hue.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    colorAdjust.setHue(sliderEffect_Color_Hue.valueProperty().doubleValue());
                    image.getWallpaperImage().getImageView().setEffect(colorAdjust);
                }
            }
        });


        sliderEffect_Color_Brightness.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    colorAdjust.setBrightness(sliderEffect_Color_Brightness.valueProperty().doubleValue());
                    image.getWallpaperImage().getImageView().setEffect(colorAdjust);
                }
            }
        });


        sliderEffect_Color_Saturation.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                for (IImageSelection image : selectionManager.getSelectedImages()) {
                    colorAdjust.setSaturation(sliderEffect_Color_Saturation.valueProperty().doubleValue());
                    image.getWallpaperImage().getImageView().setEffect(colorAdjust);
                }
            }
        });


        //ToolTip
        lbl_imgedit_selected.setTooltip(new Tooltip("click and deselect all"));


    }

    public void initI18N() {

        //+++++++++++++++    
        //i18N
        //ZP_Properties.i18NMap.put(lbl_imagedit_caption.getText(), lbl_imagedit_caption.textProperty());




        //Edit...
        ZP_Properties.i18NMap.put(tab_edit_image.getText(), tab_edit_image.textProperty());
        ZP_Properties.i18NMap.put(lbl_rotate_caption.getText(), lbl_rotate_caption.textProperty());
        ZP_Properties.i18NMap.put(lbl_edit_zoom_caption.getText(), lbl_edit_zoom_caption.textProperty());
        ZP_Properties.i18NMap.put(resetViewportButton.getText(), resetViewportButton.textProperty());
        ZP_Properties.i18NMap.put(lbl_delete_caption.getText(), lbl_delete_caption.textProperty());
        ZP_Properties.i18NMap.put(tab_edit_frame.getText(), tab_edit_frame.textProperty());
        ZP_Properties.i18NMap.put(lbl_frame.getText(), lbl_frame.textProperty());
        ZP_Properties.i18NMap.put(lbl_frame_arc.getText(), lbl_frame_arc.textProperty());
        ZP_Properties.i18NMap.put(lbl_edit_flip.getText(), lbl_edit_flip.textProperty());
        ZP_Properties.i18NMap.put(lbl_copy_caption.getText(), lbl_copy_caption.textProperty());
        ZP_Properties.i18NMap.put(lbl_noImageSelected.getText(), lbl_noImageSelected.textProperty());

        //Colors..
        ZP_Properties.i18NMap.put(tab_edit_color.getText(), tab_edit_color.textProperty());

        //Effects...
        ZP_Properties.i18NMap.put(tab_edit_effect.getText(), tab_edit_effect.textProperty());

        //tab arrange...
        ZP_Properties.i18NMap.put(tab_edit_arrange.getText(), tab_edit_arrange.textProperty());
        ZP_Properties.i18NMap.put(lbl_arrangeMirror.getText(), lbl_arrangeMirror.textProperty());


        //++++++++++++++++
        //Arranges...
        ZP_Properties.i18NMap.put(btn_arrange_mirror.getText(), btn_arrange_mirror.textProperty());

        //hack..
        //test...change menubuttons.items....
        tab_edit_image.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> o, String oldVal,
                    String newVal) {
                if (!oldVal.equals(newVal)) {
                    System.out.println("ImagePanelController->tab.textChanged");
                    tabs.getTabs().setAll(tab_edit_image, tab_edit_frame, tab_edit_color, tab_edit_arrange, tab_edit_effect);

                    // tabs.getTabs().get(0).setContent(root);
                    // System.out.println("tab_text_changed->selected.0?->"+tabs.getTabs().get(0).isSelected());
                    //hack...otherwise edit_image_tab.content empty!!
                    tabs.getSelectionModel().select(tab_edit_frame);
                    tabs.getSelectionModel().select(tab_edit_image);
                }

            }
        });

    }

    public void setWallpaper(IWallpaper wallpaper) {

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

    //********************
    // Arragements
    //********************
    @FXML
    public void handleArrange_Mirror() {
        System.out.println("handleSelectAll.clicked");
        Wallpaper wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        wallpaper.arrangeMirror();
    }

    //********************
    // Effects..
    //********************
    @FXML
    public void handleEffect_InnerShadow() {
        System.out.println("handleSelectAll.clicked");
        Wallpaper wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        wallpaper.effectInnerShadow();
    }

    //********************
    // Transforms
    //********************
    @FXML
    public void handleTransform_Perspectiv() {
        System.out.println("handleSelectAll.clicked");
        Wallpaper wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        wallpaper.transformPerspectiv();
    }

    //********************
    // Aufräumen
    //********************
    @FXML
    private void handleDeleteAction() {

//        boolean bestFit = ZP_Prototype2.getInstance().getWallpaper().isBestFit_check();
//        ZP_Prototype2.getInstance().getWallpaper().setBestFit_check(false);


        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();

//        for (IImageSelection image : selectionManager.getSelectedImages()) {
//            wallpaper.removeImage(image);
//        }
//        onImageRemoved();


        //copyAll
        ((Wallpaper) wallpaper).removeImageALL(selectionManager.getSelectedImages());
        onImageRemoved();



        //resetSelectionManager..
        selectionManager.initNewImageList();

//        //update editPanel... 
//        ZP_Prototype2.getInstance().getWallpaper().getSelectionManager().onSelectionChanged(null);
//        ZP_Prototype2.getInstance().getWallpaper().setBestFit_check(bestFit);
//        ZP_Prototype2.getInstance().getWallpaper().reArrangeImages();

    }

    protected void onImageRemoved() {
        for (IImagePanelControllerListener listener : listeners) {
            listener.imageRemoved();
        }
    }

    @FXML
    private void handleCopyAction() {

        boolean bestFit = ZP_Prototype2.getInstance().getWallpaper().isBestFit_check();
        ZP_Prototype2.getInstance().getWallpaper().setBestFit_check(false);

        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();


//        for (IImageSelection image : selectionManager.getSelectedImages()) {
//            wallpaper.copyImage(image);
//        }
        // onImageCopied();

        //copyAll
        ((Wallpaper) wallpaper).copyImageALL(selectionManager.getSelectedImages());


        //resetSelectionManager..
        //selectionManager.initNewImageList();

        //update editPanel... 
        ZP_Prototype2.getInstance().getWallpaper().getSelectionManager().onSelectionChanged(null);
        //setImage((ImageSelection)null);
        ZP_Prototype2.getInstance().getWallpaper().setBestFit_check(bestFit);
        ZP_Prototype2.getInstance().getWallpaper().reArrangeImages();

    }

    protected void onImageCopied() {
        //????
        for (IImagePanelControllerListener listener : listeners) {
            listener.imageCopied();
        }
    }

    @FXML
    private void handleFip_Horizontal_Action() {
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        for (IImageSelection image : selectionManager.getSelectedImages()) {
            image.getWallpaperImage().flipX();
        }
    }

    @FXML
    private void handleFip_Vertical_Action() {
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        for (IImageSelection image : selectionManager.getSelectedImages()) {
            image.getWallpaperImage().flipY();
        }
    }

    @FXML
    private void handleRotateCWAction() {
        //selectedImage.getWallpaperImage().rotateCW();
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        for (IImageSelection image : selectionManager.getSelectedImages()) {
            image.getWallpaperImage().rotateCW();
        }

    }

    @FXML
    private void handleRotateCCWAction() {
        //selectedImage.getWallpaperImage().rotateCCW();
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        for (IImageSelection image : selectionManager.getSelectedImages()) {
            image.getWallpaperImage().rotateCCW();
        }
    }

    @FXML
    private void handleZoomOut() {
        //currentImage.cropByPercentage(-CROP_PERCENTAGE);
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        for (IImageSelection image : selectionManager.getSelectedImages()) {
            image.getWallpaperImage().cropByPercentage(-CROP_PERCENTAGE);
        }
    }

    @FXML
    private void handleZoomIn() {
        // currentImage.cropByPercentage(CROP_PERCENTAGE);
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        for (IImageSelection image : selectionManager.getSelectedImages()) {
            image.getWallpaperImage().cropByPercentage(CROP_PERCENTAGE);
        }
    }

    @FXML
    private void handleResetViewportAction() {
        //currentImage.setViewport(null);
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        for (IImageSelection image : selectionManager.getSelectedImages()) {
            image.getWallpaperImage().setViewport(null);
        }
    }

    @FXML
    private void handleCloseAction() {
        ZP_Prototype2.getInstance().vBoxMenu.getChildren().remove(ZP_Prototype2.getInstance().imageSelectedPanel);
        if (closeAction != null) {
            closeAction.handle(null);
        }
    }

    public void setCloseAction(EventHandler<ActionEvent> closeAction) {
        this.closeAction = closeAction;
    }

    public void setImage(IImageSelection selectedImage) {

        List<IImageSelection> imagesSelected = ((MultipleSelectionManager) ZP_Prototype2.getInstance().getWallpaper().getSelectionManager()).getSelectedImages();
        if (selectedImage == null && imagesSelected.size() >= 1) {
            selectedImage = imagesSelected.get(0);
        }
        fp_ImagesSelected.getChildren().removeAll(fp_ImagesSelected.getChildren());



//        //disable tabs..
        tabs.disableProperty().set(false);
        if (selectedImage == null && imagesSelected.size() == 0) {
            tabs.disableProperty().set(true);
        } else {
            tabs.disableProperty().set(false);
        }



        //open...Accodrion.ImageEdit..
        Accordion menuAccordion = ZP_Prototype2.getInstance().menuController.getAccordion_menu();
        menuAccordion.setExpandedPane(ZP_Prototype2.getInstance().menuController.titled_imageEdit);


        //setimage...
        if (currentImage != null) {
            imagePane.getChildren().remove(currentImage.getNodeRepresentation());
        }

        this.selectedImage = selectedImage;

        if (selectedImage == null) {
            System.out.println("eh...bye");
            return;
        }

        IWallpaperImage selectedWallpaperImage = selectedImage.getWallpaperImage();
        originalImage = selectedWallpaperImage;
        originalImageViewport = originalImage.getViewport();

        internalSetImage(selectedWallpaperImage);

        setupEventHandlers();
        bindImageToOriginal();

        //+++++++++++++++++
        //updateSelecion...show all selected...
        fp_ImagesSelected.getChildren().removeAll(fp_ImagesSelected.getChildren());

        int countImages = 0;
        //List<IImageSelection> imagesSelected = ((MultipleSelectionManager) ZP_Prototype2.getInstance().getWallpaper().getSelectionManager()).getSelectedImages();

        if (imagesSelected.size() > 1) {

            Collections.reverse(imagesSelected);
            Iterator<IImageSelection> it = imagesSelected.iterator();

            while (it.hasNext()) {

                IImageSelection iImageSelection = it.next();
                ImageView selectedImageTMP = new ImageView(iImageSelection.getWallpaperImage().getImageView().getImage());
                selectedImageTMP.setPreserveRatio(true);

                selectedImageTMP.setOpacity(0.8);
                selectedImageTMP.setFitHeight(30);

                fp_ImagesSelected.getChildren().add(selectedImageTMP);
                countImages++;
                if (countImages == 4) {
                    lbl_imgedit_selected.setText("...");
                    fp_ImagesSelected.getChildren().add(lbl_imgedit_selected);
                    break;
                }

            }
        }

    }

    public void setImage(IWallpaperImage selectedImage) {

//        //open...Accodrion.ImageEdit..
//        Accordion menuAccordion=ZP_Prototype2.getInstance().menuController.getAccordion_menu();
//        menuAccordion.setExpandedPane(ZP_Prototype2.getInstance().menuController.titled_imageEdit);


        //setimage...
        if (currentImage != null) {
            imagePane.getChildren().remove(currentImage.getNodeRepresentation());
        }

        //  this.selectedImage = selectedImage;
        IWallpaperImage selectedWallpaperImage = selectedImage;
        originalImage = selectedWallpaperImage;
        originalImageViewport = originalImage.getViewport();

        internalSetImage(selectedWallpaperImage);

        setupEventHandlers();
        bindImageToOriginal();
    }

    private void internalSetImage(IWallpaperImage wallpaperImage) {

        currentImage = new WallpaperImage(wallpaperImage);
        currentImage.setIsProportionsConstrained(true);
        currentImage.getImageView().setPreserveRatio(true);


        Node node = currentImage.getNodeRepresentation();
        currentImage.setViewport(originalImageViewport);
        currentImage.getImageView().getStyleClass().add("image");


        imagePane.getChildren().add(node);


        node.setCursor(CursorFactory.getPanCursor());
        updateResetViewportButtonState();

        //remove rotation by transformation
        double rotationValue = currentImage.getRotation() % 360;
        currentImage.getImageView().setRotate(rotationValue);
        ((WallpaperImage) currentImage).getTransforms().clear();

        //fit2max....
        //fit2max....
        //fit2max....
        //fit2max....
        double maxSize = 180;// 200;//150;//180;
        if (currentImage.getHeight() > currentImage.getWidth() && currentImage.getHeight() > maxSize) {
            if (rotationValue == 90 || rotationValue == 270) {
                currentImage.getImageView().setFitWidth(maxSize);
            } else {
                currentImage.getImageView().setFitHeight(maxSize);
            }
        } else if (currentImage.getWidth() > currentImage.getHeight() && currentImage.getWidth() > maxSize) {
            //currentImage.getImageView().setFitWidth(150);
            if (rotationValue == 90 || rotationValue == 270) {
                currentImage.getImageView().setFitHeight(maxSize);
            } else {
                currentImage.getImageView().setFitWidth(maxSize);
            }
        } else {
            currentImage.getImageView().setFitWidth(maxSize);
        }



//        //v2init new Image
//        WallpaperImage currentImage = new WallpaperImage(wallpaperImage);
//        currentImage.setIsProportionsConstrained(true);
//        currentImage.getImageView().setFitWidth(150);
//        Node wpimageNode = currentImage.getNodeRepresentation();
//        double rotationValue = currentImage.getRotation() % 360;
//        currentImage.getImageView().setRotate(rotationValue);
//        currentImage.getTransforms().clear(); 
//        currentImage.getImageView().setPreserveRatio(true);
//        currentImage.setViewport(originalImageViewport);
//        wpimageNode.setCursor(CursorFactory.getPanCursor());
//        imagePane.getChildren().clear();
//        imagePane.getChildren().add(wpimageNode);
//        updateResetViewportButtonState();

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

        //thumb...
        currentImage.moveViewport(-dragX, -dragY);

        //all selected on ZP
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        selectionManager = (MultipleSelectionManager) wallpaper.getSelectionManager();
        for (IImageSelection image : selectionManager.getSelectedImages()) {
            // currentImage.moveViewport(-dragX, -dragY);
            image.getWallpaperImage().moveViewport(-dragX, -dragY);
        }


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

    public Pane getHeaderPane() {
        return header;
    }

    public void addListener(IImagePanelControllerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IImagePanelControllerListener listener) {
        listeners.remove(listener);
    }

//    @FXML
//    private void handleDeleteAction() {
//        IWallpaper wallpaper=selectedImage.getWallpaper();
//        wallpaper.removeImage(selectedImage);
//        onImageRemoved();
//    }
    @FXML
    public void handleSelectAll() {
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        ((MultipleSelectionManager) wallpaper.getSelectionManager()).selectAll();
    }

    @FXML
    public void handleUnSelectAll() {
        wallpaper = ZP_Prototype2.getInstance().getWallpaper();
        ((MultipleSelectionManager) wallpaper.getSelectionManager()).unSelectAll();
    }
//    @FXML
//    private void handleEffect_Sepia() {
//        for (IImageSelection image : selectionManager.getSelectedImages()) {
//            SepiaTone sepiaTone = new SepiaTone();
//            sepiaTone.setLevel(sliderEffect_Sepia.valueProperty().doubleValue());
//            image.getWallpaperImage().getImageView().setEffect(null);
//        }
//    }
}
