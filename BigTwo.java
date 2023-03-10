import java.util.ArrayList;

/**
 * This class is used for modeling the Big Two card game.
 * 
 * @author Chan Tsz Ho
 */
public class BigTwo implements CardGame{
    private int numOfPlayers; // number of players in the game
    private Deck deck; // the deck of cards in the game
    private ArrayList<CardGamePlayer> playerList; // the list of players in the game
    private ArrayList<Hand> handsOnTable; // the list of played hands in the game
    private int currentPlayerIdx; // the index of the current player
    private BigTwoGUI gui; // the gui of the game

    /**
     * [Constructor] Creates and returns an instance of the BigTwo class.
     */
    public BigTwo() {
        // Creates 4 players and add them into playerList
        playerList = new ArrayList<CardGamePlayer>();
        for (int i = 0; i < 4; i++) {
            CardGamePlayer player = new CardGamePlayer();
            playerList.add(player);
        }
        numOfPlayers = 4;

        // Creates BigTwoGUI
        handsOnTable = new ArrayList<Hand>();
        gui = new BigTwoGUI(this);
    }
    
    /** 
     * Returns the number of players in this game.
     * 
     * @return number of players in this game
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }
 
    /** 
     * Returns the deck of cards in this game.
     * 
     * @return deck of cards in this game
     */
    public Deck getDeck() {
        return deck;
    }

    /** 
     * Returns the list of players in this game.
     * 
     * @return list of players in this game
     */
    public ArrayList<CardGamePlayer> getPlayerList() {
        return playerList;
    }
 
    /** 
     * Returns the list of played hands in the game.
     * 
     * @return list of played hands in the game
     */
    public ArrayList<Hand> getHandsOnTable() {
        return handsOnTable;
    }
 
    /** 
     * Returns the index of current player in the game.
     * 
     * @return index of current player in the game
     */
    public int getCurrentPlayerIdx() {
        return currentPlayerIdx;
    }

    /** 
     * Starts / Restarts the game with a given shuffled deck of cards.
     * 
     * @param deck the lists of randomly selected cards ready to distribute to players
     */
    public void start(Deck deck) {
        // (i) Removes all the cards from the players
        for (CardGamePlayer player : playerList) {
            player.removeAllCards();
        }

        // (i) Removes all the cards from the table
        this.deck = deck;

        // (ii) Distributes the cards to the players
        for (int i = 0; i < 4; i++) { // 4 players
            for (int j = 0; j < 13; j++) { // 13 cards each player
                playerList.get(i).addCard(deck.getCard(13*i + j));
            }
            playerList.get(i).sortCardsInHand();   
        }

        // (iii) Indentifies the player who hold the Three of Diamonds
        // (iv) Sets both the currentPlayerIdx of the BigTwo object
        // and activePlayer of the BigTwoGUI object
        // Creates Three of Diamonds for comparison
        Card threeOfDiamonds = new Card(0, 2); // Three of Diamonds
        for (int i = 0; i < 4; i++) {
            if (playerList.get(i).getCardsInHand().contains(threeOfDiamonds)) {
                currentPlayerIdx = i;
                gui.setActivePlayer(i);
            }
        }

        // (v) Resets hands on table
        handsOnTable = new ArrayList<Hand>();

        // (vi) Shows cards on the table
        gui.repaint();

        // (vii) Prompts user to select cards to make move
        gui.promptActivePlayer();
    }

