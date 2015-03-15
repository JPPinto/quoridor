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
import javafx.scene.image.ImageView;
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

        VBox container =new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        container.getChildren().addAll(group, drawButtons(primaryStage));
        root.setCenter(container);

        scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("css/gamefieldpvp.css");
    }

    public void setIBoard(){
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++){
                if(i%2!=0 && j%2==0){
                    iBoard.add(createVerticalWall(), i, j);
                }else if(j%2!=0 && i%2==0){
                    iBoard.add(createHorizontalWall(), i, j);
                }else if(i%2==0 || j%2==0){
                    iBoard.add(createBlock(), i, j);
                }else{
                    iBoard.add(createWallConnection(), i, j);
                }
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

        iBoard.add(image, p.getPawn().getColumn(), p.getPawn().getLine());
    }

    public void addInteractiveButtons(Player p){
        Pawn pawn = p.getPawn();

        if(pawn.getLine()-2>=0) {
            iBoard.add(up, pawn.getColumn(), pawn.getLine() - 2);
        }
        if(pawn.getLine()+2<17) {
            iBoard.add(down, pawn.getColumn(), pawn.getLine() + 2);
        }
        if(pawn.getColumn()-2>=0) {
            iBoard.add(left, pawn.getColumn() - 2, pawn.getLine());
        }
        if(pawn.getColumn()+2<17) {
            iBoard.add(right, pawn.getColumn() + 2, pawn.getLine());
        }
    }

    public void makeMove(Player p, int direction){
        clearIBoard();
        board.setBoard(p.getPawn(), direction);
        setIBoard();
        board.printMatrix();
        verifyWinning(p.getPawn());
    }

    private Rectangle createBlock() {
        Rectangle rectangle = new Rectangle(35, 35);
        rectangle.setStroke(Color.web("#59D1A8"));
        rectangle.setFill(Color.web("#59D1A8"));

        return rectangle;
    }

    private Rectangle createHorizontalWall() {
        Rectangle rectangle = new Rectangle(35, 5);
        rectangle.setStroke(Color.web("#59D1A8"));
        rectangle.setFill(Color.web("#59D1A8"));

        return rectangle;
    }

    private Rectangle createVerticalWall() {
        Rectangle rectangle = new Rectangle(5, 35);
        rectangle.setStroke(Color.web("#59D1A8"));
        rectangle.setFill(Color.web("#59D1A8"));

        return rectangle;
    }

    private Rectangle createWallConnection() {
        Rectangle rectangle = new Rectangle(5, 5);
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

    public static void changeBackgroundOnHoverUsingEvents(final Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.toFront();
                node.setScaleX(1.6);
                node.setScaleY(1.6);
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setScaleX(1);
                node.setScaleY(1);
            }
        });
    }

    public GridPane drawButtons(final Stage primaryStage){
        Button homebutton = new Button();
        homebutton.setId("home");
        changeBackgroundOnHoverUsingEvents(homebutton);
        homebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Quoridor main_menu=new Quoridor();
                main_menu.QuoridorInit(primaryStage);
                primaryStage.setScene(main_menu.getScene());
            }
        });

        GridPane gamepvp_buttons = new GridPane();
        gamepvp_buttons.setHgap(20);
        gamepvp_buttons.setHgap(20);
        gamepvp_buttons.add(homebutton, 1, 1);
        gamepvp_buttons.setAlignment(Pos.CENTER);

        return gamepvp_buttons;
    }
}
