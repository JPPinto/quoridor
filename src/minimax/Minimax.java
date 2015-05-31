package minimax;

import java.util.List;

/**
 * @author louistiao
 * An abstract AIPlayer class which provides standard minimax algorithm methods for inheriting classes to use
 */
public class Minimax {

    String bestMove = new String();
    int depth;

    public Minimax(int depth){
        this.depth=depth;
    }

    public String getMove(GameState gs) {
        if (gs.currentPlayer()==0) {
            if (gs.numWalls1 == 10) {
                List<Square> path = gs.shortestPathToWin();
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
    }

    public int minimaxAlphaBetaWithMove (GameState node, int depth, int alpha, int beta, boolean maxPlayer ) {
        if (depth == 0 || node.isOver()) {
            return heuristic(node);
        }
        if (maxPlayer) {
            int val;
            for (String move:node.validMoves()) {
                GameState child = new GameState(node);
                child.move(move);
                val = minimaxAlphaBeta(child, depth - 1, alpha, beta, false);
                if (val > alpha) {
                    alpha = val;
                    bestMove = move;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return alpha;
        } else {
            for (String move:node.validMoves()) {
                GameState child = new GameState(node);
                child.move(move);
                beta = Math.min(beta, minimaxAlphaBeta(child, depth-1, alpha, beta, true));
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
    }

    public int minimaxAlphaBeta(GameState node, int depth, int alpha, int beta, boolean maxPlayer ) {
        if (depth == 0 || node.isOver()) {
            return heuristic(node);
        }
        if (maxPlayer) {
            for (String move:node.validMoves()) {
                GameState child = new GameState(node);
                child.move(move);
                alpha = Math.max(alpha, minimaxAlphaBeta(child, depth-1, alpha, beta, false));
                if (beta <= alpha) {
                    break;
                }
            }
            return alpha;
        } else {
            for (String move:node.validMoves()) {
                GameState child = new GameState(node);
                child.move(move);
                beta = Math.min(beta, minimaxAlphaBeta(child, depth-1, alpha, beta, true));
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
    }

    public int heuristic(GameState gs) {
        return gs.shortestPathToRow(gs.player1Square, 0).size()-gs.shortestPathToRow(gs.player2Square, 8).size();
    }

    public Object fromString(String move){
        if(move.length() == 3){
            return new Wall(move);
        } else {
            return new Square(move);
        }
    }
}
