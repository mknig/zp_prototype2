package zp_prototype2;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;
import zp_prototype2.resource.ResourceFactory;

public class CursorFactory {
   
    //test....
    
    static ImageCursor PanCursor,
                       PanningCursor;

    static ResourceFactory Resource = zp_prototype2.resource.ResourceFactory.instance();


    public static ImageCursor getPanningCursor()
    {
        if (PanningCursor == null)
            PanningCursor = createCursor(Resource.getGrabCursor(), 8, 8);
        return PanningCursor;
    }

    public static ImageCursor getPanCursor()
    {
        if (PanCursor == null)
            PanCursor = createCursor(Resource.getHandCursor(), 8, 8);
        return PanCursor;
    }

    public static ImageCursor createCursor(URL cursorImage, double hotspotX, double hotspotY)
    {
        Image image = new Image(cursorImage.toString());
        return new ImageCursor(image, hotspotX, hotspotY);
    }


}
