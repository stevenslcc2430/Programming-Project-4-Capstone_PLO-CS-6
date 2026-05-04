package MonopolySimulation;

/****************************************************
Group 5
Julian Cloward, Steven Benjamin, Theodore Tran
CS-2430-502 Spring 2026
Programming Project 4

Contains the two different decks of cards and allows 
for drawing and discarding.
@author Julian Cloward
*****************************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class Deck {
    private List<IntUnaryOperator> chestCards = new ArrayList<>();
    private List<IntUnaryOperator> chanceCards = new ArrayList<>();

    private IntUnaryOperator card;

    private int chestStackIndex;
    private int chanceStackIndex;

    public int GET_OUT_OF_JAIL;
    public int NO_MOVE;

    private Board board;

    public Deck(Board board) {
        this.board = board;

        buildChestCards();
        
        buildChanceCards();

        // Shuffle decks
        Collections.shuffle(chanceCards);
        Collections.shuffle(chestCards);
    }

    /**
     * Builds the chestCards list including int manipulation for position after card affect.
     */
    private void buildChestCards() {
        // Chest card deck implementation
        chestCards.add(p -> 0);  // Advance to GO
        chestCards.add(p -> p);  // Bank error in your favor
        chestCards.add(p -> p);  // Doctor's fee
        chestCards.add(p -> p);  // From sales of stock you get $50
        chestCards.add(p -> {GET_OUT_OF_JAIL--;return p;});  // Get Out of Jail Free
        chestCards.add(p -> 10); // Go to Jail
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
    }

    /**
     * Creates the chanceCards list and includes data manipulation
     */
    private void buildChanceCards() {
        // Chance card deck implementation
        chanceCards.add(p -> 39); // Advance to Boardwalk
        chanceCards.add(p ->  0); // Advance to GO
        chanceCards.add(p -> 24); // Advance to Illinois Avenue
        chanceCards.add(p -> 11); // Advance to St. Charles Palace
        chanceCards.add(p -> p);  // Bank pays you dividend of $50
        chanceCards.add(p -> {GET_OUT_OF_JAIL--;return p;});  // Get Out of Jail Free
        chanceCards.add(p -> (p - 3) % 40); // Go Back 3 Spaces
        chanceCards.add(p -> {return 10;}); // Go to Jail
        chanceCards.add(p -> p);  // Make general repairs on all your property
        chanceCards.add(p -> p);  // Speeding fine
        chanceCards.add(p -> 5);  // Take a trip to Reading Railroad
        chanceCards.add(p -> p);  // You have been elected chairman of the Board.
        chanceCards.add(p -> p);  // Your building loan matures.
        chanceCards.add(p -> {    // Advance to the nearest Railroad
            int pos = p + 1;
            while (!!board.getSquareName(pos++).equals("Railroad"));
            return pos - 1;
        });
        chanceCards.add(p -> {    // Advance to the nearest Utility
            int pos = p + 1;
            while (!board.getSquareName(pos++).equals("Utility"));
            return pos - 1;
        });
    }
    
    /**
     * Draws a card if the current position is either a Chest space or a Chance space and returns the next position.
     * 
     * @param currentPosition   The current position of the player
     * @return The resulting location for the player after the card has been drawn or no card was drawn.
     */
    public int draw(int currentPosition) {
        if (board.getSquareName(currentPosition).equals("Chest")) {
            card = chestCards.get(chestStackIndex++);
            if (chestStackIndex == 40) {
                chestStackIndex = 0;
                Collections.shuffle(chestCards);
            }
        }
        if (board.getSquareName(currentPosition).equals("Chance")) {
            card = chanceCards.get(chanceStackIndex++);
            if (chanceStackIndex == 40) {
                chanceStackIndex = 0;
                Collections.shuffle(chestCards);
            }
        }
        return card.applyAsInt(currentPosition);
    }

    public IntUnaryOperator returnCard() {
        return card;
    }
}
