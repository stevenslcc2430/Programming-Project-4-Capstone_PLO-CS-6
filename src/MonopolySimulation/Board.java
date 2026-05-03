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

    public Board() {

    }

    public String getSquareName(int index) {
        return positonNames[index];
    }

    public void incrementLanding(int index) {
        board[index]++;
    }

    public int[] getLandingCounts() {
        return board;
    }

    public int nearestRailroad(int from) {
        int pos = from + 1;
        while (!positonNames[pos++].equals("Railroad"));
        return pos - 1;
    }

    public int nearestUtility(int from) {
        int pos = from + 1;
        while (!positonNames[pos++].equals("Railroad"));
        return pos - 1;
    }
}