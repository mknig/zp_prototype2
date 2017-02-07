/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zp_prototype2.render;

/**
 *
 * @author Michael
 */
public class MemoryManager {

    static long freeAll = 0;
    static long free = 0;
    static long total = 0;
    static long max = 0;

    public static boolean isEnoughMemory(double ptWidth, double ptHeight, int minFree) {

        double memAllocate = getMem2Malloc4Image(ptWidth, ptHeight);
        double need=memAllocate + minFree;
        
        //System.out.println("ZP_MemoryManager--> isEnoughMemory frei: " + getFreeAll() + " need:" + need);

        if ( need> getFreeAll()) {
           // System.out.println("ZP_MemoryManager--> freeAll: " + getFreeAll() + " / need: " + need + " not enough Mem 2 Render on Block -");
            return false;

        } else {
            //System.out.println("memoryCheck -  passed");
            return true;
        }
    }

    public static double getMem2Malloc4Image(double ptWidth, double ptHeight) {
        double memAllocate = ptWidth * ptHeight * 4;
        memAllocate = memAllocate /8 / 1024 ;/// 1024;//Speicher in MB

        return memAllocate;
    }

    public static double getFreeAll() {
        free = (Runtime.getRuntime().freeMemory() / 1024 / 1024);
        total = (Runtime.getRuntime().totalMemory() / 1024 / 1024);
        max = (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        freeAll = max - total + free;
        return freeAll;
    }

    public static String getMemStat() {
        //Runtime gibt Einheit byte
        free = (Runtime.getRuntime().freeMemory() / 1024 / 1024);
        total = (Runtime.getRuntime().totalMemory() / 1024 / 1024);
        max = (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        freeAll = max - total + free;
        return "(" + freeAll + ")freeALL - free:" + free + " - total:" + total + " - max: " + max;
    }
}
