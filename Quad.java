/**
 * This class is used for representing a Hand of Quad in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class Quad extends FiveCardHand {
    /**
     * [Constructor] Creates and returns an instance of the Quad class.
     * 
     * @param player Player who plays the hand
     * @param cards Cards that the player plays
     */
    public Quad(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /** 
     * This hand consists of five cards, with four having the same rank. 
     * 
     * When sorted by order there are 2 possible arrangements (B is Stronger than A):
     * Case 1: A A A A B -> 4 Card of Rank A & 1 Card of Rank B are found
     * Case 2: A B B B B -> 1 Card of Rank A & 4 Card of Rank B are found
     * 
     * @return true if the given cards are a valid Quad, false otherwise
     */
    @Override
    public boolean isValid() {
        if (this.size() == 5) {
            this.sort();

            int numFirst = this.count(this.getCard(0));
            int numLast = this.count(this.getCard(4));

            // Case 1 or 2 are valid
            if ((numFirst == 1 && numLast == 4) || (numFirst == 4 && numLast == 1)) {
                return true;
            }
        }
        return false;
    }

    /** 
     * @return the type of hand
     */
    @Override
    public String getType() {
        return "Quad";
    }

    /**
     * Returns top card of Quad.
     * The card in the quadruplet with the highest suit in a quad is referred to as the top card of this quad.
     * 
     * When sorted by order there are 2 possible arrangements (B is Stronger than A):
     * Case 1: A A A A B -> topCard = 4rd Card (index = 3)
     * Case 2: A B B B B -> topCard = 5th Card (index = 4)
     * 
     * @return top card of Quad
     */
    @Override
    public Card getTopCard() {
        // Sort to ensure last card is biggest card
        this.sort();
        int numRankFirst = this.count(this.getCard(0));
        
        // Case 1
        if (numRankFirst == 4) {
            return getCard(3);
        // Case 2
        } else {
            return getCard(4);
        }
    }
}
