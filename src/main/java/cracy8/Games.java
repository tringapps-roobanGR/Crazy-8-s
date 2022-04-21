package cracy8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Games {

    PlayerStrategy playerOne = new StrategyA();
    PlayerStrategy playerTwo = new StrategyA();
    PlayerStrategy playerThree = new StrategyB();
    PlayerStrategy playerFour = new StrategyB();

    List<PlayerStrategy> players = new ArrayList();

    public int playerOneID = 0;
    int playerTwoID = 1;
    int playerThreeID = 2;
    int playerFourID = 3;

    int playerOnePoints = 0;
    int playerTwoPoints = 0;
    int playerThreePoints = 0;
    int playerFourPoints = 0;

    ArrayList<Integer> playerPoints = new ArrayList();

    int winningPoints = 200;

    List<Card> cardDeck = Card.getDeck();
    ArrayList<Card> playerOneCards = new ArrayList();
    ArrayList<Card> playerTwoCards = new ArrayList();
    ArrayList<Card> playerThreeCards = new ArrayList();
    ArrayList<Card> playerFourCards = new ArrayList();
    ArrayList<ArrayList<Card>> allPlayerCards = new ArrayList();

    int cardDeckPosition;
    
    public void playGame() {

        initializeGame();

        
        Card topCard = cardDeck.get(cardDeckPosition);
        cardDeck.remove(cardDeckPosition);
        cardDeckPosition = cardDeck.size() - 1;

        // plays multiple rounds until cardDeck is empty or if players cards are empty
        while (true) {
            for (int playerId = playerOneID; playerId <= playerFourID; playerId++) {
                if (players.get(playerId).shouldDrawCard(topCard, players.get(playerId).declareSuit())) {
                    drawCard(playerId);
                } else {
                    Card cardPlayed = players.get(playerId).playCard();
                    allPlayerCards.get(playerId).remove(cardPlayed);
                    topCard = cardPlayed;
                }
                if (isPlayerCardsEmpty()) {
                    return;
                }
                if (cardDeck.isEmpty()) {
                    return;
                }
            }
        }
    }

    /**
     * player draws card from deck if shouldDrawCard returns true
     * @param playerId identifies which player draws card
     */
    public void drawCard(int playerId) {
        if (cardDeck.size() == 0) {
            return;
        }
        players.get(playerId).receiveCard(cardDeck.get(cardDeckPosition));
        allPlayerCards.get(playerId).add(cardDeck.get(cardDeckPosition));
        cardDeck.remove(cardDeckPosition);
        cardDeckPosition = cardDeck.size() - 1;
    }

    /**
     * checks if each player's cards are empty after each player turn
     * @return true if a player has no more cards
     */
    public boolean isPlayerCardsEmpty() {
        for (int i = 0; i < allPlayerCards.size(); i++) {
           if (allPlayerCards.get(i).isEmpty()) {
               return true;
            }
        }
        return false;
    }

    /**
     * sets player Id for each player and lists its opponents
     * runs once at the start of the tournament
     */
    public void initializeTournament() {
        ArrayList<Integer> opponentIDPlayerOne = new ArrayList();
        opponentIDPlayerOne.add(playerTwoID);
        opponentIDPlayerOne.add(playerThreeID);
        opponentIDPlayerOne.add(playerFourID);

        ArrayList<Integer> opponentIDPlayerTwo = new ArrayList();
        opponentIDPlayerTwo.add(playerOneID);
        opponentIDPlayerTwo.add(playerThreeID);
        opponentIDPlayerTwo.add(playerFourID);

        ArrayList<Integer> opponentIDPlayerThree = new ArrayList();
        opponentIDPlayerThree.add(playerOneID);
        opponentIDPlayerThree.add(playerTwoID);
        opponentIDPlayerThree.add(playerFourID);

        ArrayList<Integer> opponentIDPlayerFour = new ArrayList();
        opponentIDPlayerFour.add(playerOneID);
        opponentIDPlayerFour.add(playerTwoID);
        opponentIDPlayerFour.add(playerThreeID);

        playerOne.init(playerOneID, opponentIDPlayerOne);
        playerTwo.init(playerTwoID, opponentIDPlayerTwo);
        playerThree.init(playerThreeID, opponentIDPlayerThree);
        playerFour.init(playerFourID, opponentIDPlayerFour);
    }

    /**
     * resets state of each players cards by shuffling deck of cards, clearing cards,
     * and receiving initial cards,
     * runs at the start of every new game
     */
    public void initializeGame() {
        cardDeck = Card.getDeck();
        Collections.shuffle(cardDeck);
       

        for (int index = 0; index < players.size(); index++) {
            players.get(index).reset();
            players.get(index).receiveInitialCards(cardDeck);
        }

        playerOneCards.clear();
        playerTwoCards.clear();
        playerThreeCards.clear();
        playerFourCards.clear();

        for (int cardIndex = 0; cardIndex < 4; cardIndex++) {
            playerOneCards.add(cardDeck.get(cardIndex));
            playerTwoCards.add(cardDeck.get(cardIndex));
            playerThreeCards.add(cardDeck.get(cardIndex));
            playerFourCards.add(cardDeck.get(cardIndex));
        }
    }

    /**
     * adds players to a List of all the players
     * runs at the beginning of the tournament
     */
    public void addPlayersToPlayerList() {
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        players.add(playerFour);
    }

    /**
     * adds each player's cards to a list
     * runs at the start of the tournament
     */
    public void addPlayerCardsToAllPlayerCardsList() {
        allPlayerCards.add(playerOneCards);
        allPlayerCards.add(playerTwoCards);
        allPlayerCards.add(playerThreeCards);
        allPlayerCards.add(playerFourCards);
    }

    /**
     * adds each of the player points to a list
     * runs at the beginning of the tournament
     */
    public void addPlayerPointsToList() {
        playerPoints.add(playerOnePoints);
        playerPoints.add(playerTwoPoints);
        playerPoints.add(playerThreePoints);
        playerPoints.add(playerFourPoints);
    }

    /**
     * checks to see if the tournament is over by summing all player's points at the end of each game
     * @return true if a player has more than 200 points, false otherwise
     */
    public boolean isTournamentOver() {
        for (int playerID = 0; playerID < allPlayerCards.size(); playerID++) {
            for (int i = 0; i < allPlayerCards.get(playerID).size(); i ++) {
                int cardPoint =  allPlayerCards.get(playerID).get(i).getPointValue();
                if (playerID == 0) {
                    playerTwoPoints += cardPoint;
                    playerThreePoints += cardPoint;
                    playerFourPoints +=  cardPoint;
                } else if (playerID == 1) {
                    playerOnePoints += cardPoint;
                    playerThreePoints += cardPoint;
                    playerFourPoints += cardPoint;
                } else if (playerID == 2) {
                    playerOnePoints += cardPoint;
                    playerTwoPoints += cardPoint;
                    
                    
                    playerFourPoints += cardPoint;
                } else if (playerID == 3) {
                    playerOnePoints += cardPoint;
                    playerTwoPoints += cardPoint;
                    playerThreePoints += cardPoint;
                }
                if (playerPoints.get(playerID) >= winningPoints) {
                    return true;
                }
            }
        }
        return false;
    }
	public static void main(String[]args) {
		Game game=new Game();
				game.runTournament();
				System.out.println("hello");
	}

}
