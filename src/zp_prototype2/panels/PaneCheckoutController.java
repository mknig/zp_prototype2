/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class PaneCheckoutController implements Initializable {

    
      @FXML private WebView browser;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //WebView browser = new WebView();
        //browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        
        String zpUrl="http://XXX.tapetenagentur.de/mb_ta/shop/shop.do";
        String zpQuery="?command=go&category=79021&zp_id=9414";
        String zpUrlALL=zpUrl+zpQuery;
        System.out.println("connect to: " +zpUrlALL );
        webEngine.load(zpUrlALL);
      
    }    
}
