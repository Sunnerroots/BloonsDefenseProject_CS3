
//(c) A+ Computer Science
//www.apluscompsci.com
//Name - Sunrut
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

//Creates a list of the board positions
public class Path
{
    private Cell[][] grid;

    //Constructors
    public Grid()
    {
        setSize(0,0);
    }
    public Grid(int rows, int cols)
    {
        setSize(rows,cols);
    }

    //Getter and Setter methods
    public void setSize(int rows, int cols)
    {
        grid = new Cell[rows][cols];
    }
    public void setSpot(int row,int col, Cell val)
    {
        grid[row][col] = val;

    }
    public Cell getSpot(int row, int col)
    {
        return grid[row][col];
    }
    public int getNumRows()
    {
        return grid.length;
    }
    public int getNumCols()
    {
        return grid[0].length;
    }

    //Draws each cell on the window
    public boolean drawGrid(Graphics window)
    {
        boolean full=true;
        for(int i = 0; i<grid.length;i++)
        {
            for(int x = 0; x<grid[i].length; x++)
            {
                if (getSpot(i,x) != null)
                    getSpot(i,x).draw(window);
                else
                    full = false;
            }
        }
        //for loop for col
        //get current Cell
        //if it is null
        //else
        return full;
    }

    //toString method - Prints each position's toString method
    public String toString()
    {
        String output="";
        for(Cell[] g:grid)
        {
            for(Cell k:g)
            {
                output+=k.toString();
            }
        }
        return output;
    }
}

