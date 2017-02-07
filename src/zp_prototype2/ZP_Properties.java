/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import com.thoughtworks.xstream.XStream;
import com.xuggle.mediatool.IMediaReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.io.FileUtils;
import zp_prototype2.image.WallpaperImage;
import zp_prototype2.render.MemoryManager;
import zp_prototype2.render.TranslationManager;
import zp_prototype2.render.TranslationManagerRunnable;

/**
 *
 * @author admin
 */
public class ZP_Properties {

    public static boolean DEBUG = false;
    private static String version = "b 0.2.8.28";
   private static ZP_Properties instance = null;
    //public static String zpServer = "http://localhost/mb_ta/";
    //public static String zpServer = "http://ex10-2.tapetenagentur.de/mb_ta/";
    //public static String zpServer = "http://www.tapetenagentur.de/mb_ta/";
    // public static String zpServer = "http://www.zoopraxi.com/mb_ta/";
   //public static String zpServer = "http://ex60.tapetenagentur.de/mb_ta/";
     public static String zpServer = "https://www.tapetenagentur.de/mb_ta/";
    
     
    //captureMovie...
    public Thread captureMovieThread;
    public Task captureMovie;
    public IMediaReader mediaReader;
    //++++++++++
    // server-URLs
    public static String zpServer_cmd_checkout = zpServer + "shop/shopcart.do?command=init4newZoopraxi&iFrame=true&viewType=full&productId=";
    public static String zpServer_cmd_initZP = zpServer + "common/zoopraxi.do?command=initNewZP&ZP_TAPETE_ROOT_ID=";
    //+++++++++++
    // lang...
    public static String lang = "de";
    //++++++++++
    // render...
    public static Map<String, WallpaperImage> readyImagesXXL = null;
    public static Map readyImages = null;
    public static boolean runLater = false;
    public static Map<String, StringProperty> i18NMap = new HashMap<String, StringProperty>();
    //++++++++++++
    // Files
    public static String movieFolder2Capture;
    //+++++++++++++++
    //States...choose Scene
    public static String state = "design";
    //++++++++++++++++++
    // zpID
    static int zpID = 0;
    //###############
    // processing build ZP
    // 
    public static int images2prepare = 0;
    public static int images2prepareProcessed = 0;
    public static double pt2render = 0;
    public static double pt2renderProcessed = 0;
    //steps_titledImages....
    public static ImageView step1_o;
    public static ImageView step1_c;
    public static ImageView step2_o;
    public static ImageView step2_c;
    public static ImageView step3_o;
    public static ImageView step3_c;
    public static ImageView step4_o;
    public static ImageView step4_c;
    public static ImageView step5_o;
    public static ImageView step5_c;

    private ZP_Properties() {
        // i am singletorn
        double tileImageHeight = 35;
        step1_o = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_1_selected.png"), 0, tileImageHeight, true, true));
        step1_c = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_1_unselected.png"), 0, tileImageHeight, true, true));
        step2_o = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_2_selected.png"), 0, tileImageHeight, true, true));
        step2_c = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_2_unselected.png"), 0, tileImageHeight, true, true));
        step3_o = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_3_selected.png"), 0, tileImageHeight, true, true));
        step3_c = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_3_unselected.png"), 0, tileImageHeight, true, true));
        step4_o = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_4_selected.png"), 0, tileImageHeight, true, true));
        step4_c = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_4_unselected.png"), 0, tileImageHeight, true, true));
        step5_o = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_5_selected.png"), 0, tileImageHeight, true, true));
        step5_c = new ImageView(new Image(this.getClass().getResourceAsStream("resource/steps_5_unselected.png"), 0, tileImageHeight, true, true));

    }

    public static ZP_Properties getInstance() {
        if (instance == null) {
            instance = new ZP_Properties();
        }
        return instance;
    }

    //+++++++++++++++
    //
    // Files...
    //
    public static String initMovieFolder2Capture() {
        // return movieFolder2Capture;

        //String strPath = (new JFileChooser().getFileSystemView().getDefaultDirectory()) + "/zpLocal/" + zpID + "/";
        String zpHomeFolder = System.getProperty("user.home");
        String strPath = zpHomeFolder + "/zoopraxi_upload/CaptureMovies/" + movieFolder2Capture + "/";

        //create tmpDir
        File filePath = new File(strPath);

        if (filePath.exists()) {
//            if (filePath.isDirectory()) {
//                try {
//                    FileUtils.deleteDirectory(filePath);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            System.out.println("delete capture folder: " + strPath);
            final File[] files = filePath.listFiles();
            for (File f : files) {
                f.delete();
            }

        } else {
            filePath.mkdirs();
        }
        return strPath;

    }

    public static String getMovieFolder2Capture() {
        // return movieFolder2Capture;

        //String strPath = (new JFileChooser().getFileSystemView().getDefaultDirectory()) + "/zpLocal/" + zpID + "/";
        String zpHomeFolder = System.getProperty("user.home");
        String strPath = zpHomeFolder + "/zoopraxi_upload/CaptureMovies/" + movieFolder2Capture + "/";
        return strPath;

    }

