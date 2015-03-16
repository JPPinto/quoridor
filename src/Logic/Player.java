package Logic;

/**
 * Created by João on 13/03/2015.
 */
public class Player {

    private Pawn pawn;
    private Wall[] wall;

    public Player(int line, int column, int ID, String name){
        setPawn(new Pawn(line, column, ID, name));
        wall = new Wall[10];
        for (int i = 0; i < wall.length; i++){
            wall[i]=new Wall(Wall.WDirection.HORIZONTAL,18,18);
        }
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
