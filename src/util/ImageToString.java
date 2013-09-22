package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * converts an image to a string...
 * @author zkieda
 */
public class ImageToString {
    public static String image_to_string(String image_name, BufferedImage bi){
        if(bi == null || image_name == null) return null;
        StringBuilder sb = new StringBuilder("public int[] ").append(image_name).append(" = {\n");
        for(int y=0;y<bi.getHeight();y++){
        for(int x=0;x<bi.getWidth();x++){
            sb.append(bi.getRGB(x, y)).append(",");
        } sb.append('\n');}
        
        return sb.append("};\n").toString();
    }
    private final static int MAX_INTS = 8223;
    public static String image_to_string_mult(String image_name, BufferedImage bi){
        int count = 0;
        byte num = 0;
        if(bi == null || image_name == null) return null;
        StringBuilder sb = new StringBuilder("public int[] ").append(image_name).append(" = {\n");
        for(int y=0;y<bi.getHeight();y++){
        for(int x=0;x<bi.getWidth();x++){
            count++;
            sb.append(bi.getRGB(x, y)).append(",");
            if(count == MAX_INTS){
                sb.append("};\n").append("public int[] ").append(num++).append(" = {\n");
                count = 0;
            }
        } sb.append('\n');}
//        if(kieda.math.Bitwise.logicalXOR(count != 0, num==0) ) drunken attempt lol
            
        return sb.append("};\n").toString();
    }
    
    public static String convert_to(String file_input, String image_name) throws IOException{
        BufferedImage bi = ImageIO.read(new File(file_input));
        return image_to_string(image_name, bi);
    }
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("public int[] ").append("i").append(" = {\n");
        for(int y=0;y<1;y++){
        for(int x=0;x<10000;x++){
            sb.append(x).append(",");
        } sb.append('\n');}
        System.out.println(sb.append("};"));
    }
}
