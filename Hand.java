/**
 * This class is used for representing a general Hand in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public abstract class Hand extends CardList {
    private CardGamePlayer player; // the player holding this hand

    /**
     * [Constructor] Creates and returns an instance of the Hand class.
     * Hand Class is not designed to be instantiated directly by calling the Hand Constructor.
     * 
     * @param player the player hold this hand
     * @param cards the list of cards to be played
     */
    public Hand(CardGamePlayer player, CardList cards) {
        // Adds player
        this.player = player;

        // Sorts cards
        cards.sort();

        // Adds cards
        for (int i = 0; i < cards.size(); i++) {
            this.addCard(cards.getCard(i));
        }
    }

    /** 
     * Returns the player who holds this hand
     * 
     * @return CardGamePlayer
     */
    public CardGamePlayer getPlayer() {
        return player;
    }

    /** 
     * Counts the number of occurance of card with same rank as input card in the hand.
     * 
     * @param card provides the rank of card to be counted
     * @return number of occurance of cards with same rank as input card in the hand
     */
    public int count(Card card) {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (this.getCard(i).rank == card.rank) {
                count++;
            }
        }
        return count;
    }

    /** 
     * Returns the top card of this hand.
     * Default behaviour: Take the largest card in the hand as top card.
     * 
     * @return top card of this hand
     */
    public Card getTopCard() {
        // Sort to ensure last card is biggest card
        this.sort();
        return this.getCard(this.size() - 1);
    }

    /**
     * Checks if this hand beats a specified hand.
     * Default behaviour: beats only if both hands are same type and this hand has larger top card.
     * 
     * @param hand hand to be compared with
     * @return true if this hand can beat the specified hand; otherwise false
     */
    public boolean beats(Hand hand) {
        Card thisTopCard = this.getTopCard();
        // Converts to BigTwoCard to use the modified compareTo() for BigTwoCard game
        BigTwoCard thisTopBigTwoCard = (BigTwoCard) thisTopCard;

        return (this.getType() == hand.getType()) && (thisTopBigTwoCard.compareTo(hand.getTopCard()) > 0);
    }

    /**
     * Expresses the order of five card hand as integer to quantity there strength.
     * five card hand with higher order always with that of lower order (except 0 which is not a five card hand).
     * 
     * Order of:
     * Others = 0 (Not 5 card hand)
     * Straight = 1
     * Flush = 2
     * FullHouse = 3
     * Quad = 4
     * StraightFlush = 5
     * 
     * @return the order of a given 5 card hand
     */
    public int getTypeOrder() {
        if (getType() == "Straight") {
            return 1;
        } else if (getType() == "Flush") {
            return 2;
        } else if (getType() == "FullHouse") {
            return 3;
        } else if (getType() == "Quad") {
            return 4;
        } else if (getType() == "StraightFlush") {
            return 5;
        } else {
            return 0;
        }
    }

    /**
     * Checks whether a hand is valid.
     * Detailed version is implemented in subclasses.
     * 
     * @return true if hand is valid; otherwise false
     */
    public abstract boolean isValid();

    /**
     * Gets the type of hand.
     * Detailed version is implemented in subclasses.
     * 
     * @return the name of the type of hand
     */
    public abstract String getType();
}
