package zp_prototype2.render;

import zp_prototype2.ZP_Properties;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * CommonResourceBundle contains collection of ResourceBundles and
 *
 * provides transparent access to their localized objects.
 *
 *
 *
 * @author Serguei Eremenko sergeremenko@yahoo.com
 *
 * @version 1.0
 *
 */
public abstract class CommonResourceBundle extends ResourceBundle {

    /**
     *
     * @return a resource bundle
     *
     */
    public static ResourceBundle getBundle() {

        if (instance == null) {
            instance = new DefResourceBundle();
        }
        return instance;

    }

    /**
     *
     * @return an array of all resource bundle base names
     *
     */
    public String[] getBaseName() {
        return baseName;
    }

    /**
     *
     * Adds a resource bundle to the collection of bundles
     *
     * @param bundle the ResourceBundle to add
     *
     */
    public static void addResourceBundle(ResourceBundle bundle) {

        bundles.add(bundle);

    }

    /**
     *
     * Removes a resource bundle from the collection of bundles
     *
     * @param bundle the ResourceBundle to remove
     *
     */
    public static void removeResourceBundle(ResourceBundle bundle) {

        bundles.remove(bundle);

    }

    /**
     *
     * @return Enumeration of the keys
     *
     */
    public abstract Enumeration getKeys();

    /**
     *
     * Gets an object for the given key from this resource bundle and null if
     *
     * this resource bundle does not contain an object for the given key
     *
     */
    protected abstract Object handleGetObject(String key);

    /**
     *
     * Sets the resource bundle base names as an array
     *
     */
    protected CommonResourceBundle(String[] baseName) {

        this.baseName = baseName;

    }

    /**
     *
     * Sets the resource bundle base names as an array from a string like:
     *
     * test1,test2 etc or test1 test2 etc
     *
     */
    protected CommonResourceBundle(String baseName) {

        buildBaseName(baseName, " ,");

    }

    public CommonResourceBundle() {
        this(new String[0]);
    }

    /**
     *
     * Builds the resource bundle base names as an array from a string like:
     *
     * test1,test2 etc or test1 test2 etc
     *
     */
    protected void buildBaseName(String base, String delim) {

        String s = null;

        try {

            s = System.getProperty(base);

            if (s == null) {
                return;
            }

            StringTokenizer st = new StringTokenizer(s, delim);

            baseName = new String[st.countTokens()];

            int i = 0;

            while (st.hasMoreTokens()) {
                baseName[i++] = st.nextToken().trim();
            }

        } catch (Exception e) {

            throw new RuntimeException("Can not resolve base name: " + s);

        }

    }
    /**
     * Resource bundle base names
     */
    protected String[] baseName;
    /**
     * Default implementation of this abstract class
     */
    private static DefResourceBundle instance;
    /**
     * Collection of resource bundles
     */
    private static ArrayList bundles = new ArrayList();

    ;

   /**

   *  Default implementation

   */

   static class DefResourceBundle extends CommonResourceBundle {

        public DefResourceBundle(String[] baseName) {
            super(baseName);
        }

        public DefResourceBundle(String baseName) {
            super(baseName);
        }

        public DefResourceBundle() {
            this(new String[0]);
        }

        @Override
        public boolean containsKey(String key) {
            //hack
            return true;//return super.containsKey(key);
        }

        public Enumeration getKeys() {

            return new Enumeration() {
                Enumeration enumX = null;
                int i = 0;

                public boolean hasMoreElements() {
                    boolean b = false;
                    while (enumX == null || !(b = enumX.hasMoreElements())) {
                        if (i >= bundles.size()) {
                            enumX = null;
                            return b;
                        }
                        enumX = ((ResourceBundle) bundles.get(i++)).getKeys();
                    }
                    return b;

                }

                public Object nextElement() {

                    if (enumX == null) {
                        throw new NoSuchElementException();
                    }

                    return enumX.nextElement();

                }
            };

        }

        protected Object handleGetObject(String key) {

//            ResourceBundle rb = null;
//            String val = null;
//            for (int i = 0; i < bundles.size(); i++) {
//                rb = (ResourceBundle) bundles.get(i);
//                try {
//                    val = rb.getString(key);
//                } catch (Exception e) {
//                }
//
//                if (val != null) {
//                    break;
//                }
//
//            }

//            String url = ZP_Properties.getInstance().getTranslationURL();
//
//
//            String curLang = ZP_Properties.getInstance().getLang();
//            url += "?lang=" + curLang + "&context=zoopraxiV2&keyPhrase=" + key;
//
//            String ret = "noTranslated";
//            HttpClient httpclient = new DefaultHttpClient();
//
//            try {
//                HttpGet httpget = new HttpGet(url);
//                System.out.println("executing request " + httpget.getURI());
//
//                // Create a response handler
//                ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                ret = URLDecoder.decode(httpclient.execute(httpget, responseHandler), "UTF-8");
//                //ret = httpclient.execute(httpget, responseHandler);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                // When HttpClient instance is no longer needed,
//                // shut down the connection manager to ensure
//                // immediate deallocation of all system resources
//                httpclient.getConnectionManager().shutdown();
//
//            }
//
//            return ret;
            // return val;
            // return "key - "+key;

           // return TranslationManager.translate(key);
return "";
        }
    }
}
