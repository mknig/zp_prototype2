/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

 import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**@author McDowell*/
public class LocateJRE {
  
  /**
   * Returns the URL of a given class.
   * @param c    a non-null class
   * @return    the URL for that class
   */
  public static URL getUrlOfClass(Class c) {
    if(c==null) {
      throw new NullPointerException();
    }
    String className = c.getName();
    String resourceName = className.replace('.', '/') + ".class";
    ClassLoader classLoader = c.getClassLoader();
    if(classLoader==null) {
      classLoader = ClassLoader.getSystemClassLoader();
    }
    URL url = classLoader.getResource(resourceName);
    return url;
  }
  
  /**
   * Finds the location of a given class file on the file system.
   * Throws an IOException if the class cannot be found.
   * <br>
   * If the class is in an archive (JAR, ZIP), then the returned object
   * will point to the archive file.
   * <br>
   * If the class is in a directory, the base directory will be returned
   * with the package directory removed.
   * <br>
   * The <code>File.isDirectory()</code> method can be used to
   * determine which is the case.
   * <br>
   * @param c    a given class
   * @return    a File object
   * @throws IOException
   */
  public static File getClassLocation(Class c) throws IOException, FileNotFoundException {
    if(c==null) {
      throw new NullPointerException();
    }
    
    String className = c.getName();
    String resourceName = className.replace('.', '/') + ".class";
    ClassLoader classLoader = c.getClassLoader();
    if(classLoader==null) {
      classLoader = ClassLoader.getSystemClassLoader();
    }
    URL url = classLoader.getResource(resourceName);
    
    String szUrl = url.toString();
    if(szUrl.startsWith("jar:file:")) {
      try {
        szUrl = szUrl.substring("jar:".length(), szUrl.lastIndexOf("!"));
        URI uri = new URI(szUrl);
        return new File(uri);
      } catch(URISyntaxException e) {
        throw new IOException(e.toString());
      }
    } else if(szUrl.startsWith("file:")) {
      try {
        szUrl = szUrl.substring(0, szUrl.length() - resourceName.length());
        URI uri = new URI(szUrl);
        return new File(uri);
      } catch(URISyntaxException e) {
        throw new IOException(e.toString());
      }
    }
    
    throw new FileNotFoundException(szUrl);
  }
  
}