/**
 * This class is used for representing a Hand of Triple in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class Triple extends Hand {
    /**
     * [Constructor] Creates and returns an instance of the Triple class.
     * 
     * @param player Player who plays the hand
     * @param cards Cards that the player plays
     */
    public Triple(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /** 
     * This hand consists of three cards with the same rank.
     * 
     * @return true if the given cards are a valid Triple, false otherwise
     */
    @Override
    public boolean isValid() {
        return (this.size() == 3) 
        && (this.getCard(0).getRank() == this.getCard(1).getRank())
        && (this.getCard(1).getRank() == this.getCard(2).getRank());
    }

    /** 
     * @return the type of hand
     */
    @Override
    public String getType() {
        return "Triple";
    }
}
