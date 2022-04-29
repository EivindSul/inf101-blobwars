package inf101.sem2.game.games;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import inf101.grid.BlobLocation;
// import inf101.grid.Grid;
import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.sem2.GUI.Graphics;
import inf101.sem2.game.Game;
import inf101.sem2.game.GameBoard;
import inf101.sem2.player.Player;

public class BlobWars extends Game<BlobLocation> {

	// This class name could have been more descriptive, too bad!
	// Could for example be used to move chess pieces, so it would be more logical to call it move or fromToLocation

	public BlobWars(Graphics graphics, Player p1, Player p2) {
		super(new GameBoard(8, 8), graphics);
		addPlayer(p1);
		addPlayer(p2);
		initializeBoard();
	}

	public BlobWars(Graphics graphics, Iterable<Player> players) {
		super(new GameBoard(8, 8), graphics, players);
		initializeBoard();
	}

	private void initializeBoard() {
		board.set(new Location(0, 0), getCurrentPlayer());
		board.set(new Location(7, 0), getCurrentPlayer());
		players.nextPlayer();
		board.set(new Location(0, 7), getCurrentPlayer());
		board.set(new Location(7, 7), getCurrentPlayer());
		players.nextPlayer();
	}

	@Override
	public Game<BlobLocation> copy() {
		Game<BlobLocation> newGame = new BlobWars(this.graphics, players);
		copyTo(newGame);
		return newGame;
	}

	@Override
	public boolean isWinner(Player player) {
		if (!gameOver())
			return false;
		int count = getGameBoard().countPieces(player);
		for (Player p : players) {
			if (p.equals(player))
				continue;
			if (board.countPieces(p) >= count)
				return false;
		}
		return true;
	}

	@Override
	public boolean gameOver() {
		return getPossibleMoves().isEmpty();
	}

	@Override
	public String getName() {
		return "Blob Wars";
	}

	@Override
	public boolean validMove(BlobLocation move) {
		Location locFrom = move.getLocFrom();
		Location locTo = move.getLocTo();

		if (!board.isOnBoard(locFrom) || !board.isOnBoard(locTo)){
			return false;
		}
		if (!board.canPlace(locTo)){
			return false;
		}
		if (locFrom.absoluteDistanceTo(locTo) > 2){
			return false;
		}
        if (!(board.get(locFrom) == getCurrentPlayer())) {
            return false;
        }

        return true;
	}

    public boolean isOpponent(Location loc) {
		if (!board.isOnBoard(loc))
			return false;
		if (board.get(loc) == getCurrentPlayer())
			return false;
		if (board.get(loc) == null)
			return false;
		return true;
	}

	@Override
	public void makeMove(BlobLocation move) {
		if (!validMove(move))
			throw new IllegalArgumentException("Cannot make move:\n" + move);
        Location locFrom = move.getLocFrom();
        Location locTo = move.getLocTo();
		Player current = getCurrentPlayer();
		List<Location> toFlip = getOpponentNeighbors(locTo);

        if(this.isLongMove(move)){
            board.movePiece(locFrom, locTo);
        }
        else {
            board.set(locTo, current);
        }

		displayBoard();

		for (Location l : toFlip) {
			board.swap(l, current);
		}
		displayBoard();
	}

    private boolean isLongMove(BlobLocation move){
        if (move.getLocFrom().allNeighbors().contains(move.getLocTo())){
            return false;
        }
        else return true;
    }

    private List<Location> getOpponentNeighbors(Location loc){
        Location location = loc;
        List<Location> enemyNeighbors = new ArrayList<Location>();
        for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
            Location neighbor = location.getNeighbor(dir);
            if (isOpponent(neighbor)){
                enemyNeighbors.add(neighbor);
            }
        }
        return enemyNeighbors;
    }

	@Override
	public void restart() {
		super.restart();
		initializeBoard();
	}

	@Override
	public int score(Player player) {
		int otherPiecesSum = 0;
		for (Player p : players) {
			if (player.equals(p))
				continue;
			otherPiecesSum += board.countPieces(p);
		}
		int nPlayerPieces = board.countPieces(player);
		return nPlayerPieces - otherPiecesSum;
	}

	// Got help from Brage Aasen, baa027
	@Override
	public List<BlobLocation> getPossibleMoves() {
		List<BlobLocation> moves = new ArrayList<>();
		for (Location fromLoc : board.locations()) {
			Collection<Location> neighbors1 = fromLoc.allNeighbors();
			for (Location toLoc : neighbors1){
                BlobLocation moved = new BlobLocation(fromLoc, toLoc);
                if (validMove(moved)) {
                    moves.add(moved);
                }
				Collection<Location> neighbors2 = toLoc.allNeighbors();

				for (Location toLoc2 : neighbors2){
					moved = new BlobLocation(fromLoc, toLoc2);
					if (validMove(moved)) {
						moves.add(moved);
					}
				}
			}
		}
		return moves;
	}
}