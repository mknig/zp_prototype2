 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import zp_prototype2.ZP_Properties;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebView;
import javax.imageio.ImageIO;
import zp_prototype2.IWallpaper;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.image.IImageSelection;
import zp_prototype2.image.IWallpaperImage;
import zp_prototype2.image.WallpaperImage;
import javafx.scene.text.Text;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import zp_prototype2.WallpaperDimensions;

/**
 *
 * @author Michael
 */
public class BuildZoopraxiV3 {

    //private WebView browser;
    public void buildZPAndSave(Task task, IWallpaper zpWallpaperX) {

        final IWallpaper zpWallpaper = zpWallpaperX;
        int sliceWidth = WallpaperDimensions.sliceWidth;
        double stepsHSlice = WallpaperDimensions.stepsHSlice;
        final int sizeLoadXXL = WallpaperDimensions.sizeLoadXXL;//TODO:


//       Task task = new Task<Void>() {
//             @Override 
//             public Void call() {


        ZPScene zpScene = (ZPScene) ZP_Prototype2.getInstance().getScene();
        TextField messageBox = zpScene.getDialogPaneController().getMessageBox();
        ProgressIndicator progressBar = zpScene.getDialogPaneController().getProgressBar();


        int zpID = 0;
        try {


            //+++++++++
            // let´s go...
            // ...init what i need....
            zpID = ZP_Properties.getZpID();
            String __URL__SERVER__ZOOPRAXEACTION__ = ZP_Properties.getInstance().getZoopraxiActionURL();

            //+++++++++++++
            // get all Images as Selection...
            List<IImageSelection> images = zpWallpaper.getImages();

            //+++++++++++++
            // init stuff for slice
            double scaleFactor = WallpaperDimensions.getScaleFactor();
            BufferedImage bimgSlice = null;
            File savefile = null;
            double ptWidth = zpWallpaper.getWidth() * scaleFactor;
            double ptHeight = zpWallpaper.getHeight() * scaleFactor;
            int part = 0;
            int minSliceWidth = 100;
            //int colorTyp = BufferedImage.TYPE_INT_ARGB;//nur als PNG!!!
            int colorTyp = BufferedImage.TYPE_INT_RGB;
            double ptWidthRendered = 0;
            double ptHeightRendered = 0;

            //loop starts...
            // double posXSnapShot;

            //############################
            // 0.) build Slices
            // 
            // call as long as ptWidth is not reached
            int indexSlice = 0;
            while (ptWidthRendered < zpWallpaper.getWidth() * scaleFactor) {

                System.out.println("Slice: " + indexSlice + " rendered:" + ptWidthRendered + "/" + zpWallpaper.getWidth() * scaleFactor);
                messageBox.setText("buildSliceImage: part->" + part++);

                ZP_Properties.setPt2render(zpWallpaper.getWidth() * scaleFactor);
                ZP_Properties.setPt2renderProcessed(ptWidthRendered);

                //##########################
                //define SliceWidth
                if (ptWidthRendered + sliceWidth <= zpWallpaper.getWidth() * scaleFactor) {
                    ptWidth = sliceWidth;
                } else {
                    ptWidth = zpWallpaper.getWidth() * scaleFactor - ptWidthRendered;
                }
                ptHeightRendered = zpWallpaper.getHeight() * scaleFactor;


                //##########################
                // Malloc sliceImage
                // and get graphic to paint 

                // cause border..bimgSlice = new BufferedImage((int) Math.round(ptWidth), (int)Math.round(ptHeight), colorTyp);
                int bimgSlice_width = (int) ptWidth;
                if (bimgSlice_width == 0) {
                    break;
                }
                bimgSlice = new BufferedImage(bimgSlice_width, (int) ptHeight, colorTyp);

                Graphics2D g2dSlice = (Graphics2D) bimgSlice.getGraphics();
                g2dSlice.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2dSlice.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                //##########################
                // init sliceBackground
                Color frameColor = Color.WHITE;
                //Color frameColor = Color.RED;
                java.awt.Rectangle bg = new java.awt.Rectangle(0, 0, (int) ptWidth, (int) ptHeight);
                g2dSlice.setColor(frameColor);
                g2dSlice.fill(bg);

//                //##########################
//                //texture
//                BufferedImage img = null;
//                try {
//                    // img = ImageIO.read(getClass().getResource("/zp_prototype2/resource/repeatable_apple_pinstripe_texture___free_by_itsaustinjordan-d58fpow.png"));
//                    img = ImageIO.read(getClass().getResource("/zp_prototype2/resource/textures/paper_@2X.png"));
//
//
//                } catch (IOException e) {
//                    System.out.println(e);
//                }
//
//                //Texturfarbe erstellen, Anchor-Rechteck hat genau die BildmaÃŸe
//                TexturePaint tp = new TexturePaint(img, new Rectangle2D.Double(0, 0,
//                        img.getWidth(),
//                        img.getHeight()));
//
//                //Texturfarbe setzen
//                g2dSlice.setPaint(tp);
//
//                //Rechteck fÃ¼llen
//                g2dSlice.fill(new Rectangle2D.Double(0, 0, (int) ptWidth, (int) ptHeight));


                // END init sliceBackground
                //##########################

                messageBox.setText("DONE: Malloc BufferedImg / Part: " + indexSlice);

                //##########################
                // select Images 2Render..
                // define img2Render
                // * ignore images not in SliceRange
                // * ignore images already rendered
                // * ignore images already loaded XXL
                // * cleanXXL cache -> ZP_Properties.getReadyImagesXXL()
                //
                ArrayList<IImageSelection> img2Render = new ArrayList<IImageSelection>();
                int idxImageInRange = 0;
                for (IImageSelection imageSelection : zpWallpaper.getImages()) {
                    IWallpaperImage curImage = imageSelection.getWallpaperImage();
                    Point2D startRenderPoint = imageSelection.getPosition();
                    double image_x0 = startRenderPoint.getX() * scaleFactor;
                    double imageWidth = curImage.getImageView().getBoundsInParent().getWidth() * scaleFactor;
                    double image_x1 = startRenderPoint.getX() * scaleFactor + imageWidth;
                    double range_x1 = ptWidthRendered + ptWidth;
                    double range_x0 = ptWidthRendered;
                    if (startRenderPoint.getY() * scaleFactor < ptHeight
                            && image_x0 <= range_x1
                            //     && startRenderPoint.getX() * scaleFactor+curImage.getImageView().getFitWidth()*scaleFactor >= ptWidthRendered
                            //      && startRenderPoint.getX() * scaleFactor+curImage.getWidth()*scaleFactor >= ptWidthRendered
                            && image_x1 >= range_x0) {

                        //System.out.print("<-in");
                        //System.out.println("selectImages4Slice(" + indexSlice + ") image x0/x1: " + image_x0 + "/" + image_x1 + " - range x0/x1:" + range_x0 + "/" + range_x1 + " File:" + curImage.getFileURI());
                        img2Render.add(imageSelection);
                    } else {
                        // messageBox.setText("add NOT IN RANGE img {curImage.image.url}");
                        //+++++++++++++++++++++++
                        //remove XXL-CACHE!!!
                        if (ZP_Properties.getReadyImagesXXL().containsKey(imageSelection.getXXLKey())) {
                            ZP_Properties.getReadyImagesXXL().remove(imageSelection.getXXLKey());
                            //System.out.println("remove XXL from cache");
                        }
                    }
                    idxImageInRange++;
                }



                //+++++++++++++++++++++
                // 3.) render...  
                // * loadXXL or take CacheXXL
                // * initSnapshot (runLater)...save image in -->ZP_Properties.getReadyImages()
                // * wait till Shapshot ready
                // * render Images on Slice
                // * clean cache -->ZP_Properties.getReadyImages()
                String zpPath = ZP_Properties.getInstance().getZoopraxiLocalPath();
                int imageIndex = 0;


                for (IImageSelection imageSelection : img2Render) {


                    final WallpaperImage wpImage = (WallpaperImage) imageSelection.getWallpaperImage();

//!!!!!!!!!!!!!!!!
// posX/Y...
                    double posYSnapShot = 0;

                    double posXSnapShot = ptWidthRendered - imageSelection.getPosition().getX() * scaleFactor;
                    double heightSnapShot_HSlice = imageSelection.getHeight() * scaleFactor / stepsHSlice;

//                    //rounded 4 closeGaps DOES NOT WORK!!! Cause GAP!!!!
//                    double posXSnapShot = Math.round(ptWidthRendered - imageSelection.getPosition().getX() * scaleFactor);
//                    double heightSnapShot_HSlice = Math.round(imageSelection.getHeight() * scaleFactor / stepsHSlice);

                    //##################
                    //iterate HSlices...
                    for (int i = 0; i < stepsHSlice; i++) {

                        posYSnapShot = heightSnapShot_HSlice * i;


                        //+++++++++++++++++++++++++++++
                        //
                        // get SnapShot for Slice
                        //
                  
                        
                        //++++++++++++++++++++++++++
                        //get wpImageXXL...in Cache?
                        WallpaperImage wpImage_XXL = null;
                        //System.out.println("size ZP_Properties.getReadyImagesXXL().size--> " + ZP_Properties.getReadyImagesXXL().size());
                        if (ZP_Properties.getReadyImagesXXL().containsKey(imageSelection.getXXLKey())) {
                            //  System.out.println("XXL-Node in Cache>" + imageSelection.getXXLKey());
                            wpImage_XXL = (WallpaperImage) ZP_Properties.getReadyImagesXXL().get(imageSelection.getXXLKey());

                        } else {
                            // System.out.println("XXL-Node loaded>" + imageSelection.getXXLKey());
                            wpImage_XXL = BuildZP_XXLLoader.getInstance().loadXXL(wpImage, WallpaperDimensions.sizeThumb, sizeLoadXXL, scaleFactor);
                          
                            //disable? -> low performace<-->mem
                            ZP_Properties.getReadyImagesXXL().put(imageSelection.getXXLKey(), wpImage_XXL);

                        }

                        //+++++++++++++++++++++++++++++
                        // take SnapShot
                        // BuildZoopraxiController.lock.acquire();
                        while (ZP_Properties.runLater) {
                            System.out.println("Runlater busy...");
                            Thread.sleep(10);
                        }
                        ZP_Properties.runLater = true;
                        // synchronized (this) {
                        Platform.runLater(new BuildZP_RunLaterV2(imageSelection, wpImage, wpImage_XXL, posXSnapShot, posYSnapShot, ptWidth, heightSnapShot_HSlice));//end Runnable RunLater...
                        //  }


                        //+++++++++++++++
                        //wait for snapShot...
                        int iteration_wait = 0;
                        while (!ZP_Properties.getReadyImages().containsKey(imageSelection.getSnapShotKey(posXSnapShot, posYSnapShot, ptWidth, heightSnapShot_HSlice))) {
//                            if (iteration_wait == 6000) {
//                                throw new RuntimeException("no SnapShot return!!!");
//                            }
                            Thread.sleep(5);//Performance Imapct!!!
                            iteration_wait++;
                            // System.out.println("XXL-Node> wait for " + imageSelection.getSnapShotKey(posXSnapShot, posYSnapShot, ptWidth, heightSnapShot_HSlice));
                        }


                        //+++++++++++++++++++++++++++++
                        //
                        // draw SnapShot on Slice
                        //
                        BufferedImage bfImage = (BufferedImage) ZP_Properties.getReadyImages().get(imageSelection.getSnapShotKey(posXSnapShot, posYSnapShot, ptWidth, heightSnapShot_HSlice));

                        double startX = 0;
                        //double startY =Math.round(posYSnapShot + imageSelection.getPosition().getY() * scaleFactor);
                        double startY = posYSnapShot + imageSelection.getPosition().getY() * scaleFactor;
                        //double startY =posYSnapShot + Math.round(imageSelection.getPosition().getY()) * scaleFactor;    
                        //double startY =posYSnapShot + imageSelection.getWallpaperImage().getPosition().getY() * scaleFactor;    

                        g2dSlice.translate(startX, startY);
                        AffineTransform transformer = new AffineTransform();
                        g2dSlice.drawImage(bfImage, transformer, null);
                        g2dSlice.translate(-startX, -startY);

                        //remove rendered readyImage.snapshot...
                        ZP_Properties.getReadyImages().remove(imageSelection.getSnapShotKey(posXSnapShot, posYSnapShot, ptWidth, heightSnapShot_HSlice));

                        //remove fully rendered readyImageXXL..
                       // Collection<WallpaperImage> readyImageXXLs = ZP_Properties.getReadyImagesXXL().values();
                     //   List readyImagesXXL2remove = new ArrayList();
                       // for (WallpaperImage imageXXL : readyImageXXLs) {
                            double img_rest2render = imageSelection.getWidth() * scaleFactor - (ptWidthRendered - imageSelection.getPosition().getX() * scaleFactor);
                            //check width...
                            if (img_rest2render < bimgSlice.getWidth()) {
                                //check height
                                double imgHeight = imageSelection.getHeight() * scaleFactor;
                                    
                                if (imgHeight <= heightSnapShot_HSlice*(i+1)) {
                                    System.out.println("remove Image: key:" + imageSelection.getXXLKey() +"width:"+ img_rest2render + " <" + bimgSlice.getWidth() + "height: " + imgHeight +"<= hSliceYpos:"+ heightSnapShot_HSlice*(i+1));
                                    //readyImagesXXL2remove.add(imageSelection.getXXLKey());
                                    ZP_Properties.getReadyImagesXXL().remove(imageSelection.getXXLKey());
                                }
                            }
                       // }
//                        for (Object removeXXL : readyImagesXXL2remove) {
//                            ZP_Properties.getReadyImagesXXL().remove(removeXXL);
//                        }

                        //progess....
                        // WallpaperImage wpImage = (WallpaperImage) imageSelection.getWallpaperImage();
                        String msg = "draw Image on slice: " + indexSlice + " / " + wpImage.getFileURI();
                        // messageBox.setText(msg);
                        //System.out.println(msg);

                    }
                    imageIndex++;
                }
                g2dSlice.dispose();


                //+++++++++++++++++++++++
                // 4.) save slice (bimgSlice)
                // * set quality
                //
                //
                //File fileZP = new File(zpPath + "image_" + imageIndex + "_" + "zpSlice" +indexSlice + ".png");
                //ImageIO.write(bimgSlice, "png", fileZP);

                //brauch BF mit colorty  int colorTyp = BufferedImage.TYPE_INT_RGB;
                //File fileZP = new File(zpPath + "image_" + imageIndex + "_" + "zpSlice" + indexSlice + ".jpeg");
                File fileZP = new File(zpPath + "_" + "zpSlice" + indexSlice + ".jpeg");

                //ImageIO.write(bimgSlice, "jpeg", fileZP);
                //ImageIO.write(bimgSlice, "png", fileZP);


                //###################
                //Adjustable quality...
                //
                ImageWriter iw = ImageIO.getImageWritersByFormatName("jpg").next();
                //ImageWriter iw = ImageIO.getImageWritersByFormatName("png").next();
                ImageOutputStream ios = ImageIO.createImageOutputStream(fileZP);
                iw.setOutput(ios);

                // Set the compression quality to 0.9f.
                ImageWriteParam iwParam = iw.getDefaultWriteParam();
                iwParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

                float jpg_quality = 0.8f;
                iwParam.setCompressionQuality(jpg_quality);

                // Write image
                iw.write(null, new IIOImage(bimgSlice, null, null), iwParam);
                iw.dispose();
                ios.close();
                //
                //Adjustable quality...
                //###################


                String msg = "save Slice local: " + fileZP.getName();
                //   messageBox.setText(msg);
                System.out.println(msg);

                //end write file
                //+++++++++++++++++++++++

                //++++++++++
                // progress ptWidthRendered
                //stop scliceing
                //ptWidthRendered = 100000000000000d;
                ptWidthRendered += bimgSlice.getWidth();
                ptWidth = (zpWallpaper.getWidth() * scaleFactor) - ptWidthRendered;


                //Main.wb.renderZP(img2Render, img,ptWidthRendered,0);


                //XXXXXXXXX
                //upload ZP
                //upload ZP
                //upload ZP
                //upload ZP

                final int fi_indexSlice = indexSlice;
                final int fi_zpID = zpID;
                final File fi_File = fileZP;



//                Task task_UploadSlice = new Task<Void>() {
//                    @Override
//                    public Void call() {

                String message = "uploading your ZP....";
                String __URL__SERVER__UPLOAD__SERVLET__ = ZP_Properties.getInstance().getZoopraxiUploadServletURL();
                //savefile.getName()
                // ZP_ID.8944.Part.1.FileName.jpeg
                String fileName = "ZP_ID." + fi_zpID + "Part." + fi_indexSlice + ".FileName" + ".jpeg";//.png";
                String uploadURL = __URL__SERVER__UPLOAD__SERVLET__ + "?file=" + fileName + "&ZP_IMG_Typ=DETAIL_ZP&ZPID=" + fi_zpID;

                // messageBox.setText("upload: " + fileName);

                ZP_HttpRequest_Apache.uploadFile(uploadURL, fi_File, "DETAIL_ZP", fi_zpID);

//                        return null;
//                    }
//                };//end Task
//                new Thread(task_UploadSlice).start();


                fileZP = null;
                bimgSlice = null;
                //  System.gc();

                System.out.println("Mem after Slice " + indexSlice + " ->" + MemoryManager.getMemStat());
                indexSlice++;


            }

            //++++++++++
            //done
            System.out.println("process build heighResolution completed");

            //++++++++++++++++++
            //
            // init renderZP on Server...
            //
            String initBuildServerURL = __URL__SERVER__ZOOPRAXEACTION__ + "?command=render_zp&zp_ID=" + zpID;
            ZP_HttpRequest_Apache.requestZPID(initBuildServerURL);


//                    //++++++++++++++++++++++
//                    //
//                    // checkout...
//                    //
//                    String __URL__ORDER__ZP___ = "http://ex10.tapetenagentur.de/mb_ta/shop/shopcart.do?command=init4newZoopraxi&iFrame=true&viewType=full&productId=" + zpID;
//                    browser = new WebView();
//                    WebEngine webEngine = browser.getEngine();
//                    webEngine.load(__URL__ORDER__ZP___);
//                    ZP_Prototype2.getInstance().getContentPanel().setCenter(browser);
//
//
//                    zpScene.setBlockUIAndShowProgress(false);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
//             return null;
//             }
//           
//        };//end Task
//       new Thread(task).start();
    }

    private WritableImage fixBadJPEG_2(Image img) {
        int width = Double.valueOf(img.getWidth()).intValue();
        int hight = Double.valueOf(img.getHeight()).intValue();
        int[] ary = new int[width * hight];
        img.getPixelReader().getPixels(0, 0, width, hight,
                PixelFormat.getIntArgbInstance(), ary, 0, width);
        for (int i = ary.length - 1; i >= 0; i--) {
            int y = ary[i] >> 16 & 0xFF; // Y
            int b = (ary[i] >> 8 & 0xFF) - 128; // Pb
            int r = (ary[i] & 0xFF) - 128; // Pr

            int g = (y << 8) + -88 * b + -183 * r >> 8; //
            b = (y << 8) + 454 * b >> 8;
            r = (y << 8) + 359 * r >> 8;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }

            ary[i] = 0xFF000000 | (r << 8 | g) << 8 | b;
        }
        WritableImage imgNew = new WritableImage(width, hight);
        img.getPixelReader().getPixelFormat();
        imgNew.getPixelWriter().setPixels(0, 0, width, hight,
                PixelFormat.getIntArgbInstance(),
                ary, 0, width);
        return imgNew;
    }

    //++++++++++++
    // ????
    private static BufferedImage fixBadJPEG(BufferedImage img) {
        int[] ary = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), ary, 0, img.getWidth());
        for (int i = ary.length - 1; i >= 0; i--) {
            int y = ary[i] >> 16 & 0xFF; // Y
            int b = (ary[i] >> 8 & 0xFF) - 128; // Pb
            int r = (ary[i] & 0xFF) - 128; // Pr

            int g = (y << 8) + -88 * b + -183 * r >> 8; //
            b = (y << 8) + 454 * b >> 8;
            r = (y << 8) + 359 * r >> 8;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }

            ary[i] = 0xFF000000 | (r << 8 | g) << 8 | b;
        }
        img.setRGB(0, 0, img.getWidth(), img.getHeight(), ary, 0, img.getWidth());
        return img;
    }
}
