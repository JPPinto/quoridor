package Logic;

/**
 * Created by João on 13/03/2015.
 */
public class Player {

    private Pawn pawn;
    private Wall[] wall;

    public Player(int line, int column, int ID){
        setPawn(new Pawn(line, column, ID));
        setWall(new Wall[10]);
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public Wall[] getWall() {
        return wall;
    }

    public void setWall(Wall[] wall) {
        this.wall = wall;
    }
}
