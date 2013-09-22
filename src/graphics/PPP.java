package graphics;

import java.awt.image.BufferedImage;
import kieda.util.ResettingList;
import kieda.util.LinkedRL;
import util.ScreenInfo;

/**
 * Stands for "PER PIXEL PAINT"
 * 
 * Use the per pixel algorithms!
 * 
 * 
 * 
 * @author zkieda
 */
public class PPP {
    private static ResettingList<PerPixel> pixel_ops = new LinkedRL<PerPixel>();
    private static int[] RGB;
        //the RGBA buffer. Done in bytes, so we don't have to decompose from 
        //ints into its product unnecessarily often.
    


    //processes per pixel ops as if every pixel is processed individually and 
    //without influence of other pixels. Very fast. Multithreaded to further 
    //improve time
    public static void process(PerPixel op){
        assert op != null;
        final int w = ScreenInfo.SCREEN_SIZE.width;
        final int h = ScreenInfo.SCREEN_SIZE.height;
        //grabs the pixels from AWT to prepare them for processing.
        RGB = AWT.awt_getImage().getRGB(0, 0, w, h, null, 0, w);
        //from position 0,0 to height, width. 

        
        int hol, pos, x, y;
        byte[] RGB_pixel = new byte[3];
        
        for(y = 0; y < h; y++)
        for(x = 0; x < w; x++){
            pos = x + y*w;
            hol = RGB[pos];
            RGB_pixel[0] = (byte)(hol>>16&0xFF);
            RGB_pixel[1] = (byte)(hol>>8&0xFF);
            RGB_pixel[2] = (byte)(hol&0xFF);
            op.process(x, y, RGB_pixel);
            RGB[pos] = 0xFF000000
                    | (RGB_pixel[0]<<16&0xFF0000)
                    | (RGB_pixel[1]<<8&0xFF00)
                    | (RGB_pixel[2]&0xFF)
                    ;
        }
        
        AWT.awt_getImage().setRGB(0, 0, w, h, RGB, 0, w);
    }
    public static void process2(PerPixel op){
        assert op != null;
        final int w = ScreenInfo.SCREEN_SIZE.width;
        final int h = ScreenInfo.SCREEN_SIZE.height;
        
        //grabs the pixels from AWT to prepare them for processing.
        
        int[] RGB = AWT.awt_getImage().getRGB(0, 0, w, h, null, 0, w);
        if(PPP.RGB == null) PPP.RGB = new int[RGB.length];
        System.arraycopy(RGB, 0, PPP.RGB, 0, RGB.length);
//        if(PPP.RGB == null)

        //from position 0,0 to height, width. 
        
        int hol, pos, x, y;
        byte[] RGB_pixel = new byte[3];
        
        for(y = 0; y < h; y++)
        for(x = 0; x < w; x++){
            pos = x + y*w;
            hol = RGB[pos];
            RGB_pixel[0] = (byte)(hol>>16&0xFF);
            RGB_pixel[1] = (byte)(hol>>8&0xFF);
            RGB_pixel[2] = (byte)(hol&0xFF);
            op.process(x, y, RGB_pixel);
            RGB[pos] = 0xFF000000
                    | (RGB_pixel[0]<<16&0xFF0000)
                    | (RGB_pixel[1]<<8&0xFF00)
                    | (RGB_pixel[2]&0xFF)
                    ;
        }
        AWT.awt_getImage().setRGB(0, 0, w, h, RGB, 0, w);
    }
    
