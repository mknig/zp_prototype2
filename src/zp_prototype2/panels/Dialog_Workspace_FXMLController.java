/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.ImageSelection;
import zp_prototype2.render.BuildZoopraxiController;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class Dialog_Workspace_FXMLController implements Initializable {

    String dialogTyp = "";
    public static String dialogtype_loadingImages = "loadingImages";
    public static String dialogtype_capturemovie = "dialogtype_capturemovie";
    public static String dialogtype_buildZP = "buildZP";
    FadeTransition fadeTransition;
    @FXML
    Pane pane_Dialog;
    @FXML
    Button btn_diag_close;
    @FXML
    Label lblDialog;
    @FXML
    WebView webView;
    //render....
    @FXML
    TextField messageBox;
    @FXML
    ProgressIndicator progressBar;
    //loading Images...
    @FXML
    VBox vbox_diag_ImageLoad;
    @FXML
    public ImageView diag_ImageView;
    @FXML
    public Label lbl_Count_Image_Loaded;
    @FXML
    public Label lbl_Count_Image_Loaded_Ready;
    @FXML
    public HBox vbox_imageLoad;
    @FXML
    public HBox hboxMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        messageBox.visibleProperty().set(false);
        progressBar.visibleProperty().set(false);
        
        vbox_diag_ImageLoad.visibleProperty().set(false);
      
        pane_Dialog.toFront();
       // vbox_imageLoad.setVisible(false);
        
        //+++++++++++++++++++
        //i18N
        ZP_Properties.i18NMap.put(btn_diag_close.getText(), btn_diag_close.textProperty());


    }

    @FXML
    private void handleCloseAction() {

        pane_Dialog.setVisible(false);
        webView.visibleProperty().setValue(Boolean.FALSE);
        ZP_Prototype2.getInstance().getScene().setBlockUI(false);

        //++++++++++++++++++++++
        //abort build ZP...
        if (dialogTyp.equals("buildZP")) {
            BuildZoopraxiController.task_ALL.cancel();
            // BuildZoopraxiController.threadAll. interrupt();
            dialogTyp = "";
        }

        //++++++++++++++++++++++
        //abort loading image...
        if (dialogTyp.equals(dialogtype_loadingImages)) {
            //abort not ready loaded images....
            ObservableList newList = ZP_Prototype2.getInstance().getWallpaper().initNewImageList();
            List<IImageSelection> img = ZP_Prototype2.getInstance().getWallpaper().getImages();
            ZP_Prototype2.getInstance().getWallpaper().setImages(newList);
            for (IImageSelection imgSelection : img) {
                double progressImage = imgSelection.getWallpaperImage().getImage().getProgress();
                if (progressImage == 1) {
                    newList.add(imgSelection);
                } else {
                    imgSelection.getWallpaperImage().getImage().cancel();
                }
            }
        }

        //++++++++++++++++++++++
        //abort capture movie...
        if (dialogTyp.equals(dialogtype_capturemovie)) {

            try {
                //abort capture Movie.....try and error...:-)
                ZP_Properties.getInstance().captureMovie.cancel();
                //ZP_Properties.getInstance().captureMovieThread.interrupt();
                ZP_Properties.getInstance().captureMovieThread.stop();
                //ZP_Properties.getInstance().mediaReader.close();
                //ZP_Prototype2.getInstance().fileController.threadCapture.stop();
            } catch (Exception e) {
                e.printStackTrace();
            };

        }


    }

    //++++++++++++++
    //
    // handling....
    //
    public void showLblDialog(String msg) {

        webView.visibleProperty().setValue(Boolean.FALSE);
        vbox_imageLoad.setVisible(false);
        lblDialog.textProperty().set(msg);

        FadeTransition fadeTransition = FadeTransitionBuilder.create()
                .duration(Duration.seconds(1))
                //.node(lblDialog)
                .node(hboxMessage)
                .fromValue(1)
                .toValue(0.2)
                .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();
        fadeTransition.play();


    }

    public void showDiag_loadImages(String msg) {

        dialogTyp = dialogtype_loadingImages;

        showLblDialog(msg);
        vbox_imageLoad.setVisible(true);
        vbox_diag_ImageLoad.visibleProperty().set(true);

        //bind counter...
        lbl_Count_Image_Loaded_Ready.textProperty().bind(ZP_Prototype2.getInstance().getWallpaper().countImageLoadedReady);
        lbl_Count_Image_Loaded.textProperty().bind(ZP_Prototype2.getInstance().getWallpaper().countImageLoaded);

    }

    public void showDiag_captureMovie(String msg) {

        dialogTyp = dialogtype_capturemovie;

        showLblDialog(msg);
        vbox_imageLoad.setVisible(false);
        vbox_diag_ImageLoad.visibleProperty().set(false);

        //bind counter...
        lbl_Count_Image_Loaded_Ready.textProperty().bind(ZP_Prototype2.getInstance().getWallpaper().countImageLoadedReady);
        lbl_Count_Image_Loaded.textProperty().bind(ZP_Prototype2.getInstance().getWallpaper().countImageLoaded);

    }

    public void showDiag_buildZoopraxi(String msg) {

        //  showLblDialog(msg);//Attention...cause Exection an abort building...WHY
        vbox_diag_ImageLoad.visibleProperty().set(false);
        vbox_imageLoad.setVisible(true);

        getProgressBar().setVisible(true);
        getMessageBox().setVisible(true);
        getDiag_ImageView().setVisible(true);

        dialogTyp = "buildZP";


    }

    public void setWebViewUrl(String url) {

        showLblDialog("nice to see you...Loading");

        ZP_Prototype2.getInstance().getScene().setBlockUI(true);
        WebEngine webEngine = webView.getEngine();
        // process page loading
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
                if (newState == State.SUCCEEDED) {
                    //fadeTransition.stop();
                    webView.visibleProperty().setValue(Boolean.TRUE);
                }
            }
        });
        //load
        webEngine.load(url);
    }

    //++++++++
    //stupidos
    //stupidos
    //stupidos
    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public Label getLblDialog() {
        return lblDialog;
    }

    public void setLblDialog(Label lblDialog) {
        this.lblDialog = lblDialog;
    }

    public TextField getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(TextField messageBox) {
        this.messageBox = messageBox;
    }

    public ProgressIndicator getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressIndicator progressBar) {
        this.progressBar = progressBar;
    }

    public ImageView getDiag_ImageView() {
        return diag_ImageView;
    }

    public void setDiag_ImageView(ImageView diag_ImageView) {
        this.diag_ImageView = diag_ImageView;
    }
}
