package inf101.sem2.game.games;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf101.grid.BlobLocation;
import inf101.grid.Location;
import inf101.sem2.GUI.DummyGraphics;
import inf101.sem2.game.Game;
import inf101.sem2.game.TestGame;
import inf101.sem2.player.Player;

class BlobWarsTest extends TestGame{
	BlobWars game;

    // This is directly copied and adapted from the included OthelloTest.java

	@BeforeEach
	protected void init() {
		super.init();
		game = new BlobWars(new DummyGraphics(), player1, player2);
		assertEquals(player1,game.getCurrentPlayer());
	}

	@Override
	public Game<?> getGame() {
		return game;
	}

	@Test
	void canConstruct() {
		assertEquals("Blob Wars", game.getName());
		BlobWars game2 = new BlobWars(game.getGraphics(), game.players());
		assertEquals("Blob Wars", game2.getName());
		boolean lik = game.getGameBoard().equals(game2.getGameBoard());
		assertTrue(lik);
	}
	
	@Test
	void startsWith4pieces() {
		assertEquals(player1,game.getGameBoard().get(new Location(0, 0)));
		assertEquals(player2,game.getGameBoard().get(new Location(0, 7)));
		assertEquals(player1,game.getGameBoard().get(new Location(7, 0)));
		assertEquals(player2,game.getGameBoard().get(new Location(7, 7)));
	}
	
	@Test
	void validMoveTest() {

        Location fromLoc = new Location(0, 0);
        Location toLoc = new Location(0, 1);
        assertTrue(game.validMove(new BlobLocation(fromLoc, toLoc)));

        game.nextPlayer();

        fromLoc = new Location(7, 7);
        toLoc = new Location(5, 7);
        assertTrue(game.validMove(new BlobLocation(fromLoc, toLoc)));

        game.nextPlayer();

        fromLoc = new Location(0, 0);
        toLoc = new Location(0, 3);
        assertFalse(game.validMove(new BlobLocation(fromLoc, toLoc)));

        game.nextPlayer();

        fromLoc = new Location(0, 0);
        toLoc = new Location(0, -1);
        assertFalse(game.validMove(new BlobLocation(fromLoc, toLoc)));

        game.nextPlayer();

        fromLoc = new Location(7, 7);
        toLoc = new Location(7, 8);
        assertFalse(game.validMove(new BlobLocation(fromLoc, toLoc)));

		}
	
	@Test
	void testMakeMove() {
        game.nextPlayer();
        Location fromLoc = new Location(7, 7);
        Location toLoc = new Location(5, 7);
		BlobLocation move = new BlobLocation(fromLoc, toLoc);
		assertTrue(game.validMove(move));
		game.makeMove(move);
		game.nextPlayer();
		assertEquals(player2, game.getGameBoard().get(toLoc));
		assertEquals(player2, game.getGameBoard().get(new Location(5,7)));
		assertEquals(player1, game.getCurrentPlayer());
	}
	
	
	@Test
	void testCopy() {
		TestGame.testCopy(game);
		assertTrue(Arrays.equals("test".getBytes(),"test".getBytes()));
	}
	
	@Test
	void testGameOver() {

    Location fromLoc = new Location(0, 0);
    Location toLoc = new Location(2, 0);
	BlobLocation move = new BlobLocation(fromLoc, toLoc);
	assertTrue(game.validMove(move));
	game.makeMove(move);
	game.nextPlayer();
	assertEquals(player2,game.getCurrentPlayer());
    assertFalse(game.gameOver());

    fromLoc = new Location(7, 7);
    toLoc = new Location(7, 5);
	move = new BlobLocation(fromLoc, toLoc);
	assertTrue(game.validMove(move));
	game.makeMove(move);
	game.nextPlayer();
	assertEquals(player1,game.getCurrentPlayer());
    assertFalse(game.gameOver());

    fromLoc = new Location(2, 0);
    toLoc = new Location(4, 0);
	move = new BlobLocation(fromLoc, toLoc);
	assertTrue(game.validMove(move));
	game.makeMove(move);
	game.nextPlayer();
	assertEquals(player2,game.getCurrentPlayer());
    assertFalse(game.gameOver());
    
    fromLoc = new Location(7, 5);
    toLoc = new Location(7, 3);
	move = new BlobLocation(fromLoc, toLoc);
	assertTrue(game.validMove(move));
	game.makeMove(move);
	game.nextPlayer();
	assertEquals(player1,game.getCurrentPlayer());
    assertFalse(game.gameOver());

    fromLoc = new Location(4, 0);
    toLoc = new Location(6, 0);
	move = new BlobLocation(fromLoc, toLoc);
	assertTrue(game.validMove(move));
	game.makeMove(move);
	game.nextPlayer();
	assertEquals(player2,game.getCurrentPlayer());
    assertFalse(game.gameOver());

    fromLoc = new Location(7, 3);
    toLoc = new Location(7, 1);
	move = new BlobLocation(fromLoc, toLoc);
	assertTrue(game.validMove(move));
	game.makeMove(move);
	game.nextPlayer();
	assertEquals(player1,game.getCurrentPlayer());
    assertTrue(game.gameOver());
	}

	
	// @Test
	// void testRestart() {
	// 	game.makeMove(new Location(4,2));
	// 	assertEquals(player1,getPlayer(4,2));
	// }
	
	// Player getPlayer(int row, int col) {
	// 	return game.getGameBoard().get(new Location(4,2));
	// }
	
	// @Test
	// void testFirstMoveNumPieces() {
	// 	Location loc = player1.getMove(game);
	// 	game.makeMove(loc);
	// 	game.nextPlayer();
	// 	assertEquals(4,game.getGameBoard().countPieces(player1));
	// 	assertEquals(1,game.getGameBoard().countPieces(player2));
	// 	loc = player2.getMove(game);
	// 	game.makeMove(loc);
	// 	assertEquals(3,game.getGameBoard().countPieces(player1));
	// 	assertEquals(3,game.getGameBoard().countPieces(player2));
	// }
}
