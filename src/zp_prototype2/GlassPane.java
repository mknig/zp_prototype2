/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Pedro
 */
public class GlassPane extends Region {

    Cursor previousMouseCursor;
  
    ProgressIndicator progressIndicator;
    
    ProgressIndicator progressBar;
    Text messageBox = new Text("");
    
    
    
    
    
    public GlassPane() {
        setMouseTransparent(true);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);

        progressBar = new ProgressBar();
        progressBar.setVisible(false);

        VBox v1 = new VBox();
        v1.setPadding(new Insets(20));
        v1.getChildren().addAll(messageBox, progressIndicator, progressBar);

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(1024, 800);
        stackPane.getChildren().add(v1);
        getChildren().addAll(stackPane);

    }

    public void setBlockUIAndShowProgress(boolean block) {
        if (block) {
            //  centerProgressBar();
            setMouseTransparent(false);
            progressIndicator.setVisible(true);
            progressBar.setVisible(true);
            previousMouseCursor = getCursor();
            setCursor(Cursor.WAIT);
        } else {
            setMouseTransparent(true);
            setCursor(previousMouseCursor);
            progressIndicator.setVisible(false);
            progressBar.setVisible(false);
            messageBox.setVisible(false);
        }
    }

    private void centerProgressBar() {

        double width = getWidth();
        double height = getHeight();
        double progressBarWidth = progressIndicator.getBoundsInLocal().getWidth();
        double progressBarHeight = progressIndicator.getBoundsInLocal().getHeight();

        progressIndicator.setScaleX(1.5);
        progressIndicator.setScaleY(1.5);
        progressIndicator.setLayoutX((width / 2.0) - (progressBarWidth / 2.0));
        progressIndicator.setLayoutY((height / 2.0) - (progressBarHeight / 2.0));
        
        progressBar.setScaleX(2.5);
        progressBar.setScaleY(2.5);
        progressBar.setLayoutX((width / 2.0) - (progressBar.getBoundsInLocal().getWidth() / 2.0));
        progressBar.setLayoutY((height / 2.0) - (progressBar.getBoundsInLocal().getHeight() / 2.0));
        
        //messageBox.setLayoutX((width / 2.0) - (progressBarWidth / 2.0));
        messageBox.setLayoutY((height / 2.0) - (progressBarHeight));
        messageBox.setTextAlignment(TextAlignment.CENTER);
        //messageBox.setStyle("-fx-text-alignment: center");
    }

    //++++++++++++++
    //stupidos
    //stupidos
    //stupidos
    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public void setProgressIndicator(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    public Text getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(Text messageBox) {
        this.messageBox = messageBox;
    }

    public Cursor getPreviousMouseCursor() {
        return previousMouseCursor;
    }

    public void setPreviousMouseCursor(Cursor previousMouseCursor) {
        this.previousMouseCursor = previousMouseCursor;
    }

    public ProgressIndicator getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressIndicator progressBar) {
        this.progressBar = progressBar;
    }

    
}