    /** 
     * Makes a move by a player with the specified index using the cards specified by the list of indices.
     * 
     * @param playerIdx index of current player
     * @param cardIdx list of indices specifying the cards that the player intends to play
     */
    public void makeMove(int playerIdx, int[] cardIdx) {
        // Repeats until the move is valid (Pass / legal hands)
        checkMove(playerIdx, cardIdx);

        // Declare variables
        CardGamePlayer currPlayer = playerList.get(playerIdx);
        Hand currHand = composeHand(currPlayer, currPlayer.play(cardIdx));

        // makeMove will be called multiple times if invalid moves are made
        // To prevent incrementing the currentPlayerIdx multiple times
        // playerIdx is checked again currentPlayerIdx
        // Also, incrementation is performed only if the move is valid
        if (!isValidMove(playerIdx, cardIdx) || playerIdx != currentPlayerIdx) {
            return;
        }

        // Increments players only when valid moves are made
        currentPlayerIdx = (currentPlayerIdx + 1) % 4;
        gui.setActivePlayer(currentPlayerIdx);

        // Put the hand to handsOnTable when a valid hand (Non-null) is played 
        if (currHand != null) {
            // Removes card from player
            currPlayer.removeCards(currHand);

            // Put cards to table (If has)
            handsOnTable.add(currHand);

            // Print the current hand
            gui.printMsg("{" + currHand.getType() + "} " + currHand + '\n');
        } else {
            gui.printMsg("{Pass} \n");
        }

        // Redraws the gui
        gui.repaint();

        // Prompts player to input
        gui.promptActivePlayer();
    }
    
    /** 
     * Supports isValidMove methods. Handles the first case.
     * The move is invalid if:
     * 1. The player passes (No cards are played)
     * 2. The combination of cards is illegal (hand = null)
     * 3. Three of Diamonds is not player in the hand
     * Otherwise, the move is valid.
     * 
     * @param hand the intended hand to player
     * @return true if the first move is invalid; otherwise false
     */
    public boolean checkFirstMove(Hand hand) {
        // Declare variables for checking
        BigTwoCard threeOfDiamond = new BigTwoCard(0, 2);

        // Point 1 & 2: No valid hand
        if (hand == null) {
            return false;
        // Point 3: Three of Diamonds is not included
        } else if (!hand.contains(threeOfDiamond)) {
            return false;
        }
        return true;
    }
  
    /** 
     * Supports isValidMove methods. Handles the second case.
     * The move is invalid if:
     * 1. Player is the one who played the last hand 
     * 1.1 and passes.
     * 1.2 and plays illegal combinations
     * 
     * 2. Player is not the one who played the last hand 
     * 2.1 and plays illegal combinations.
     * 2.2 and plays hand that cannot beat last hand.
     * 
     * Otherwise, the move is valid.
     * 
     * @param playerIdx index of current player
     * @param cardIdx list of indices specifying the cards that the player intends to play
     * @return true if the other move is invalid; otherwise false
     */
    public boolean checkOtherMove(int playerIdx, int[] cardIdx) {
        // Declare variables for checking
        int numHand = getHandsOnTable().size();
        CardGamePlayer currPlayer = getPlayerList().get(playerIdx);
        CardList currCardList = currPlayer.play(cardIdx);
        Hand currHand = composeHand(currPlayer, currCardList);
        Hand lastHand = getHandsOnTable().get(numHand - 1);
        CardGamePlayer lastPlayer = lastHand.getPlayer();

        // Point 1: If you are the last player
        if (currPlayer.getName() == lastPlayer.getName()) {
            if (currHand == null) {
                return false;
            }
            return true;
        }

        // Point 2: If you are not the last player
        // Point 2.1: Plays illegal combinations
        if (currCardList != null & currHand == null) {
            return false;
        // Othewise: If pass
        } else if (currCardList == null & currHand == null) {
            return true;
        // Point 2.2: If can beats
        } else if (currHand.beats(lastHand)) {
            return true;
        }

        return false;
    }

