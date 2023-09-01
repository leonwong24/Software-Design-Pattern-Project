package javaproject.main;

import javax.swing.*;
import java.awt.*;

/***
 * This class will create a jframe with all the needed details set thus it can call directly from the game class*
 */
public class Display {


    private JFrame frame;
    private Canvas canvas;

    private String title;
    private int width,height;


    //Constructor

    /**
     * It takes a string as the JFrame title, 2 int for jframe/game width and height
     * @param title
     * @param width
     * @param height
     */
    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }

    /**
     * create a fully functional jframe will the all the details set
     */
    private void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setMaximumSize(new Dimension(width,height));
        canvas.setMinimumSize(new Dimension(width,height));
        canvas.setFocusable(false);//it makes the jframe the only object to be focus


        frame.add(canvas);
        frame.pack();

    }

    //access canvas in other class since canvas is defined as private in here
    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return frame;
    }
}
