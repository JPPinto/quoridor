package Logic;

/**
 * Created by João on 13/03/2015.
 */
public class Player {

    private Pawn pawn;
    private int wallCount=0;
    private int target_line;

    public Player(int line, int column, int ID, String name, int target_line){
        setPawn(new Pawn(line, column, ID, name));
        this.target_line=target_line;
    }

    public Player(Player player){
        setPawn(new Pawn(player.getPawn().getLine(), player.getPawn().getColumn(), player.getPawn().getID(), player.getPawn().getName()));
        this.target_line=player.getTargetLine();
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public int leftWallNum() {
        return 10-wallCount;
    }

    public int usedWallNum() {
        return wallCount;
    }

    public void incWallCount(){
        wallCount++;
    }

    public int getTargetLine(){
        return target_line;
    }
}
