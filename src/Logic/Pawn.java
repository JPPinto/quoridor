package Logic;

/**
 * Created by João on 12/03/2015.
 */
public class Pawn {
    private int line;
    private int column;
    private int ID;

    public Pawn(int line, int column, int ID){
        this.setLine(line);
        this.setColumn(column);
        this.setID(ID);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        if(!(line<0 || line>16)){
            this.line = line;
        }
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        if(!(column<0 || column>16)) {
            this.column = column;
        }
    }

    public boolean equals(Pawn p){
        return this.line==p.line && this.column==p.column;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
