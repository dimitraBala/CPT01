import java.awt.*;      // used for graphics and colour
import java.awt.event.*;
import javax.swing.*;

public class StartScreen01 implements ActionListener {
    final static String LABEL_TEXT = "Click \"START\" to begin";
    JFrame frame;
    JPanel contentPane;
    JLabel label;
    JButton button;

    public StartScreen01() {
        /* Create and set up the frame */
        frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Create a content pane with a BoxLayout and empty borders */
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(2,0, 10, 5));
        contentPane.setBorder(BorderFactory.createEmptyBorder(600, 950, 600 , 950));

        /* Create and add label that is centered and has empty borders */
        label = new JLabel(LABEL_TEXT);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        contentPane.add(label);

        /* Create and add button that is centered */
        button = new JButton("START");
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.setActionCommand("START");
        button.addActionListener(this);
        contentPane.add(button);

        /* Add content pane to frame */
        frame.setContentPane(contentPane);

        /* Size and then display the frame */
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Handle button click action event
     * pre: Action event is START
     * post: Game begins after the button is clicked
     */
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();
        if (eventName.equals("START")) {
            //begin game
        } else {
            label.setText(LABEL_TEXT);
            button.setText("START");
            button.setActionCommand("START");
        }
    }

    /**
     * Create and show the GUI
     */
    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        StartScreen01 begin = new StartScreen01();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run () {
                runGUI();
            }
        });
    }
}
