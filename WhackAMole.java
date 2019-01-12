import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.Random;
import javax.sound.sampled.*;

import javax.swing.*;


public class WhackAMole {

    public static void main(String[] args) {
        Game runGame = new Game();
        runGame.runWhackAMole();
    }
}

/**
 * TODO: BRAINSTORM AD IDEAS
 */


class Game implements ActionListener {

    //Buttons
    private JButton startButton, pauseButton, resetButton;    //JButton that will start game
    private JButton[] buttons;      //The array of buttons used for whack a mole

    //Text
    private JLabel timeLabel, scoreLabel, strikeLabel;   //the JLabels for the words "time", and "score"
    private JLabel title;
    private JTextArea timeArea, scoreArea, strikeArea; //the text that will contain the time remaining and score

    private JFrame parent;


    //Info
    private static Random r = new Random(); //a global random creator
    private int moleWidth = 99;    //the width of mole hitbox
    private int moleHeight = 99;  // the height of mole hitbox
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
    private boolean isMoleHit = false;  //boolean determining whether the mole is clicked
    private boolean resetCheck = false;

    Icon playIcon = new ImageIcon("img/play.png");
    Icon pauseIcon = new ImageIcon("img/pause.png");
    Icon resetIcon = new ImageIcon("img/reset.png");
    Icon moleOff = new ImageIcon("img/moleOff.png");
    Icon moleOn = new ImageIcon("img/moleOn.png");


    public void runWhackAMole() {

        //starting x and y positions
        int xval = xPos;
        int yval = yPos + 50;

        Color bgColor = new Color(49, 148, 50);

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

        //sets background image

        //start button
        panel.setBackground(bgColor);
        startButton = new JButton(playIcon);
        startButton.setBounds(10, 10, 30, 30); //creates the size and sets position of button
        panel.add(startButton);
        startButton.addActionListener(this);

        //pause button
        pauseButton = new JButton(pauseIcon);
        pauseButton.setBounds(50, 10, 30, 30);
        pauseButton.setEnabled(false);
        pauseButton.setOpaque(true);
        panel.add(pauseButton);
        pauseButton.addActionListener(this);

        title = new JLabel("WHACK-A-MOLE!");
        title.setBounds(200, -30, 400, 100);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        panel.add(title);

        //reset button
        resetButton = new JButton(resetIcon);
        resetButton.setBounds(90, 10, 30, 30);
        resetButton.setEnabled(false);
        resetButton.setOpaque(true);
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
        timeArea.setBackground(bgColor);
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
        strikeArea.setBackground(bgColor);
        panel.add(strikeArea);
        strikeArea.setVisible(true);

        //score value text
        scoreArea = new JTextArea();
        scoreArea.setText("0");
        scoreArea.setBounds(160, 500, 50, 20);
        scoreArea.setEditable(false);
        scoreArea.setBackground(bgColor);
        panel.add(scoreArea);
        scoreArea.setVisible(true);


        //initializing the array for the moles

        buttons = new JButton[9];

        int num = 0;
        //Nested for loop for CREATING and FORMATTING the buttons
        for (int i = 1; i <= 3; i++) {

            for (int j = 1; j <= 3; j++) {

                buttons[num] = new JButton();
                buttons[num].setBounds(xval, yval, moleWidth, moleHeight);
                buttons[num].setBorder(BorderFactory.createEmptyBorder());
                buttons[num].setIcon(moleOff);
                panel.add(buttons[num]);
                buttons[num].addActionListener(this);
                buttons[num].setEnabled(true);


                num++;
                xval += 150;
            }

            yval += 120;
            xval = xPos;

        }

        frame.setContentPane(panel);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton) {
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

                            if (resetCheck) {
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

                /**
                 * THREAD FOR THE MOLES
                 */
                @Override
                public void run() {

                    //Main game code
                    while (time > -1 && pauseCheck == 0) {

                        try {

                            moleNumber = r.nextInt(buttons.length);
                            System.out.println(moleNumber);
                            buttons[moleNumber].setEnabled(true);
                            buttons[moleNumber].setIcon(moleOn);

                            Thread.sleep(levelMode);


                            if (!isMoleHit && !imJoking) {
                                strike++;
                                strikeArea.setText("" + strike);
                            }

                            if (resetCheck) {
                                reset();
                                break;
                            }
                            buttons[moleNumber].setIcon(moleOff);
                            isMoleHit = false;

                            if (strike == 3) {
                                time = 0;
                                JOptionPane.showMessageDialog(parent, "YOU LOST! YOUR SCORE IS " + score);
                                reset();
                                break;

                            }

                            if (time == 0) {
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

        if (e.getSource() == resetButton) {
            imJoking = true;
            reset();
        }

        if (e.getSource() == pauseButton) {
            pause();
        }

        try {
            if (e.getSource() == buttons[moleNumber]) {
                if (!isMoleHit) {
                    score++;
                    scoreArea.setText("" + score);
                    isMoleHit = true;
                    buttons[moleNumber].setIcon(moleOff);
                } else {
                    System.out.println("Mole already hit -- else statement reached");
                }
            }
        }catch(Exception ex){
            //this error shouldn't occur
        }



    }


    private void pause() {
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        resetButton.setEnabled(true);
        pauseCheck = 1;
        imJoking = true;

    }

    private void reset() {
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

