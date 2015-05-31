package minimax;

import java.util.LinkedList;
import java.util.List;

public class Square {
    private int row;
    private int column;

    public Square() {
    }

    /**
     * @param row row number that this square will be on
     * @param column column number that this square will be on
     */
    public Square(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * string constructor
     * @param s
     */
    public Square(String s) {
        // TODO Should probably have preconditions to check the string is valid though that may be the job of isValidMove
        if (s.length() > 1) {
            this.row = s.charAt(1)-'1';
            this.column = s.charAt(0)-'a';
        }
    }

    /**
     * copy constructor
     * @param sq
     */
    public Square(Square sq) {
        this.row = sq.getRow();
        this.column = sq.getColumn();
    }

    /**
     * @return the row number for this square
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the column number for this square
     */
    public int getColumn() {
        return column;
    }

    /**
     * Precondition: this.row+row cannot be negative, this.column+column cannot be negative
     * @param row displacement of row
     * @param column displacement of column
     * @return new square with displacements applied
     */
    public Square neighbor(int row, int column) {
        return new Square(this.row+row, this.column+column);
    }

    /**
     * @param r numeric displacement of squares surrounding current square
     * @return list of squares surrounding current square according to displacement
     */
    public List<Square> neighbourhood (int r) {
        List <Square> neighbors = new LinkedList<Square>();
        for (int d = -r; d < r+1; d++) {
            if (d != 0) {
                if (row+d >= 0 && row+d < 9) {
                    neighbors.add(new Square(row+d,column));
                }
                if (column+d >= 0 && column+d < 9) {
                    neighbors.add(new Square(row,column+d));
                }
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        //return "[Row: "+row+", Column: "+column+"]";
        char row = '1';
        char column = 'a';
        row += this.row;
        column += this.column;
        return ""+column+row;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Square) {
            Square c = (Square)obj;
            return (c.row==row && c.column==column);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 9*row+column;
    }

    /**
     * @param sq square to check if current square is cardinal to sq.
     * @return checks if square is on same the same row or column of current square
     */
    public boolean isCardinalTo(Square sq) {
        return (row - sq.row != 0) ^ (column - sq.column != 0);
    }

    /**
     * @param sq square to get a square opposite to it (for jumping)
     * @return square that 2 squares away from current square
     */
    public Square opposite(Square sq) {
        return new Square(2*sq.row - row, 2*sq.column - column);
    }


}
