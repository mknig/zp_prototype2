/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import com.javafx.experiments.scenicview.ScenicView;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import zp_prototype2.panels.ZoomPanelController;
import zp_prototype2.panels.HeaderPanelController;
import zp_prototype2.panels.MainPanelController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.panels.Controller_FileManager;
import zp_prototype2.panels.ImagePanelController;
import zp_prototype2.panels.Menu_accordion_PanelController;
import zp_prototype2.render.TranslationManager;

/**
 *
 * @author Rackor
 */
public class ZP_Prototype2 extends Application {

    private static final double STAGE_DEFAULT_WIDTH = 1024;
    private static final double STAGE_DEFAULT_HEIGHT = 768;
    public static ZP_Prototype2 me = null;
    private Workspace workspace;
    private Wallpaper wallpaper = null;
    //SceneGraphs...
    public static Parent root;
    public static Stage primaryStage;
    public ZPScene scene;
    //Panels
    public FXMLLoaderWrapper fxmlLoader;
    public Pane contentPanel = null;
    //  public BorderPane borderPane = null;
    public Pane headerPanel = null;
    public HeaderPanelController headerPanelController;
    public Pane mainPanel = null;
    public MainPanelController mainPanelController;
    public Pane donePanel = null;
    public Menu_accordion_PanelController menuController;
    public Pane menuAccodion = null;
    public Pane fileManagerPanel = null;
    public Controller_FileManager fileController = null;
    public Pane imageSelectedPanel = null;
    public VBox vBoxMenu = null;
    public ImagePanelController imageSelectedPanelController = null;
    public ZoomPanelController zoomController = null;

    public void setZoomController(ZoomPanelController controller) {
        zoomController = controller;
    }
    // public ImageEditPanelController imageSelectedPanelController=null; 
    private ImagePanelPopup imagePanelPopup;
    private SelectionManagerListener selectionManagerListener;

    public static ZP_Prototype2 getInstance() {
        ZP_Prototype2 ret;
        if (me != null) {
            ret = me;
        } else {
            ret = new ZP_Prototype2();
        }
        return ret;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("ZP main called...");
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("ZP main called...");
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {


        me = this;
        fxmlLoader = new FXMLLoaderWrapper();

        //check enviroment...
        System.out.println("JDK version: " + System.getProperties().get("java.version"));
        System.out.println("javafx.runtime.version: " + System.getProperties().get("javafx.runtime.version"));
        System.out.println("os.name: " + System.getProperties().get("os.name"));
        String lang = (String) System.getProperties().get("user.language");
        System.out.println("user.language: " + lang);


        //exit Zoopraxi...assure stopping Threads 
        primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent onClosing) {
                System.out.println("exit Zoopraxi....");
                System.exit(0);
            }
        });
        // set icon
        //primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("resource/bg_boden_3.jpg")));
        //primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("resource/null.png")));
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("resource/logo_icon.jpg")));

        // set title
        //primaryStage.setTitle("you");

