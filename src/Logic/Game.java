package Logic;

/**
 * Created by João on 17/04/2015.
 */
public class Game {

    private Board board;
    private Player p1;
    private Player p2;

    public Game(){

        //init board and players
        setBoard(new Board());
        setP1(new Player(0, 8, 1, "pawn1"));
        setP2(new Player(16, 8, 2, "pawn2"));

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