    public static void setMovieFolder2Capture(String movieFolder2Capture) {
        ZP_Properties.movieFolder2Capture = movieFolder2Capture;
    }

    public static String getZoopraxiLocalPath() {

//        if (zpID == 0) {
//            throw new RuntimeException("ZPID is 0");
//        }

        //String strPath = (new JFileChooser().getFileSystemView().getDefaultDirectory()) + "/zpLocal/" + zpID + "/";
        String zpHomeFolder = System.getProperty("user.home");
        String strPath = zpHomeFolder + "/zoopraxi_upload/" + zpID + "/";

        //create tmpDir
        File filePath = new File(strPath);
        filePath.mkdirs();
        return strPath;
    }

    public static String getCurrentVersion() {
        return TranslationManager.translate("CurrentVersion");
    }

    //++++++++++++++++++++++++++++++++++++++
    // translation
    public static void updateLanguage(String lang) {
        //updateLanguageEach(lang);
        if (DEBUG == false) {
            ArrayList<String> keys = new ArrayList<String>();
            Iterator<String> itLangKeysX = i18NMap.keySet().iterator();
            while (itLangKeysX.hasNext()) {
                String key = itLangKeysX.next();
                keys.add(key);
            }
            TranslationManagerRunnable trans = new TranslationManagerRunnable(keys, i18NMap);
            Platform.runLater(trans);
        }
    }

    public static void updateLanguageEach(String lang) {
        //translate each by each        ....Performance - GRRR            
        Iterator<String> itLangKeys = i18NMap.keySet().iterator();
        while (itLangKeys.hasNext()) {
            String key = itLangKeys.next();
            try {
                //SimpleStringProperty trans = TranslationManager.translateAsync(key, i18NMap.get(key));
                //i18NMap.get(key).bind(trans);
                TranslationManagerRunnable trans = new TranslationManagerRunnable(key, i18NMap.get(key));
                Platform.runLater(trans);

            } catch (Exception e) {
                System.out.println("Error UpdateLanguage...");
                e.printStackTrace();
            }

        }
    }

    public String getTranslationURL() {
        return zpServer + "servlet/TranslationService";
    }

    //__URL__SERVER__ZOOPRAXEACTION__
    public String getZoopraxiActionURL() {
        return zpServer + "common/zoopraxi.do";
    }

    //__URL__SERVER__UPLOAD__SERVLET__
    public String getZoopraxiUploadServletURL() {
        return zpServer + "/servlet/UploadControllerApache";
    }

    public static Map getReadyImages() {
        //System.out.println("**********ZP_PROPS->readyImages.Size: " + readyImages.size() + "-Mem: " + MemoryManager.getMemStat());
        return readyImages;
    }

    public static Map getReadyImagesXXL() {
        // System.out.println("**********ZP_PROPS->readyImagesXXL.SIZE: " + readyImagesXXL.size() + "-Mem: " + MemoryManager.getMemStat());
        return readyImagesXXL;
    }

    public static String getZPRootID(ZP_Medium medium) {

        //TODO: productcomposer...getID
        //return "9245";
        //return "11680";
        // return "11731";
        //String mediumID = TranslationManager.translate("mediumID."+medium.type);
        //return mediumID;

        try {
            ZP_Medium mediumX = ZP_Medium.unmarshall("Wallpaper");
            return "" + mediumX.id;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }

    public static String getVersion() {
        String buildVersion = version;
        String curVersion = TranslationManager.translate("current_version");
        if (!buildVersion.equals(curVersion)) {
            version = "(u!)";//+buildVersion+"/"+curVersion;
            //Check for update....!!!
            version += buildVersion + "/" + curVersion;
        }
        return "Version:" + version;
    }

    //+++++++++++
    // stuipidos
    public static int getZpID() {
        return zpID;
    }

    public void setZpID(int zpID) {
        this.zpID = zpID;
    }

    public static String getLang() {
        return lang;
    }

    public static void setLang(String lang) {
        ZP_Properties.lang = lang;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        ZP_Properties.state = state;
    }

    public static synchronized int getImages2prepare() {
        return images2prepare;
    }

    public static synchronized void setImages2prepare(int images2prepare) {
        ZP_Properties.images2prepare = images2prepare;
    }

    public static synchronized int getImages2prepareProcessed() {
        return images2prepareProcessed;
    }

    public static synchronized void setImages2prepareProcessed(int images2prepareProcessed) {
        ZP_Properties.images2prepareProcessed = images2prepareProcessed;
    }

    public static void setReadyImages(Map readyImages) {
        ZP_Properties.readyImages = readyImages;
    }

    public static double getPt2render() {
        return pt2render;
    }

    public static void setPt2render(double pt2render) {
        ZP_Properties.pt2render = pt2render;
    }

    public static double getPt2renderProcessed() {
        return pt2renderProcessed;
    }

    public static void setPt2renderProcessed(double pt2renderProcessed) {
        ZP_Properties.pt2renderProcessed = pt2renderProcessed;
    }

    public static void setReadyImagesXXL(Map readyImagesXXL) {
        ZP_Properties.readyImagesXXL = readyImagesXXL;
    }

    public static void setVersion(String version) {
        ZP_Properties.version = version;
    }
}
