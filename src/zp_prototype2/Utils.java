/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2;

import java.awt.image.BufferedImage;
import java.io.File;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ColorBuilder;

/**
 *
 * @author Pedro
 */
public class Utils {

    public static double getLeastCommonDenominator(int a, int b) {

           if(a<=0 || b<=0) {
          throw new IllegalArgumentException("Cannot compute the least "+
                                             "common multiple of two "+
                                             "numbers if one, at least,"+
                                             "is negative.");
      }
      
      int x1, x2,r=0;
      if (a<b) {
          x1=b;
          x2= a;
      } else {
          x1=a;
          x2=b;
      }
      
     // System.out.println("euklid: x1:" +x1 +"/ x2:" + x2 +"/r:" + r);
      r=x1%x2;
      while(r!=0){
          x1=x2;
          x2=r;
          r=x1%x2;
          System.out.println("euklid: x1:" +x1 +"/ x2:" + x2 +"/r:" + r);
      }
      System.out.println("euklid: x1:" +x1 +"/ x2:" + x2 +"/r:" + r);
      return x2;
        
        
    }

    public static double getCollisionArea(Bounds firstBounds, Bounds secondBounds) {
        double horizontalArea = 0;
        double verticalArea = 0;
        // X
        if (firstBounds.getMinX() < secondBounds.getMinX() && secondBounds.getMinX() < firstBounds.getMaxX()) {
            if (firstBounds.getMinX() < secondBounds.getMaxX() && secondBounds.getMaxX() < firstBounds.getMaxX()) // firstBounds includes secondBounds
            {
                horizontalArea = secondBounds.getMaxX() - secondBounds.getMinX();
            } else {
                horizontalArea = firstBounds.getMaxX() - secondBounds.getMinX();
            }
        } else if (firstBounds.getMinX() < secondBounds.getMaxX() && secondBounds.getMaxX() < firstBounds.getMaxX()) {
            horizontalArea = secondBounds.getMaxX() - firstBounds.getMinX();
        } else if (firstBounds.getMinX() >= secondBounds.getMinX() && firstBounds.getMaxX() <= secondBounds.getMaxX()) // secondBounds includes firstBounds or secondBounds equals firstBounds
        {
            horizontalArea = firstBounds.getMaxX() - firstBounds.getMinX();
        }
        // Y
        if (firstBounds.getMinY() < secondBounds.getMinY() && secondBounds.getMinY() < firstBounds.getMaxY()) {
            if (firstBounds.getMinY() < secondBounds.getMaxY() && secondBounds.getMaxY() < firstBounds.getMaxY()) // firstBounds includes secondBounds
            {
                verticalArea = secondBounds.getMaxY() - secondBounds.getMinY();
            } else {
                verticalArea = firstBounds.getMaxY() - secondBounds.getMinY();
            }
        } else if (firstBounds.getMinY() < secondBounds.getMaxY() && secondBounds.getMaxY() < firstBounds.getMaxY()) {
            verticalArea = secondBounds.getMaxY() - firstBounds.getMinY();
        } else if (firstBounds.getMinY() >= secondBounds.getMinY() && firstBounds.getMaxY() <= secondBounds.getMaxY()) // secondBounds includes firstBounds or secondBounds equals firstBounds
        {
            verticalArea = firstBounds.getMaxY() - firstBounds.getMinY();
        }

        return horizontalArea * verticalArea;
    }

    public static java.awt.Color toAWTColor(javafx.scene.paint.Color fxColor) {
        return new java.awt.Color((float) fxColor.getRed(), (float) fxColor.getGreen(), (float) fxColor.getBlue(), (float) fxColor.getOpacity());
    }

    public static javafx.scene.paint.Color fromAWTColor(java.awt.Color awtColor) {
        return ColorBuilder.create()
                .red(awtColor.getRed() / 255.0)
                .green(awtColor.getGreen() / 255.0)
                .blue(awtColor.getBlue() / 255.0).build();
    }

    // There is a problem with this implementation: transparent pixels on the BufferedImage aren't converted to transparent pixels on the fxImage.
    public static javafx.scene.image.Image convertToFxImage(java.awt.image.BufferedImage awtImage) {
        if (Image.impl_isExternalFormatSupported(BufferedImage.class)) {
            return javafx.scene.image.Image.impl_fromExternalImage(awtImage);
        } else {
            return null;
        }
    }

    public static java.awt.image.BufferedImage convertToAwtImage(javafx.scene.image.Image fxImage) {
        if (Image.impl_isExternalFormatSupported(BufferedImage.class)) {
            java.awt.image.BufferedImage awtImage = new BufferedImage((int) fxImage.getWidth(), (int) fxImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            return (BufferedImage) fxImage.impl_toExternalImage(awtImage);
        } else {
            return null;
        }
    }

    public static void logFile(File imageFile) {
        try {
            System.out.println("imageFile.getAbsolutePath()" + imageFile.getAbsolutePath());
            System.out.println("imageFile.getAbsoluteFile()" + imageFile.getAbsoluteFile());
            System.out.println("imageFile.getCanonicalPath()" + imageFile.getCanonicalPath());
            System.out.println("imageFile.getCanonicalFile()" + imageFile.getCanonicalFile());
            System.out.println("iimageFile.getName()" + imageFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
