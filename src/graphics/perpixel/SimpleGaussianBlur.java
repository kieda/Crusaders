package graphics.perpixel;

import graphics.PPP;
import graphics.PerPixel;
import java.util.Arrays;
import static util.ScreenInfo.SCREEN_SIZE;

/**
 * a very simple area gaussian blur with 8 surrounding samples for each pixel
 * (radius of 2).
 * 
 * Standard deviation of 1.0 pixels. Ensures blur does not lose colors.
 * @author zkieda
 */
public class SimpleGaussianBlur implements PerPixel{
    
    /**Constants - based on gaussian distribution**/
    
    /*Weights*/
    
    /**
     * Weight dist:
     * 
     *      C B C
     *      B A B
     *      C B C
     */
    final static float A = (float)(1.0/(1.0 + 1/Math.sqrt(Math.E) + 1/Math.E));
    //B[] and C[] weight based on how many are present.
    //i.e. B[3] returns the weight for the pixel when three nearby pixels are
    //available
    //i.e. this would be in the situatiun at the top of the image,
    //
    //------------------------------------------------------------[top of image]
    //          B A B
    //          C B C
    
    //this is re-weighted so the entire probability adds to 1.0 (or so that we 
    //don't) have to do excess division operations
    final static float[] B;
    final static float[] C;
    static {
        /**weight for the center pixel*/
        

        /**weight for the pixels to the left, right, up, and down*/
        final float _B = (float)(1.0/((1.0 + 1/Math.sqrt(Math.E) + 1/Math.E)*Math.sqrt(Math.E)));

        /**weight for the pixels to the top left, top right, bottom left, and bottom right*/
        final float _C = (float)(1.0/((1.0 + 1/Math.sqrt(Math.E) + 1/Math.E)*Math.E));
        
        C = new float[]{0, _C, _C/2.0f, _C/3.0f, _C/4.0f};
        B = new float[]{0, _B, _B/2.0f, _B/3.0f, _B/4.0f};
//        System.out.println(A);
//        System.out.println(Arrays.toString(B));
//        System.out.println(Arrays.toString(C));
    }
    
    
    @Override
    public void process(int x, int y, byte[] src_pixel) {
        byte bounds = 0;//each bit stores if the left, top, bottom, or right is visible.
        //as in
        //n = 0 |-> left 
        //n = 1 |-> top 
        //n = 2 |-> bottom
        //n = 3 |-> right
        //where the bits are placed
        //  bounds = (NONE, NONE, NONE, NONE, right, bottom, top, left)
        
       //compute as follows : "nth side is visible" <==> bounds>>n&1 != 0;
        
        byte num = 0;//what are the number of components we can see?
        
        //we add together the nearby components into component_tot. After we add, we multiply by its weight.
        
        char component_tot_r = 0,
             component_tot_g = 0, 
             component_tot_b = 0;
        
        final int x_plus_1 = x+1, y_plus_1 = y+1;
        final int y_minus_1 = y-1, x_minus_1 =x - 1;
        
        int pixel_holder;//holds the current pixel
        float r, g, b;//rgb compnents that will have operations performed on them.
        
        if(x_minus_1 >= 0){
            num=1;
            bounds = 1;
            
            //calculate left component
            pixel_holder = PPP.getRGB()[x_minus_1 + y*SCREEN_SIZE.width];
            component_tot_r = (char)(pixel_holder>>16&0xFF);
            component_tot_g = (char)(pixel_holder>>8&0xFF);
            component_tot_b = (char)(pixel_holder&0xFF);
        }
        if(y_minus_1 >= 0){
            num++;
            bounds |= 0b10;
            
            //calculate top component
            pixel_holder = PPP.getRGB()[x + y_minus_1*SCREEN_SIZE.width];
            
            component_tot_r += (char)(pixel_holder>>16&0xFF);
            component_tot_g += (char)(pixel_holder>>8&0xFF);
            component_tot_b += (char)(pixel_holder&0xFF);
        }
        if(y_plus_1 < SCREEN_SIZE.height){
            num++;
            bounds |= 0b100;
            
            //calculate bottom component
            pixel_holder = PPP.getRGB()[x + y_plus_1*SCREEN_SIZE.width];
            component_tot_r += (char)(pixel_holder>>16&0xFF);
            component_tot_g += (char)(pixel_holder>>8&0xFF);
            component_tot_b += (char)(pixel_holder&0xFF);
        }
        if(x_plus_1 < SCREEN_SIZE.width){
            num++;
            bounds |= 0b1000;
            
            //calculate right component
            pixel_holder = PPP.getRGB()[x_plus_1 + y*SCREEN_SIZE.width];
            component_tot_r += (char)(pixel_holder>>16&0xFF);
            component_tot_g += (char)(pixel_holder>>8&0xFF);
            component_tot_b += (char)(pixel_holder&0xFF);
        }
        if(num==0)return;//there is only one pixel in the image. We don't modify it, as blurring a single pixel is silly.
        
        //we now know the number of B pixels, and the r, g, b, components 
        //from the pixels in the B position.
        

        //we then may add these to the final component.
        float factor = B[num];
        r = ((src_pixel[0]&0xFF)*A + component_tot_r*factor);
        g = ((src_pixel[1]&0xFF)*A + component_tot_g*factor);
        b = ((src_pixel[2]&0xFF)*A + component_tot_b*factor);
       
        component_tot_r = 0; component_tot_g = 0;component_tot_b = 0;//reset the components.
        num = 0;
        
        //  bounds = (NONE, NONE, NONE, NONE, right, bottom, top, left)
        if((bounds & 0b11) == 0b11){//top left
            num++;
            
            pixel_holder = PPP.getRGB()[x_minus_1 + y_minus_1*SCREEN_SIZE.width];
            component_tot_r = (char)(pixel_holder>>16&0xFF);
            component_tot_g = (char)(pixel_holder>>8&0xFF);
            component_tot_b = (char)(pixel_holder&0xFF);
        }
        if((bounds & 0b1010) == 0b1010){//top right
            num++;
            
            pixel_holder = PPP.getRGB()[x_plus_1 + y_minus_1*SCREEN_SIZE.width];
            component_tot_r += (char)(pixel_holder>>16&0xFF);
            component_tot_g += (char)(pixel_holder>>8&0xFF);
            component_tot_b += (char)(pixel_holder&0xFF);
        }
        if((bounds & 0b101) == 0b101){//bottom left
            num++;
            
            pixel_holder = PPP.getRGB()[x_minus_1 + y_plus_1*SCREEN_SIZE.width];
            component_tot_r += (char)(pixel_holder>>16&0xFF);
            component_tot_g += (char)(pixel_holder>>8&0xFF);
            component_tot_b += (char)(pixel_holder&0xFF);
        }
        if((bounds & 0b1100) == 0b1100){//bottom right
            num++;
            pixel_holder = PPP.getRGB()[x_plus_1 + y_plus_1*SCREEN_SIZE.width];
            component_tot_r += (char)(pixel_holder>>16&0xFF);
            component_tot_g += (char)(pixel_holder>>8&0xFF);
            component_tot_b += (char)(pixel_holder&0xFF);
        }
        factor = C[num];
        src_pixel[0] = (byte)(Math.round(r + component_tot_r*factor));
        src_pixel[1] = (byte)(Math.round(g + component_tot_g*factor));
        src_pixel[2] = (byte)(Math.round(b + component_tot_b*factor));
    }
}
