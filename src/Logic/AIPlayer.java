package Logic;

/**
 * Created by João on 30/05/2015.
 */
public class AIPlayer extends Player {
    private int depth;

    public AIPlayer(int line, int column, int ID, String name, int target_line){
        super(line, column, ID, name, target_line);
        this.depth=3;
    }

    public AIPlayer(int line, int column, int ID, String name, int target_line, int depth){
        super(line, column, ID, name, target_line);
        this.depth = depth;
    }
/*
    public String getMove(GameState gs) {
        if (gs.currentPlayer()==0) {
            if (gs.numWalls1 == 10) {
                List <Square> path = gs.shortestPathToWin();
                if (gs.isValidTraversal(path.get(0))) {
                    return path.get(0).toString();
                } else {
                    List <String> validMoves = gs.validMoves();
                    return validMoves.get((int)(Math.random() * validMoves.size()));
                }
            } else {
                minimaxAlphaBetaWithMove(gs, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            }
        } else {
            if (gs.numWalls2 == 10) {
                List <Square> path = gs.shortestPathToWin();
                if (gs.isValidTraversal(path.get(0))) {
                    return path.get(0).toString();
                } else {
                    List <String> validMoves = gs.validMoves();
                    return validMoves.get((int)(Math.random() * validMoves.size()));
                }
            } else {
                minimaxAlphaBetaWithMove(gs, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            }
        }
        return bestMove;
    }*/
}
