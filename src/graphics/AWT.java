package graphics;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

//use the class like this please: import static AWT.*
//this class is meant to have a "central"
//useful for a single blanket painter.
public final class AWT {
    private static Graphics2D g2d;
    private static BufferedImage linked_image;

    //states (based on bit position)
    //1 : boolean initialized
    //2 through 8 : unassigned
    //bad coding style tradeoff for saving an extremely small amount of space
    private static byte state = 0;

    private AWT(){}

    //@requires awt_isInitialized()
    //@ensures \result != null
    public static Graphics2D awt_getGraphics(){
        if(!awt_isInitialized())
            throw new IllegalStateException("Graphics must be initialized.");
        else return g2d;
    }

    public static BufferedImage awt_getImage(){
        return linked_image;
    }

    //@requires image != null
    //@ensures awt_isInitialized()
    //@ensures g2d != null
    public static void awt_initialize(BufferedImage image){
        if(image == null) throw new IllegalArgumentException("input image may not be null");
        linked_image = image;
        state |= 1;
        g2d = image.createGraphics();
//            g2d.setRenderingHint
//  (RenderingHints.KEY_ANTIALIASING,
//   RenderingHints.VALUE_ANTIALIAS_ON); 
    }

    //@requires width > 0 && height > 0
    //@ensures awt_isInitialized()
    //@ensures g2d != null
    public static void awt_initialze(int width, int height){
        if(width <= 0 || height <= 0 ) throw new IllegalArgumentException("width and height must be greater than zero. Width : \"" + width + "\" height : \"" + height + "\"");
        awt_initialize(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    }

    public static boolean awt_isInitialized(){
        return (state&1) == 1;
    }

    //@ensures \result > 0 && \result == awt_getImage().getWidth()
    public static int awt_getWidth(){
        return linked_image.getWidth();
    }

    //@ensures \result > 0 && \result == awt_getImage().getHeight()
    public static int awt_getHeight(){
        return linked_image.getHeight();
    }

    public static void awt_setPaint(Paint p){
        g2d.setPaint(p);
    }
    public static void awt_setColor(Color c){
        g2d.setColor(c);
    }

    public static void awt_setStroke(Stroke s){
        g2d.setStroke(s);
    }
    public static void awt_clearRect(int x, int y, int width, int height){
        g2d.clearRect(x, y, width, height);
    }
    public static void awt_clip(Shape shape){
        g2d.clip(shape);
    }
    public static void awt_drawImage(BufferedImage bi, AffineTransform at, ImageObserver op){
        g2d.drawImage(linked_image, at, op);
    }
    public static void awt_drawLine(int x1, int y1, int x2, int y2){
        g2d.drawLine(x1, y1, x2, y2);
    }
    public static void awt_drawOval(int x, int y, int width, int height){
        g2d.drawOval(x, y, width, height);
    }
    public static void awt_drawPolygon(Polygon p){
        g2d.drawPolygon(p);
    }
    public static void awt_drawPolygon(int[] x_points, int[] y_points, int i){
        g2d.drawPolygon(x_points, y_points, i);
    }
    public static void awt_drawPolyLine(int[] x_points, int[] y_points, int i){
        g2d.drawPolyline(x_points, y_points, i);
    }
    public static void awt_drawRect(int x, int y, int width, int height){
        g2d.drawRect(x, y, width, height);
    }
    public static void awt_drawRoundRect(int i1, int i2, int i3, int i4, int i5, int i6){
        g2d.drawRoundRect(i1, i2, i3, i4, i5, i6);
    }
    public static void awt_drawString(String str, int x, int y){
        g2d.drawString(str, x, y);
    }
    public static void awt_drawImage(Image image, int x, int y, ImageObserver obs){
        g2d.drawImage(image, x, y, obs);
    }
    public static void awt_drawImage(Image image, int x, int y, int width, int height, ImageObserver obs){
        g2d.drawImage(image, x, y, width, height, obs);
    }
    public static void awt_fill(Shape shape){
        g2d.fill(shape);
    }
    public static void awt_fillArc(int i1, int i2, int i3, int i4, int i5, int i6){
        g2d.fillArc(i1,i2,i3,i4,i5,i6);
    }
    public static void awt_fillOval(int x, int y, int width, int height){
        g2d.fillOval(x, y, width, height);
    }
    public static void awt_fillPolygon(Polygon poly){
        g2d.fillPolygon(poly);
    }
    public static void awt_fillPolygon(int[] x_coords, int[] y_coords, int i){
        g2d.fillPolygon(x_coords, y_coords, i);
    }
    public static void awt_fillRect(int x, int y, int width, int height){
        g2d.fillRect(x, y, width, height);
    }
    public static void awt_fillRoundRect(int i1, int i2, int i3, int i4, int i5, int i6){
        g2d.fillRoundRect(i1, i2, i3, i4, i5, i6);
    }
    public static FontMetrics awt_getFontMetrics(){
        return g2d.getFontMetrics();
    }
    public static FontMetrics awt_getFontMetrics(Font font){
        return g2d.getFontMetrics(font);
    }
    public static void awt_rotate(double rot){
        g2d.rotate(rot);
    }
    public static void awt_rotate(double xrot, double yrot, double zrot){
        g2d.rotate(xrot, yrot, zrot);
    }
    public static void awt_scale(double xscale, double yscale){
        g2d.scale(xscale, yscale);
    }
    public static void awt_setBackground(Color c){
        g2d.setBackground(c);
    }
    public static void awt_setClip(Shape shape){
        g2d.setClip(shape);
    }
    public static void awt_setClip(int x, int y, int width, int height){
        g2d.setClip(x, y, width, height);
    }
    public static void awt_setComposite(Composite comp){
        g2d.setComposite(comp);
    }
    public static void awt_setFont(Font f){
        g2d.setFont(f);
    }
    public static void awt_setRenderingHint(RenderingHints.Key key, Object o){
        g2d.setRenderingHint(key, o);
    }
    public static void awt_setTransform(AffineTransform aff){
        g2d.setTransform(aff);
    }
    public static void awt_shear(double xshear, double yshear){
        g2d.shear(xshear, yshear);
    }
    public static void awt_transform(AffineTransform transform){
        g2d.transform(transform);
    }
    public static void awt_translate(double xtranslate, double ytranslate){
        g2d.translate(xtranslate, ytranslate);
    }
    public static void awt_translate(int xtranslate, int ytranslate){
        g2d.translate(xtranslate, ytranslate);
    }
    /**
     * applies RGBA to the color to the pixel at [pixel_x, pixel_y]
     * 
     * Calculated like this...
     * 
     * RGBA_new = 
     * 
     * 
     * throws an error if the blend_op is not a known type
     */
    public static void awt_applyColor(final int pixel_x, final int pixel_y, char r, char g, char b, char a, byte blend_op){
        a &= 0xFF;
        int dat = linked_image.getRGB(pixel_x, pixel_y);
        if(blend_op == BLEND_RGBA_OUT){
            throw error.GeneralError.raise("unsupported op");
        } else if (blend_op == BLEND_RGB_OUT){
            char a_256_minus = (char)(256 - a);
            a += 1;

            r = (char)((a*r + a_256_minus*(dat>>16&0xFF))>>8);
            g = (char)((a*g + a_256_minus*(dat>>8 &0xFF))>>8);
            b = (char)((a*b + a_256_minus*(dat    &0xFF))>>8);
            dat  = 0xFF000000;//A is set to 0xFF
            dat |=  b    &0xFF
                   |g<<8 &0xFF00
                   |r<<16&0xFF0000;

            linked_image.setRGB(pixel_x, pixel_y, dat);

        } else throw error.GeneralError.raise();

    }
    /**
     * sets the final pixel on a color to an RGB value (A value of 255).
     * This is what you're used to.
     */
    public static final byte BLEND_RGB_OUT  = 0;
    /**
     * sets the final pixel on a color to an RGBA value, composed with the 
     * alpha of the input. 
     */
    public static final byte BLEND_RGBA_OUT = 1;
}
