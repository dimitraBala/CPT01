import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;

//paddles that is controllable by arrow keys and character keys
//December 28 2018
//Manchind Singh


public class PongPaddlesUser extends JPanel implements ActionListener, KeyListener {
    // variables for paddle
    Timer t = new Timer(5, this);
    double x = 0, y = 0, vely = 0;
    private int checkValue = 0;

    public PongPaddlesUser() {
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    //creating graphic for paddle
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D paddle1 = (Graphics2D) g;
        Graphics2D paddle2 = (Graphics2D) g;
        paddle1.fill(new Rectangle.Double(40, y, 30, 100));  
        paddle2.fill(new Rectangle.Double(x, y, 30, 100));  
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
        if (y + vely < 0) {
            vely = 1;
        } else if (y + vely > getHeight() - 100) {    //only y values have to change based on end of panel and keystrokes
            vely = -1;
        }
        x = (getWidth()) - 80;    // where paddle should appear (x-value)
        y += vely;    //y-value changes depending on if paddle hits edge of panel
    }

    public void up(int checkValue) {
        if (checkValue == 1)
            vely = -1.5 * 2;    //this doubles the speed of the paddle going up
        else
            vely = -1.5; // y-value decreases if player wants paddle to go up
    }

    public void down(int checkValue) {
        if (checkValue == 1)
            vely = 1.5 * 2; //this doubles the speed of the paddle going down
        else
            vely = 1.5; // y-value decreases if player wants paddle to go up
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {    //when player presses up arrow key
            up(1);       //calls up method
        }
        if (code == KeyEvent.VK_S) {  //when player presses down arrow key
            down(1);     //calls down method
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }


    public static void main(String[] args) {
        JFrame f = new JFrame();
        PongPaddlesUser s = new PongPaddlesUser();
        f.add(s);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
    }

}
