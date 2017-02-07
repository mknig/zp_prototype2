/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import com.thoughtworks.xstream.XStream;
import java.util.HashMap;
import zp_prototype2.render.TranslationManager;

/**
 *
 * @author admin
 */
public class ZP_Medium {

    //++++++++++++++++
    //org stuff
    public int id;
    public String name;
    public String type;
    public double priceSquareMeter = 0;//set by MB
    //++++++++++++++++
    //printing stuff
    int targetResolution;
    //++++++++++++++++
    //visualisation suff...
    //bgImage...
    //++++++++++++++++
    // Sizeing...Stuff
    //select size by square
    double min_size;
    double max_size;
    double min_square;//widht or heigt of printPanel
    double defaultWidth;
    double defaultHeight;
    //select size by contract
    boolean sizeFixed;
    HashMap fixedSizes = new HashMap();

    public static void main(String[] args) {

        ZP_Medium me = new ZP_Medium();
        try {
            me.marshall();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ZP_Medium() {

        id = 0;
        type = "Wallpaper";
        name = "Name";
        priceSquareMeter = 50.50d;
        targetResolution = 190;

        min_size = 46.5;
        max_size = 1000;
        min_square = 46.5;


        sizeFixed = false;
        fixedSizes = new HashMap();
        fixedSizes.put(1, "10*10");
        fixedSizes.put(2, "10*20");

        defaultWidth = 46.5;
        defaultHeight = 46.5;;

    }

    public static ZP_Medium unmarshall(String dic_key) throws Exception {
        try {
            // String mediumID = TranslationManager.translate("mediumID."+medium.type);
            String mediumID = TranslationManager.translate("Xstream.mediumID." + dic_key);

            XStream xstream = new XStream();
            ZP_Medium medium = (ZP_Medium) xstream.fromXML(mediumID);
            return medium;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String marshall() {
        try {
            XStream xstream = new XStream();
            xstream.autodetectAnnotations(true);
            xstream.toXML(this, System.out);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            // throw e;
        }

//            try{
//            JAXBContext jc = JAXBContext.newInstance(Wallpaper.class);
//            Marshaller marshaller = jc.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            JAXBElement<Wallpaper> jaxbElement = new JAXBElement<Wallpaper>(new QName("Wallpaper"), Wallpaper.class, ZP_Prototype2.getInstance().getWallpaper());
//            marshaller.marshal(jaxbElement, System.out);
//            }catch(Exception e){
//                e.printStackTrace();
//            }


    }

    //++++++++++++++++
    //Stupidos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPriceSquareMeter() {
        return priceSquareMeter;
    }

    public void setPriceSquareMeter(double priceSquareMeter) {
        this.priceSquareMeter = priceSquareMeter;
    }

    public int getTargetResolution() {
        return targetResolution;
    }

    public void setTargetResolution(int targetResolution) {
        this.targetResolution = targetResolution;
    }

    public double getMin_size() {
        return min_size;
    }

    public void setMin_size(double min_size) {
        this.min_size = min_size;
    }

    public double getMax_size() {
        return max_size;
    }

    public void setMax_size(double max_size) {
        this.max_size = max_size;
    }

    public double getMin_square() {
        return min_square;
    }

    public void setMin_square(double min_square) {
        this.min_square = min_square;
    }

    public boolean isSizeFixed() {
        return sizeFixed;
    }

    public void setSizeFixed(boolean sizeFixed) {
        this.sizeFixed = sizeFixed;
    }

    public HashMap getFixedSizes() {
        return fixedSizes;
    }

    public void setFixedSizes(HashMap fixedSizes) {
        this.fixedSizes = fixedSizes;
    }

    public double getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(double defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public double getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(double defaultHeight) {
        this.defaultHeight = defaultHeight;
    }
    
}
