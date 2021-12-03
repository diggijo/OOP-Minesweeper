package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame implements ActionListener {
    static int timePassed=0;
    JMenu gameMenu;
    JMenu helpMenu;
    JMenuItem item=null;
    JPanel scoreBoard;
    JTextField timer;
    static JTextField flagsLeft;
    JButton smiley;
    Grid g = new Grid(14,18,40);

    /* ****************************************************
     * Title: How to code a TIMER in Java! | Java Tutorial
     * Author: Soham Govande
     * Site owner/sponsor: youtube.com
     * Date: 2014
     * Code version: edited Dec 31 2014
     * Availability: https://www.youtube.com/watch?v=36jbBSQd3eU (Accessed 27 November 2021)
     * Modified: N/A
     * *****************************************************/
    public static final Timer t = new Timer();
    private final TimerTask task = new TimerTask() {
        public void run() {
            timePassed++;
            timer.setText("" + timePassed);
        }
    };

    public void start(){
        t.scheduleAtFixedRate(task, 0, 1000);
    }

    public Grid getG() {
        return g;
    }

    public void setG(Grid g) {
        this.g = g;
    }

    public static void setFlagsLeft(String newflagsLeft) {
        flagsLeft.setText(newflagsLeft);
    }

    public Main(){
        super("MineSweeper");
        createGameMenu();
        createHelpMenu();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        scoreBoard = new JPanel();
        scoreBoard.setLayout(new GridLayout(1, 3));
        scoreBoard.setSize(720, 50);

        timer = new JTextField("" + timePassed);
        timer.setFont(new Font("Stencil", Font.BOLD, 36));
        timer.setForeground(Color.RED);
        timer.setBackground(Color.BLACK);
        timer.setEditable(false);
        timer.setHorizontalAlignment(SwingConstants.CENTER);

        /* ****************************************************
         * Title: How to resize JLabel ImageIcon?
         * Author: trolologuy
         * Site owner/sponsor: stackoverflow.com
         * Date: 2011
         * Code version: edited Aug 20 2013 at 12:46
         * Availability: https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon (Accessed 23 Nov 2021)
         * Modified: N/A
         * *****************************************************/
        ImageIcon imgSmiley = new ImageIcon(new ImageIcon("Minesweeper/img/smiley.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        smiley = new JButton(imgSmiley);
        smiley.setBackground(Color.LIGHT_GRAY);
        smiley.addActionListener(this);

        flagsLeft = new JTextField("" + g.getTotalBombs());
        flagsLeft.setFont(new Font("Stencil", Font.BOLD, 36));
        flagsLeft.setForeground(Color.RED);
        flagsLeft.setBackground(Color.BLACK);
        flagsLeft.setEditable(false);
        flagsLeft.setHorizontalAlignment(SwingConstants.CENTER);

        scoreBoard.add(flagsLeft);
        scoreBoard.add(smiley);
        scoreBoard.add(timer);

        add(scoreBoard, BorderLayout.NORTH);

        add(getG());
        setSize(800,800);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        Main m = new Main();
        m.start();
    }

    private void createGameMenu() {
        gameMenu = new JMenu("Game");
        String[] itemNames = {"New Game", "Options", "Quit"};

        for (String itemName : itemNames) {
            item = new JMenuItem(itemName);
            item.addActionListener(this);

            gameMenu.add(item);
        }
    }

    private void createHelpMenu() {
        helpMenu = new JMenu("Help");
        String[] itemNames = {"How To Play", "About"};

        for (String itemName : itemNames) {
            item = new JMenuItem(itemName);
            item.addActionListener(this);

            helpMenu.add(item);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("New Game") || e.getActionCommand().equals("")){
            dispose();
            Main m = new Main();
            m.start();
        }
        else if(e.getActionCommand().equals("Options")){
            String[] difficulties = {"Easy", "Medium", "Hard"};
            JComboBox<String> comboBox = new JComboBox<>(difficulties);
            comboBox.setSelectedIndex(1);
            JOptionPane.showMessageDialog(null, comboBox, "Select Difficulty", JOptionPane.QUESTION_MESSAGE);
            if(comboBox.getSelectedIndex()==0)
            {
                dispose();
                setG(new Grid(8,10,10));
                new Main();
            }
            else if(comboBox.getSelectedIndex()==1)
            {
                dispose();
                setG(new Grid(14,18,40));
                new Main();
            }
            else
            {
                dispose();
                setG(new Grid(20,24,99));
                new Main();
            }
        }
        else if(e.getActionCommand().equals("Quit")){
            dispose();
            System.exit(0);
        }
        else if(e.getActionCommand().equals("How To Play")){
            JOptionPane.showMessageDialog(null, """
                    Rules:\s
                    Each Minesweeper game starts out with a grid of unmarked squares.
                    After clicking one of these squares, some of the squares will disappear,\s
                    some will remain blank, and some will have numbers on them,
                    indicating the number of bombs nearby. It's your job to use the numbers
                    to figure out which of the blank squares have mines and which are safe to click.

                    Some Terms:
                    Flag: Right Click to put a flag in a zone when you have confirmed that there is a mine.
                    Smiley face: Click it if you want to reset the game,""", "How To Play", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getActionCommand().equals("About")){
            JOptionPane.showMessageDialog(null, "About:  \nJoseph Diggins \nT00172862\nMineSweeper \nJava OOP Mini-Project 2021 \nVersion 0.1", "About", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

