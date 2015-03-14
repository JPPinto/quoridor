package Logic;

/**
 * Created by João on 12/03/2015.
 */
public class Board {

    public static enum BoardState {
        UNPLAYABLE(0), EMPTY(1), WALL(2), PLAYER(3);

        private final int value;
        private BoardState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private BoardState board [][];

    public Board(){
        initGame();
    }

    public void setBoard(Pawn p, int direction){//0-up/1-down/2-left/3-right
        switch(direction){
            case 0:
                board[p.getLine()][p.getColumn()]=BoardState.EMPTY;
                p.setLine(p.getLine() - 2);
                if(board[p.getLine()][p.getColumn()]==BoardState.PLAYER){
                    p.setLine(p.getLine() - 2);
                }
                board[p.getLine()][p.getColumn()]=BoardState.PLAYER;
                break;
            case 1:
                board[p.getLine()][p.getColumn()]=BoardState.EMPTY;
                p.setLine(p.getLine()+2);
                if(board[p.getLine()][p.getColumn()]==BoardState.PLAYER){
                    p.setLine(p.getLine() + 2);
                }
                board[p.getLine()][p.getColumn()]=BoardState.PLAYER;
                break;
            case 2:
                board[p.getLine()][p.getColumn()]=BoardState.EMPTY;
                p.setColumn(p.getColumn()-2);
                if(board[p.getLine()][p.getColumn()]==BoardState.PLAYER){
                    p.setColumn(p.getColumn()-2);
                }
                board[p.getLine()][p.getColumn()]=BoardState.PLAYER;
                break;
            case 3:
                board[p.getLine()][p.getColumn()]=BoardState.EMPTY;
                p.setColumn(p.getColumn()+2);
                if(board[p.getLine()][p.getColumn()]==BoardState.PLAYER){
                    p.setColumn(p.getColumn()+2);
                }
                board[p.getLine()][p.getColumn()]=BoardState.PLAYER;
                break;
        }
    }

    public void printMatrix(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j].getValue() + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    public void fillMatrixBackground(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j]=BoardState.UNPLAYABLE;
            }
        }
    }

    public void initBoard(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(i%2==0 || j%2==0) {
                    board[i][j] = BoardState.EMPTY;
                }
            }
        }
    }

    public void initPawn(){
        board[8][8]=BoardState.PLAYER;
        board[16][8]=BoardState.PLAYER;
    }

    public void initGame(){
        board  = new BoardState[17][17];
        fillMatrixBackground();
        initBoard();
        initPawn();

        printMatrix();
    }
}