    /** 
     * Supports the checkMove method. Seperate valid moves into 2 cases.
     * Case 1: If the player is first player in the game
     * Case 2: If the player is not the first player in the game
     * 
     * @param playerIdx index of current player
     * @param cardIdx list of indices specifying the cards that the player intends to play
     * @return true if the lists of cards are a valid move; false otherwise
     */
    public boolean isValidMove(int playerIdx, int[] cardIdx) {
        // Declare variables
        int numHand = getHandsOnTable().size(); // Decide whether first player
        CardGamePlayer currPlayer = playerList.get(playerIdx);
        
        // Converts cards to a Hand object (Returns null if can't compose)
        Hand currHand = composeHand(currPlayer, currPlayer.play(cardIdx));

        // Case 1: First player 
        if (numHand == 0) {
            return checkFirstMove(currHand);
        // Case 2: Not First player
        } else {
            return checkOtherMove(playerIdx, cardIdx);
        }
    }
    
    /** 
     * Checks whether a move is valid.
     * If valid: Continue to makeMove() and play the cards / pass (if applicable)
     * If invalid: prints "Not a legal move!!!" and prompts the player to remake the move.
     * 
     * @param playerIdx index of current player
     * @param cardIdx list of indices specifying the cards that the player intends to play
     */
    public void checkMove(int playerIdx, int[] cardIdx) {
       if (!isValidMove(playerIdx, cardIdx)) {
           gui.printMsg("Not a legal move!!! \n");
           gui.promptActivePlayer();
       }
    }
    
    /** 
     * Checks if the game has finished.
     * The game is finished when one player has played all the cards.
     * 
     * @return true if the game has finished; Otherwise false
     */
    public boolean endOfGame() {
        for (int i = 0; i < 4; i++) {
            if (playerList.get(i).getNumOfCards() == 0) {
                return true;
            }
        }
        return false;
    }
    
    /** 
     * Returns a valid hand from the specified list of cards of the player. 
     * Returns null if no valid hand can be composed from the specified list of cards.
     * 
     * @param player the player who played the cards
     * @param cards the list of cards to be played
     * @return the hand composed from the list of cards
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards) {
        // If no cards are played
        if (cards == null || cards.size() == 0) {
            return null;
        }

        Single single = new Single(player, cards);
        if (single.isValid()) {
            return single;
        }

        Pair pair = new Pair(player, cards);
        if (pair.isValid()) {
            return pair;
        }

        Triple triple = new Triple(player, cards);
        if (triple.isValid()) {
            return triple;
        }

        StraightFlush straightFlush = new StraightFlush(player, cards);
        if (straightFlush.isValid()) {
            return straightFlush;
        }

        Quad quad = new Quad(player, cards);
        if (quad.isValid()) {
            return quad;
        }

        FullHouse fullHouse = new FullHouse(player, cards);
        if (fullHouse.isValid()) {
            return fullHouse;
        }

        Flush flush = new Flush(player, cards);
        if (flush.isValid()) {
            return flush;
        }

        Straight straight = new Straight(player, cards);
        if (straight.isValid()) {
            return straight;
        }

        // If cannot make any legel hands
        return null;
    }
    
    /**
     * Runs the game
     */
    public void run() {
        // (ii) Creates and shuffle a deck of cards
        BigTwoDeck bigTwoDeck = new BigTwoDeck();
        bigTwoDeck.shuffle();

       // (iii) Starts the game with deck of cards
        this.start(bigTwoDeck);

    }

    public void printEndGameMsg() {
        gui.printMsg("Game ends");
        for (int i = 0; i < 4; i++) {
            if (this.getPlayerList().get(i).getNumOfCards() == 0) {
                gui.printMsg("Player " + i + " wins the game.");
            } else {
                String wordCard = this.getPlayerList().get(i).getNumOfCards() > 1 ? "cards" : "card"; // Determines plural or single
                gui.printMsg("Player " + i + " has " + this.getPlayerList().get(i).getNumOfCards() + " " + wordCard + " in hand.");
            }
        }
        
    }

    /** 
     * Creates an instance of the BigTwo class and start the Big Two game.
     * 
     * @param args not being used in this application.
     */
    public static void main(String[] args) {
        // (i) Creates Big Two card game (Creates 4 Players and UI)
        BigTwo bigTwo = new BigTwo();
        bigTwo.run();
 
    }
}