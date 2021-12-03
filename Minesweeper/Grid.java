package Minesweeper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Book.java
/**
 * An instantiable class which defines a Grid.
 * It extends the JPanel class & implements the Mouse Listener interface.
 * @author Joseph Diggins
 * */

public class Grid extends JPanel implements MouseListener {
    Cell c;
    private Cell[][] cells;
    private int rows;
    private int columns;
    private int totalBombs;
    private int flags;
    public int numCellsOpened;
    private final ImageIcon imgBomb = new ImageIcon("Minesweeper/img/bomb.png");
    private final ImageIcon imgUnopened = new ImageIcon(new ImageIcon("Minesweeper/img/unopened.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private final ImageIcon imgFlagged= new ImageIcon(new ImageIcon("Minesweeper/img/flagged.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    public int finishTime;


    /**
     * Grid 3-argument constructor.
     * Calls 3 mutators and a number of methods to
     * initialise the attributes of a Grid object.
     * @param rows numbers of rows for the Grid
     * @param columns number of columns for the Grid
     * @param totalBombs total bombs within the Grid
     * */

    public Grid(int rows, int columns, int totalBombs){
        setLayout(new GridLayout(rows, columns));
        setRows(rows);
        setColumns(columns);
        setTotalBombs(totalBombs);
        setCells(new Cell[rows][columns]);
        createCells();
        setBombs();
        calculateBombs();
        setFlags(totalBombs);
        setNumCellsOpened();
    }

    /**
     * Method to set the x & y co-ordinates of a Cell object.
     * */
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * Method to set the rows of a Grid Object
     * @param rows the number of rows in a Grid
     * */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Method to set the columns of a Grid Object
     * @param columns the number of columns in a Grid
     * */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * Method to get the number of bombs in a Grid Object
     * @return an integer value specifying the number of total bombs
     * */
    public int getTotalBombs() {
        return totalBombs;
    }

    /**
     * Method to set the number of bombs in a Grid Object
     * @param totalBombs the number of bombs in a grid
     * */
    public void setTotalBombs(int totalBombs) {
        this.totalBombs = totalBombs;
    }

    /**
     * Method to set the number of flags left to place in a Grid Object
     * @param flags the number of flags left to place
     * */
    public void setFlags(int flags) {
        this.flags = flags;
    }

    /**
     * Method to set the starting number of cells opened to zero.
     * */
    public void setNumCellsOpened() {
        this.numCellsOpened = 0;
    }

    /**
     * Method to create cells withing a grid using a 2D array.
     */
    public void createCells()
    {
        /* ****************************************************
         * Title: Multidimensional Arrays in Java
         * Author: praneethharsha10
         * Site owner/sponsor: geeksforgeeks.org
         * Date: 2020
         * Code version: edited May 06 2020
         * Availability: https://www.geeksforgeeks.org/multidimensional-arrays-in-java/ (Accessed 20 Nov 2021)
         * Modified: N/A
         * *****************************************************/

        for(int i=0; i < rows; i++)
        {
            for(int j=0; j < columns; j++)
            {
                Cell c = new Cell(i,j);
                add(c);
                c.addMouseListener(this);
                cells[i][j] = c;
                cells[i][j].setIcon(imgUnopened);
                cells[i][j].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    /**
     * Method to randomly places bombs throughout the Grid.
     */
    public void setBombs(){
        int bombs = 0;

        while(bombs<totalBombs)
        {
            int i = (int)(Math.random()*(rows));
            int j = (int)(Math.random()*(columns));
            boolean isBomb = cells[i][j].isBomb();
            if(!isBomb)
            {
                cells[i][j].setBomb(true);
                bombs++;
            }
        }
    }

    /**
     * Method to display all bombs throughout the Grid when the user loses the game.
     */
    public void showAllBombs()
    {
        for(int i=0; i < rows; i++)
        {
            for(int j=0; j < columns; j++)
            {
                if(cells[i][j].isBomb())
                {
                    cells[i][j].setBackground(Color.LIGHT_GRAY);
                    cells[i][j].setIcon(imgBomb);
                }
            }
        }
    }

    /**
     * Method to calculate the amount of bombs neighbouring each of the cells created.
     */
    public void calculateBombs(){
        for(int i=0; i<rows; i++)
        {
            for(int j=0; j<columns; j++)
            {
                if(!cells[i][j].isBomb())
                {
                    int bombsAround=0;

                    if(i > 0 &&  cells[i-1][j].isBomb())
                        bombsAround++;
                    if(i < rows-1 && cells[i+1][j].isBomb())
                        bombsAround++;
                    if(j > 0 && cells[i][j-1].isBomb())
                        bombsAround++;
                    if(j < columns-1 &&  cells[i][j+1].isBomb())
                        bombsAround++;
                    if(i > 0 && j > 0 && cells[i-1][j-1].isBomb())
                        bombsAround++;
                    if(i > 0 && j < columns-1 && cells[i-1][j+1].isBomb())
                        bombsAround++;
                    if(i < rows-1 && j > 0 && cells[i+1][j-1].isBomb())
                        bombsAround++;
                    if(i < rows-1 && j < columns-1 && cells[i+1][j+1].isBomb())
                        bombsAround++;

                    cells[i][j].setBombsAround(bombsAround);
                }
            }
        }
    }

    /**
     * Method to check on each click if the customer has won the game.
     */
    public void checkWin() {
        if((numCellsOpened >= (rows*columns - totalBombs)) && flags==0)
        {
            Main.t.cancel();
            JOptionPane.showMessageDialog(null, "Congratulations!! You won in " + Main.timePassed + " seconds!");
            Main.timePassed = finishTime;
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Method to action the code within once the mouse has been left/right clicked.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        c = (Cell)e.getComponent();

        if(e.getButton()==1)
        {
            if(c.isBomb())
            {
                showAllBombs();
                JOptionPane.showMessageDialog(null, "GAME OVER!", "GAME OVER!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                /* ****************************************************
                 * Title: How to usesetEnabledmethodinjavax.swing.JButton
                 * Author: tabnine
                 * Site owner/sponsor: tabnine.com
                 * Date: Unknown
                 * Code version: Unknown
                 * Availability: https://www.tabnine.com/code/java/methods/javax.swing.JButton/setEnabled (Accessed 26 Nov 2021)
                 * Modified: N/A
                 * *****************************************************/
                if(!c.isFlagged()) // && c.isEnabled())
                {
                    if(c.getBombsAround()==0)
                    {
                        c.setIcon(null);
                        c.setBackground((new Color(160,160,160)));
                        c.setBorder(new LineBorder(Color.GRAY));
                        // c.setEnabled(false);
                    }
                    else
                    {
                        c.setIcon(new ImageIcon(new ImageIcon("Minesweeper/img/" + c.getBombsAround() + ".png").getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH)));
                        //c.setEnabled(false);
                    }
                    numCellsOpened++;
                    checkWin();
                }
            }
        }

        else if(e.getButton()==3)
        {
            if(!c.isFlagged())
            {
                c.setIcon(imgFlagged);
                c.setFlagged(true);
                flags--;
            }
            else
            {
                c.setIcon(imgUnopened);
                c.setFlagged(false);
                flags++;
            }
            Main.setFlagsLeft("" + flags);
            checkWin();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
