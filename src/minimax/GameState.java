package minimax;

import Logic.Game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameState {
    static final int BOARD_SIZE = 9;
    static final char PLAYER_1_ICON = 'A';
    static final char PLAYER_2_ICON = 'B';

    protected HashMap <Square,LinkedList<Square>> adjacencyList = new HashMap <Square,LinkedList<Square>> ();
    Square player1Square = new Square("e1");
    Square player2Square = new Square("e9");
    LinkedList <Wall> walls = new LinkedList<Wall>();
    int numWalls1 = 0;
    int numWalls2 = 0;
    int turn = 0;


    public GameState() {
        // Initialize adjacency list
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                LinkedList<Square> adjacent = new LinkedList<Square>();
                for (int d = -1; d < 2; d++) {
                    if (d != 0) { // Vertices are not self-connecting
                        if (i+d >= 0 && i+d < BOARD_SIZE)
                            adjacent.add(new Square(i+d,j));
                        if (j+d >= 0 && j+d < BOARD_SIZE)
                            adjacent.add(new Square(i,j+d));
                    }
                }
                adjacencyList.put(new Square(i,j), adjacent);
            }
        }
    }

    public GameState(GameState gs) {
        for (Square sq:gs.adjacencyList.keySet()) {
            LinkedList<Square> list = new LinkedList<Square>();
            list.addAll(gs.adjacencyList.get(sq));
            adjacencyList.put(sq, list);
        }
        player1Square = new Square(gs.player1Square);
        player2Square = new Square(gs.player2Square);
        walls.addAll(gs.walls);
        turn = gs.turn;
    }

    public GameState(Game game) {
        player1Square = new Square(game.getP1().getPawn().getLine()/2, game.getP1().getPawn().getColumn()/2);
        player2Square = new Square(game.getP2().getPawn().getLine()/2, game.getP2().getPawn().getColumn()/2);
        //walls.addAll(gs.walls);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                LinkedList<Square> adjacent = new LinkedList<Square>();
                for (int d = -1; d < 2; d++) {
                    if (d != 0) { // Vertices are not self-connecting
                        if (i+d >= 0 && i+d < BOARD_SIZE)
                            adjacent.add(new Square(i+d,j));
                        if (j+d >= 0 && j+d < BOARD_SIZE)
                            adjacent.add(new Square(i,j+d));
                    }
                }
                adjacencyList.put(new Square(i,j), adjacent);
            }
        }
        //placeWall(Wall wall)
        for (int i = 0; i < game.getWall().size(); i++) {
            placeWall(new Wall(new Square(game.getWall().get(i).getLine(), game.getWall().get(i).getColumn()), game.getWall().get(i).getDir()));
        }
    }

    /**
     * String constructor
     * @param moves
     */
        public GameState(List <String> moves) {
        this();
        for (String e:moves) {
            move(e);
        }
    }

    protected void addEdge (Square a, Square b) {
        // Edges are undirected
        adjacencyList.get(a).add(b);
        adjacencyList.get(b).add(a);
    }

    public int currentPlayer () {
        return turn%2;
    }

    public Square currentPlayerPosition () {
        return currentPlayer () == 0 ? player1Square : player2Square;
    }

    protected void displayAdjacencyList () {
        for (Square e:adjacencyList.keySet()) {
            System.out.println(e+": "+adjacencyList.get(e));
        }
    }

    protected boolean hasPathToGoal () {
        return !(shortestPathToRow(player1Square, 0).isEmpty() || shortestPathToRow(player2Square, 8).isEmpty());
    }

    protected boolean hasPlayer (int i, int j) {
        if (i%2 == 1 && j%2 == 1) {
            Square transformed = transform(i, j);
            return player1Square.equals(transformed) || player2Square.equals(transformed);
        }
        return false;
    }

    protected boolean hasWall (int i, int j) {
        if (i%2==0) {
            return walls.contains(new Wall(transform(i-1, j), Logic.Wall.WDirection.HORIZONTAL)) || walls.contains(new Wall(transform(i-1, j-2), Logic.Wall.WDirection.HORIZONTAL));
        } else {
            return walls.contains(new Wall(transform(i, j-1), Logic.Wall.WDirection.VERTICAL)) || walls.contains(new Wall(transform(i-2, j-1), Logic.Wall.WDirection.VERTICAL));
        }
    }

    /**
     * For printing. Transform (i, j) coordinate to square coordinates.
     * @param i
     * @param j
     * @return
     */
    protected Square transform (int i, int j) {
        return new Square((i-1)>>1, (j-1)>>1);
    }

    public boolean isOver() {
        return player1Square.getRow() == 0 || player2Square.getRow() == 8;
    }

    public int winner () {
        return player1Square.getRow() == 0 ? 0 : 1;
    }

    protected char winnerIcon () {
        return winner() == 0 ? PLAYER_1_ICON : PLAYER_2_ICON;
    }

    /**
     * @param move
     * @return true if the string represents a traversal move
     */
    protected boolean isTraversal (String move) {
        return isValidSyntax(move) && move.length() == 2;
    }

    protected boolean isValidSyntax (String move) {
        Pattern p = Pattern.compile("[a-i][0-9][hv]?");
        Matcher m = p.matcher(move);
        return m.matches();
    }

    public boolean isValidTraversal (Square dest) {
        if (dest.equals(currentPlayerPosition()) || dest.equals(otherPlayerPosition())) {
            return false;
        } else if (adjacencyList.get(currentPlayerPosition()).contains(dest)) {
            return true;
        } else if (adjacencyList.get(currentPlayerPosition()).contains(otherPlayerPosition())) {
            if (adjacencyList.get(otherPlayerPosition()).contains(currentPlayerPosition().opposite(otherPlayerPosition()))) {
                return adjacencyList.get(otherPlayerPosition()).contains(dest) && currentPlayerPosition().isCardinalTo(dest);
            } else {
                return adjacencyList.get(otherPlayerPosition()).contains(dest);
            }
        }
        return false;
    }

    public int currentPlayerNumWalls() {
        if (currentPlayer()==0) {
            return numWalls1;
        } else {
            return numWalls2;
        }
    }

    public int otherPlayerNumWalls() {
        if (currentPlayer()==0) {
            return numWalls2;
        } else {
            return numWalls1;
        }
    }

    public boolean isValidWallPlacement (Wall wall) {

        if (currentPlayerNumWalls() >= 10) {
            return false;
        }

        // Check wall is not being placed at border
        if (wall.northWest.getColumn()==8 || wall.northWest.getRow()==8) {
            return false;
        }

        // Check wall is not intersecting existing wall
        if (wall.orientation == Logic.Wall.WDirection.HORIZONTAL) {
            if (walls.contains(wall) || walls.contains(wall.neighbor(0, 0, Logic.Wall.WDirection.VERTICAL)) || walls.contains(wall.neighbor(0, -1, Logic.Wall.WDirection.HORIZONTAL)) || walls.contains(wall.neighbor(0, 1, Logic.Wall.WDirection.HORIZONTAL))) {
                return false;
            }
        } else {
            if (walls.contains(wall) || walls.contains(wall.neighbor(0, 0, Logic.Wall.WDirection.HORIZONTAL)) || walls.contains(wall.neighbor(-1, 0, Logic.Wall.WDirection.VERTICAL)) || walls.contains(wall.neighbor(1, 0, Logic.Wall.WDirection.VERTICAL))) {
                return false;
            }
        }

        // If the wall does not intersect existing walls, proceed to update the graph
        // to remove associated edges
        if (wall.orientation==Logic.Wall.WDirection.HORIZONTAL) {
            removeEdge(wall.northWest, wall.northWest.neighbor(1, 0));
            removeEdge(wall.northWest.neighbor(0, 1), wall.northWest.neighbor(1,1));
        } else {
            removeEdge(wall.northWest, wall.northWest.neighbor(0, 1));
            removeEdge(wall.northWest.neighbor(1, 0), wall.northWest.neighbor(1,1));
        }

        // Check if we have removed a path for any player by the
        // placement of a wall
        boolean hasPath = hasPathToGoal();
        if (wall.orientation==Logic.Wall.WDirection.HORIZONTAL) {
            addEdge(wall.northWest, wall.northWest.neighbor(1, 0));
            addEdge(wall.northWest.neighbor(0, 1), wall.northWest.neighbor(1,1));
        } else {
            addEdge(wall.northWest, wall.northWest.neighbor(0, 1));
            addEdge(wall.northWest.neighbor(1, 0), wall.northWest.neighbor(1,1));
        }
        return hasPath;
    }

    protected boolean isWallPlacement (String move) {
        return isValidSyntax(move) && move.length() == 3;
    }

    public boolean move (String move) {
        boolean valid = true;
        valid &= !isOver();
        if (isWallPlacement(move)) {
            Wall wall = new Wall(move);
            valid &= isValidWallPlacement(wall);
            if (valid) {
                placeWall(wall);
            }
        } else {
            Square sq = new Square(move);
            valid &= isValidTraversal(sq);
            if (valid) {
                traverse(sq);
            }
        }
        if (valid) {
            turn++;
        }
        return valid;
    }

    public Square otherPlayerPosition () {
        return currentPlayerPosition().equals(player1Square) ? player2Square : player1Square;
    }

    protected void placeWall(Wall wall) {
        if (currentPlayer()==0) {
            numWalls1++;
        } else {
            numWalls2++;
        }
        walls.add(wall);
        if (wall.getOrientation() == Logic.Wall.WDirection.HORIZONTAL) {
            removeEdge(wall.northWest, wall.northWest.neighbor(1, 0));
            removeEdge(wall.northWest.neighbor(0, 1), wall.northWest.neighbor(1,1));
        } else {
            removeEdge(wall.northWest, wall.northWest.neighbor(0, 1));
            removeEdge(wall.northWest.neighbor(1, 0), wall.northWest.neighbor(1,1));
        }
    }

    /**
     * Called after verifying (i, j) coordinate has player
     * @param i
     * @param j
     * @return icon representing the player at the coordinate.
     */
    protected char player (int i, int j) {
        return player1Square.equals(transform(i,j)) ? PLAYER_1_ICON : PLAYER_2_ICON;
    }

    // TODO Kind of a first world problem, but could make use of Java's unicode support to render a more aesthetic board.
    /**
     * For ease of printing. Different characters are rendered for different coordinates. This method returns these.
     * @param i
     * @param j
     * @return
     */
    private String print (int i, int j) {
        StringBuilder sb = new StringBuilder();
        if ((i+j)%2 == 0) {
            if (j%2 == 0) {
                sb.append ("+");
            } else {
                if (hasPlayer(i,j))
                    sb.append (" "+player(i,j)+" ");
                else
                    sb.append ("   ");
            }
        } else {
            if (i%2 == 0) {
                if (hasWall(i,j))
                    sb.append ("###");
                else
                    sb.append ("---");
            } else {
                if (hasWall(i,j))
                    sb.append ("#");
                else
                    sb.append ("|");
            }

        }
        if (j == 2*BOARD_SIZE)
            sb.append ("\n");
        return sb.toString();
    }

    // Need to check preconditions
    /**
     * Remove the corresponding entries from the list for a and b respectively
     * @param a
     * @param b
     */
    protected void removeEdge (Square a, Square b) {
        adjacencyList.get(a).remove(b);
        adjacencyList.get(b).remove(a);
    }

    /**
     * @return list representing shortest path for current player to win
     */
    public List<Square> shortestPathToWin () {
        if (currentPlayer()==0) {
            return shortestPathToRow(player1Square, 0);
        } else {
            return shortestPathToRow(player2Square, 8);
        }
    }


    /**
     * @param row
     * @return list representing shortest path for current player to row
     */
    public List<Square> shortestPathToRow (int row) {
        return shortestPathToRow(currentPlayerPosition(), row);
    }

    /**
     * @param src
     * @param row
     * @return shortest path from src to specified row
     * N.B. There is a bug with recognizing valid jump moves from this state. See design report.
     */
    protected List<Square> shortestPathToRow (Square src, int row) {
        List<Square> path = new LinkedList<Square>();
        Queue <Square> queue = new LinkedList<Square>();
        HashMap <Square,Square> parentNode = new HashMap<Square,Square>();
        // enqueue start configuration onto queue
        queue.add(src);
        // mark start configuration
        parentNode.put(src, null);
        while (!queue.isEmpty()) {
            Square t = queue.poll();
            if (t.getRow() == row) {
                while (!t.equals(src)) {
                    path.add(t);
                    t = parentNode.get(t);
                }
                Collections.reverse(path);
                return path;
            }
            for (Square e: adjacencyList.get(t)) {
                if (!parentNode.containsKey(e)) {
                    parentNode.put(e, t);
                    queue.add(e);
                }
            }
        }
        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Turn: "+turn+" | Player to Move: "+(currentPlayer() == 0 ? PLAYER_1_ICON : PLAYER_2_ICON)+" | Walls Remaining: "+(10-currentPlayerNumWalls())+"\n");
        sb.append("   ");
        for (char c = 'a' ; c < 'j' ; c++)
            sb.append(c+"   ");
        sb.append("\n");
        for (int i = 0 ; i < 2*BOARD_SIZE+1 ; i++) {
            for (int j = 0 ; j < 2*BOARD_SIZE+1 ; j++) {
                if (j == 0) {
                    if (i%2==0)
                        sb.append(" ");
                    else
                        sb.append((i+1)>>1);
                }
                sb.append (print (i,j));
            }
        }
        return sb.toString();
    }

    /**
     * Called after traversal validity test is passed. Updates corresponding fields
     * @param sq
     */
    protected void traverse(Square sq) {
        if (currentPlayer()==0) {
            player1Square = sq;
        } else {
            player2Square = sq;
        }
    }

    /**
     * @return current turn
     */
    public int turn () {
        return turn;
    }

    /**
     * @return list of valid moves from the current state
     * N.B. There is a bug with recognizing valid jump moves from this state. See design report.
     */
    public List<String> validMoves() {
        List<String> validMoves = new LinkedList<String>();
        for (Square sq:currentPlayerPosition().neighbourhood(2)) {
            if (isValidTraversal(sq)) {
                validMoves.add(sq.toString());
            }
        }
        for (int i = 0; i < BOARD_SIZE ; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Square sq = new Square(i,j);
                for (Logic.Wall.WDirection o: Logic.Wall.WDirection.values()) {
                    Wall wall = new Wall(sq, o);
                    if (isValidWallPlacement(wall)) {
                        validMoves.add(wall.toString());
                    }
                }
            }
        }
        return validMoves;
    }
}
