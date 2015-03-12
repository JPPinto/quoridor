package quoridor;

import Logic.Board;
import Logic.Pawn;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by João on 02/03/2015.
 */
public class GamePVP {

    private Scene scene;

    private Board board;
    private Pawn p1 = new Pawn(0, 8, 1);
    private Pawn p2 = new Pawn(16, 8, 2);

    public GamePVP(final Stage primaryStage){

        board = new Board();

        primaryStage.setTitle("Quoridor");

        BorderPane root = new BorderPane();

        GridPane gridPane = new GridPane();
        gridPane.setId("border");
        gridPane.setPadding(new Insets(50, 50, 50, 50));
        gridPane.setHgap(9); gridPane.setVgap(9);

        gridPane.setGridLinesVisible(true);
        //root.setCenter(border);

        root.setId("root");
        scene = new Scene(gridPane, 500, 500);
        scene.getStylesheets().add("css/gamefieldpvp.css");
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if(ke.getCode()== KeyCode.UP) {
                    board.setBoard(p2,p1, 0);
                    board.printMatrix();
                }else if(ke.getCode()== KeyCode.DOWN){
                    board.setBoard(p2, p1,1);
                    board.printMatrix();
                }else if(ke.getCode()== KeyCode.LEFT){
                    board.setBoard(p2, p1,2);
                    board.printMatrix();
                }else if(ke.getCode()== KeyCode.RIGHT){
                    board.setBoard(p2, p1,3);
                    board.printMatrix();
                }
            }
        });
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
}