//        GraphicsConfiguration[] graphicConf= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getConfigurations();
//        for (GraphicsConfiguration graphicsConfiguration : graphicConf) {
//            System.out.println(graphicsConfiguration);
//        }

        //++++++++++++++++++
        //load font...
        javafx.scene.text.Font FjallaOne = javafx.scene.text.Font.loadFont(getClass().getResourceAsStream("resource/fonts/FjallaOne-Regular.ttf"), 10);
        javafx.scene.text.Font fontDosis = javafx.scene.text.Font.loadFont(getClass().getResourceAsStream("resource/fonts/dosis/Dosis-Regular.ttf"), 24);
        javafx.scene.text.Font fontAwesome = javafx.scene.text.Font.loadFont(getClass().getResourceAsStream("resource/fonts/FortAwesome-Font-Awesome-v3.0.2/font/fontawesome-webfont.ttf"), 24);
        javafx.scene.text.Font fontEntypo = javafx.scene.text.Font.loadFont(getClass().getResourceAsStream("resource/fonts/Entypo.ttf"), 32);




        this.primaryStage = primaryStage;

        Pane libraryPanel = createLibraryPanel(primaryStage);

        //wallpaper = new Wallpaper((ILibrary) fxmlLoader.getController(), WallpaperDimensions.DEFAULT_WIDTH_VALUE, WallpaperDimensions.DEFAULT_HEIGHT_VALUE);

        double defaultWidht = ZP_MediumManager.getInstance().getDefaultMedium().getDefaultWidth();
        double defaultHeight = ZP_MediumManager.getInstance().getDefaultMedium().getDefaultHeight();
        wallpaper = new Wallpaper((ILibrary) fxmlLoader.getController(), WallpaperDimensions.convertToPixels(defaultWidht), WallpaperDimensions.convertToPixels(defaultHeight));


        workspace = new Workspace(wallpaper);
        mainPanel = createMainPanel(wallpaper, (ILibrary) fxmlLoader.getController());
        // mainPanel = fxmlLoader.loadFXML("panels/Menu_accordion_Panel.fxml");
        mainPanelController = (MainPanelController) fxmlLoader.getController();
        fileManagerPanel = createPanelFileManager(wallpaper);
        donePanel = createDonePanel();
        headerPanel = createHeaderPanel(primaryStage);
        headerPanelController = (HeaderPanelController) fxmlLoader.getController();

        //++++++++++++
        //load Root....
        //load Root....
        //load Root....
        //load Root....

        // root = layoutControls_as_BorderPane_till_2_3_3(workspace, mainPanel, null, donePanel, headerPanel, libraryPanel);
        // root = layoutControls(workspace, mainPanel, zoomPanel, donePanel, headerPanel, libraryPanel);
        root = layoutControls_as_BorderPane(workspace, mainPanel, null, donePanel, headerPanel, libraryPanel);
        //root = layoutControls_as_StackPane(workspace, mainPanel, null, donePanel, headerPanel, libraryPanel);
        //root = layoutControls_as_Anchor(workspace, mainPanel, null, donePanel, headerPanel, libraryPanel);
        //  root = layoutControls_as_AnchorXXX(workspace, wallpaper, (ILibrary) fxmlLoader.getController(), donePanel, headerPanel, libraryPanel);

        //imagePanelPopup = new ImagePanelPopup();
        // imagePanelPopup.setX(300);
        // imagePanelPopup.setY(200);

        selectionManagerListener = new SelectionManagerListener();
        wallpaper.getSelectionManager().addListener(selectionManagerListener);

        scene = new ZPScene(root);
        scene.getStylesheets().add("/zp_prototype2/zp_prototype.css");
        //String image = this.getClass().getResource("/zp_prototype2/resource/wall_texture.JPG").toExternalForm();
        //String image = this.getClass().getResource("/zp_prototype2/resource/lightLinen.png").toExternalForm();
        //root.setStyle("-fx-background-image: url('" + image + "');" );// -fx-background-position: center center; -fx-background-repeat: stretch;");

        //+++++++++++++++
        // debug ScenicView at startUP
        // debug ScenicView at startUP
        // debug ScenicView at startUP
        //ScenicView.show(scene);
        // debug ScenicView at startUP
        // debug ScenicView at startUP
        // debug ScenicView at startUP
        //++++++++++++++++++

        primaryStage.setScene(scene);
        primaryStage.setWidth(STAGE_DEFAULT_WIDTH);
        primaryStage.setHeight(STAGE_DEFAULT_HEIGHT);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.initStyle(StageStyle.UTILITY);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.DECORATED);

        // lang="de";
        ZP_Properties.setLang(lang);
        ZP_Properties.updateLanguage(lang);


        //Welcome 
        //shop/shop.do?command=go&category=79021&showJSP=/html/de/mb_templates/zoopraxi/Zoopraxi_START.cat78061.jsp 
        String dic_intro_URL = TranslationManager.translate("dic_intro_URL");
        String starterUrl = ZP_Properties.zpServer + dic_intro_URL;
        getScene().getDialogPaneController().setWebViewUrl(starterUrl);
