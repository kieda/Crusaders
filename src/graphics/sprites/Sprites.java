package graphics.sprites;

import java.awt.image.BufferedImage;

/**
 * is of type ARGB
 * @author zkieda
 */
public class Sprites {
//    static final int WHITE = 0x3FF00FFF;
//    static final int BLACK = 0xFF000000;
//    static final int RED   = 0xFFFF0000;
//    static final int GREEN = 0xFF00FF00;
//    static final int BLUE  = 0xFF0000FF;
//    static final int YELLOW = 0xFFFFFF00;
//
//    static final BufferedImage SMILEY = makeSprite( new int[]{
//        WHITE, BLACK,  BLACK,  BLACK, BLACK, BLACK, BLACK, WHITE,
//        BLACK, YELLOW, YELLOW, YELLOW, YELLOW, YELLOW, YELLOW, BLACK,
//        BLACK, YELLOW, BLACK,  YELLOW, YELLOW, BLACK,  YELLOW, BLACK,
//        BLACK, YELLOW, YELLOW, YELLOW, YELLOW, YELLOW, YELLOW, BLACK,
//        BLACK, YELLOW, BLACK,  YELLOW, YELLOW, BLACK,  YELLOW, BLACK,
//        BLACK, YELLOW, YELLOW,  BLACK,BLACK, YELLOW,  YELLOW, BLACK,
//        BLACK, YELLOW, YELLOW, YELLOW, YELLOW, YELLOW, YELLOW, BLACK,
//        WHITE, BLACK,  BLACK,  BLACK,  BLACK, BLACK,BLACK, WHITE,
//    }, 8);
  
//    public static final BufferedImage LOGO = makeSprite(
    //, 199);
    public static BufferedImage makeSprite(int[] TYPE_INT_ARGB, final int image_width){
        assert image_width > 0;
        assert TYPE_INT_ARGB.length %image_width == 0;
        final int image_height = TYPE_INT_ARGB.length/image_width;
        BufferedImage image = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);
        
        for(int x = 0; x < image_width; x++)
        for(int y = 0; y < image_height; y++)
            image.setRGB(x, y, TYPE_INT_ARGB[x + image_width*y]);
            //there is NO way to do this conveniently/directly. WOW.
        return image;
    }
    public static BufferedImage makeSprite(util.IntPtr[] TYPE_INT_ARGB, final int image_width){
        assert image_width > 0;
        assert TYPE_INT_ARGB.length %image_width == 0;
        final int image_height = TYPE_INT_ARGB.length/image_width;
        BufferedImage image = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);
        for(int x = 0; x < image_width; x++)
        for(int y = 0; y < image_height; y++)
            image.setRGB(x, y, TYPE_INT_ARGB[x + image_width*y].val);
            //there is NO way to do this conveniently/directly. WOW.
        return image;
    }
//    public static void main(String[] args) {
//        BufferedImage b = makeSprite(new int[]{
//            1, 0,
//            4, 0
//        }, 2);
//    }
}
