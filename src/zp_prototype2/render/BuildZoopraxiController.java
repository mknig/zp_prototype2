/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import javafx.concurrent.Task;
import zp_prototype2.IWallpaper;
import zp_prototype2.WallpaperDimensions;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Medium;
import zp_prototype2.ZP_MediumManager;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.image.WallpaperImage;
import zp_prototype2.panels.MainPanelController;

/**
 *
 * @author admin
 */
public class BuildZoopraxiController {

    public static Semaphore lock = new Semaphore(1);
    public static Thread threadAll;
    public static Task task_ALL;

    public static void buildZoopraxi() {
        System.out.println("Done Clicked");

        //init, reset maps...
        ZP_Properties.readyImagesXXL=new HashMap<String, WallpaperImage>();
        ZP_Properties.readyImages=new HashMap();
        
        ZP_Properties.images2prepare = 0;
        ZP_Properties.images2prepareProcessed = 0;
        ZP_Properties.pt2render = 0;
        ZP_Properties.pt2renderProcessed = 0;
        
        
        
         task_ALL = new Task<Void>() {
            @Override
            public Void call() {


                //initDiag....
                ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().getScene();

                //   zpScene.closeDialog();
                //   zpScene.getDialogPaneController().showLblDialog("init Zoopraxi");


                zpScene.setBlockUI(true);
                zpScene.showDialog();

                zpScene.getDialogPaneController().showDiag_buildZoopraxi("XXXX");
                // zpScene.getDialogPaneController().getProgressBar().setVisible(true);
                // zpScene.getDialogPaneController().getMessageBox().setVisible(true);
                // zpScene.getDialogPaneController().getDiag_ImageView().setVisible(false); 


                String message = "init Zoopraxi:";
                zpScene.getDialogPaneController().getMessageBox().setText(message);



                //XXXXXXXXX
                // 1. 
                // getZpID
                // modal
                String __URL__SERVER__ZOOPRAXEACTION__ = ZP_Properties.getInstance().getZoopraxiActionURL();



//                ZP_Medium medium = new ZP_Medium();
//                medium.type = "WALLPAPER";
//                String ZP_PRODUCT_ROOT_ID = ZP_Properties.getZPRootID(medium);
                
                ZP_Medium medium= ZP_MediumManager.getInstance().getSelectedMedium();
                String ZP_PRODUCT_ROOT_ID =""+medium.getId();
                
                

                String wb_zoopraxi_width = "" + WallpaperDimensions.convertPixelToCM(ZP_Prototype2.getInstance().getWallpaper().getWidth());
                String wb_zoopraxi_height = "" + WallpaperDimensions.convertPixelToCM(ZP_Prototype2.getInstance().getWallpaper().getHeight());
                String wb_zoopraxi_price = "" + WallpaperDimensions.getPrice();


                String requestZPIdURL = __URL__SERVER__ZOOPRAXEACTION__ + "?command=initNewZP&ZP_TAPETE_ROOT_ID=" + ZP_PRODUCT_ROOT_ID + "&ZP_TAPETE_WIDTH=" + wb_zoopraxi_width + "&ZP_TAPETE_HEIGHT=" + wb_zoopraxi_height + "&ZP_TAPETE_PRICE=" + wb_zoopraxi_price;

                Integer zpID = 0;
                try {

                    //+++++++++++++++++++++++++++
                    //comment for debug...
                    //comment for debug...
                    //comment for debug...

                    zpID = ZP_HttpRequest_Apache.requestZPID(requestZPIdURL);

                    //comment for debug...
                    //comment for debug...
                    //comment for debug...
                    //+++++++++++++++++++++++++++

                } catch (Exception e) {
                    System.out.println("ERROR: initNewZP..");
                    e.printStackTrace();
                }
                ZP_Properties.getInstance().setZpID(zpID);

                message = "your Zoopraxi-ID: " + zpID;
                zpScene.getDialogPaneController().getMessageBox().setText(message);



                //xxxxxxxxxxxxxxxxxxxxx
                //
                // 2. 
                // build ZP and upload...
                // build ZP and upload...
                // build ZP and upload...
                // build ZP and upload...
                // build ZP and upload...
                //
                //  

                //starts...
                final IWallpaper zpWallpaper = MainPanelController.wallpaper;

                //V1...snapShot complete node....
                //BuildZoopraxi builder = new BuildZoopraxi();


                //V2...snapShot slicewidth complete height.....
                //stops at...93*250 one Picture
                BuildZoopraxiV3 builder = new BuildZoopraxiV3();
                builder.buildZPAndSave(this, zpWallpaper);


                //hide Dialog
                zpScene.closeDialog();
                zpScene.getDialogPaneController().getProgressBar().setVisible(false);
                zpScene.getDialogPaneController().getMessageBox().setVisible(false);
                zpScene.getDialogPaneController().getDiag_ImageView().setVisible(false);
                zpScene.setBlockUI(false);

                return null;

            }

            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("Done!");

                //++++++++++++++++++++++
                //
                // checkout...
                //
                int zpID = ZP_Properties.getInstance().getZpID();
                String __URL__ORDER__ZP___ = ZP_Properties.zpServer_cmd_checkout + zpID;

                //start Checkout in App  
                //  ZP_Prototype2.getInstance().getScene().getDialogPaneController().setWebViewUrl(__URL__ORDER__ZP___);
                //  ZP_Prototype2.getInstance().getScene().showDialog();

                //start Checkout in Browser..
                try {
                    //String starterUrl = "http://www.tapetenagentur.de/mb_ta/html/de/mb_templates/zoopraxi/Zoopraxi_START.cat78061.jsp";
                    //String starterUrl = ZP_Properties.zpServer+ TranslationManager.translate("dic_checkout_finish_URL");

                    //String starterUrl = ZP_Properties.zpServer+"shop/shop.do?command=go&category=79021&showJSP=/html/de/mb_templates/zoopraxi/Zoopraxi_START.cat78061.jsp";
                    String checkoutJSP = TranslationManager.translate("dic_checkout_finish_URL");
                    String starterUrl = ZP_Properties.zpServer + checkoutJSP;

                    ZP_Prototype2.getInstance().getScene().getDialogPaneController().setWebViewUrl(starterUrl);
                    ZP_Prototype2.getInstance().getScene().showDialog();
                    //java.awt.Desktop.getDesktop().browse(new URI(__URL__ORDER__ZP___));

                    BarBoneLauncher.openURL(__URL__ORDER__ZP___);
                    
                    //reset
                    ZP_Properties.getInstance().setZpID(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            protected void cancelled() {
                super.cancelled();
                System.out.println("build ZP-> cancelled");
                
                ZP_Properties.getInstance().setZpID(0);
                updateMessage("Cancelled!");
                
                
            }
//                };//end Task
//                new Thread(task_RenderZP_and_Uplaod).start();
//                
//                return null;
//            }
        };//end Task
        threadAll = new Thread(task_ALL);
        threadAll.start();
        threadAll.setPriority(Thread.MAX_PRIORITY);

    }
}
