package inf101.sem2.game.games;

import java.util.ArrayList;
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
	public boolean validMove(BlobLocation loc) {
		if (!board.canPlace(loc.getLocTo()))
			return false;
        if (((Math.abs(loc.getLocFrom().row) - Math.abs(loc.getLocTo().row) > 2) || (Math.abs(loc.getLocFrom().col) - Math.abs(loc.getLocTo().col) > 2))){
            return false;
        }
        if (!(board.get(loc.getLocFrom()) == getCurrentPlayer())) {
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
		List<Location> toFlip = getFlipTargets(locTo);

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

    private List<Location> getFlipTargets(Location loc){
        Location location = loc;
        ArrayList<Location> enemyNeighbors = new ArrayList<Location>();
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

	// Got help from Brage Aasen, 
	// TODO funker ikke for 2 steg
	@Override
	public List<BlobLocation> getPossibleMoves() {
		ArrayList<BlobLocation> moves = new ArrayList<>();
		for (Location fromLoc : board.locations()) {
            for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
                Location toLoc = fromLoc.getNeighbor(dir);
                BlobLocation moved = new BlobLocation(fromLoc, toLoc);
                if (validMove(moved)) {
                    moves.add(moved);
                }
                // for (GridDirection dir2 : GridDirection.EIGHT_DIRECTIONS) {
                //     toLoc = fromLoc.getNeighbor(dir2);
                //     BlobLocation moved2 = new BlobLocation(fromLoc, toLoc);
                //     if (moves.contains(moved2)) {
                //         continue;
                //     }
                //     if (validMove(moved2)){
                //         moves.add(moved2);
                //     }
                // }
            }
		}
		return moves;
	}

}