package cracy8;
/**
 * Main game engine that runs entire tournament of Crazy 8 game
 */
public class Game {
	Games games=new Games();
	 /**
     * runs one tournament of Crazy 8's and plays multiple games until tournament has ended
     */
    public void runTournament() {
        games.initializeTournament();
        games.addPlayersToPlayerList();
        games.addPlayerCardsToAllPlayerCardsList();
        games.addPlayerPointsToList();

        while (!games.isTournamentOver()) {
        	games.playGame();
        }
    }

 
}