//        scene.showDialog();


        //here i am :)
        primaryStage.show();

    }

    private Parent layoutControls_as_BorderPane(Workspace workspace, Pane mainPanel, Pane zoomPanel, Pane donePanel, Pane headerPanel, Pane libraryPanel) {
        try {


            //     Font.loadFont(ZP_Properties.class.getResource("resource/fonts/FjallaOne-Regular.ttf").toExternalForm(), 10);
            //Ensemble2.class.getResourceAsStream("images/icon-48x48.png



            //++++++++++++++++++
            //content
            BorderPane bp_contentPane = new BorderPane();


            bp_contentPane.getStyleClass().add("headerPanel");


//            bp_contentPane.getStyleClass().add("contentPanel");
//            headerPanel.getStyleClass().add("headerPanel");
//            mainPanel.getStyleClass().add("sidePanel");
            workspace.getStyleClass().add("workspacePanel");
//            
//            bp_contentPane.getStyleClass().add("contentPanel");
//            headerPanel.getStyleClass().add("contentPanel");
//            mainPanel.getStyleClass().add("contentPanel");
//            workspace.getStyleClass().add("contentPanel");



//            //EffectHeader
//            DropShadow shadowHeader = new DropShadow();
//            shadowHeader.setColor(Color.BLACK);
//            shadowHeader.setRadius(10);
//            shadowHeader.setBlurType(BlurType.GAUSSIAN);
//            headerPanel.setEffect(shadowHeader);

            //INNERSHADOW
//            InnerShadow isHeader = new InnerShadow();
//            isHeader.setColor(Color.WHITE);
//            isHeader.radiusProperty().set(10);
//            //   isHeader.setOffsetX(2.0f);
//            //   isHeader.setOffsetY(2.0f);
//            headerPanel.setEffect(isHeader);

            //++++++++++++++++++++++++++
            //add
            bp_contentPane.setTop(headerPanel);
            bp_contentPane.setCenter(workspace);

            //menu...
            Pane panelMenuAccordion = fxmlLoader.loadFXML("panels/Menu_accordion_Panel.fxml");
            menuController = (Menu_accordion_PanelController) fxmlLoader.getController();
            //Pane panel = fxmlLoader.loadFXML("panels/Menu_Titled_Panel.fxml");
            menuAccodion = panelMenuAccordion;


            //ImageEdit...
            //Pane imageSelectedPanel = fxmlLoader.loadFXML("panels/ImagePanel.fxml");
            imageSelectedPanel = fxmlLoader.loadFXML("panels/ImagePanel.fxml");
            imageSelectedPanelController = (ImagePanelController) fxmlLoader.getController();
            //imageSelectedPanelController=(ImageEditPanelController) fxmlLoader.getController();
            imageSelectedPanelController.setWallpaper(wallpaper);
            imageSelectedPanelController.initI18N();

            menuController.setImageEdit_ScrollPane(imageSelectedPanel);

//is null!
//            ScrollPane sc_ImageEdit = (ScrollPane) menuAccodion.lookup("imageEdit_ScrollPane");
//            sc_ImageEdit.contentProperty().set(imageSelectedPanel);



            //  Pane libary = fxmlLoader.loadFXML("panels/LibaryPanel.fxml");
            // vBoxMenu.getChildren().addAll(paneli,libary,panel);
            // vBoxMenu.getChildren().addAll(libary,panel);
            //vBoxMenu.getChildren().addAll( imageSelectedPanel,panel); 


            vBoxMenu = new VBox();
            vBoxMenu.getStyleClass().add("menuPanel");

            //styleMenu...

            //vBoxMenu.getStyleClass().add("headerPanel");

//            //DROPSHADOW...
//            DropShadow shadow = new DropShadow();
//            //shadow.setColor(Color.WHITE);
//            shadow.setColor(Color.BLACK);
//            //shadow.setOffsetX(10);
//            shadow.setOffsetY(5);
//            shadow.setRadius(10);
//            shadow.setBlurType(BlurType.GAUSSIAN);
//            vBoxMenu.setEffect(shadow);
//



//            //INNERSHADOW
//            InnerShadow is = new InnerShadow();
//            is.setColor(Color.WHITE);
//            is.radiusProperty().set(10);
//            // is.setOffsetX(2.0f);
//            // is.setOffsetY(2.0f);
//            vBoxMenu.setEffect(is);



//            //LIGHT
//            Distant light = new Distant();
//            light.setAzimuth(-135.0f);
//            Lighting l = new Lighting();
//            l.setLight(light);
//            l.setSurfaceScale(5.0f);
//            vBoxMenu.setEffect(l);


//            //PERSPECTIVE COOL?
//            PerspectiveTransform pt = new PerspectiveTransform();
//            pt.setUlx(0.0f);
//            pt.setUly(0.0f);
//            pt.setUrx(250.0f);
//            pt.setUry(40.0f);
//            pt.setLlx(00.0f);
//            pt.setLly(650.0f);
//            pt.setLrx(250.0f);
//            pt.setLry(550.0f);
//            vBoxMenu.setEffect(pt);


            VBox vBoxSplitter = new VBox();
            VBox.setVgrow(vBoxSplitter, Priority.ALWAYS);
            vBoxMenu.getChildren().addAll(menuAccodion, vBoxSplitter);
            // bp_contentPane.setRight(vBoxMenu);
            bp_contentPane.setLeft(vBoxMenu);

            contentPanel = bp_contentPane;
            return contentPanel;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private Parent layoutControls_as_StackPane(Workspace workspace, Pane mainPanel, Pane zoomPanel, Pane donePanel, Pane headerPanel, Pane libraryPanel) {

        //++++++++++++++++++
        //content
        StackPane contentPane_stack = new StackPane();
        contentPane_stack.getStyleClass().add("contentPanel");

        //add styles
        workspace.getStyleClass().add("workspacePanel");
        headerPanel.getStyleClass().add("headerPanel");
        donePanel.getStyleClass().add("sidePanel");
        mainPanel.getStyleClass().add("sidePanel");

        //add panes

        //contentPane_stack.setAlignment(Pos.CENTER);
        contentPane_stack.getChildren().add(workspace);


        //workspace.setTranslateY(100 );

        contentPane_stack.setAlignment(Pos.TOP_CENTER);
        contentPane_stack.getChildren().add(headerPanel);
        contentPane_stack.getChildren().add(mainPanel);
        contentPane_stack.getChildren().add(donePanel);

        mainPanel.setTranslateX(-400);
        mainPanel.setTranslateY(100);

        donePanel.setTranslateX(+380);
        donePanel.setTranslateY(100);



        contentPanel = contentPane_stack;
        return contentPanel;

    }

    public Pane createMainPanel(Wallpaper wallpaper, ILibrary library) throws Exception {

        Pane panel = fxmlLoader.loadFXML("panels/MainPanel.fxml");
        MainPanelController panelController = (MainPanelController) fxmlLoader.getController();
        panelController.setWallpaper(wallpaper);
        //  panelController.setLibrary(library);
        //  panelController.setStage(primaryStage);


        //panelController.cb_Medium.getSelectionModel().select(ZP_MediumManager.getInstance().getDefaultMedium().getName());


        return panel;
    }

    //  public Pane createPanelFileManager(Wallpaper wallpaper, ILibrary library) throws Exception {
    public Pane createPanelFileManager(Wallpaper wallpaper) throws Exception {

        Pane panel_FileManager = fxmlLoader.loadFXML("panels/Panel_FileManager.fxml");
        fileController = (Controller_FileManager) fxmlLoader.getController();
        fileController.setWallpaper(wallpaper);

        //panelController.setLibrary(library);
        fileController.setStage(primaryStage);

        return panel_FileManager;
    }

    private Pane createZoomPanel(Workspace workspace) throws Exception {
        Pane panel = fxmlLoader.loadFXML("panels/ZoomPanel.fxml");
        ZoomPanelController zoomController = (ZoomPanelController) fxmlLoader.getController();
        zoomController.setWorkspace(workspace);
        return panel;
    }

    private Pane createDonePanel() throws Exception {
        return fxmlLoader.loadFXML("panels/DonePanel.fxml");
        //return fxmlLoader.loadFXML("panels/paneCheckout.fxml");

    }

    private Pane createHeaderPanel(final Stage stage) throws Exception {
        Pane panel = fxmlLoader.loadFXML("panels/HeaderPanel.fxml");
        HeaderPanelController controller = (HeaderPanelController) fxmlLoader.getController();
        controller.addMouseDraggedListener(new IMouseHeaderListener() {
            double sceneX;
            double sceneY;

            @Override
            public void mouseDragged(MouseEvent event) {
                stage.setX(event.getScreenX() - sceneX);
                stage.setY(event.getScreenY() - sceneY);
            }

            @Override
            public void mousePressed(MouseEvent event) {
                sceneX = event.getSceneX();
                sceneY = event.getSceneY();
            }
        });
        return panel;
    }

    private Pane createLibraryPanel(Stage primaryStage) throws Exception {
        return fxmlLoader.loadFXML("panels/LibraryPanel.fxml");
    }

//    public class SelectionManagerListener implements ISelectionManagerListener {
//
//        @Override
//        public void selectionChanged(IImageSelection selectedImage) {
//        }
//
//        @Override
//        public void mouseReleasedOnImage(IImageSelection image, boolean wasMoved) {
//            if (image == null || wasMoved) {
//                imagePanelPopup.hide();
//            } else {
//
//                System.out.println("SelectionManagerListener called 4 " + image.getWallpaperImage().getFileURI());
//
//                Transform localToSceneTransform = image.getNodeRepresentation().getLocalToSceneTransform();
//
//                Point3D topRight = new Point3D(0, 0, 0);
//                Point3D topRightInScene = localToSceneTransform.impl_transform(topRight);
//
//                double maxY = image.getNodeRepresentation().getBoundsInLocal().getMaxY();
//                Point3D bottomRight = new Point3D(0, maxY, 0);
//                Point3D bottomRightInScene = localToSceneTransform.impl_transform(bottomRight);
//
//                //Popup at the selectedImage..
//                double posX = primaryStage.getX() + topRightInScene.getX();
//                double posY = primaryStage.getY() + bottomRightInScene.getY();
//
//                //Popup centered
//                posX = primaryStage.getX() + primaryStage.getWidth() / 2 - imagePanelPopup.getWidth() / 2;
//                posY = primaryStage.getY() + primaryStage.getHeight() / 2 - imagePanelPopup.getHeight() / 2;
//
//                imagePanelPopup.setY(posY);
//                imagePanelPopup.setX(posX);
//                imagePanelPopup.setImage(image);
//                imagePanelPopup.show(primaryStage);
//
//                // imagePanelPopup.show(workspace,100,100);
//
//            }
//        }
//    }
    public class SelectionManagerListener implements ISelectionManagerListener {

        @Override
        public void selectionChanged(IImageSelection selectedImage) {
//PopUP...            
//            if (selectedImage == null) {
//                imagePanelPopup.hide();
//            } else {
//             
//                //show ImagePopUP....
//                
//                Transform localToSceneTransform = selectedImage.getNodeRepresentation().getLocalToSceneTransform();
//
//                Point3D topRight = new Point3D(0, 0, 0);
//                Point3D topRightInScene = localToSceneTransform.impl_transform(topRight);
//
//                double maxY = selectedImage.getNodeRepresentation().getBoundsInLocal().getMaxY();
//                Point3D bottomRight = new Point3D(0, maxY, 0);
//                Point3D bottomRightInScene = localToSceneTransform.impl_transform(bottomRight);
//
//                //Popup at the selectedImage..
//                double posX = primaryStage.getX() + topRightInScene.getX();
//                double posY = primaryStage.getY() + bottomRightInScene.getY();
//
//                //Popup centered
////                double posX = primaryStage.getX() + primaryStage.getWidth() / 2 - imagePanelPopup.getWidth() / 2;
////                double posY = primaryStage.getY() + primaryStage.getHeight() / 2 - imagePanelPopup.getHeight() / 2;
//
//                imagePanelPopup.setY(posY);
//                imagePanelPopup.setX(posX);
//                imagePanelPopup.setImage(selectedImage);
//            
//                
//                
//                if (((BorderPane) ZP_Prototype2.getInstance().getContentPanel()).getRight() == null) {
//                     imagePanelPopup.show(primaryStage);
//                }
//                
//               
//                
//                
//            }
        }

        @Override
        public void mouseReleasedOnImage(IImageSelection image, boolean wasMoved) {
        }
    }

//    private Pane createImagePanel(SelectionManager selectionManager) throws Exception
//    {
//        Pane panel = fxmlLoader.loadFXML("panels/ImagePanel.fxml");
//        selectionManager.addListener((ImagePanelController)fxmlLoader.getController());
//        ((ImagePanelController)fxmlLoader.getController()).setOwnerWindow(primaryStage);
//        return panel;
//    }
//    private void setStageMaximized(Stage stage)
//    {
//        Screen screen = Screen.getPrimary();
//        Rectangle2D bounds = screen.getVisualBounds();
//        stage.setX(bounds.getMinX());
//        stage.setY(bounds.getMinY());
//        stage.setWidth(bounds.getWidth());
//        stage.setHeight(bounds.getHeight());
//    }
    //+++++++++++++++++++
    // stupidos
    // stupidos 
    // stupidos 
    // stupidos
    public Pane getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(Pane contentPanel) {
        this.contentPanel = contentPanel;
    }

    public SelectionManagerListener getSelectionManagerListener() {
        return selectionManagerListener;
    }

    public void setSelectionManagerListener(SelectionManagerListener selectionManagerListener) {
        this.selectionManagerListener = selectionManagerListener;
    }

    public static String getVersion() {


        return ZP_Properties.getVersion();
        //return version;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public Pane getDonePanel() {
        return donePanel;
    }

    public void setDonePanel(Pane donePanel) {
        this.donePanel = donePanel;
    }

    public Pane getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(Pane mainPanel) {
        this.mainPanel = mainPanel;
    }

    public static Parent getRoot() {
        return root;
    }

    public Wallpaper getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(Wallpaper wallpaper) {
        this.wallpaper = wallpaper;
    }

    public ZPScene getScene() {
        return scene;
    }

    public void setScene(ZPScene scene) {
        this.scene = scene;
    }
}
