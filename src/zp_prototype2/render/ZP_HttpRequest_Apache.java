/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import java.io.File;
import java.io.FileInputStream;
import javafx.scene.control.ProgressIndicator;
import javax.swing.JProgressBar;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Prototype2;

/**
 *
 * @author admin
 */
public class ZP_HttpRequest_Apache {

    public static String uploadFile(String targetURL, java.io.File file2Upload, String ZP_IMG_Typ, Integer zpID) {

        HttpClient httpclient = new DefaultHttpClient();
        try {

            
            HttpPost httppost = new HttpPost(targetURL);
            File file = file2Upload;
            InputStreamEntity reqEntity = new InputStreamEntity(
                    new FileInputStream(file), -1);
            reqEntity.setContentType("binary/octet-stream");
            reqEntity.setChunked(true);
            
            httppost.setEntity(reqEntity);
            
            JProgressBar progressBar = new JProgressBar(0, 100);
         
            ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().primaryStage.getScene();
           // ProgressIndicator progressIndicatorUpload= zpScene.getGlassPane().getProgressIndicator(); 
             ProgressIndicator progressIndicatorUpload=new ProgressIndicator(); 
            
            
            ProgressBarListener listener = new ProgressBarListener(progressIndicatorUpload,  file2Upload.length() );
            FileEntityWithProgressBar fileEntity = new FileEntityWithProgressBar(file, "binary/octet-stream", listener);
            httppost.setEntity(fileEntity);

           
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

       
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
                System.out.println("Chunked?: " + resEntity.isChunked());
            }
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
            return "done";
        }

    }

    public static Integer requestZPID(String url) throws Exception {

        Integer zpID=null;
        HttpClient httpclient = new DefaultHttpClient();
        try {

            
           // HttpGet httpget = new HttpGet("http://www.google.com/");
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
   
            
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            
            responseBody=responseBody.replaceAll("\\r\\n", "");
            zpID=new Integer(responseBody);
            
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
             return zpID;
        }


    }

}
