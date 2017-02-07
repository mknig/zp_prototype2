/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import com.thoughtworks.xstream.XStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.beans.property.StringProperty;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import zp_prototype2.ZP_Medium;

import zp_prototype2.ZP_Properties;

/**
 *
 * @author admin
 */
public class TranslationManagerRunnable implements Runnable {

    String key = null;
    StringProperty value = null;
    List keys = null;
    Map i18nMap = null;

    public TranslationManagerRunnable(String key, StringProperty value) {
        this.key = key;
        this.value = value;
    }

    public TranslationManagerRunnable(ArrayList keys, Map i18nMap) {
        this.keys = keys;
        this.i18nMap = i18nMap;
    }

    public void run() {

        if (i18nMap!=null) {
            transKeyList();
        } else {
            transKeyValue();
        }
    }

    public void transKeyList() {

        String url = ZP_Properties.getInstance().getTranslationURL();
        String curLang = ZP_Properties.getInstance().getLang();
       


        //marshallKey...
        String keysAsXML = "";
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        keysAsXML = xstream.toXML(keys);
        keysAsXML= URLEncoder.encode(keysAsXML);

         url += "?lang=" + curLang +"&translist=translist"+"&context=zoopraxiV2&keyPhrase=" + keysAsXML;


        String ret = "noTranslated";
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            ret = URLDecoder.decode(httpclient.execute(httpget, responseHandler), "UTF-8");
           
            //unMarschall...
            //XStream xstream = new XStream();
            Map trans= (Map) xstream.fromXML(ret);
            
            Iterator itKeySet = trans.keySet().iterator();
            while(itKeySet.hasNext() ){
                String key=(String)itKeySet.next();
                ((StringProperty) i18nMap.get(key)).setValue((String)trans.get(key));
            }
            
            
             
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        
    }

    public void transKeyValue() {

        String url = ZP_Properties.getInstance().getTranslationURL();
        String curLang = ZP_Properties.getInstance().getLang();
        url += "?lang=" + curLang + "&context=zoopraxiV2&keyPhrase=" + key;


        String ret = "noTranslated";
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            ret = URLDecoder.decode(httpclient.execute(httpget, responseHandler), "UTF-8");
            //ret = httpclient.execute(httpget, responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        value.setValue(ret);
    }
}
