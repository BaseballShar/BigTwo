/**
 * This class is used to model cards played in Big Two game (To reflect the real order of cards).
 * 
 * @author Chan Tsz Ho
 */

public class BigTwoCard extends Card {
	/**
	 * [Constructor] Creates and returns an instance of the BigTwoCard class.
	 * 
	 * @param suit Suit of the card
	 * @param rank Rank of the card
	 * 
	 * The definition of suit and rank is the same as that of Card class.
	 */
    public BigTwoCard(int suit, int rank) {
		super(suit, rank);
    }
	
	/**
	 * Compares this Big Two card with the specified card for order.
	 * Originally: 0 = 'A', 1 = '2', 2 = '3', ..., 10 = 'J', 11 = 'Q', 12 = 'K'.
	 * To reflect that '2' and 'A' are the largest and second largest card in the game,
	 * 'A' is mapped to 13 and '2' is mapped to 14 (Each + 13).
	 * After shifting: 2 = '3', ..., 12 = 'K', 13 = 'A', 14 = '2'.
	 * 
	 * @param card the card to be compared
	 * @return a negative integer, zero, or a positive integer as this card is less
	 *         than, equal to, or greater than the specified card
	 */
	@Override
    public int compareTo(Card card) {
		int thisRealRank = this.rank <= 1 ? this.rank + 13 : this.rank;
		int cardRealRank = card.rank <= 1 ? card.rank + 13 : card.rank;

		if (thisRealRank > cardRealRank) {
			return 1;
		} else if (thisRealRank < cardRealRank) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
    }

	
	/**
	 * (The method prioritises suit over rank, i.e. suit is considered first then rank)
	 * Compares this Big Two card with the specified card for order.
	 * Originally: 0 = 'A', 1 = '2', 2 = '3', ..., 10 = 'J', 11 = 'Q', 12 = 'K'.
	 * To reflect that '2' and 'A' are the largest and second largest card in the game,
	 * 'A' is mapped to 13 and '2' is mapped to 14 (Each + 13).
	 * After shifting: 2 = '3', ..., 12 = 'K', 13 = 'A', 14 = '2'.
	 * 
	 * @param card the card to be compared
	 * @return a negative integer, zero, or a positive integer as this card is less
	 *         than, equal to, or greater than the specified card
	 */
	public int compareBySuit(Card card) {
		int thisRealRank = this.rank <= 1 ? this.rank + 13 : this.rank;
		int cardRealRank = card.rank <= 1 ? card.rank + 13 : card.rank;

		if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else if (thisRealRank > cardRealRank) {
			return 1;
		} else if (thisRealRank < thisRealRank) {
			return -1;
		} else {
			return 0;
		}
	}
}
