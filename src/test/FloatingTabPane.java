import javafx.application.Application;
import javafx.beans.value.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class FloatingTabPane extends Application {
  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage stage) {
    // create some tabs.
    final TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("Sites");     tab1.setContent(createPane("lightgreen"));
    Tab tab2 = new Tab("Favorites"); tab2.setContent(createPane("lightsteelblue"));
    Tab tab3 = new Tab("Loves");     tab3.setContent(createPane("pink"));
    tabPane.getTabs().addAll(tab1, tab2, tab3);
    
    // create a button to toggle floating.
    final RadioButton floatControl = new RadioButton("Float");
    floatControl.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override public void changed(ObservableValue<? extends Boolean> prop, Boolean wasSelected, Boolean isSelected) {
        if (isSelected) {
          tabPane.getStyleClass().add(TabPane.STYLE_CLASS_FLOATING);
        } else {
          tabPane.getStyleClass().remove(TabPane.STYLE_CLASS_FLOATING);
        }
      }
    });

    // layout the stage.
    VBox layout = new VBox(10);
    layout.getChildren().addAll(floatControl, tabPane);
    VBox.setVgrow(tabPane, Priority.ALWAYS);
    layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
    Scene scene = new Scene(layout);
    stage.setScene(scene);
    stage.show();
  }
  
  // create a pane of a given color to hold tab content.
  private Pane createPane(String color) {
    Pane pane = new Pane();
    pane.setPrefSize(400, 200);
    pane.setStyle("-fx-background-color: " + color);
    return pane;
  }
}