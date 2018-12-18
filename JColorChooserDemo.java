
import java.awt.*;        // Using AWT's Graphics and Color
import java.awt.event.*;  // Using AWT's event classes and listener interfaces
import javax.swing.*;     // Using Swing's components and containers

/**
 * An example of using ColorChooser to set the background
 */
@SuppressWarnings("serial")
public class JColorChooserDemo extends JFrame {
    JPanel panel;
    Color bgColor = Color.LIGHT_GRAY;  // Panel's background color

    // Constructor to setup the UI components and event handlers
    public JColorChooserDemo() {
        panel = new JPanel(new BorderLayout());

        JButton btnColor = new JButton("Change Color");
        panel.add(btnColor, BorderLayout.SOUTH);
        btnColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Color color = JColorChooser.showDialog(JColorChooserDemo.this,
                        "Choose a color", bgColor);
                if (color != null) { // new color selected
                    bgColor = color;
                }
                panel.setBackground(bgColor); // change panel's background color
            }
        });

        setContentPane(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("JColorChooser Demo");
        setSize(300, 200);
        setLocationRelativeTo(null);  // center the application window
        setVisible(true);             // show it
    }

    // The entry main() method
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JColorChooserDemo();  // Let the constructor do the job
            }
        });
    }
}