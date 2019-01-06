import java.awt.Font;
import java.awt.Cursor;
import  java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.Icon;

public class WhackAMole {

    public static void main(String[] args) {
        Game runGame = new Game();
        runGame.runWhackAMole();
    }
}

/**
 * TODO:
 *
 */
class Game implements ActionListener {

    //Buttons
    private JButton startButton, pauseButton, resetButton;    //JButton that will start game
    private JButton[] buttons;      //The array of buttons used for whack a mole

    private JFrame parent;

    //Text
    private JLabel timeLabel, scoreLabel, strikeLabel;   //the JLabels for the words "time", and "score"
    private JTextArea timeArea, scoreArea, strikeArea; //the text that will contain the time remaining and score

    //Info
    private static Random r = new Random(); //a global random creator
    private int moleWidth = 100;    //the width of mole hitbox
    private int moleHeight = 100;  // the height of mole hitbox
    private int xPos = 100;    //the starting x positions for the grid
    private int yPos = 50;   //the starting y positions of the grid
    private int time = 60; //60 seconds for the timer
    private int score = 0; //variable storing the score of the player
    private int pauseCheck = 0; //checks pause (0 = no pause, 1 means pause)
    private int moleNumber = -1; //rng moleNumber
    private int levelMode = 1000; //variable controlling the amount of seconds the mole stays up (controls difficulty for later)
    private int strike = 0; //3 strikes means ur out

    private boolean imJoking = false; //this fixes the bug that occurs the thread to continue after pause/resetting and trigger the
                                      //strike amount to increase
    private boolean isMoleHit = false;  //boolean
    private boolean resetCheck = false;

    public void runWhackAMole() {

        Icon playIcon = new ImageIcon("img/play.png");
        Icon pauseIcon = new ImageIcon("img/pause.png");
        Icon resetIcon = new ImageIcon("img/reset.png");

        //starting x and y positions
        int xval = xPos;
        int yval = yPos;


        //creating the JFrame window
        JFrame frame = new JFrame("CAPTCHA Whack-a-Mole");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        frame.setCursor(cursor);

        JFrame loseFrame = new JFrame("YOU LOST!");
        loseFrame.setSize(200, 200);
        loseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JPanel to add the components on
        JPanel panel = new JPanel();



        //used so setBounds() can be actually used
        panel.setLayout(null);

        //start button
        startButton = new JButton(playIcon);
        startButton.setActionCommand("start");
        startButton.setBounds(10, 10, 30, 30); //creates the size and sets position of button
        panel.add(startButton);
        startButton.addActionListener(this);

        //pause button
        pauseButton = new JButton(pauseIcon);
        pauseButton.setActionCommand("pause");
        pauseButton.setBounds(50, 10, 30, 30);
        pauseButton.setEnabled(false);
        panel.add(pauseButton);
        pauseButton.addActionListener(this);

        //reset button
        resetButton = new JButton(resetIcon);
        resetButton.setActionCommand("reset");
        resetButton.setBounds(90, 10, 30, 30);
        resetButton.setEnabled(false);
        panel.add(resetButton);
        resetButton.addActionListener(this);

        //time left text
        timeLabel = new JLabel("Time Left:");
        timeLabel.setBounds(420, 500, 80, 20);
        timeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        panel.add(timeLabel);

        //time value text
        timeArea = new JTextArea();
        timeArea.setText("0");
        timeArea.setBounds(490, 500, 80, 20);
        timeArea.setEditable(false);
        panel.add(timeArea);
        timeArea.setVisible(true);

        //score label text
        scoreLabel = new JLabel("Score:");
        scoreLabel.setBounds(100, 500, 80, 20);
        scoreLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        panel.add(scoreLabel);

        //strike label text
        strikeLabel = new JLabel("Strikes:");
        strikeLabel.setBounds(260, 500, 80, 20);
        strikeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        panel.add(strikeLabel);

        strikeArea = new JTextArea();
        strikeArea.setText("0");
        strikeArea.setBounds(330, 500, 80, 20);
        strikeArea.setEditable(false);
        panel.add(strikeArea);
        strikeArea.setVisible(true);

        //score value text
        scoreArea = new JTextArea();
        scoreArea.setText("0");
        scoreArea.setBounds(160, 500, 50, 20);
        scoreArea.setEditable(false);
        panel.add(scoreArea);
        scoreArea.setVisible(true);

        //initalizing the array for the moles
        buttons = new JButton[9];



        Icon warnIcon = new ImageIcon("img/zelda.gif");


        int num = 0;
        //Nested for loop for CREATING and FORMATTING the
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                buttons[num] = new JButton(warnIcon);
                buttons[num].setBounds(xval, yval, moleWidth, moleHeight);
                buttons[num].setBackground(Color.red);
                buttons[num].setActionCommand("hit");
                panel.add(buttons[num]);
                buttons[num].addActionListener(this);
                buttons[num].setEnabled(false);

                num++;
                xval += 160;
            }

            yval += 170;
            xval = xPos;

        }

        frame.setContentPane(panel);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String eventName = e.getActionCommand();


        if (eventName.equals("start")) {
            System.out.println("Game started");
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resetCheck = false;
            pauseCheck = 0;
            imJoking = false;

            Thread timerThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (time > -1 && pauseCheck == 0) {
                        try {
                            timeArea.setText("" + time);
                            time--;
                            Thread.sleep(levelMode);

                            if(resetCheck){
                                startButton.setEnabled(true);
                                pauseButton.setEnabled(false);
                                break;
                            }
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            timerThread.start();

            Thread moleThread = new Thread(new Runnable() {
                Random r = new Random();

                /**
                 * THREAD FOR THE MOLES
                 */
                @Override
                public void run() {
                    JFrame parent = new JFrame();

                    while (time > -1 && pauseCheck == 0) {

                        try {
                            moleNumber = r.nextInt(buttons.length - 1);
                            System.out.println(moleNumber);
                            buttons[moleNumber].setEnabled(true);
                            buttons[moleNumber].setBackground(Color.green);

                            Thread.sleep(levelMode);

                            if(!isMoleHit && !imJoking){
                                strike++;
                                strikeArea.setText(""  + strike);
                            }

                            if(resetCheck){
                                reset();
                                break;
                            }
                            buttons[moleNumber].setBackground(Color.red);
                            buttons[moleNumber].setEnabled(false);
                            isMoleHit = false;

                            if(strike == 3){
                                JOptionPane.showMessageDialog(parent, "YOU LOST!");
                                reset();
                                break;

                            }

                            if(time == 0){
                                JOptionPane.showMessageDialog(parent, "GAME OVER! YOUR SCORE IS " + score);
                                reset();
                                break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            moleThread.start();
        }

        if (eventName.equals("reset")) {
            imJoking = true;
           reset();
        }

        if (eventName.equals("pause")) {
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            resetButton.setEnabled(true);
            pauseCheck = 1;
            imJoking = true;
        }

        if (eventName.equals("hit")) {
            if (!isMoleHit) {
                score++;
                scoreArea.setText("" + score);
                isMoleHit = true;
                buttons[moleNumber].setBackground(Color.red);
            } else {
                System.out.println("Mole already hit -- else statement reached");
            }
        }

    }

    private void reset(){
        resetCheck = true;
        resetButton.setEnabled(false);
        score = 0;
        scoreArea.setText("" + score);
        time = 60;
        timeArea.setText("" + time);
        strike = 0;
        strikeArea.setText("" + strike);
        pauseCheck = 0;
    }
}

