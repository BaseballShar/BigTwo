/**
 * This class is used to represent a deck of BigTwoCards in Big Two card games.
 * 
 * @author Chan Tsz Ho
 */
public class BigTwoDeck extends Deck {
    @Override
	/**
	 * Initializes the deck of BigTwoCards.
	 */
    public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
                // creates BigTwoCard cards instead
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
    }
}
