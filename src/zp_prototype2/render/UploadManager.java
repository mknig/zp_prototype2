/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import zp_prototype2.ZP_Properties;

/**
 *
 * @author admin
 */
public class UploadManager {
    
    
    public static void test () {
       
        try{
        
       String __URL__SERVER__ZOOPRAXEACTION__  ="";
        __URL__SERVER__ZOOPRAXEACTION__ = "http://ex10.tapetenagentur.de/mb_ta/common/zoopraxi.do";
       
       
       String ZP_PRODUCT_ROOT_ID="9245";
       String wb_zoopraxi_width="232.5";
       String wb_zoopraxi_height="46.5";
       String wb_zoopraxi_price="50";
       
       String requestZPIdURL = ZP_Properties.zpServer_cmd_initZP +ZP_PRODUCT_ROOT_ID+"&ZP_TAPETE_WIDTH="+wb_zoopraxi_width+"&ZP_TAPETE_HEIGHT="+wb_zoopraxi_height+"&ZP_TAPETE_PRICE="+wb_zoopraxi_price;
       //String requestZPIdURL ="/mb_ta/common/zoopraxi.do?command=initNewZP&ZP_TAPETE_ROOT_ID="+ZP_PRODUCT_ROOT_ID+"&ZP_TAPETE_WIDTH="+wb_zoopraxi_width+"&ZP_TAPETE_HEIGHT="+wb_zoopraxi_height+"&ZP_TAPETE_PRICE="+wb_zoopraxi_price;
       Integer zpID=ZP_HttpRequest_Apache.requestZPID(requestZPIdURL);
       System.out.println("ZP-ID: .-->" + zpID);
        }catch(Exception e){
            e.printStackTrace();
        }
                      
    }
    
}
