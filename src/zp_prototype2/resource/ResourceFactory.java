/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.resource;

import java.net.URL;

/**
 *
 * @author Pedro
 */
public class ResourceFactory {
    private static ResourceFactory Resource = new ResourceFactory();

    private ResourceFactory()
    {
    }

    public static ResourceFactory instance()
    {
        return Resource;
    }
    
    public URL getHandCursor()
    {
        return getClass().getResource("handCursor.png");
    }

    public URL getGrabCursor()
    {
        return getClass().getResource("grabCursor.png");
    }
}
