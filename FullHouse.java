/**
 * This class is used for representing a Hand of FullHouse in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class FullHouse extends FiveCardHand {
    /**
     * [Constructor] Creates and returns an instance of the FullHouse class.
     * 
     * @param player Player who plays the hand
     * @param cards Cards that the player plays
     */
    public FullHouse(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /** 
     * This hand consists of five cards, with two having the same rank 
     * and three having another same rank. 
     * 
     * When sorted by order there are 2 possible arrangements (B is Stronger than A):
     * Case 1: A A A B B -> 3 Card of Rank A & 2 Card of Rank B are found
     * Case 2: A A B B B -> 2 Card of Rank A & 3 Card of Rank B are found
     * 
     * @return true if the given cards are a valid FullHouse, false otherwise
     */
    @Override
    public boolean isValid() {
        if (this.size() == 5) {
            this.sort();

            int numRankFirst = this.count(this.getCard(0)); // Number of cards with rank = 1st Card
            int numRankLast = this.count(this.getCard(4)); // Number of cards with rank = last Card
            
            // Case 1 or 2 are valid
            if ((numRankFirst == 2 && numRankLast == 3) || (numRankFirst == 3 && numRankLast == 2)) {
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
        return "FullHouse";
    }

    /**
     * Returns top card of FullHouse.
     * The card in the triplet with the highest suit in a full house is referred to as the top card of this full house.
     *  
     * When sorted by order there are 2 possible arrangements (B is Stronger than A):
     * Case 1: A A A B B -> topCard = 3rd Card (index = 2)
     * Case 2: A A B B B -> topCard = 5th Card (index = 4)
     * 
     * @return top card of FullHouse
     */
    @Override
    public Card getTopCard() {
        // Sort to ensure last card is biggest card
        this.sort();
        int numRankFirst = this.count(this.getCard(0));
        
        // Case 1
        if (numRankFirst == 3) {
            return getCard(2);
        // Case 2
        } else {
            return getCard(4);
        }
    }
}
