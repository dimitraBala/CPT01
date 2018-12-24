package CompSciCPT;

import javax.swing.*;
import java.awt.*;

public class BBV2 extends JPanel {

    //variables for ball
    int x = 0, y = 0;
    int anglex = 1, angley = 1;


    // ball moving constantly
    private void move() {
        if (x + anglex < 0) {
            anglex = 1;
        } else if (x + anglex > getWidth() - 50) {
            anglex = -1;
        } else if (y + angley < 0) {
            angley = 1;
        } else if (y + angley > getHeight() - 50) {
            angley = -1;
        }
        x = x + anglex;
        y = y + angley;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval(x, y, 50, 50);  //size of ball
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Bouncing Ball!");
        BBV2 BouncingBallz = new BBV2();
        frame.add(BouncingBallz);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            BouncingBallz.move();
            BouncingBallz.repaint();
            Thread.sleep(3); // speed of ball movement
        }
    }

}
