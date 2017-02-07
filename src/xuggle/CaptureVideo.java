/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xuggle;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author admin
 */
public class CaptureVideo implements Runnable {

    File videoFile = null;

    public CaptureVideo(File file) {
        videoFile = file;
    }

    @Override
    public void run() {

        String inputFilename = videoFile.getAbsolutePath();//+ videoFile.getName();
        IMediaReader mediaReader = ToolFactory.makeReader(inputFilename);

        // stipulate that we want BufferedImages created in BGR 24bit color space
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        mediaReader.addListener(new VideoThumbnailsExample.ImageSnapListener());

        // read out the contents of the media file and
        // dispatch events to the attached listener
        while (mediaReader.readPacket() == null) ;

    }
}
