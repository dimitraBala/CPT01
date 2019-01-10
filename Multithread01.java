/**
 * Paints an object and moves it to the left each time the "LEFT" button is clicked
 *  2 objects for players
 * Differentiate by left side or right
 * Different x and y values for paddles
 * point system
 * https://www.geeksforgeeks.org/multithreading-in-java/
 * https://dzone.com/articles/java-thread-tutorial-creating-threads-and-multithr
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Multithread01 {

    private JComponent component;

    public Multithread01(JComponent component) {
        this.component = component;
    }

    // Move the component to its new location. The component will stop moving
    // when it reaches the bounds of its container.
    public void move(int deltaX, int deltaY) {
        int componentWidth = component.getSize().width;
        int componentHeight = component.getSize().height;

        Dimension parentSize = component.getParent().getSize();
        int parentWidth = parentSize.width;
        int parentHeight = parentSize.height;

        // Determine next X position
        int nextX = Math.max(component.getLocation().x + deltaX, 0);
        if (nextX + componentWidth > parentWidth) {
            nextX = parentWidth - componentWidth;
        }

        // Determine next Y position
        int nextY = Math.max(component.getLocation().y + deltaY, 0);
        if (nextY + componentHeight > parentHeight) {
            nextY = parentHeight - componentHeight;
        }

        // Move the component
        component.setLocation(nextX, nextY);
    }

    private class MotionAction extends AbstractAction implements ActionListener {
        private int deltaX;
        private int deltaY;

        public MotionAction(String name, int deltaX, int deltaY) {
            super(name);
            this.deltaX = deltaX;
            this.deltaY = deltaY;

        }

        public void actionPerformed(ActionEvent e) {
            move(deltaX, deltaY);
        }
    }

    public MotionAction addAction(String name, int deltaX, int deltaY) {
        MotionAction action = new MotionAction(name, deltaX, deltaX);
        KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(pressedKeyStroke, name);
        component.getActionMap().put(name, action);
        return action;
    }

    private static JButton addMotionSupport(JComponent component) {
        int delta = 3;
        Multithread01 motion = new Multithread01(component);
        motion.addAction("LEFT", -delta, 0);
        motion.addAction("RIGHT", delta, 0);
        motion.addAction("UP", 0, -delta);
        motion.addAction("DOWN", 0, delta);

        Action left = component.getActionMap().get("LEFT");
        return new JButton(left);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Create a panel with a component to be moved
                JPanel content = new JPanel();
                content.setLayout(null);

                JLabel component = new JLabel(new ColorIcon(Color.BLUE,40, 40));
                component.setSize(component.getPreferredSize());
                component.setLocation(100, 100);
                content.add(component);

                JButton left = addMotionSupport(component);

                JFrame.setDefaultLookAndFeelDecorated(true);
                JFrame frame = new JFrame("Multithread01 Frame");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(content);
                frame.add(left, BorderLayout.SOUTH);
                frame.setSize(600, 600);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }

    static class ColorIcon implements Icon {
        private Color color;
        private int width;
        private int height;

        public ColorIcon (Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int getIconWidth() {
            return width;
        }

        public int getIconHeight() {
            return height;
        }

        public void paintIcon (Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
    }
}

