/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;
import java.awt.image.BufferedImage;
import zp_prototype2.image.WallpaperImage;
import java.net.URL;
import java.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import zp_prototype2.IWallpaper;
import zp_prototype2.WallpaperDimensions;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.image.IImageSelection;


import java.io.File;
import javafx.concurrent.Task;
import xuggle.VideoThumbnailsExample;

/**
 *
 * @author Rackor
 */
public class Controller_FileManager implements Initializable {

    private final File DEFAULT_FILECHOOSER_DIR = new JFileChooser().getFileSystemView().getDefaultDirectory();
    public static IWallpaper wallpaper;
    List<File> lastChoosenFiles;
    File lastChoosenDir;
    Stage stage;
    public static List errorLoadingList = new ArrayList();
    boolean isInitializing = false;
    private final int DEFAULT_TARGET_HEIGHT = 1739;
    private ResourceBundle resourceBundle;
    @FXML
    Button browseButton;
    @FXML
    public Label lbl_caption_load;
    @FXML
    Button btn_add_folder;
    @FXML
    Button btn_load_movie;
    @FXML
    Button btn_reset_loaded;
    @FXML
    public Label lbl_fileMonitor;

    //captureMovie...
    //public Task task_CaptureMovie;
    //public Thread threadCapture;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.resourceBundle = resourceBundle;
        isInitializing = true;
        isInitializing = false;

        //ZP_Pane_Controller rootController=new ZP_Pane_Controller();
        //rootController.setDrag(pane_Main);

        setWallpaper(ZP_Prototype2.getInstance().getWallpaper());
        //panelController.setLibrary(library);
        setStage(ZP_Prototype2.getInstance().primaryStage);

        //+++++++++++++++    
        //i18N
        ZP_Properties.i18NMap.put(lbl_caption_load.getText(), lbl_caption_load.textProperty());
        ZP_Properties.i18NMap.put(browseButton.getText(), browseButton.textProperty());
        ZP_Properties.i18NMap.put(btn_add_folder.getText(), btn_add_folder.textProperty());
        ZP_Properties.i18NMap.put(btn_reset_loaded.getText(), btn_reset_loaded.textProperty());
        ZP_Properties.i18NMap.put(btn_load_movie.getText(), btn_load_movie.textProperty());
        ZP_Properties.i18NMap.put(lbl_fileMonitor.getText(), lbl_fileMonitor.textProperty());

    }

    @FXML
    private void handleBrowse4ImageFile(ActionEvent event) {

        //reset
        lastChoosenDir = null;

        //let´s go...
        FileChooser fileChooser = new FileChooser();

        //filters...
        FileChooser.ExtensionFilter extFilter_jpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter_jpg);
        FileChooser.ExtensionFilter extFilter_jpeg = new FileChooser.ExtensionFilter("jpeg files (*.jpeg)", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter_jpeg);
        FileChooser.ExtensionFilter extFilter_png = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter_png);

