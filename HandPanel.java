import java.awt.Color;
import java.awt.*;

/**
 * This class is used to represent a panel of cards of a hand in the GUI.
 * 
 * @author Chan Tsz Ho
 */
public class HandPanel extends CardPanel {

    /**
     * @param activePlayerIdx index of active player
     */
    public HandPanel(int activePlayerIdx) {
        super();
        this.activePlayerIdx = activePlayerIdx;
    }

    /**
     * Cleans components of the hand panel.
     */
    public void cleanHand() {
        avatarIcon = null;
        activePlayerIdx = -1;
        cardList = null;

    }
    
    /** 
     * Draws the following components:
     * 1. Player name
     * 2. Player avatar
     * 3. List of cards 
     * 
     * Update the hand panel according to:
     * 1. The list of cards that the hand currently owns.
     * 
     * @param g the graphics object to draw the components
     */
    @Override
    public void paintComponent(Graphics g) {
        // Sets background colour
        Graphics2D g2D = (Graphics2D) g;
        this.setBackground(DARK_GREEN);
        g2D.setColor(DARK_GREEN);
        g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draws name
        String name = (activePlayerIdx == -1) ? "" : "Played by Player " + activePlayerIdx;
        g2D.setColor(Color.BLACK);
        g2D.drawString(name, NAME_HORT_OFFSET, NAME_VERT_OFFSET);

        // Draws avatar
        g2D.drawImage(avatarIcon, NAME_HORT_OFFSET, NAME_VERT_OFFSET + NAME_IMAGE_SPACE, this);

        // Draws card
        printCardList(g2D, true);
    }
    
}
