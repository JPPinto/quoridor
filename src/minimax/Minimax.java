package minimax;

import Logic.Game;
import Logic.Wall;

import java.util.List;

/**
 * Created by João on 28/05/2015.
 */
public class Minimax {

    private Object bestMove;//wall or pawn

    public double minimaxAlphaBetaWithMove (Game node, int depth, double alpha, double beta, boolean maxPlayer ) {
        if (depth == 0 || node.isOver()) {
            return heuristic(node);
        }
        if (maxPlayer) {
            double val;
            for (Object move:node.validMoves()) {
                Game child = new Game(node);
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
            for (Object move:node.validMoves()) {
                Game child = new Game(node);
                child.move(move);
                beta = Math.min(beta, minimaxAlphaBeta(child, depth-1, alpha, beta, true));
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
    }

    public double minimaxAlphaBeta(Game node, int depth, double alpha, double beta, boolean maxPlayer ) {
        if (depth == 0 || node.isOver()) {
            return heuristic(node);
        }
        if (maxPlayer) {
            for (Object move:node.validMoves()) {
                Game child = new Game(node);
                child.move(move);
                alpha = Math.max(alpha, minimaxAlphaBeta(child, depth - 1, alpha, beta, false));
                if (beta <= alpha) {
                    break;
                }
            }
            return alpha;
        } else {
            for (Object move:node.validMoves()) {
                Game child = new Game(node);
                child.move(move);
                beta = Math.min(beta, minimaxAlphaBeta(child, depth-1, alpha, beta, true));
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
    }

    public double heuristic(Game gs) {
        return gs.verifyWallPosition(gs.getP1(),gs.getP1().getWall()[0].getDir()== Wall.WDirection.HORIZONTAL?true:false, gs.getP1().getWall()[0].getLine(), gs.getP1().getWall()[0].getColumn())-
                gs.verifyWallPosition(gs.getP2(),gs.getP2().getWall()[0].getDir()== Wall.WDirection.HORIZONTAL?true:false, gs.getP2().getWall()[0].getLine(), gs.getP2().getWall()[0].getColumn());
    }
}
