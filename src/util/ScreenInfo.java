package util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * @author zkieda
 */
public final class ScreenInfo {
    private ScreenInfo(){}
    private static Dimension screen_d;
    
    private static Point paint_pos;
    private static Dimension paint_d;
    
    //the universal aspect ratio of the game. Deal with it.
    private static final Dimension aspect_ratio = new Dimension(4, 3);
    
    //this is the size of the buffered image to be prepared and rendered to your
    //screen. The buffered image is drawn at this scale, and then modified 
    //to suit your needs. 
    public static final Dimension SCREEN_SIZE = new Dimension(180 +40 , 135+30);
    private static int smallest_d;
    public static int smallest_d(){
        return smallest_d;
    }
    /**
     * returns the screen dimensions
     */
    public static Dimension sc_d(){
        return screen_d;
    }
    
    /**
     * returns the top left position where painting should begin.
     */
    public static Point paint_pos(){
        return paint_pos;
    }
    /**
     * returns the dimensions for the painting clip. 
     */
    public static Dimension paint_d(){
        return paint_d;
    }
    public static void calculate(){
        screen_d = Toolkit.getDefaultToolkit().getScreenSize();
        
        {//todo : check for overflow
        int l1 = aspect_ratio.width*screen_d.height;
        int l2 = aspect_ratio.height*screen_d.width;
        assert l1 > 0 && l2 > 0;
        if(l1 <= l2){
            smallest_d = SCREEN_SIZE.width;
            l1 /= aspect_ratio.height;//the width of the screen.
            paint_d = new Dimension(
                    l1,
                    screen_d.height
                    );
            //because we know the width of the paint, and we know that the 
            //height covers the entire screen, we can then calculate the
            //top left position of the screen. 
            
            //top_left, left position = (screen_width - l1)/2
            //top_left, right position = 0
            
            paint_pos = new Point((screen_d.width - l1)/2, 0);
        } else{
            smallest_d = SCREEN_SIZE.height;
            l2 /= aspect_ratio.width;//the width of the renderable screen.
            paint_d = new Dimension(
                    screen_d.width,
                    l2
                    );
            
            //see logic above...
            paint_pos = new Point(0, (screen_d.height - l2)/2);
        }}
    }
}
