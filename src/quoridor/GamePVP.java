package quoridor;

import Logic.Board;
import Logic.Pawn;
import Logic.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private Scene scene;

    private Board board;
    private Player p1;
    private Player p2;

    private Button up, down, left, right;

    public GamePVP(final Stage primaryStage){

        board = new Board();
        p1 = new Player(0, 8, 1);
        p2 = new Player(16, 8, 2);

        up=new Button("TOP");
        up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("UP");
            }
        });
        down=new Button("BOT");
        down.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("BOT");
            }
        });
        left=new Button("LEF");
        left.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("LEFT");
            }
        });
        right=new Button("RIG");
        right.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("RIGHT");
            }
        });

        primaryStage.setTitle("Quoridor");

        BorderPane root = new BorderPane();

        TilePane grid = new TilePane();
        Group border = new Group(grid);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPrefRows(9);
        grid.setPrefColumns(9);
        for (int i = 0; i < 81; i++) {
            grid.getChildren().add(createElement());
        }
        border.setId("border");

        //border.set

        /*GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 50, 50, 50));
        gridPane.setHgap(17); gridPane.setVgap(17);

        gridPane.setGridLinesVisible(true);
        gridPane.add(up, 3, 2);
        gridPane.add(new Label(""), 17, 17);
        //root.setCenter(border);*/

        root.setId("root");
        scene = new Scene(border, 500, 500);
        scene.getStylesheets().add("css/gamefieldpvp.css");
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if(ke.getCode()== KeyCode.UP) {
                    board.setBoard(p2.getPawn(), 0);
                    board.printMatrix();
                }else if(ke.getCode()== KeyCode.DOWN){
                    board.setBoard(p2.getPawn(), 1);
                    board.printMatrix();
                }else if(ke.getCode()== KeyCode.LEFT){
                    board.setBoard(p2.getPawn(), 2);
                    board.printMatrix();
                }else if(ke.getCode()== KeyCode.RIGHT){
                    board.setBoard(p2.getPawn(), 3);
                    board.printMatrix();
                }
                verifyWinning(p2.getPawn());
            }
        });
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
                node.setId("backbutton_hover");
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setId("backbutton_exited");
            }
        });
    }

    private Rectangle createElement() {
        Rectangle rectangle = new Rectangle(20, 20);
        rectangle.setStroke(Color.ORANGE);
        rectangle.setFill(Color.STEELBLUE);

        return rectangle;
    }
}
