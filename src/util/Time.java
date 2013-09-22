package util;

/**
 * @author zkieda
 */
public class Time {
    private static long begin_t = System.currentTimeMillis();
    private static long curr_t = System.currentTimeMillis();
    private static long dt = 10;
    public static long getTimeElapsed(){
        curr_t = System.currentTimeMillis();
        return curr_t - begin_t;
    }
    public static long getCurrentTime(){
        return System.currentTimeMillis() - begin_t;
    }
    
    
    /**
     * call once per loop, please.
     */
    public static void calculate_dt(){
        long prev = curr_t;
        dt =  (curr_t = System.currentTimeMillis()) - prev;
    }
    public static long get_dt(){
        return dt;
    }
    public static void sleep(long millis){
        assert millis >= 0;
        try{
            Thread.currentThread().sleep(millis);
        } catch(InterruptedException ex){}
    }
}
