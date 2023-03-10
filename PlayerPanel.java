import javax.swing.*;
import java.awt.Color;
import java.awt.*;

/**
 * This class is used to represent a panel of cards of a player in the GUI.
 * 
 * @author Chan Tsz Ho
 */
public class PlayerPanel extends CardPanel {
    
    public PlayerPanel(int playerIdx, String avatarFileName) {
        super(playerIdx);

        // Creates avatar
        avatarIcon = new ImageIcon(avatarFileName).getImage();
    }

    /** 
     * Draws the following components:
     * 1. Player name
     * 2. Player avatar
     * 3. List of cards 
     * 
     * Update the player panel according to:
     * 1. The list of cards that the player currently owns.
     * 2. The press state of each card.
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
        String name = (playerIdx == activePlayerIdx) ? "You" : "Player "  + playerIdx;
        Color textColor = (playerIdx == activePlayerIdx) ? Color.YELLOW : Color.BLACK;
        g2D.setColor(textColor);
        g2D.drawString(name, NAME_HORT_OFFSET, NAME_VERT_OFFSET);

        // Draws avatar
        g2D.drawImage(avatarIcon, NAME_HORT_OFFSET, NAME_VERT_OFFSET + NAME_IMAGE_SPACE, this);

        // Draws cards
        if (playerIdx != activePlayerIdx) {
            printCardList(g2D, false);
        } else {
            printCardList(g2D, true);;
        }
    }

}
