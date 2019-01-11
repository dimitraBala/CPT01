import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongPaddlesUser extends JPanel implements ActionListener, KeyListener {

    // variables for paddle
    Timer t = new Timer(5, this);
    double x = 0, y = 0, vely = 0; // x-value remains the same for both paddles
    double y2 = 0, vely2 = 0; // variables with "2" at the end are for paddle 2

    public PongPaddlesUser() {
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    // creating graphic for paddles
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D paddle1 = (Graphics2D) g;
        Graphics2D paddle2 = (Graphics2D) g;
        paddle1.fill(new Rectangle.Double(x, y, 30, 100)); // paddle 1
        paddle2.fill(new Rectangle.Double(40, y2, 30, 100)); // paddle 2
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
        if (y + vely < 0) {
            vely = 1;
        } else if (y + vely > getHeight() - 100) {    //only y values have to change based on end of panel and keystrokes
            vely = -1;
        }
        if (y2 + vely2 < 0) {
            vely2 = 1;
        } else if (y2 + vely2 > getHeight() - 100) { //vely2 operates the same as vely, but was needed to make the paddles move separately
            vely2 = -1;
        }
        x = (getWidth()) - 80;    // where paddle should appear (x-value)
        y += vely;    //y-value changes depending on if paddle hits edge of panel
        y2 += vely2;
    }

    public void up(int checkValue) {
        if (checkValue == 1) {
            vely = -1.5 * 2;    //this doubles the speed of the paddle going up
        } else {
            vely = -1.5; // y-value decreases if player wants paddle to go up
        }
    }

    public void up2 (int checkValue) {
        if (checkValue == 1) {
            vely2 = -1.5 * 2;
        } else {
            vely2 = -1.5;
        }
    }

    public void down(int checkValue) {
        if (checkValue == 1)
            vely = 1.5 * 2; //this doubles the speed of the paddle going down
        else
            vely = 1.5; // y-value decreases if player wants paddle to go up
    }

    public void down2(int checkValue) {
        if (checkValue == 1) {
            vely2 = 1.5 * 2; //this doubles the speed of the paddle going down
        } else {
            vely2 = 1.5; // y-value decreases if player wants paddle to go up
        }
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {    //when player presses "W" key
            up(1);       //calls up method
        }
        if (code == KeyEvent.VK_S) {  //when player presses "S" key
            down(1);     //calls down method
        }
        if (code == KeyEvent.VK_UP) { //when player presses up arrow key
            up2(1); //calls up method for paddle 2
        }
        if (code == KeyEvent.VK_DOWN) { //when player presses down arrow key
            down2(1); //calls down method for paddle 2
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