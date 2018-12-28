package CompSciCPT;

import javax.swing.*;
import java.awt.*;

//paddles that runs randomly
// not controllable by arrow keys (will post one that can be controlled by keys)
//December 28 2018
public class PongPaddles extends JPanel {

    // variables for paddle
    int x1 = 0, x2 = 0, y = 0;
    int anglex = 1, angley = 1;

    // paddle moving constantly
    private void move() {
        if (y + angley < 0) {
            angley = 1;
        } else if (y + angley > getHeight() - 100) {    //only y values have to change
            angley = -1;
        }
        x1 = 50;        //where first panel will appear
        x2 =  (getWidth()) -80;    // where second panel should appear
        y = y + angley;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(x1, y, 30, 100);  //size of paddle #1
        g.fillRect(x2,y,30,100); // paddle #2  (remove this line for separate code for letting user control paddle with keys)
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Paddles");
        PongPaddles Paddle = new PongPaddles();
        frame.add(Paddle);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true) {
            Paddle.move();
            Paddle.repaint();
            Thread.sleep(3); // speed of paddle movement
        }
    }

}
