/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import zp_prototype2.render.TranslationManager;

/**
 *
 * @author Pedro
 */
public class WallpaperDimensions {

    public static int targetResolution = 190;// 600->not much diffent to 190;//190;//190;//190;// 100;//100;Quality !!! 190Failure win32Bit
    public static int DPI = 10;//72;//10;//10;//;19;//19;//190;//72;//190;//72;
    public static int sliceWidth = 500;//200;
    public static double stepsHSlice = 10;//0;//25;//1;//10;
    
    public static int sizeLoadXXL = 2000;//2.8.9 TODO: TEST? not need anymore..size by freeMem*3 in WallpaperImage.loadXXL
    public static int sizeThumb = 200;
    public static double CMS_IN_ONE_INCH = 2.54;
    
    
//    public static String DEFAULT_WIDTH = "232.5";//"21.0";
//    public static String DEFAULT_HEIGHT = "46.5";//"29.7";
//    public static double DEFAULT_WIDTH_VALUE = convertToPixels(DEFAULT_WIDTH);
//    public static double DEFAULT_HEIGHT_VALUE = convertToPixels(DEFAULT_HEIGHT);
//    public static String[] DIMENSIONS = {
//       
//        DEFAULT_HEIGHT
//        ,DEFAULT_WIDTH
//    };
    
    
    
    
//    public static String[] DIMENSIONS = {
//        "42.6",
//        "113.8",
//        "21.0",
//        "29.7",
//        "31.5",
//        DEFAULT_HEIGHT,
//        "46.5",
//        "85.1",
//        "93",
//        "139.5",
//        "140",
//        "186",
//        DEFAULT_WIDTH,
//        "232.5",
//                "250",
//        "260",
//        "270" //        ,"279",
//        , "325.5" //      ,  "372"
//        //      ,  "418.5"
//        , "491.5"
//        , "465"
//    };

    
//    
//    public static String DEFAULT_MEDIUM = "Tapete";    
//    public static String[] MEDIUMS = {
//        DEFAULT_MEDIUM
//        ,"Acryl"
//        ,"Poster"
//        ,"DIN A4"
//        ,"Facebook TopImage"
//        ,"WebGallery"
//        //"Frei"
//    };
    
        
    public static double convertToPixels(String cms) {
        return convertToPixels(Double.parseDouble(cms));
    }

    public static double convertToPixels(double cms) {
        double pixelsInOneCM = DPI / CMS_IN_ONE_INCH;
        //return (int) Math.round(cms * pixelsInOneCM);
        return cms * pixelsInOneCM;
    }

    public static double convertPixelToCM(double pixel) {
        double pixelsInOneCM = pixel / (new Double(DPI) / CMS_IN_ONE_INCH);
        return pixelsInOneCM;
    }

    public static double convertPixelToM(double pixel) {
        double pixelsInOneCM = pixel / (new Double(DPI) / CMS_IN_ONE_INCH);
        return pixelsInOneCM / 100;
    }

    public static double getScaleFactor() {
        return targetResolution / DPI;//190/72;
    }

    public static double getQMPrice() {
        try {
            return Double.parseDouble(TranslationManager.translate("QMPRICE"));
        } catch (Exception e) {
            e.printStackTrace();
            return 50;
        }
    }

    public static double getPrice() {
        double ret;
        double qmPrice = getQMPrice();
        //at initTime wallpaper isnt initiated...
        if (ZP_Prototype2.getInstance().getWallpaper() != null) {
            ret = qmPrice * convertPixelToM(ZP_Prototype2.getInstance().getWallpaper().getWidth()) * convertPixelToM(ZP_Prototype2.getInstance().getWallpaper().getHeight());
        } else {
            ret= 0d;
            //ret = qmPrice * convertPixelToM(DEFAULT_WIDTH_VALUE) * convertPixelToM(DEFAULT_HEIGHT_VALUE);
        }
        DecimalFormat twoDForm = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return Double.valueOf(twoDForm.format(ret));
    }
}
