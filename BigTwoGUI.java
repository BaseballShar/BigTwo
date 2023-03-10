import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * A graphical interface for a big two card game user interface.
 * Feel free to find the EASTER EGG ^_^ XD.
 * 
 * @author Chan Tsz Ho
 * 
 */
public class BigTwoGUI implements CardGameUI {
    private BigTwo game;
    private boolean[] selected;
    private int activePlayer;
    private JFrame frame;
    private JPanel bigTwoPanel;
    private JPanel msgPanel;
    private JPanel actionPanel;

    private ArrayList<PlayerPanel> playerPanelList;
    private HandPanel handPanel;

    private JButton playButton;
    private JButton passButton;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu msgMenu;
    private JMenu easterEggMenu;
    private JMenuItem restartButton;
    private JMenuItem quitButton;
    private JMenuItem clearMsgButton;
    private JMenuItem easterEggButton;

    private JLabel msgLabel;
    private JTextArea msgArea;
    private JTextArea chatArea;
    private JTextField chatInput;

    private static final int TEXT_COLUMN = 30;
    private static final int TEXT_ROW = 15;
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 1000;

    private class ChatInputListener implements KeyListener {

        @Override
        /**
         * Gets the input from chatInput and paste it to msgArea.
         */
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                String text = chatInput.getText();
                chatArea.append("Player " + activePlayer + ": " + text + '\n');
                chatInput.setText("");
            }
        }

        // Not used in this GUI
        @Override
        public void keyPressed(KeyEvent e) {
            return;        
        }

        // Not used in this GUI
        @Override
        public void keyReleased(KeyEvent e) {
            return;
        }

    }

    private class QuitMenuItemListener implements ActionListener {

        /**
         * Quits game
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
            
        }

    }

    private class RestartMenuItemListener implements ActionListener {

        // Resets game
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();

        }

    }

    private class clearMsgMenuItemListener implements ActionListener {

        /**
         * Clears message of the chatArea.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            chatArea.setText("");
            
        }

    }

    private class BigTwoPanelListener implements MouseListener {

        // Not used in this GUI
        @Override
        public void mouseClicked(MouseEvent e) {
            return;            
        }

        // Not used in this GUI
        @Override
        public void mousePressed(MouseEvent e) {
            return;            
        }

        /**
         * Checks which card of the active player is selected
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            // Calculates the position of the leftmost corner of cards (ref. to big two panel)
            int OrigX = PlayerPanel.MAX_IMAGE_WIDTH;
            int OrigY = activePlayer * playerPanelList.get(0).getHeight() + PlayerPanel.NAME_VERT_OFFSET + PlayerPanel.CARD_VERT_OFFSET;
            int numOfCard = game.getPlayerList().get(activePlayer).getNumOfCards();

            // Checking for clicking except last card
            for (int i = 0; i < numOfCard - 1; i++) {
                boolean inCardX = (e.getX() >= OrigX + i * PlayerPanel.CARD_HORT_OFFSET) && (e.getX() <= OrigX + (i + 1) * PlayerPanel.CARD_HORT_OFFSET);
                boolean inCardY = (e.getY() >= OrigY) && (e.getY() <= OrigY + PlayerPanel.CARD_HEIGHT);
                if (inCardX && inCardY) {
                    selected[i] = !selected[i];
                }
            }

            // Checking for last card
            boolean inCardX = (e.getX() >= OrigX + (numOfCard - 1) * PlayerPanel.CARD_HORT_OFFSET) && (e.getX() <= OrigX + (numOfCard - 1) * PlayerPanel.CARD_HORT_OFFSET + PlayerPanel.CARD_WIDTH);
            boolean inCardY = (e.getY() >= OrigY) && (e.getY() <= OrigY + PlayerPanel.CARD_HEIGHT);
            if (inCardX && inCardY) {
                selected[numOfCard - 1] = !selected[numOfCard - 1];
            }
            
            // Update selected cards
            playerPanelList.get(activePlayer).setSelectedCard(selected);
            frame.repaint();
        }

        // Not used in this GUI
        @Override
        public void mouseEntered(MouseEvent e) {
            return;     

        }

        // Not used in this GUI
        @Override
        public void mouseExited(MouseEvent e) {
            return;   

        }

    }

    private class PlayButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] cardIdx = getSelected();
            resetSelected();

            // Cannot play a empty hand
            if (cardIdx == null) {
                printMsg("Not a legal move!!! [Cannot play empty hand] \n");
                promptActivePlayer();
                return;
            }

            // Makes move
            game.makeMove(activePlayer, cardIdx);
            playerPanelList.get(activePlayer).resetSelectedCard();

            // Stops the game when it is ended
            if (game.endOfGame()) {
                game.printEndGameMsg();
                disable();
            }

            frame.repaint();
            
        }

    }

    private class PassButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] cardIdx = getSelected();
            resetSelected();
            game.makeMove(activePlayer, cardIdx);
            
        }
        
    }

    private class EasterEggListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Image memeIcon = new ImageIcon("Nothing/stonk.png").getImage();
            frame.setTitle("You have been stonked");
            for (int i = 0; i < 4; i++) {
                playerPanelList.get(i).setAvatarIcon(memeIcon);
                Font font = new Font("Zapsino", Font.ITALIC, 32);
                msgArea.setFont(font);
                msgArea.append("STONKKKKKKKK !!!" + '\n');
                msgArea.append("STONKKKKKKKK !!!" + '\n');
                chatArea.setFont(font);
                chatArea.append("STONKKKKKKKK !!!" + '\n');
                chatArea.append("STONKKKKKKKK !!!" + '\n');
                msgArea.setBackground(Color.RED);
                chatArea.setBackground(Color.YELLOW);
            }            

            frame.repaint();
        }
    }
	/**
	 * Creates and returns an instance of the BigTwoGUI class.
	 * 
	 * @param game a BigTwo object associated with this GUI
	 */
    public BigTwoGUI(BigTwo game) {
        this.game = game;
        this.initFrame();
        this.initActionPanel();
        this.initMsgPanel();
        this.initMenu();
        this.initBigTwoPanel();
        frame.validate();
    }

    /**
     * Initializes the Big Two Panel.
     */
    private void initBigTwoPanel() {
        // Creates a black background with 5 slots
        // Vertical gaps are for delimters
        bigTwoPanel = new JPanel();
        bigTwoPanel.setLayout(new GridLayout(5, 1, 0, 2));
        bigTwoPanel.setBackground(Color.BLACK);

        // Creates list of player panels
        playerPanelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            PlayerPanel playerPanel = new PlayerPanel(i, "Avatar Icon/playerAvatar" + i + ".png");
            playerPanelList.add(playerPanel);
            // Adds them to bigTwoPanel
            bigTwoPanel.add(playerPanel);
        }

        handPanel = new HandPanel(-1);
        bigTwoPanel.add(handPanel);
        bigTwoPanel.addMouseListener(new BigTwoPanelListener());

        frame.add(bigTwoPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes menu.
     */
    private void initMenu() {
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        restartButton = new JMenuItem("Restart");
        restartButton.addActionListener(new RestartMenuItemListener());
        quitButton = new JMenuItem("Quit");
        quitButton.addActionListener(new QuitMenuItemListener());

        msgMenu = new JMenu("Message");
        clearMsgButton = new JMenuItem("Clear");
        clearMsgButton.addActionListener(new clearMsgMenuItemListener());

        easterEggMenu = new JMenu("");
        easterEggButton = new JMenuItem("Do not click");
        easterEggButton.addActionListener(new EasterEggListener());

        gameMenu.add(restartButton);
        gameMenu.add(quitButton);
        msgMenu.add(clearMsgButton);
        easterEggMenu.add(easterEggButton);
        menuBar.add(gameMenu);
        menuBar.add(msgMenu);
        menuBar.add(easterEggMenu);

        frame.add(menuBar, BorderLayout.NORTH);
    }

    /**
     * Initializes message boxes.
     */
    private void initMsgPanel() {
        msgPanel = new JPanel();
        msgPanel.setBackground(Color.GRAY);

        // Vertical gaps are for delimters
        msgPanel.setLayout(new GridLayout(2, 1, 0, 2));
        msgPanel.setPreferredSize(new Dimension(FRAME_WIDTH * 2 / 5, FRAME_HEIGHT));

        msgArea = new JTextArea(TEXT_ROW, TEXT_COLUMN);
        chatArea = new JTextArea(TEXT_ROW, TEXT_COLUMN);   
        JScrollPane msgScrollPane = new JScrollPane(msgArea);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        msgArea.setEditable(false);
        chatArea.setEditable(false);
        chatArea.setForeground(Color.BLUE); // Sets font colour

        msgPanel.add(msgScrollPane);
        msgPanel.add(chatScrollPane);

        frame.add(msgPanel, BorderLayout.EAST);
    }

    /**
     * Initializes action items.
     */
    private void initActionPanel() {
        actionPanel = new JPanel();
        playButton = new JButton("Play");
        playButton.addActionListener(new PlayButtonListener());
        passButton = new JButton("Pass");
        passButton.addActionListener(new PassButtonListener());

        msgLabel = new JLabel("Message:");
        chatInput = new JTextField(TEXT_COLUMN);
        chatInput.addKeyListener(new ChatInputListener());
        
        actionPanel.add(playButton);
        actionPanel.add(passButton);
        actionPanel.add(msgLabel);
        actionPanel.add(chatInput);

        frame.add(actionPanel, BorderLayout.SOUTH);
    }

    /**
     * Initializes the frame.
     */
    private void initFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Big Two");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
    }
    
    /** 
     * Sets the index of the active player.
     * 
     * @param activePlayerIdx
     */
    @Override
    public void setActivePlayer(int activePlayerIdx) {
        this.activePlayer = activePlayerIdx;

        // Sets active player to player panels
        for (int i = 0; i < playerPanelList.size(); i++) {
            playerPanelList.get(i).setActivePlayer(activePlayerIdx);
        }
        
        // Sets the selected array for card clicking
        int numOfCard = game.getPlayerList().get(activePlayerIdx).getNumOfCards();
        selected = new boolean[numOfCard];
        playerPanelList.get(activePlayerIdx).initSelected(numOfCard);
    }

    /**
     * Redraws the GUI.
     */
    @Override
    public void repaint() {
        // Updates player cards
        for (int i = 0; i < 4; i++) {
            playerPanelList.get(i).updateCardList(game.getPlayerList().get(i).getCardsInHand());
        }

        // Update hand on table cards
        if (game.getHandsOnTable() != null && game.getHandsOnTable().size() != 0) {            
            int numOfHand = game.getHandsOnTable().size();
            Hand lastHand = game.getHandsOnTable().get(numOfHand - 1);
            String lastHandPlayerName = lastHand.getPlayer().getName();
            int lastHandPlayerId = lastHandPlayerName.charAt(lastHandPlayerName.length() - 1) - '0';

            handPanel.setActivePlayer(lastHandPlayerId);
            handPanel.setAvatarIcon(playerPanelList.get(lastHandPlayerId).getAvatarIcon());
            handPanel.updateCardList(lastHand);
        }

        frame.repaint();
    }

    /**
	 * Prints the specified string to the message area of GUI.
	 * 
	 * @param msg the string to be printed to the GUI
	 */
    @Override
    public void printMsg(String msg) {
        msgArea.append(msg + '\n');
    }

    /**
	 * Clears the message area of the GUI.
	 */
    @Override
    public void clearMsgArea() {
        msgArea.setText("");    
    }

    /**
     * Resets the GUI.
     */
    @Override
    public void reset() {
        // Updates the GUI by replacing the listener if game has not completed
        if (!game.endOfGame()) {
            disable();
        }

        // Resets selected cards
        playerPanelList.get(activePlayer).resetSelectedCard();
        handPanel.cleanHand();

        // Restarts game
        clearMsgArea();
        game.run();
        enable();
    }
    
    /**
     * Enables user interactions.
     */
    @Override
    public void enable() {
        playButton.setEnabled(true);
        passButton.setEnabled(true);
        chatInput.setEditable(true);
        bigTwoPanel.addMouseListener(new BigTwoPanelListener());;

    }

    /**
     * Disables user interactions.
     */
    @Override
    public void disable() {
        playButton.setEnabled(false);
        passButton.setEnabled(false);
        chatInput.setEditable(false);
        bigTwoPanel.removeMouseListener(bigTwoPanel.getMouseListeners()[0]);
        
    }

    /**
	 * Prompts active player to select cards and make his/her move.
	 */
    @Override
    public void promptActivePlayer() {
        printMsg(game.getPlayerList().get(activePlayer).getName() + "'s turn:");
    }

    /**
	 * Returns an array of indices of the cards selected through the GUI.
     * Holds until the play button is pressed
	 * 
	 * @return an array of indices of the cards selected, or null if no valid cards
	 *         have been selected
	 */
    private int[] getSelected() {
        int[] cardIdx = null;

        // If choose to pass
        if (selected == null) {
            return cardIdx;
        // If choose to play -> gets indices of selected cards
        } else {
            int count = 0;
            for (int j = 0; j < selected.length; j++) {
                if (selected[j]) {
                    count++;
                }
            }
    
            if (count != 0) {
                cardIdx = new int[count];
                count = 0;
                for (int j = 0; j < selected.length; j++) {
                    if (selected[j]) {
                        cardIdx[count] = j;
                        count++;
                    }
                }
            }
            return cardIdx;
        }

    }

    /**
	 * Resets the list of selected cards to an empty list.
	 */
    private void resetSelected() {
        for (int j = 0; j < selected.length; j++) {
			selected[j] = false;
            playerPanelList.get(activePlayer).setSelectedCard(selected);
		}
    }
    
}
