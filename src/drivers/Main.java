package drivers;

import core.Core;
import game.states.OpeningState;
import game.states.StatePool;
import static graphics.AWT.*;
import static graphics.PPP.*;
import graphics.anim.FadeOut;
import graphics.perpixel.CircleGradient;
import graphics.perpixel.Darken;
import graphics.perpixel.SimpleGaussianBlur;
import graphics.perpixel.Speculate;
import graphics.perpixel.SunRays;
import gui.MainFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import state.State;
import util.ScreenInfo;

/**
 * main class and all that jazz
 * @author zkieda
 */
public class Main {
    
    public static void main(String[] args) {
        ScreenInfo.calculate();
        final java.awt.Dimension d = ScreenInfo.SCREEN_SIZE;
        awt_initialze(d.width, d.height);
        MainFrame.init();
        graphics.sprites.logo.Logo3.calculate();
        Core.init(StatePool.OPENING_STATE);
    }    
}
