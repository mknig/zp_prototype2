/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

/**
 * @author Administrator
 * @date 17.12.2003
 */
public class PerformanceMeasure {

    public static Logger logger = Logger.getLogger("PerformanceMeasure.class");
    private static PerformanceMeasure me = null;
    // private Map timeMap = Collections.synchronizedMap(new
    // MK_CacheHashMap("PerformanceCache_timeMap"));
    // private Map timeMapLastCall = Collections.synchronizedMap(new
    // MK_CacheHashMap("PerformanceCache_timeMapLastCall"));
//	private Map timeMap = new MK_CacheHashMap("CACHE_PERFORMANCE_TIMEMAP");
//	private Map timeMapLastCall = new MK_CacheHashMap("CACHE_PERFORMANCE_TIMEMAP_LASTCALL");
    private Map timeMap = new ConcurrentHashMap();
    private Map timeMapLastCall = new ConcurrentHashMap();
    private static String rootContext = "";

    private PerformanceMeasure() {
    }

    public static PerformanceMeasure getInstance() {
        if (me == null) {
            me = new PerformanceMeasure();
            rootContext = " (ZP)";
        }
        return me;
    }

    public void resetMaps() {
//		timeMap = new MK_CacheHashMap("CACHE_PERFORMANCE_TIMEMAP");
//		timeMapLastCall = new MK_CacheHashMap("CACHE_PERFORMANCE_TIMEMAP_LASTCALL");
        timeMap = new ConcurrentHashMap();
        timeMapLastCall = new ConcurrentHashMap();
    }

    public static void setStartPoint(Object obj) {

        // wird gelogt???
        String PerformanceMeasure_ON = "TRUE";
        if (!PerformanceMeasure_ON.equalsIgnoreCase("TRUE")) {
            return;
        }

        me = getInstance();
        long time = new Date().getTime();
        me.timeMap.put(obj.getClass().getName(), new Long(time));
        me.timeMapLastCall.put(obj.getClass().getName(), new Long(time));

        String logString = "        Class: " + obj;
        // logger.info(logString) ;
    }

    public static void setStartPoint(String str) {

        // wird gelogt???
        String PerformanceMeasure_ON = "TRUE";
        if (!PerformanceMeasure_ON.equalsIgnoreCase("TRUE")) {
            return;
        }

        me = getInstance();
        long time = new Date().getTime();
        me.timeMap.put(str, new Long(time));
        me.timeMapLastCall.put(str, new Long(time));

        String logString = "        Point: " + str;
        // logger.info(logString) ;

    }

    /**
     *
     * Verwendung in Methode: PerformanceMeasure.measurePoint(this);
     *
     * Achtung: Zuvor muss ein startPoint gesetzt sein
     *
     * PerformanceMeasure.setStartPoint(this);
     *
     * @param obj
     * @return
     */
    // +++++++++++++++++++++++++++++++++
    // Measure by Class-Point
    // ++++++++++++++++++++++++++++++++++
    public static long measurePoint(Object obj) {
        return measurePoint(obj, true);
    }

    // +++++++++++++++++++++++++++++++++
    // Measure by String-Point
    // ++++++++++++++++++++++++++++++++++
    public static long measurePoint(String obj) {
        return measurePoint(obj, true, "");
    }

    public static long measurePoint(String obj, String comment) {
        return measurePoint(obj, true, comment);
    }

    public static long measurePoint(Object obj, boolean output) {
        return measurePoint(obj, output, "");
    }

    public static long measurePoint(Object obj, boolean output, String comment) {



        try {
            if (comment != null && comment.equals("")) {
                comment = "noComment";
            }

            me = getInstance();
            long time = new Date().getTime();

            //startzeit ermitteln...
            long startTime = 0;
            if (!(obj instanceof java.lang.String)) {
                startTime = ((Long) me.timeMap.get(obj.getClass().getName())).longValue();

            } else {
                Object mesObj = me.timeMap.get(obj);
                if (mesObj == null) {
                    me.timeMapLastCall.put(obj, new Long(new Date().getTime()));
                    return -9999;
                }
                startTime = ((Long) mesObj).longValue();
            }

            //zeiten berechnen...
            long ret = 0;
            ret = time - startTime;

            long lastCall = ((Long) me.timeMapLastCall.get(obj)).longValue();
            long timeLastCall = 0;
            lastCall = time - lastCall;

            if (output) {
                //if (false) {	
                if (lastCall >= 0) {

                    StringBuilder sb = new StringBuilder();
                    Formatter formatter = new Formatter(sb, Locale.GERMAN);

                    if (ret == lastCall) {
                        String msg=formatter.format("%1$6s %2$-25s (ms:%3$11s) Out:%4$2s", rootContext,
                                obj.toString(),
                                lastCall,
                                comment).toString();
                        //logger.log(Level.ALL,msg );
                        System.out.println(msg);
                    } else {
                         String msg=formatter.format("%1$6s %2$-25s (ms:%3$4s/a:%4$4s) Out:%5$2s",
                                rootContext,
                                obj.toString(),
                                lastCall,
                                ret,
                                comment).toString();
                        //logger.log(Level.ALL,msg);
                          System.out.println(msg);
                         
                    }

                }
            }


            // set new StartPointTime...
            // set new StartPointTime...
            // set new StartPointTime...
            if (!(obj instanceof java.lang.String)) {
                me.timeMapLastCall.put(obj.getClass().getName(), new Long(time));
            } else {
                me.timeMapLastCall.put(obj, new Long(time));
            }


            return ret;
        } catch (Exception e) {
            logger.log(Level.ALL,"Error measuring Performance:" + rootContext + "+ Obj: " + obj + "/ Comment:" + comment + "\n"
                    + e);
            return 99999;
        }
    }
}