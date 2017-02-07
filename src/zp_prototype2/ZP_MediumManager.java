/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import zp_prototype2.render.TranslationManager;

/**
 *
 * @author admin
 */
public class ZP_MediumManager {

    static ZP_MediumManager me;
    List<ZP_Medium> mediums = new ArrayList<ZP_Medium>();
    ZP_Medium selectedMedium;
    ZP_Medium defualtMedium;
    
    public static ZP_MediumManager getInstance() {

        if (me == null) {
            me = new ZP_MediumManager();
            me.init();
        }
        return me;
    }

    private void init() {

        //get MediumIds..
        String dic_mediumIDs = TranslationManager.translate("zp_medium_ids");

        String[] mediumIds = dic_mediumIDs.split(Pattern.quote(","));
        System.out.println(Arrays.toString(mediumIds));

        //get Medium.XStream ->unmarshall to Object...
        for (int i = 0; i < mediumIds.length; i++) {
            try {
                ZP_Medium medium = ZP_Medium.unmarshall(mediumIds[i]);
                mediums.add(medium);
            } catch (Exception e) {
                //mediums.add("NA");
            }
        }
    }

    public List getNameMediums() {
        ArrayList<String> ret = new ArrayList();

        for (Iterator<ZP_Medium> it = mediums.iterator(); it.hasNext();) {
            ZP_Medium medium = it.next();
            ret.add(medium.getName());
        }
        return ret;
    }

    public ZP_Medium getMedium(Object obj) {

        for (Iterator<ZP_Medium> it = mediums.iterator(); it.hasNext();) {
            ZP_Medium medium = it.next();
            if (obj.equals(medium.getName())) {
                return medium;
            }
        }
        return null;
    }

    public ZP_Medium getDefaultMedium(){
         String dic_medium_default = TranslationManager.translate("zp_medium_default");
         ZP_Medium selected=getMedium(dic_medium_default);
         setSelectedMedium(selected);
         return selected;
    }
    
    public ZP_Medium getSelectedMedium() {
        return selectedMedium;
    }

    public void setSelectedMedium(ZP_Medium selectedMedium) {
        this.selectedMedium = selectedMedium;

    }
}
