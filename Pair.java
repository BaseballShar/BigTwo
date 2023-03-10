/**
 * This class is used for representing a Hand of Pair in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class Pair extends Hand {
    /**
     * [Constructor] Creates and returns an instance of the Pair class.
     * 
     * @param player Player who plays the hand
     * @param cards Cards that the player plays
     */
    public Pair(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /** 
     * This hand consists of two cards with the same rank.
     * 
     * @return true if the given cards are a valid Pair, false otherwise
     */
    @Override
    public boolean isValid() {
        return (this.size() == 2) 
        && (this.getCard(0).getRank() == this.getCard(1).getRank());
    }

    /** 
     * @return the type of hand
     */
    @Override
    public String getType() {
        return "Pair";
    }
}
