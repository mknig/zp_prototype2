/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author admin
 */
public class ZP_Pane_Controller  {
 
    private double sx = 0;
    private double coordXReal = 0;
    private double sy = 0;
    private double coordYReal = 0;
    
    
    public void setDrag(Node panel){
       
        final Node panelX=panel;
        panel.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                sx = t.getSceneX() - coordXReal;
                sy = t.getSceneY() - coordYReal;
            }
        });

        panel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                coordXReal = t.getSceneX() - sx;
                coordYReal = t.getSceneY() - sy;
                panelX.translateXProperty().set(coordXReal);
                panelX.translateYProperty().set(coordYReal);
            }
        });
    }
}
