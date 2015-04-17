package Logic;

import javafx.scene.image.Image;

/**
 * Created by João on 12/03/2015.
 */
public class Pawn {
    private int line;
    private int column;
    private int ID;
    private Image image;

    public Pawn(int line, int column, int ID, String name){
        this.setLine(line);
        this.setColumn(column);
        this.setID(ID);
        setImage(new Image(getClass().getResourceAsStream("/images/"+name+".png")));
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
        return this.line==p.line && this.column==p.column && this.ID==p.ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
