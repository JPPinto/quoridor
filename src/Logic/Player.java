package Logic;

/**
 * Created by João on 13/03/2015.
 */
public class Player {

    private Pawn pawn;
    private Wall[] wall;
    private int wallCount=0;

    public Player(int line, int column, int ID, String name){
        setPawn(new Pawn(line, column, ID, name));
        wall = new Wall[10];
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

    public Wall getWallById(int i) {
        return wall[i];
    }

    public void setWall(Wall[] wall) {
        this.wall = wall;
    }

    public void addWallByIndex(int id, Wall wall) throws ArrayIndexOutOfBoundsException{
        this.wall[id] = wall;
    }

    public int getWallCount() {
        return wallCount;
    }

    public void incWallCount(){
        wallCount++;
    }
}