    public static void process(BufferedImage bi, PerPixel op){
        assert op != null;
        final int w = ScreenInfo.SCREEN_SIZE.width;
        final int h = ScreenInfo.SCREEN_SIZE.height;
        //grabs the pixels from AWT to prepare them for processing.
        int[] RGB = bi.getRGB(0, 0, w, h, null, 0, w);
        //from position 0,0 to height, width. 

        
        int hol, pos, x, y;
        byte[] RGB_pixel = new byte[3];
        
        for(y = 0; y < h; y++)
        for(x = 0; x < w; x++){
            pos = x + y*w;
            hol = RGB[pos];
            RGB_pixel[0] = (byte)(hol>>16&0xFF);
            RGB_pixel[1] = (byte)(hol>>8&0xFF);
            RGB_pixel[2] = (byte)(hol&0xFF);
            op.process(x, y, RGB_pixel);
            RGB[pos] = 0xFF000000
                    | (RGB_pixel[0]<<16&0xFF0000)
                    | (RGB_pixel[1]<<8&0xFF00)
                    | (RGB_pixel[2]&0xFF)
                    ;
        }
        
        bi.setRGB(0, 0, w, h, RGB, 0, w);
    }
    public static void process_bulk(){
        final int w = ScreenInfo.SCREEN_SIZE.width;
        final int h = ScreenInfo.SCREEN_SIZE.height;
        //grabs the pixels from AWT to prepare them for processing.
        RGB = AWT.awt_getImage().getRGB(0, 0, w, h, null, 0, w);
        //from position 0,0 to height, width. 

        
        PerPixel op;
        int hol, pos, x, y;
        byte[] RGB_pixel = new byte[3];
        
        for(y = 0; y < h; y++)
        for(x = 0; x < w; x++){
            pos = x + y*w;
            hol = RGB[pos];
            RGB_pixel[0] = (byte)(hol>>16&0xFF);
            RGB_pixel[1] = (byte)(hol>>8&0xFF);
            RGB_pixel[2] = (byte)(hol&0xFF);
            while((op = pixel_ops.next()) != null)
                op.process(x, y, RGB_pixel);
            RGB[pos] = 0xFF000000
                    | (RGB_pixel[0]<<16&0xFF0000)
                    | (RGB_pixel[1]<<8&0xFF00)
                    | (RGB_pixel[2]&0xFF)
                    ;
        }
        
        AWT.awt_getImage().setRGB(0, 0, w, h, RGB, 0, w);
    }
    public static void process_bulk(ResettingList<PerPixel> pixel_ops){
        assert pixel_ops != null;
        
        final int w = ScreenInfo.SCREEN_SIZE.width;
        final int h = ScreenInfo.SCREEN_SIZE.height;
        //grabs the pixels from AWT to prepare them for processing.
        RGB = AWT.awt_getImage().getRGB(0, 0, w, h, null, 0, w);
        //from position 0,0 to height, width. 

        
        PerPixel op;
        int hol, pos, x, y;
        byte[] RGB_pixel = new byte[3];
        
        for(y = 0; y < h; y++)
        for(x = 0; x < w; x++){
            pos = x + y*w;
            hol = RGB[pos];
            RGB_pixel[0] = (byte)(hol>>16&0xFF);
            RGB_pixel[1] = (byte)(hol>>8&0xFF);
            RGB_pixel[2] = (byte)(hol&0xFF);
            while((op = pixel_ops.next()) != null)
                op.process(x, y, RGB_pixel);
            RGB[pos] = 0xFF000000
                    | (RGB_pixel[0]<<16&0xFF0000)
                    | (RGB_pixel[1]<<8&0xFF00)
                    | (RGB_pixel[2]&0xFF)
                    ;
        }
        
        AWT.awt_getImage().setRGB(0, 0, w, h, RGB, 0, w);
    }
    public static void process_bulk(BufferedImage bi, ResettingList<PerPixel> pixel_ops){
        assert bi != null && pixel_ops != null;
        
        final int w = bi.getWidth();
        final int h = bi.getHeight();
        //grabs the pixels from AWT to prepare them for processing.
        int[] RGB = bi.getRGB(0, 0, w, h, null, 0, w);
        //from position 0,0 to height, width. 

        PerPixel op;
        int hol, pos, x, y;
        byte[] RGB_pixel = new byte[3];
        
        for(y = 0; y < h; y++)
        for(x = 0; x < w; x++){
            pos = x + y*w;
            hol = RGB[pos];
            RGB_pixel[0] = (byte)(hol>>16&0xFF);
            RGB_pixel[1] = (byte)(hol>>8&0xFF);
            RGB_pixel[2] = (byte)(hol&0xFF);
            while((op = pixel_ops.next()) != null)
                op.process(x, y, RGB_pixel);
            RGB[pos] = 0xFF000000
                    | (RGB_pixel[0]<<16&0xFF0000)
                    | (RGB_pixel[1]<<8&0xFF00)
                    | (RGB_pixel[2]&0xFF)
                    ;
        }
        
        bi.setRGB(0, 0, w, h, RGB, 0, w);
    }
    //processes per pixel ops stage by stage. Useful if there's something like
    //a gaussian blur that needs to be applied at the end and relies on all
    //of the other ops being calculated.
    public static void process_stage(){
        final int w = ScreenInfo.SCREEN_SIZE.width;
        final int h = ScreenInfo.SCREEN_SIZE.height;
        //grabs the pixels from AWT to prepare them for processing.
        RGB = AWT.awt_getImage().getRGB(0, 0, w, h, null, 0, w);
        //from position 0,0 to height, width. 

        PerPixel op;
        int hol, pos, x, y;
        byte[] RGB_pixel = new byte[3];
        
        while((op = pixel_ops.next()) != null)
        for(y = 0; y < h; y++)
        for(x = 0; x < w; x++){
            pos = x + y*w;
            hol = RGB[pos];
            RGB_pixel[0] = (byte)(hol>>16&0xFF);
            RGB_pixel[1] = (byte)(hol>>8&0xFF);
            RGB_pixel[2] = (byte)(hol&0xFF);
            op.process(x, y, RGB_pixel);
            RGB[pos] = 0xFF000000
                    | (RGB_pixel[0]<<16&0xFF0000)
                    | (RGB_pixel[1]<<8&0xFF00)
                    | (RGB_pixel[2]&0xFF)
                    ;
        }
        
        AWT.awt_getImage().setRGB(0, 0, w, h, RGB, 0, w);
    }
    public static int[] getRGB(){return RGB;}
    public static void addOP(PerPixel op){if(op==null)return;pixel_ops.add(op);}
    public static void removeOP(PerPixel op){if(op==null)return;pixel_ops.remove(op);}
}