//        FileChooser.ExtensionFilter extFilter_bmp = new FileChooser.ExtensionFilter("bmp files (*.bmp)", "*.bmp");
//        fileChooser.getExtensionFilters().add(extFilter_bmp);
        FileChooser.ExtensionFilter extFilter_wildcard = new FileChooser.ExtensionFilter("*.* (*.*)", "*.*");
        fileChooser.getExtensionFilters().add(extFilter_wildcard);
        
        //dir...
        fileChooser.setInitialDirectory(DEFAULT_FILECHOOSER_DIR);
        fileChooser.setTitle(this.resourceBundle.getString("Browse_Images_Dialog_Title"));

        lastChoosenFiles = fileChooser.showOpenMultipleDialog(ZP_Prototype2.getInstance().primaryStage);


        onFileChoosen();

    }

    @FXML
    private void handleBrowse4Movie(ActionEvent event) {


        try {
            //reset
            lastChoosenDir = null;

            //let´s go...
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(DEFAULT_FILECHOOSER_DIR);
            fileChooser.setTitle(this.resourceBundle.getString("Browse_MOVIE_Dialog_Title"));
            //  ZPScene zpScene = (ZPScene) stage.getScene();


            lastChoosenFiles = fileChooser.showOpenMultipleDialog(ZP_Prototype2.getInstance().primaryStage);


            for (File imageFile : lastChoosenFiles) {

                //Diag...
                showDiagLoading("capture movie...", Dialog_Workspace_FXMLController.dialogtype_capturemovie);


                if (imageFile.getName().indexOf(".mov") != -1
                        || imageFile.getName().indexOf(".MOV") != -1 //quickTime?
                        || imageFile.getName().indexOf(".mp4") != -1 //MPEG  
                        ) {

                    //setDir...
                    ZP_Properties.setMovieFolder2Capture(imageFile.getName());
                    ZP_Properties.initMovieFolder2Capture();
                    final File mv2capture = imageFile;
                    //capture...
                    //TODO:return fileList...
                    Task task_CaptureMovie = new Task<Void>() {
                       
                        @Override
                        public Void call() {
                            captureMovie(mv2capture);
                              return null;
                        }

                        @Override
                        protected void succeeded() {
                            super.succeeded();
                            //getFiles...
                            File dir = new File(ZP_Properties.getMovieFolder2Capture());
                            loadImages(Arrays.asList(dir.listFiles()));
                        }

                        @Override
                        protected void cancelled() {
                            super.cancelled(); //To change body of generated methods, choose Tools | Templates.
                            //getFiles...
                            File dir = new File(ZP_Properties.getMovieFolder2Capture());
                            loadImages(Arrays.asList(dir.listFiles()));
                        }
                    };

                    //is null by referenzing from DialogWorkspace...?
                    ZP_Properties.getInstance().captureMovie = task_CaptureMovie;
                    Thread threadCapture = new Thread(task_CaptureMovie);
                    ZP_Properties.getInstance().captureMovieThread = threadCapture;

                    threadCapture.start();
                }
            }

        } catch (Exception e) {
            System.out.println("Error handleBrowse4Movie:" + e);
        }
    }

    @FXML
    private void handleBrowseDirectoryButton(ActionEvent event) {

        //reset...
        lastChoosenFiles = null;

        //let´s go...
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(DEFAULT_FILECHOOSER_DIR);
        chooser.setTitle(this.resourceBundle.getString("Browse_Images_Dialog_Title"));
        ZPScene zpScene = (ZPScene) stage.getScene();


        lastChoosenDir = chooser.showDialog(stage);

        //Diag...
        // showDiagLoading("loading images from folder...");

        onFileChoosen();

    }

    @FXML
    /*
     * reset all Images
     */
    private void handleResetAction(ActionEvent event) {
        wallpaper.resetImages();
        
    }

    @FXML
    private void handleDiag_help_load() {
        ZP_Prototype2.getInstance().getScene().getDialogPaneController().setWebViewUrl("http://www.tapetenagentur.de");
        ZP_Prototype2.getInstance().getScene().showDialog();
    }

    public void listDir(File dir, List choosenFiles) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                System.out.print(files[i].getAbsolutePath());

                choosenFiles.add(files[i]);
