import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Maze {
    private static final byte OPEN = 0;
    private static final byte WALL = 1;
    private static final byte VISITED = 2;

    private int rows, columns;
    private byte[][] grid;

    ArrayList<Integer> rowOffsets = new ArrayList<Integer>(Arrays.asList(-1, 1, 0, 0));
    ArrayList<Integer> colOffsets = new ArrayList<Integer>(Arrays.asList(0, 0, -1, 1));

    // A constructor that makes a maze of the given size
    public Maze(int r, int c) {
        rows = r;
        columns = c;
        grid = new byte[r][c];
        for (r = 0; r < rows; r++) {
            for (c = 0; c < columns; c++) {
                grid[r][c] = WALL;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    // Return true if a wall is at the given location, otherwise false
    public boolean wallAt(int r, int c) {

        return grid[r][c] == WALL;
    }

    // Return true if this location has been visited, otherwise false
    public boolean visitedAt(int r, int c) {
        return grid[r][c] == VISITED;
    }

    // Put a visit marker at the given location
    public void placeVisitAt(int r, int c) {
        grid[r][c] = VISITED;
    }

    // Remove a visit marker from the given location
    public void removeVisitAt(int r, int c) {
        grid[r][c] = OPEN;
    }

    // Put a wall at the given location
    public void placeWallAt(int r, int c) {
        grid[r][c] = WALL;
    }

    // Remove a wall from the given location
    public void removeWallAt(int r, int c) {
        grid[r][c] = 0;
    }

    // Carve out a maze
    public void carve() {
        int startRow = (int) (Math.random() * (rows - 2)) + 1;
        int startCol = (int) (Math.random() * (columns - 2)) + 1;
        carve(startRow, startCol);
    }


    public void carve(int r, int c) {
        int booleanCounter = 0;
        //constrains seem good
        if (((r == 0) || (r >= getRows() - 1) || (c >= getColumns() - 1) || (c == 0))) {
            return;

        }
        //check if it's an open space
        else if ((!(wallAt(r, c)))) {
            return;
        } else {

            if (wallAt(r, c - 1)) {
                booleanCounter++;
            }
            //down
            if (wallAt(r, c + 1)) {
                booleanCounter++;
            }
            if (wallAt(r - 1, c)) {
                booleanCounter++;
            }
            if (wallAt(r + 1, c)) {
                booleanCounter++;
            }


            if (booleanCounter >= 3) {
                //return true;
                grid[r][c] = OPEN;
                ArrayList<Integer> rowOffsets = new ArrayList<Integer>(Arrays.asList(-1, 1, 0, 0));
                ArrayList<Integer> colOffsets = new ArrayList<Integer>(Arrays.asList(0, 0, -1, 1));

                for (int i = 0; i < 4; i++) {
                    Random post = new Random();
                    int randomPostion = post.nextInt(rowOffsets.size());

                    carve(r + rowOffsets.get(randomPostion), c + colOffsets.get(randomPostion));

                    rowOffsets.remove(randomPostion);
                    colOffsets.remove(randomPostion);
                }

            }
        }


        //   }
    }



    // Determine the longest path in the maze from the given start location
    public ArrayList<Point2D> longestPath() {


        ArrayList<Point2D> temp = new ArrayList<>();
        for (int r = 1; r < getRows() - 1; r++) {
            for (int c = 1; c < getColumns() - 1; c++) {
                if (longestPathFrom(r, c).size() > temp.size()) {
                    temp = longestPathFrom(r, c);
                }
            }
        }

        return temp;
    }

    // Determine the longest path in the maze from the given start location
    public ArrayList<Point2D> longestPathFrom(int r, int c) {
        ArrayList<Point2D> path = new ArrayList<Point2D>();


        if (wallAt(r, c) || visitedAt(r, c)) {
            return path;
        }


        placeVisitAt(r, c);

        ArrayList<Point2D> top = longestPathFrom(r - 1, c);
        ArrayList<Point2D> right = longestPathFrom(r, c + 1);
        ArrayList<Point2D> bottom = longestPathFrom(r + 1, c);
        ArrayList<Point2D> left = longestPathFrom(r, c - 1);


        removeVisitAt(r, c);

        int[] numbers = {top.size(), right.size(), bottom.size(), left.size()};

        //temporary list
        ArrayList<Point2D> largestarraylist = new ArrayList<Point2D>();

        int highest = top.size();
        int position = 0;


        for (int i = 0; i < 4; i++) {
            if (numbers[i] > highest) {
                highest = numbers[i];    //finds the arraylist with most point 2d objects
                position = i;  //record it's index in the array **Super Important
            }

        }

        switch (position) {        //**corresponds
            case 0:
                largestarraylist = top;
                break;
            case 1:
                largestarraylist = right;
                break;
            case 2:
                largestarraylist = bottom;
                break;
            case 3:
                largestarraylist = left;
                break;
        }


        for (Point2D t : largestarraylist)
            path.add(t);
        path.add(new Point2D(r, c));

        // Write your code here
        //write your code here but then how would i solve

        return path;
    }
}