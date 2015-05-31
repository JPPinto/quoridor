package Logic;

/**
 * Created by João on 13/03/2015.
 */
public class Wall {

    public static enum WDirection {
        HORIZONTAL, VERTICAL;
    }

    private WDirection dir;
    private int line;
    private int column;
    private int owningPlayer;

    public Wall(WDirection dir, int line, int column, int oP){
        this.setDir(dir);
        this.setLine(line);
        this.setColumn(column);
        this.setOwningPlayer(oP);
    }

    public WDirection getDir() {
        return dir;
    }

    public void setDir(WDirection dir) {
        this.dir = dir;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isHorizontal(WDirection dir){
        return dir==WDirection.HORIZONTAL?true:false;
    }

    public int getOwningPlayer() {
        return owningPlayer;
    }

    public void setOwningPlayer(int owningPlayer) {
        this.owningPlayer = owningPlayer;
    }
}
