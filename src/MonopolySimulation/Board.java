package MonopolySimulation;
/****************************************************
Group 5
Julian Cloward, Steven Benjamin, Theodore Tran
CS-2430-502 Spring 2026
Programming Project 4

This class contains a simulated board and card decks 
which allows you to step through each turn a player 
has and interactivly draw cards and track player 
positions.
@author Julian Cloward
*****************************************************/

public class Board {

    private String[] positonNames = {
        "GO", "Property", "Chest", "Property", "Deposit", "Railroad", "Property", "Chance", "Property", "Property",
        "Jail", "Property", "Utility", "Property", "property", "Railroad", "Property", "Chest", "Property", "Property",
        "Free Parking", "Property", "Chance", "Property", "Property", "Railroad", "Property", "Property", "Utility", "Property",
        "Go To Jail", "Property", "Property", "Chest", "Property", "Railroad", "Chance", "Property", "Deposit", "Property"
    };

    private int[] board = new int[40];
    public final int GO_INDEX;
    public final int JAIL_INDEX;
    public final int GO_TO_JAIL_INDEX;
    public final int NUM_SQUARES;

    public Board() {
        GO_INDEX = 0;
        JAIL_INDEX = 10;
        GO_TO_JAIL_INDEX = 30;
        NUM_SQUARES = 40;
    }

    /**
     * Returns the name of the selected location
     * 
     * @param index The index of where the location is
     * @return the name of the tile.
     */
    public String getSquareName(int index) {
        if (index > NUM_SQUARES) {
            throw new IndexOutOfBoundsException("index " + index + " out of bounds for size " + NUM_SQUARES);
        }
        return positonNames[index];
    }

    /**
     * increments the board landing count for the given index
     * 
     * @param index The index of where the the current position is
     */
    public void incrementLanding(int index) {
        if (index > NUM_SQUARES) {
            throw new IndexOutOfBoundsException("index " + index + " out of bounds for size " + NUM_SQUARES);
        }
        board[index]++;
    }

    /**
     * Returns the total number of landing locations for each tile
     * 
     * @return board
     */
    public int[] getLandingCounts() {
        return board;
    }

    /**
     * Obtains the index of the nearest Railroad location
     * 
     * @param from The postion of the player or location of where the card was drawn
     * @return the index for the board position of the next Railroad location
     */
    public int nearestRailroad(int from) {
        if (from > NUM_SQUARES) {
            throw new IndexOutOfBoundsException("index " + from + " out of bounds for size " + NUM_SQUARES);
        }
        int pos = from + 1;
        while (!positonNames[pos++].equals("Railroad"));
        return pos - 1;
    }

    /**
     * Obtains the index of the nearest Utility location
     * 
     * @param from THe position of the player or location of where the card was drawn
     * @return the index for the board position of the next Utility location
     */
    public int nearestUtility(int from) {
        if (from > NUM_SQUARES) {
            throw new IndexOutOfBoundsException("index " + from + " out of bounds for size " + NUM_SQUARES);
        }
        int pos = from + 1;
        while (!positonNames[pos++].equals("Railroad"));
        return pos - 1;
    }
}