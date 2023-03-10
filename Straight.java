/**
 * This class is used for representing a Hand of Straight in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class Straight extends FiveCardHand {
    /**
     * [Constructor] Creates and returns an instance of the Straight class.
     * 
     * @param player Player who plays the hand
     * @param cards Cards that the player plays
     */
    public Straight(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /** 
     * This hand consists of five cards with consecutive ranks. 
     * For the sake of simplicity, 2 and A can only form a straight with K but not with 3.
     * 
     * Lowest possible order: 3, 4, 5, 6, 7
     * Highest possible order: J, Q, K, A, 2
     * 
     * @return true if the given cards are a valid Straight, false otherwise
     */
    @Override
    public boolean isValid() {
        // Sorts the list to ease the comparison
        this.sort();
        if (this.size() == 5) {
            // Checks whether cards have consecutive ranks
            for (int i = 0; i < 4; i++) {
                int currRealRank = this.getCard(i).getRank() <= 1 ? this.getCard(i).getRank() + 13 : this.getCard(i).getRank();
                int nextRealRank = this.getCard(i+1).getRank() <= 1 ? this.getCard(i+1).getRank() + 13 : this.getCard(i+1).getRank();
                if ((nextRealRank - currRealRank) != 1) {
                    return false;
                }
            } // If all element differs by 1
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
        return "Straight";
    }
}