//recursive???..here                        
//                        if (files[i].isDirectory()) {
//				System.out.print(" (Ordner)\n");
//				listDir(files[i],choosenFiles); // ruft sich selbst mit dem 
//					// Unterverzeichnis als Parameter auf
//				}
//			else {
//				System.out.print(" (Datei)\n");
//                                
//			}
            }
        }
    }
    //++++++++++++++++++++++++++
    //+++ XUGGLE :-) CaptureMovie
    //+++

    public void captureMovie(File videoFile) {

        String inputFilename = videoFile.getAbsolutePath();//+ videoFile.getName();
        IMediaReader mediaReader = ToolFactory.makeReader(inputFilename);

        ZP_Properties.getInstance().mediaReader = mediaReader;

        // stipulate that we want BufferedImages created in BGR 24bit color space
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        mediaReader.addListener(new VideoThumbnailsExample.ImageSnapListener());


        // read out the contents of the media file and
        // dispatch events to the attached listener
        while (mediaReader.readPacket() == null) ;



    }

    //+++
    //+++ CaptureMovie
    //++++++++++++++++++++++++++
    //+++++++++++
    //getImageFiles.selected by FileManager..
    private void onFileChoosen() {

        try {
            List<File> choosenFiles = new ArrayList<File>();
            if (lastChoosenDir != null) {

                listDir(lastChoosenDir, choosenFiles);
                //  lastChoosenDir.listFiles();
                //  List<File>chossenFiles=
            } else {
                choosenFiles = Controller_FileManager.this.getLastChoosenImageFiles();
                if (choosenFiles == null) {
                    return;
                }
            }

            loadImages(choosenFiles);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadImage(String filename) {
        ArrayList<File> file = new ArrayList<File>();
        file.add(new File(filename));
        loadImages(file);
    }

    public void loadImages(List<File> choosenFiles) {

        //Diag...
        showDiagLoading("loading images...", Dialog_Workspace_FXMLController.dialogtype_loadingImages);


        try {
            int indexImage = 0;
            for (File imageFile : choosenFiles) {

                int rotateAngle = 0;
                int imgSize = WallpaperDimensions.sizeThumb;//100;//TODO:...effect in rendering..

                //++++++++++++++++++++++++++++
                //load images scaled...
                // url - the string representing the URL to use in fetching the pixel data
                // requestedWidth - the image's bounding box width
                // requestedHeight - the image's bounding box height
                // preserveRatio - indicates whether to preserve the aspect ratio of the original image when scaling to fit the image within the specified bounding box
                // smooth - indicates whether to use a better quality filtering algorithm or a faster one when scaling this image to fit within the specified bounding box
                // backgroundLoading - indicates whether the image is being loaded in the background

                final String fileUrl = imageFile.toURI().toURL().toString();
                // final Image image = new Image(fileUrl, 0, imgSize, true, false, true);
                //final Image image = new Image(fileUrl, 0, imgSize, true, true, true);
                final Image image = new Image(fileUrl, imgSize,0, true, true, true);
                

                image.progressProperty().addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov,
                            Number old_val, Number new_val) {
                        //System.out.println("imageload:"+new_val.doubleValue() + " %");
                        if (new_val.doubleValue() == 1) {
                            SimpleStringProperty obs_Count_Image_Loaded_Ready = ZP_Prototype2.getInstance().getWallpaper().countImageLoadedReady;
                            Integer curLoaded = new Integer(obs_Count_Image_Loaded_Ready.getValue());
                            curLoaded++;
                            obs_Count_Image_Loaded_Ready.setValue("" + curLoaded);

                            //images fully loaded!
                            int toLoad = Integer.parseInt(ZP_Prototype2.getInstance().getWallpaper().countImageLoaded.getValue());
                            if (curLoaded == toLoad) {
                                // if(curLoaded%10== 0){
                                wallpaper.removeErrorImages();
                                wallpaper.reArrangeImages();
                            }

                        }
                    }
                });


                //#################
                //image...loading progress....
                final Text progressText = new Text("0 %");
                progressText.setFill(Color.BLUEVIOLET);
                image.progressProperty().addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov,
                            Number old_val, Number new_val) {
                        //  System.out.println(new_val + "loading Image progressPropertyChanged: " + fileUrl);
                        progressText.setText((int) new_val.doubleValue() + " %");

                        if (new_val.doubleValue() == 1.0) {

                            //check all Images ready
                            wallpaper.getIsLoadingImageReady();

                            //show loading images...
                            ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().primaryStage.getScene();
                            zpScene.getDialogPaneController().getDiag_ImageView().visibleProperty().set(true);
                            zpScene.getDialogPaneController().getDiag_ImageView().setImage(image);


                        }
                    }
                });


                //#################
                //image...add ErrorListener...
                image.errorProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue ov,
                            Boolean old_val, Boolean new_val) {

                        System.out.println("loading - error for: " + fileUrl);
                        if (new_val.booleanValue() == true) {
                            System.out.println("loading - error for: " + fileUrl);

                            //TODO
                            // what error occurs?
                            // remove Image from Wallpaper

                            //wallpaper.removeErrorImages();

                            errorLoadingList.add(fileUrl);

                        }

                    }
                });



                //#################
                // read EXIF and apply...
                File jpegFile = new File(imageFile.toURI());
                Metadata metadata = null;
                if(jpegFile.getName().endsWith("jpg") || jpegFile.getName().endsWith("jpeg") ){
                try {
                    metadata = JpegMetadataReader.readMetadata(jpegFile);

                    //+++++++++++++++++++++++++++++++++++++++++
                    // iterate through metadata directories
                    // + apply rotation
                    // TODO test, other than 270', etc...
                    Iterator directories = metadata.getDirectories().iterator();
                    if (directories != null) {
                        while (directories.hasNext()) {
                            Directory directory = (Directory) directories.next();
                            // iterate through tags and print to System.out
                            Iterator tags = directory.getTags().iterator();
                            while (tags.hasNext()) {
                                Tag tag = (Tag) tags.next();
                                if (tag.getTagName().equalsIgnoreCase("orientation")) {
                                    System.out.println(imageFile.toURI() + "-->>" + tag);
                                    if (tag.getDescription().indexOf("90") != -1) {
                                        rotateAngle = 90;
                                    }
                                    if (tag.getDescription().indexOf("180") != -1) {
                                        rotateAngle = 180;
                                    }
                                    if (tag.getDescription().indexOf("270") != -1) {
                                        rotateAngle = 270;
                                    }
                                    if (tag.getDescription().indexOf("360") != -1) {
                                        rotateAngle = 360;
                                    }
                                }
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                }
                // read EXIF and apply...
                //#################

                //++++++++++++++++++++++
                // initWallpaperImage
                WallpaperImage wallpaperImage = new WallpaperImage(image, rotateAngle, imageFile.toURI());
                IImageSelection imageSelection = wallpaper.addImage(wallpaperImage);

                indexImage++;


            }//end for

        } catch (Exception e) {
            System.out.println("ERROR load choosenImagesFiles" + e);
            e.printStackTrace();
        }
    }

    private void showDiagLoading(String msg, String dialogtype) {
        ZPScene scene = ZP_Prototype2.getInstance().getScene();
        // scene.getDialogPaneController().showLblDialog(msg);
        if (dialogtype.equals(Dialog_Workspace_FXMLController.dialogtype_loadingImages)) {
            scene.getDialogPaneController().showDiag_loadImages(msg);
        }
        if (dialogtype.equals(Dialog_Workspace_FXMLController.dialogtype_capturemovie)) {
            scene.getDialogPaneController().showDiag_captureMovie(msg);
        }



        scene.showDialog();
    }

    //+++++++++
    //
    // stupidos
    //
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setWallpaper(IWallpaper wallpaper) {
        this.wallpaper = wallpaper;

    }

    public List<File> getLastChoosenImageFiles() {
        return lastChoosenFiles;
    }
    //    @FXML
    //    private void handleWidthComboBoxAction(ActionEvent event) {
    //        if (!isInitializing) {
    //            double widthInPixels = WallpaperDimensions.convertToPixels((String) widthComboBox.getSelectionModel().getSelectedItem());
    //            wallpaper.setWidth(widthInPixels);
    //        }
    //    }
    //
    //    @FXML
    //    private void handleHeightComboBoxAction(ActionEvent event) {
    //        if (!isInitializing) {
    //            double heightInPixels = WallpaperDimensions.convertToPixels((String) heightComboBox.getSelectionModel().getSelectedItem());
    //            wallpaper.setHeight(heightInPixels);
    //        }
    //    }
}
