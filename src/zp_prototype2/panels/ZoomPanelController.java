/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.panels;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import zp_prototype2.IWorkspaceListener;
import zp_prototype2.Workspace;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;

/**
 *
 * @author ines
 */
public class ZoomPanelController implements Initializable {

    Workspace workspace;
    double zoomStep;
    double numberOfZoomSteps;
    boolean isUpdatingSlider;
    @FXML
    Label lbl_caption_zoom;
    @FXML
    Button minusButton;
    @FXML
    Button plusButton;
    @FXML
    Slider slider;
    @FXML
    public CheckBox checkBox_panMode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        ZP_Prototype2.getInstance().setZoomController(this);

        //workspace = ZP_Prototype2.getInstance().getWorkspace();
        setWorkspace(ZP_Prototype2.getInstance().getWorkspace());

        isUpdatingSlider = false;
        numberOfZoomSteps = 3;//2;//1;


        //bind workspace.drag&drop/panMode.....
        ZP_Prototype2.getInstance().getWorkspace().dragModeActiveProperty.bind(ZP_Prototype2.getInstance().zoomController.checkBox_panMode.selectedProperty());



        //slider.setSnapToTicks(true);
        slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
                if (!newValue && !isUpdatingSlider) {
                    workspace.zoomTo(slider.getValue());
                }
            }
        });
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
                if (!slider.isValueChanging() && !isUpdatingSlider) {
                    workspace.zoomTo(slider.getValue());
                }
            }
        });

        //+++++++++++++++++++
        //i18N
        ZP_Properties.i18NMap.put(lbl_caption_zoom.getText(), lbl_caption_zoom.textProperty());
        ZP_Properties.i18NMap.put(checkBox_panMode.getText(), checkBox_panMode.textProperty());

    }

    @FXML
    private void handleZoomIn(ActionEvent event) {
        workspace.zoom(zoomStep);
    }

    @FXML
    private void handleZoomOut(ActionEvent event) {
        workspace.zoom(-zoomStep);
        //scrollbar...
        workspace.me.setVvalue(0);
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
        workspace.addListener(new IWorkspaceListener() {
            @Override
            public void zoomChanged() {
                workspaceZoomChanged();
            }

            @Override
            public void wallpaperSizeChanged() {
                updateZoomStep();
            }

            @Override
            public void minimumZoomChanged() {
                workspaceMinimumZoomChanged();
            }
        });
        updateSliderMaxAndMin();
    }

    private void workspaceMinimumZoomChanged() {
//        isUpdatingSlider = true;
        updateZoomStep();
        updateSliderMaxAndMin();
        updateSliderValue();
//        isUpdatingSlider = false;
    }

    private void workspaceZoomChanged() {
        if (!workspace.isUpdatingZoom()) {
            updateUI();
            updateSliderValue();
        }
    }

    private void updateSliderMaxAndMin() {
        isUpdatingSlider = true;
        slider.setMin(workspace.getMinimumZoom());
        slider.setMax(workspace.getMaximumZoom());
        isUpdatingSlider = false;
    }

    private void updateSliderValue() {
        isUpdatingSlider = true;
        slider.setValue(workspace.getCurrentZoom());
        isUpdatingSlider = false;
    }

    protected void updateZoomStep() {
        zoomStep = (workspace.getMaximumZoom() - workspace.getMinimumZoom()) / numberOfZoomSteps;
        //zoomStep=10;
        isUpdatingSlider = true;
        slider.setMajorTickUnit(zoomStep);
        isUpdatingSlider = false;
    }

    private void updateUI() {
        minusButton.setDisable(workspace.isMinimumZoom());
        plusButton.setDisable(workspace.isMaximumZoom());
    }
}
