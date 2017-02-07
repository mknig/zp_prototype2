/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.image.WallpaperImage;

/**
 *
 * @author admin
 */
public class BuildZP_XXLLoader {

    static BuildZP_XXLLoader instance = null;

    private BuildZP_XXLLoader() {
        // i am singletorn
    }

    public static BuildZP_XXLLoader getInstance() {
        if (instance == null) {
            instance = new BuildZP_XXLLoader();
        }
        return instance;
    }

    public synchronized WallpaperImage loadXXL(WallpaperImage wpImage, int sizeThumb, int size, double scaleFactor) throws Exception {
        
        WallpaperImage ret = null;
        
        //workaround...if image small it loadError might happen...
        int countTries=0;
        double width = 0;
        while (width == 0) {
            ret = wpImage.loadXXL(sizeThumb, size, scaleFactor);
            Image image = ret.getImage();
            width = image.getWidth();
            if (width == 0) {
                System.out.println("XXL-LOADER_2-RETRY!!!:" + image.getWidth() + " ->" + ret.getFileURI());
            }
            countTries++;
            if(countTries>5){
                 System.out.println("XXL-LOADER_2-EXIT!!!:" + image.getWidth() + " ->" + ret.getFileURI());
                break;
            }
        }

        //show loading images...
        ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().primaryStage.getScene();
        zpScene.getDialogPaneController().getDiag_ImageView().visibleProperty().set(true);
        zpScene.getDialogPaneController().getDiag_ImageView().setImage(ret.getImage());

        //Thread.sleep(2000);

        //measure
        PerformanceMeasure.measurePoint("" + wpImage.getFileURI().hashCode(), "BuildZP_XXLLoader.loadXXL->" + wpImage.getFileURI());

        return ret;
    }
}
