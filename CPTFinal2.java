package CompSciCPT;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class CPTFinal2 extends JPanel {
    public static void main(String[] args) {

        JFrame f = new JFrame();
        PongPaddlesUserMulti s = new PongPaddlesUserMulti();
        f.add(s);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
    }
}

class PongPaddlesUserMulti extends CPTFinal2 implements ActionListener, KeyListener {

    Timer t = new Timer(5, this);
    int xR = 0, yR = 0, velyR = 0; //x and y values for right side and variable for change in speed/direction
    int yL = 0, velyL = 0; // y value for left side and variable for change in speed/direction

    int xBall = 0, yBall = 0;   //x and y value for position of ball
    int anglex = 1, angley = 1; //change in speed of ball

    Ellipse2D oval = new Ellipse2D.Double();       //ball
    Rectangle2D padR = new Rectangle2D.Double();    //right paddle
    Rectangle2D padL = new Rectangle2D.Double();    //left paddle

    public PongPaddlesUserMulti() {
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    // creating graphic for paddles
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D ball = (Graphics2D) g;   //graphics object created for the shapes
        Graphics2D paddle1 = (Graphics2D) g;
        Graphics2D paddle2 = (Graphics2D) g;
        oval = new Ellipse2D.Double(xBall, yBall, 50, 50); //position and size of ball
        padR = new Rectangle2D.Double(xR, yR, 30, 100);    //position and size of paddles
        padL = new Rectangle2D.Double(40, yL, 30, 100);
        ball.fill(oval);       //displaying ball
        paddle1.fill(padR);     //displaying right paddle
        paddle2.fill(padL);     //displaying left paddle
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
        if (xBall + anglex < 0) {       //when ball is about to go past the left sid of frame
            anglex = 1;                 //ball goes right
        } else if (xBall + anglex > getWidth() - 50) {  //when ball is about to go past the right side of frame
            anglex = -1;                                //ball goes left
        } else if (yBall + angley < 0) {    //when ball is about to go past the top side of frame
            angley = 1;         //ball goes down
        } else if (yBall + angley > getHeight() - 50) { //when ball is about to go past the bottom side of frame
            angley = -1;        //ball goes up
        }

        xBall = xBall + anglex; //change of x value of ball
        yBall = yBall + angley; //change of y value of ball

        if (yR + velyR < 0) {
            velyR = 1;
        } else if (yR + velyR > getHeight() - 100) {    //only y values have to change based on end of panel
            velyR = -1;
        }
        if (yL + velyL < 0) {
            velyL = 1;
        } else if (yL + velyL > getHeight() - 100) { //operates the same as velyR, but was needed to make the paddles move separately
            velyL = -1;
        }
        xR = (getWidth()) - 80;    // where right paddle should appear (x-value)
        yR += velyR;    //y value of right paddle
        yL += velyL;    //y value of left paddle

        if (oval.intersects(padR)) { //if ball hits the right paddle
            anglex = -1;            //ball changes direction
            angley += 1;
        }
        if (oval.intersects(padL)) { //if the ball hits the left paddle
            anglex = +1;      //ball changes direction
            angley = -1;
        }
    }

    public void upR(int checkValue) {   //when user clicks up key
        if (checkValue == 1) {
            velyR = -2 * 2;    //this doubles the speed of the paddle going up
        } else {
            velyR = -2; // y-value decreases if player wants paddle to go up
        }
    }

    public void upL(int checkValue) {       //when user clicks 'W' key
        if (checkValue == 1) {
            velyL = -2 * 2;             //velocity doubles
        } else {
            velyL = -2;
        }
    }

    public void downR(int checkValue) {    //when user clicks down key
        if (checkValue == 1)
            velyR = 2 * 2; //this doubles the speed of the paddle going down
        else
            velyR = 2; // y-value decreases if player wants paddle to go up
    }

    public void downL(int checkValue) { //when user clicks 's' key
        if (checkValue == 1) {
            velyL = 2 * 2; //this doubles the speed of the paddle going down
        } else {
            velyL = 2; // y-value decreases if player wants paddle to go up
        }
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {    //when player presses "W" key
            upL(1);       //calls up method
        }
        if (code == KeyEvent.VK_S) {  //when player presses "S" key
            downL(1);     //calls down method
        }
        if (code == KeyEvent.VK_UP) { //when player presses up arrow key
            upR(1); //calls up method for paddle 2
        }
        if (code == KeyEvent.VK_DOWN) { //when player presses down arrow key
            downR(1); //calls down method for paddle 2
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}

