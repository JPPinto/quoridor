package Logic;

import minimax.Dijkstra;
import minimax.Edge;
import minimax.Minimax;
import minimax.Vertex;
import java.util.ArrayList;

/**
 * Created by João on 17/04/2015.
 */
public class Game {

    private Board board;
    private Player p1;
    private Player p2;
    private Minimax m1;
    private Minimax m2;
    private int playerPlaying=1;
    private ArrayList<Wall> walls;
    private int turn=0;

    public Game(){

        //init board and players
        board = new Board();
        p1 = new Player(16, 8, 1, "pawn1", 0);
        p2 = new Player(0, 8, 2, "pawn2", 16);
        walls = new ArrayList<Wall>();
    }

    public Game(int depth){

        //init board and players
        board = new Board();
        p1 = new Player(16, 8, 1, "pawn1", 0);
        p2 = new Player(0, 8, 2, "pawn2", 16);
        m1 = new Minimax(depth);
        walls = new ArrayList<Wall>();
    }

    public Game(int depth, int depth2){

        //init board and players
        board = new Board();
        p1 = new Player(16, 8, 1, "pawn1", 0);
        p2 = new Player(0, 8, 2, "pawn2", 16);
        m1 = new Minimax(depth);
        m2 = new Minimax(depth2);
        walls = new ArrayList<Wall>();
    }

    public void makeMove(Player p, int direction){
        board.setBoard(p.getPawn(), direction);
        board.printMatrix();
    }

    public void verifyWinning(Pawn p){
        if(p.getLine()==0 && p.getID()==1){
            System.out.println("Player 1 - You win");
        }else if(p.getLine()==16 && p.getID()==2){
            System.out.println("Player 2 - You win");
        }
    }

    public boolean createWall(boolean horizontal_wall, int line, int column){
        if(currentPlayerPlaying().usedWallNum()<10 && board.getBoard()[line][column]!= Board.BoardState.WALL && verifyWallPosition(currentPlayerPlaying(), horizontal_wall, line, column)<1000 && verifyWallPosition(otherPlayerPlaying(), horizontal_wall, line, column)<1000){
            Wall wall = new Wall(horizontal_wall ? Wall.WDirection.HORIZONTAL : Wall.WDirection.VERTICAL, line, column, playerPlaying);
            walls.add(wall);
            board.createWall(horizontal_wall, line, column);
            currentPlayerPlaying().incWallCount();

            return true;
        }else{
            return false;
        }
    }

    public double verifyWallPosition(Player p, boolean horizontal_wall,int line, int column){
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
        createGraph(vertex, board.getBoard());

        Dijkstra dijkstra= new Dijkstra();
        dijkstra.computePaths(vertex[p.getPawn().getLine()][p.getPawn().getColumn()]);
        for(int i=0; i<vertex[p.getTargetLine()].length; i++){
            double totalCost=vertex[p.getTargetLine()][i].minDistance;
            /*System.out.println("Distance to " + vertex[p.getPawn().getLine()][p.getPawn().getColumn()] + ": " + totalCost);
            List<Vertex> path = dijkstra.getShortestPathTo(vertex[p.getTargetLine()][i]);
            System.out.println("Path: " + path);*/

            if(minCost>totalCost){
                minCost=totalCost;
            }
        }

        fillEmptyWall(horizontal_wall, line, column, Board.BoardState.WALL_BLOCK);
        if(minCost>10000 && minCost<20000){
            minCost-=9999;
        }
        return minCost;
    }

    public void createGraph(Vertex[][] vertex, Board.BoardState [][]board){
        //Adding Vertices
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                vertex[i][j] = new Vertex(i + "|" + j);
            }
        }
        //Adding Edges
        int cost = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j]== Board.BoardState.WALL || board[i][j]== Board.BoardState.PLAYER){
                    cost=10000;
                }
                ArrayList<Edge> edges = new ArrayList<Edge>();
                addEdge(edges, vertex, i+1, j,  cost);
                addEdge(edges, vertex, i - 1, j, cost);
                addEdge(edges, vertex, i, j + 1, cost);
                addEdge(edges, vertex, i, j-1, cost);

                vertex[i][j].adjacencies.addAll(edges);
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

    public int getCurrentPlayer(){
        return playerPlaying;
    }

    public int getOtherPlayer(){
        if(playerPlaying==1){
            return 2;
        }
        return 1;
    }

    public void setCurrentPlayer(int playerPlaying){
        this.playerPlaying=playerPlaying;
    }

    public ArrayList<Wall> getWall(){
        return walls;
    }

    public Player currentPlayerPlaying(){
        if(playerPlaying==1){
            return p1;
        }
        return p2;
    }

    public Player otherPlayerPlaying(){
        if(playerPlaying==1){
            return p2;
        }
        return p1;
    }

    public void nextPlayer(){
        if(playerPlaying==1){
            playerPlaying=2;
        }else{
            playerPlaying=1;
        }
    }

    public Minimax getM1() {
        return m1;
    }

    public void setM1(Minimax m1) {
        this.m1 = m1;
    }

    public Minimax getM2() {
        return m2;
    }

    public void setM2(Minimax m2) {
        this.m2 = m2;
    }

    public int getTurn(){
        return turn;
    }

    public void nextTurn(){
        turn += 1;
    }
}
