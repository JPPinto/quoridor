package Logic;

import minimax.Dijkstra;
import minimax.Edge;
import minimax.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by João on 17/04/2015.
 */
public class Game {

    private Board board;
    private Player p1;
    private Player p2;

    public Game(){

        //init board and players
        board = new Board();
        p1 = new Player(0, 8, 1, "pawn1");
        p2 = new Player(16, 8, 2, "pawn2");

    }

    public void makeMove(Player p, int direction){
        board.setBoard(p.getPawn(), direction);
        board.printMatrix();

        verifyWinning(p.getPawn());
    }

    public void verifyWinning(Pawn p){
        if(p.getLine()==16 && p.getID()==1){
            System.out.println("Player 1 - You win");
        }else if(p.getLine()==0 && p.getID()==2){
            System.out.println("Player 2 - You win");
        }
    }

    public void createWall(Player p1,Player p2, Boolean horizontal_wall, int line, int column){
        if(board.getBoard()[line][column]!= Board.BoardState.WALL && verifyWallPosition(p1, horizontal_wall, line, column) && verifyWallPosition(p2, horizontal_wall, line, column)){
            p1.addWallByIndex(p1.getWallCount(), new Wall(horizontal_wall ? Wall.WDirection.HORIZONTAL : Wall.WDirection.VERTICAL, line, column));
            Wall wall = p1.getWallById(p1.getWallCount());

            board.createWall(wall, horizontal_wall, line, column);
            p1.incWallCount();
        }else{
            System.out.println("You cant place wall here. Play again.");
        }
    }

    public boolean verifyWallPosition(Player p, Boolean horizontal_wall,int line, int column){
        if(horizontal_wall) {
            if (board.getBoard()[line][column + 1] == Board.BoardState.WALL || board.getBoard()[line][column - 1] == Board.BoardState.WALL) {
                return false;
            }
        }else{
            if (board.getBoard()[line+1][column] == Board.BoardState.WALL || board.getBoard()[line-1][column] == Board.BoardState.WALL) {
                return false;
            }
        }
        fillEmptyWall(horizontal_wall, line, column, Board.BoardState.WALL);

        //Adding Vertices
        Vertex[][] vertex = new Vertex[17][17];
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                vertex[i][j] = new Vertex(i + "|" + j);
            }
        }
        //Adding Edges
        int cost = 1;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if(board.getBoard()[i][j]== Board.BoardState.WALL){
                    cost=10000;
                }
                ArrayList<Edge> edges = new ArrayList<Edge>();
                if (i + 1 < 17) {
                    edges.add(new Edge(vertex[i + 1][j], cost));
                }
                if (i - 1 >= 0) {
                    edges.add(new Edge(vertex[i - 1][j], cost));
                }
                if (j + 1 < 17) {
                    edges.add(new Edge(vertex[i][j + 1], cost));
                }
                if (j - 1 >= 0) {
                    edges.add(new Edge(vertex[i][j - 1], cost));
                }
                vertex[i][j].adjacencies = new Edge[edges.size()];
                vertex[i][j].adjacencies = edges.toArray(vertex[i][j].adjacencies);
                cost=1;
            }
        }
        Dijkstra dijkstra= new Dijkstra();
        dijkstra.computePaths(vertex[p.getPawn().getLine()][p.getPawn().getColumn()]);
        double totalCost=vertex[16][8].minDistance;
        System.out.println("Distance to " + vertex[p.getPawn().getLine()][p.getPawn().getColumn()] + ": " + totalCost);
        List<Vertex> path = dijkstra.getShortestPathTo(vertex[16][8]);
        System.out.println("Path: " + path);

        fillEmptyWall(horizontal_wall, line, column, Board.BoardState.WALL_BLOCK);

        if(totalCost>1000){
            return false;
        }
        return true;
    }

    public void fillEmptyWall(Boolean horizontal_wall,int line, int column, Board.BoardState state){
        if(horizontal_wall){
            board.getBoard()[line][column-1]=state;
            board.getBoard()[line][column]=state;
            board.getBoard()[line][column+1]=state;
        }else{
            board.getBoard()[line-1][column]=state;
            board.getBoard()[line][column]=state;
            board.getBoard()[line+1][column]=state;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }
}
