import javax.swing.*;
import java.awt.Color;
import java.awt.*;

/**
 * This class is used to represent a panel of cards in the GUI.
 * 
 * @author Chan Tsz Ho
 */
public class CardPanel extends JPanel {
    protected int playerIdx;
    protected int activePlayerIdx;
    protected CardList cardList;
    protected boolean[] selected;
    protected Image avatarIcon;

    // Constants for position of elements in the GUI
    public static final Color DARK_GREEN = new Color(0, 127, 63);
    public static final int NAME_HORT_OFFSET = 10;
    public static final int NAME_VERT_OFFSET = 15;
    public static final int CARD_WIDTH = 73;
    public static final int CARD_HEIGHT = 97;
    public static final int CARD_HORT_OFFSET = 25;
    public static final int CARD_VERT_OFFSET = 30;
    public static final int MAX_IMAGE_WIDTH = 200;
    public static final int NAME_IMAGE_SPACE = 10;

    public CardPanel() {
        super();
        cardList = new CardList();
    }

    /**
     * 
     * @param playerIdx the player who plays the cards on the panel
     */
    public CardPanel(int playerIdx) {
        super();
        cardList = new CardList();
        this.playerIdx = playerIdx;
    }

    
    /** 
     * Sets the active player index.
     * 
     * @param activePlayerIdx
     */
    public void setActivePlayer(int activePlayerIdx) {
        this.activePlayerIdx = activePlayerIdx;
    }

    
    /** 
     * Returns the avatar icon of the panel.
     * 
     * @return avatar icon of the panel
     */
    public Image getAvatarIcon() {
        return avatarIcon;
    }

    
    /**
     * Sets the avatar icon of the panel.
     * 
     * @param avatarIcon avatar icon of the panel
     */
    public void setAvatarIcon(Image avatarIcon) {
        this.avatarIcon = avatarIcon;
    }

    
    /** 
     * Initalize the selected cards.
     * 
     * @param numOfCard number of cards currently the player has
     */
    public void initSelected(int numOfCard) {
        selected = new boolean[numOfCard];
    }

    /**
     * Resets the selected cards.
     */
    public void resetSelectedCard() {
        for (int i = 0; i < selected.length; i++) {
            this.selected[i] = false;
        }
    }

    
    /** 
     * Sets the selected cards.
     * 
     * @param selected the value to be set
     */
    public void setSelectedCard(boolean[] selected) {
        for (int i = 0; i < selected.length; i++) {
            this.selected[i] = selected[i];
        }
    }

    
    /** 
     * Updates the card list.
     * 
     * @param cardList the list of cards that the player currently owns
     */
    public void updateCardList(CardList cardList) {
        // Prevents null cardlist
        if (this.cardList == null) {
            this.cardList = new CardList();
        }
            this.cardList.removeAllCards();
        for (int i = 0; i < cardList.size(); i++) {
            this.cardList.addCard(cardList.getCard(i));
        }
    }

    
    /** 
     * Locates the image of a card.
     * 
     * @param card the card desired
     * @return image of a card
     */
    public Image getCardImage(Card card) {
        char[] toSuit = {'d', 'c', 'h', 's'};
        char[] toRank = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};

        String cardImageName = "Card Image/" + Character.toString(toRank[card.getRank()]) + Character.toString(toSuit[card.getSuit()]) + ".gif";

        return new ImageIcon(cardImageName).getImage();
    }

    
    /**
     * Prints image of cards owned by the player.
     * 
     * @param g2D Graphics2D object to draw the cards
     * @param showFace draws the front face of card if true; otherwise false
     */
    public void printCardList(Graphics2D g2D, boolean showFace) {
        Image cardImage;

        // Prevents null cardList
        if (cardList == null) {
            return;
        }

        // Calculates position of card images
        for (int i = 0; i < cardList.size(); i++) {
            int cardX = MAX_IMAGE_WIDTH + CARD_HORT_OFFSET * i; // x coordinate of card
            int cardY = NAME_VERT_OFFSET + CARD_VERT_OFFSET; // y coordinate of card
            int raised = 0; // 1 if raised; 0 if not raised

            if (selected != null && i < selected.length) {
                raised = selected[i] ? 1 : 0;
            }

            // For active players
            if (showFace) {
                cardImage = getCardImage(cardList.getCard(i));
            // For inactive players
            } else {
                cardImage = new ImageIcon("Card Image/b.gif").getImage();
            }
            g2D.drawImage(cardImage, cardX, cardY - raised * CARD_VERT_OFFSET, this);
        }
    }

}
