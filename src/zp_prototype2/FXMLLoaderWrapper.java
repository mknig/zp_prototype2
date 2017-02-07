/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.Pane;
import zp_prototype2.render.CommonResourceBundle;

/**
 *
 * @author Pedro
 */
public class FXMLLoaderWrapper {

    private FXMLLoader loader;
    private ResourceBundle resourceBundle;
 
  //  private final static Locale Locale = new Locale("en", "GB");
  //  private final static ResourceBundle DEFAULT_BUNDLE = null;//= ResourceBundle_ZP.getBundle("zp_prototype2/resource/text", Locale);
    private final static Locale Locale = new Locale("en", "GB");
    public final static ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle("zp_prototype2/resource/text", Locale);

    // private final static ResourceBundle DEFAULT_BUNDLE = Common.getBundle("zp_prototype2/resource/text", Locale);
    public FXMLLoaderWrapper() {
        this(null);
    }

    public FXMLLoaderWrapper(ResourceBundle bundle) {
        if (bundle == null)
            resourceBundle = CommonResourceBundle.getBundle();
        else
            resourceBundle = bundle;
    }

    private void createLoader(ResourceBundle bundle) {
        loader = new FXMLLoader();
        loader.setResources(bundle);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
    }

    public Pane loadFXML(String FXMLResource) throws Exception {
        createLoader(resourceBundle);
        URL location = getClass().getResource(FXMLResource);
        loader.setLocation(location);
        InputStream inputStream = null;
        Pane panel;
        try {
            inputStream = location.openStream();
            panel = (Pane) loader.load(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return panel;
    }

    public Object getController() {
        return loader.getController();
    }
}
