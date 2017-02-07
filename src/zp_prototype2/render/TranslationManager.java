/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.params.CoreConnectionPNames;

import zp_prototype2.ZP_Properties;

/**
 *
 * @author admin
 */
public class TranslationManager {

    public static String translate(String key) {
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
        return ret;
    }

    public static SimpleStringProperty translateAsync(final String key, final StringProperty value) throws Exception {

        final SimpleStringProperty ret = new SimpleStringProperty();

        String url = ZP_Properties.getInstance().getTranslationURL();
        String curLang = ZP_Properties.getInstance().getLang();
        url += "?lang=" + curLang + "&context=zoopraxiV2&keyPhrase=" + key;


        final  HttpAsyncClient httpclient = new DefaultHttpAsyncClient();
        httpclient.getParams()
                .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000)
                .setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000)
                .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
                .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);

        httpclient.start();
        try {
            HttpGet[] requests = new HttpGet[]{
                new HttpGet(url),};
            final CountDownLatch latch = new CountDownLatch(requests.length);
            for (final HttpGet request : requests) {
                httpclient.execute(request, new FutureCallback<HttpResponse>() {
                    public void completed(final HttpResponse response) {

                        try {
                            //latch.countDown();
                        
                            //System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                            String content = URLDecoder.decode(IOUtils.toString(response.getEntity().getContent(), "UTF-8"), "UTF-8");
                            System.out.println(Platform.isFxApplicationThread()+":"+key + "->" + content);
                            
                           
                            value.setValue(content);
                           //shuttdown
                            httpclient.shutdown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    public void failed(final Exception ex) {
                        //          latch.countDown();
                        System.out.println(request.getRequestLine() + "->" + ex);
                    }

                    public void cancelled() {
                        //            latch.countDown();
                        System.out.println(request.getRequestLine() + " cancelled");
                    }
                });
            }
            //  latch.await();
           // System.out.println("Shutting down");
        } finally {
            //  httpclient.shutdown();
        }
        //System.out.println("Done");
        return ret;

//
//        String url = ZP_Properties.getInstance().getTranslationURL();
//        String curLang = ZP_Properties.getInstance().getLang();
//        url += "?lang=" + curLang + "&context=zoopraxiV2&keyPhrase=" + key;
//
//        String ret = "noTranslated";
//
//        HttpAsyncClient httpclient = new DefaultHttpAsyncClient();
//        httpclient.getParams()
//                .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000)
//                .setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000)
//                .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
//                .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
//        try {
//            httpclient.start();
//            final HttpGet request = new HttpGet(url);
//            httpclient.execute(request, new FutureCallback<HttpResponse>() {
//                public void completed(final HttpResponse response) {
//                    System.out.println("->" + response.getStatusLine());
//                }
//
//                public void failed(final Exception ex) {
//                    System.out.println("->" + ex);
//                }
//
//                public void cancelled() {
//                    System.out.println(" cancelled");
//                }
//            });
//
//            System.out.println("Shutting down");
//
//            System.out.println("Done");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            httpclient.shutdown();
//        }

    }
}
