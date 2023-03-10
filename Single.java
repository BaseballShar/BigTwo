/**
 * This class is used for representing a Hand of Single in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class Single extends Hand{
    /**
     * [Constructor] Creates and returns an instance of the Single class.
     * 
     * @param player Player who plays the hand
     * @param cards Cards that the player plays
     */
    public Single(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    
    /** 
     * This hand consists of only one single card.
     * 
     * @return true if the given cards are a valid Single, false otherwise
     */
    @Override
    public boolean isValid() {
        return (this.size() == 1);
    }

    
    /** 
     * @return the type of hand
     */
    @Override
    public String getType() {
        return "Single";
    }
}
