package MonopolySimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.IntUnaryOperator;


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

    private String[] posiitonNames = {
        "GO", "Property", "Chest", "Property", "Deposit", "Railroad", "Property", "Chance", "Property", "Property",
        "Jail", "Property", "Utility", "Property", "property", "Railroad", "Property", "Chest", "Property", "Property",
        "Free Parking", "Property", "Chance", "Property", "Property", "Railroad", "Property", "Property", "Utility", "Property",
        "Go To Jail", "Property", "Property", "Chest", "Property", "Railroad", "Chance", "Property", "Deposit", "Property"
    };
    private int[] board = new int[40];
    private int consecutiveRolls;
    private int position;

    private List<IntUnaryOperator> chestCards = new ArrayList<>();
    private List<IntUnaryOperator> chanceCards = new ArrayList<>();

    private int chestStackIndex;
    private int chanceStackIndex;

    private boolean inJail;
    private int jailFreeCards;

    public Board() {

        // Chance card deck implementation
        chanceCards.add(p -> 39); // Advance to Boardwalk
        chanceCards.add(p ->  0); // Advance to GO
        chanceCards.add(p -> 24); // Advance to Illinois Avenue
        chanceCards.add(p -> 11); // Advance to St. Charles Palace
        chanceCards.add(p -> p);  // Bank pays you dividend of $50
        chanceCards.add(p -> {jailFreeCards++; return p;});  // Get Out of Jail Free
        chanceCards.add(p -> (p - 3) % 40); // Go Back 3 Spaces
        chanceCards.add(p -> {inJail = true; return 10;}); // Go to Jail
        chanceCards.add(p -> p);  // Make general repairs on all your property
        chanceCards.add(p -> p);  // Speeding fine
        chanceCards.add(p -> 5);  // Take a trip to Reading Railroad
        chanceCards.add(p -> p);  // You have been elected chairman of the Board.
        chanceCards.add(p -> p);  // Your building loan matures.
        chanceCards.add(p -> {    // Advance to the nearest Railroad
            int pos = p + 1;
            while (!posiitonNames[pos++].equals("Railroad"));
            return pos - 1;
        });
        chanceCards.add(p -> {    // Advance to the nearest Utility
            int pos = p + 1;
            while (!posiitonNames[pos++].equals("Utility"));
            return pos - 1;
        });
        
        // Chest card deck implementation
        chestCards.add(p -> 0);  // Advance to GO
        chestCards.add(p -> p);  // Bank error in your favor
        chestCards.add(p -> p);  // Doctor's fee
        chestCards.add(p -> p);  // From sales of stock you get $50
        chestCards.add(p -> {jailFreeCards++; return p;});  // Get Out of Jail Free
        chestCards.add(p -> {inJail = true; return 10;}); // Go to Jail
        chestCards.add(p -> p);  // Holiday fund matures
        chestCards.add(p -> p);  // Income tax refund
        chestCards.add(p -> p);  // It is your birthday
        chestCards.add(p -> p);  // Life insurance matures
        chestCards.add(p -> p);  // Pay hospical fees
        chestCards.add(p -> p);  // Pay school fees
        chestCards.add(p -> p);  // Recieve $25 consultance fee
        chestCards.add(p -> p);  // You are assessed for street reapir
        chestCards.add(p -> p);  // You have won second prize in a beauty contest
        chestCards.add(p -> p);  // You inherit $100

        // Shuffle decks
        Collections.shuffle(chanceCards);
        Collections.shuffle(chestCards);
        
    }

    /**
     * Steps through one turn on the board and checks for 
     */
    public void step() {
        Random rand = new Random();
        int num1 = rand.nextInt(1, 7);
        int num2 = rand.nextInt(1, 7);
        int roll = num1 + num2;

        // Check for doubles & jail
        if (inJail && jailFreeCards > 0) {
            jailFreeCards--;
            inJail = false;
        } else if (num1 == num2 && inJail) {
            inJail = false;
        } else if (num1 == num2 && ++consecutiveRolls == 3) {

            position = 10;
            inJail = true;
            board[position]++;
            return;
        }

        consecutiveRolls = 0;

        position += roll;
        position %= 40;

        if (posiitonNames[position].equals("Chance")) {
            position = chanceCards.get(chanceStackIndex).applyAsInt(position);
            if (chanceStackIndex == 16) {
                Collections.shuffle(chanceCards);
                chanceStackIndex = 0;
            }
        }
        else if (posiitonNames[position].equals("Chest")) {
            position = chestCards.get(chestStackIndex).applyAsInt(position);
            if (chestStackIndex == 16) {
                Collections.shuffle(chestCards);
                chestStackIndex = 0;
            }
        }

        board[position]++;
        
    }
}