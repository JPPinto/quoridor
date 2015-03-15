package quoridor;

import Logic.Board;
import Logic.Pawn;
import Logic.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by João on 02/03/2015.
 */
public class GamePVP {

    private Board board;
    private Player p1;
    private Player p2;

    private Scene scene;
    private Button up, down, left, right;
    private GridPane iBoard;
    private Node [][] iBackBoard = new Node[9][9];

    public GamePVP(final Stage primaryStage){

        primaryStage.setTitle("Quoridor");

        //init board and players
        board = new Board();
        p1 = new Player(0, 0, 1, "pawn1");
        p2 = new Player(16, 8, 2, "pawn2");

        //create interactive button up
        up=new Button();
        change(up);
        up.setId("up");
        up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeMove(p2, 0);
            }
        });

        //create interactive button down
        down=new Button();
        change(down);
        down.setId("down");
        down.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeMove(p2, 1);
            }
        });

        //create interactive button left
        left=new Button();
        change(left);
        left.setId("left");
        left.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeMove(p2, 2);
            }
        });

        //create interactive button right
        right=new Button();
        change(right);
        right.setId("right");
        right.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeMove(p2, 3);
            }
        });

        BorderPane root = new BorderPane();
        root.setId("root");

        iBoard = new GridPane();
        iBoard.setId("iBoard");
        iBoard.setHgap(5);
        iBoard.setVgap(5);
        setIBoard();
        Group group = new Group(iBoard);
        root.setCenter(group);

        scene = new Scene(root, 500, 500);
        scene.getStylesheets().add("css/gamefieldpvp.css");
    }

    public void setIBoard(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++){
                iBackBoard[i][j] = createElement();
                iBoard.add(iBackBoard[i][j], i, j);
            }
        }
        //addPawn(p1);
        //addInteractiveButtons(p1);
        addPawn(p2);
        addInteractiveButtons(p2);
    }

    public void addPawn(Player p){//add Image
        ImageView image = new ImageView();
        image.setFitHeight(35);
        image.setFitWidth(35);
        image.setImage(p.getPawn().getImage());

        int line = (int)((double)p.getPawn().getLine()/2+0.5);
        int column = (int)((double)p.getPawn().getColumn()/2+0.5);

        iBackBoard[line][column] = image;
        iBoard.add(iBackBoard[line][column], column, line);
    }

    public void addInteractiveButtons(Player p){
        Pawn pawn = p.getPawn();
        int line = (int)((double)pawn.getLine()/2+0.5);
        int column = (int)((double)pawn.getColumn()/2+0.5);

        if(line-1>=0) {
            iBoard.add(up, column, line - 1);
        }
        if(line+1<9) {
            iBoard.add(down, column, line + 1);
        }
        if(column-1>=0) {
            iBoard.add(left, column - 1, line);
        }
        if(column+1<9) {
            iBoard.add(right, column + 1, line);
        }
    }

    public void makeMove(Player p, int direction){
        clearIBoard();
        board.setBoard(p.getPawn(), direction);
        setIBoard();
        //setIBoard(p);
        board.printMatrix();
        verifyWinning(p.getPawn());
    }

    private Rectangle createElement() {
        Rectangle rectangle = new Rectangle(35, 35);
        rectangle.setStroke(Color.web("#59D1A8"));
        rectangle.setFill(Color.web("#59D1A8"));

        return rectangle;
    }

    public void clearIBoard(){
        iBoard.getChildren().clear();
    }

    public void verifyWinning(Pawn p){
        if(p.getLine()==0 && p.getID()==1){
            System.out.println("Player 1 - You win");
        }else if(p.getLine()==0 && p.getID()==2){
            System.out.println("Player 2 - You win");
        }
    }

    public Scene getScene(){
        return scene;
    }

    public static void change(final Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setStyle("-fx-opacity:0.5");
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setStyle("-fx-opacity:1");
            }
        });
    }
}
