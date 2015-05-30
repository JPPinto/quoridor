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
        p1 = new Player(0, 8, 1, "pawn1", 16);
        p2 = new Player(16, 8, 2, "pawn2", 0);

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

    public boolean createWall(Player p1,Player p2, Boolean horizontal_wall, int line, int column){
        if(board.getBoard()[line][column]!= Board.BoardState.WALL && verifyWallPosition(p1, horizontal_wall, line, column)<15000 && verifyWallPosition(p2, horizontal_wall, line, column)<15000){
            p1.addWallByIndex(p1.getWallCount(), new Wall(horizontal_wall ? Wall.WDirection.HORIZONTAL : Wall.WDirection.VERTICAL, line, column));
            Wall wall = p1.getWallById(p1.getWallCount());

            board.createWall(wall, horizontal_wall, line, column);
            p1.incWallCount();
            return true;
        }else{
            return false;
        }
    }

    public double verifyWallPosition(Player p, Boolean horizontal_wall,int line, int column){
        double minCost=20000;
        if(horizontal_wall) {
            if (board.getBoard()[line][column + 1] == Board.BoardState.WALL || board.getBoard()[line][column - 1] == Board.BoardState.WALL) {
                return minCost;
            }
        }else{
            if (board.getBoard()[line+1][column] == Board.BoardState.WALL || board.getBoard()[line-1][column] == Board.BoardState.WALL) {
                return minCost;
            }
        }
        fillEmptyWall(horizontal_wall, line, column, Board.BoardState.WALL);

        //Create Graph
        Vertex[][] vertex = new Vertex[17][17];
        createGraph(vertex);

        Dijkstra dijkstra= new Dijkstra();
        dijkstra.computePaths(vertex[p.getPawn().getLine()][p.getPawn().getColumn()]);
        for(int i=0; i<vertex[p.getTargetLine()].length; i++){
            double totalCost=vertex[p.getTargetLine()][i].minDistance;
            System.out.println("Distance to " + vertex[p.getPawn().getLine()][p.getPawn().getColumn()] + ": " + totalCost);
            List<Vertex> path = dijkstra.getShortestPathTo(vertex[p.getTargetLine()][i]);
            System.out.println("Path: " + path);

            if(minCost>totalCost){
                minCost=totalCost;
            }
        }

        fillEmptyWall(horizontal_wall, line, column, Board.BoardState.WALL_BLOCK);
        return minCost;
    }

    public void createGraph(Vertex[][] vertex){
        //Adding Vertices
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                vertex[i][j] = new Vertex(i + "|" + j);
            }
        }
        //Adding Edges
        int cost = 1;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if(board.getBoard()[i][j]== Board.BoardState.WALL || board.getBoard()[i][j]== Board.BoardState.PLAYER){
                    cost=10000;
                }
                ArrayList<Edge> edges = new ArrayList<Edge>();
                addEdge(edges, vertex, i+1, j,  cost);
                addEdge(edges, vertex, i-1, j, cost);
                addEdge(edges, vertex, i, j+1, cost);
                addEdge(edges, vertex, i, j-1, cost);

                vertex[i][j].adjacencies = new Edge[edges.size()];
                vertex[i][j].adjacencies = edges.toArray(vertex[i][j].adjacencies);
                cost=1;
            }
        }
    }

    public void addEdge(ArrayList<Edge> edges, Vertex[][] vertex, int i, int j, int cost){
        try{
            edges.add(new Edge(vertex[i][j], cost));
        }catch(ArrayIndexOutOfBoundsException ex){

        }
    }

    public void fillEmptyWall(Boolean horizontal_wall,int line, int column, Board.BoardState state){
        if(horizontal_wall){
            board.getBoard()[line][column-1]=state;
            board.getBoard()[line][column]=state;
            board.getBoard()[line][column+1]=state;

            if(column-3>=0 && board.getBoard()[line][column-3]== Board.BoardState.WALL){
                board.getBoard()[line][column-2]= Board.BoardState.WALL;
            }
            if(column+3<17 && board.getBoard()[line][column+3]== Board.BoardState.WALL){
                board.getBoard()[line][column+2]=Board.BoardState.WALL;
            }
        }else{
            board.getBoard()[line-1][column]=state;
            board.getBoard()[line][column]=state;
            board.getBoard()[line+1][column]=state;

            if(line-3>=0 && board.getBoard()[line-3][column]== Board.BoardState.WALL){
                board.getBoard()[line-2][column]= Board.BoardState.WALL;
            }
            if(line+3<17 && board.getBoard()[line+3][column]== Board.BoardState.WALL){
                board.getBoard()[line+2][column]= Board.BoardState.WALL;
            }
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
