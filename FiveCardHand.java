/**
 * This class is used for representing a general Five Card Hand in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public abstract class FiveCardHand extends Hand {

    /**
     * [Constructor] Creates and returns an instance of the FiveCardHand class.
     * FiveCardHand Class is not designed to be instantiated directly by calling the FiveCardHand Constructor.
     * 
     * @param player the player hold this hand
     * @param cards the list of cards to be played
     */
    public FiveCardHand(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    
   /**
     * Checks if this FiveCardHand beats a specified hand.
     * Default behaviour: beats only if one of the following cases is satisfied.
     * 1. Both are five card hands and this hand has a higher order type
     * 2. Both hands are same type and this hand has larger top card
     * 
     * @param hand hand to be compared with
     * @return true if this hand can beat the specified hand; otherwise false
     */
    @Override
    public boolean beats(Hand hand) {
        Card thisTopCard = this.getTopCard();
        // Converts to BigTwoCard to use the modified compareTo() for BigTwoCard game
        BigTwoCard thisTopBigTwoCard = (BigTwoCard) thisTopCard;
        boolean isFiveCard = (this.size() == 5) && (hand.size() == 5); // Checks if both hands are FiveCardHands

        if ((isFiveCard) && (this.getTypeOrder() > hand.getTypeOrder()) ) {
            return true;
        } else {
            return (this.getType() == hand.getType()) && (thisTopBigTwoCard.compareTo(hand.getTopCard()) > 0);
        }
    }
}
