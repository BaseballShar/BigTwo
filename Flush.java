/**
 * This class is used for representing a Hand of Flush in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class Flush extends FiveCardHand{
    /**
     * [Constructor] Creates and returns an instance of the Flush class.
     * 
     * @param player Player who plays the hand
     * @param cards Cards that the player plays
     */
    public Flush(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /** 
     * This hand consists of five cards with the same suit. 
     * 
     * @return true if the given cards are a valid Flush, false otherwise
     */
    @Override
    public boolean isValid() {
        if (this.size() == 5) {
            for (int i = 0; i < 4; i++) {
                if (this.getCard(i+1).getSuit() != this.getCard(i).getSuit()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /** 
     * @return the type of hand
     */
    @Override
    public String getType() {
        return "Flush";
    }
    
    /**
     * Checks if this flush beats a specified hand.
     * 
     * @return true if this flush can beat the specified hand
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
            return (this.getType() == hand.getType()) && (thisTopBigTwoCard.compareBySuit(hand.getTopCard()) > 0);
        }
    }
}
