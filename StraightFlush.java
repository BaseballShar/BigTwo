/**
 * This class is used for representing a Hand of StraightFlush in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class StraightFlush extends FiveCardHand {
    /**
     * [Constructor] Creates and returns an instance of the StraightFlush class.
     * 
     * @param player Player who plays the hand
     * @param cards Cards that the player plays
     */
    public StraightFlush(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /** 
     * This hand consists of five cards with consecutive ranks and the same suit. 
     * For the sake of simplicity, 2 and A can only form a straight flush with K but not with 3. 
     * 
     * Lowest possible order: 3, 4, 5, 6, 7
     * Highest possible order: J, Q, K, A, 2
     * 
     * @return true if the given cards are a valid StraightFlush, false otherwise
     */
    @Override
    public boolean isValid() {
        // Sorts the list to ease the comparison
        this.sort();
        if (this.size() == 5) {
            // Checks whether cards have consecutive ranks and same suit
            for (int i = 0; i < 4; i++) {
                int currRealRank = this.getCard(i).getRank() <= 1 ? this.getCard(i).getRank() + 13 : this.getCard(i).getRank();
                int nextRealRank = this.getCard(i+1).getRank() <= 1 ? this.getCard(i+1).getRank() + 13 : this.getCard(i+1).getRank();

                if ((nextRealRank - currRealRank != 1) 
                || (this.getCard(i+1).getSuit() != this.getCard(i).getSuit()) ){
                    return false;
                }
            }
            return true;
        }
        // If size != 5
        return false;
    }

    /** 
     * @return the type of hand
     */
    @Override
    public String getType() {
        return "StraightFlush";
    }
}
