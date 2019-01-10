import java.awt.*;      // used for graphics and colour
import java.awt.event.*;
import javax.swing.*;

public class StartScreen02 implements ActionListener {

        final static String LABEL_TEXT = "Which game would you like to play?";
        JFrame frame;
        JPanel contentPane;
        JLabel label;
        JButton button1, button2;

        public StartScreen02() {
            /* Create and set up the frame */
            frame = new JFrame("MAIN MENU");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            /* Create a content pane with empty borders */
            contentPane = new JPanel();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.setBorder(BorderFactory.createEmptyBorder(700, 700, 700 , 700));

            /* Create and add label that is centered and has empty borders */
            label = new JLabel(LABEL_TEXT);
            label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            label.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
            contentPane.add(label);

            /* Create and add buttons */
            // button1
            button1 = new JButton("WHACK-A-MOLE");
            button1.setAlignmentX(JButton.CENTER_ALIGNMENT);
            button1.setActionCommand("WHACK-A-MOLE");
            button1.addActionListener(this);
            contentPane.add(button1);
            // button2
            button2 = new JButton("Pong");
            button2.setAlignmentX(JButton.CENTER_ALIGNMENT);
            button2.setActionCommand("Pong");
            button2.addActionListener(this);
            contentPane.add(button2);

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
            if (eventName.equals("WHACK-A-MOLE")) {
                label.setText("Begin game: WHACK-A-MOLE");
            } else if (eventName.equals("Pong")){
                label.setText("Begin game: PONG");
            }
        }

        /**
         * Create and show the GUI
         */
        private static void runGUI() {
            JFrame.setDefaultLookAndFeelDecorated(true);
            StartScreen02 begin = new StartScreen02();
        }

        public static void main(String[] args) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run () {
                    runGUI();
                }
            });
        }
    }
