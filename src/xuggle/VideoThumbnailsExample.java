/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xuggle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import zp_prototype2.ZPScene;
import zp_prototype2.ZP_Properties;
import zp_prototype2.ZP_Prototype2;
import zp_prototype2.panels.Dialog_Workspace_ShowImage;

public class VideoThumbnailsExample {

    public static final double SECONDS_BETWEEN_FRAMES =0.1;//0.05;// 0.15;//0.25;//0.1;//0.041;
  
    // private static final String inputFilename = "c:/Java_is_Everywhere.mp4";
    private static final String inputFilename = "x:/tmp/xuggle/MVI_0764.MOV";
    // private static final String outputFilePrefix = "x:/tmp/xuggle/mysnapshot";
    // The video stream index, used to ensure we display frames from one and
    // only one video stream from the media container.
    private static int mVideoStreamIndex = -1;
    // Time of last frame write
    private static long mLastPtsWrite = Global.NO_PTS;
    public static final long MICRO_SECONDS_BETWEEN_FRAMES =
            (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);

    public static void main(String[] args) {

        IMediaReader mediaReader = ToolFactory.makeReader(inputFilename);

        // stipulate that we want BufferedImages created in BGR 24bit color space
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

        mediaReader.addListener(new ImageSnapListener());

        // read out the contents of the media file and
        // dispatch events to the attached listener
        while (mediaReader.readPacket() == null) ;

    }

    public static class ImageSnapListener extends MediaListenerAdapter {

        public void onVideoPicture(IVideoPictureEvent event) {

            if (event.getStreamIndex() != mVideoStreamIndex) {
                // if the selected video stream id is not yet set, go ahead an
                // select this lucky video stream
                if (mVideoStreamIndex == -1) {
                    mVideoStreamIndex = event.getStreamIndex();
                } // no need to show frames from this video stream
                else {
                    return;
                }
            }

            // if uninitialized, back date mLastPtsWrite to get the very first frame
            if (mLastPtsWrite == Global.NO_PTS) {
                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
            }

            // if it's time to write the next frame
            if (event.getTimeStamp() - mLastPtsWrite
                    >= MICRO_SECONDS_BETWEEN_FRAMES) {

                //String outputFilename = dumpImageToFile(event.getImage());
                String outputFilename = dumpImageToFile(event.getImage(), ZP_Properties.getMovieFolder2Capture());

                // indicate file written
                double seconds = ((double) event.getTimeStamp())
                        / Global.DEFAULT_PTS_PER_SECOND;
                System.out.printf(
                        "at elapsed time of %6.3f seconds wrote: %s\n",
                        seconds, outputFilename);

                // update last write time
                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
            }

        }

        private String dumpImageToFile(BufferedImage image, String outputFolder) {
            try {

                //display...need runnable...
                // Runnable showImage=  new Dialog_Workspace_ShowImage(image);
                // Platform.runLater(showImage);
                //showImage.run();

                //write...
                final String outputFilename = outputFolder
                        + System.currentTimeMillis() + ".jpg";
                ImageIO.write(image, "jpg", new File(outputFilename));

                //runLater....
                //update Dialog...
                Platform.runLater( new Dialog_Workspace_ShowImage(image, outputFilename));
                
                return outputFilename;


            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
