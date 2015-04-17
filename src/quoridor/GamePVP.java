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
    private Button up, down, left, right, hWall, vWall;
    private GridPane iBoard;

    private boolean horizontal_wall=true;
    private int playerPlaying=1;

    public GamePVP(final Stage primaryStage){

        primaryStage.setTitle("Quoridor");

        //init board and players
        board = new Board();
        p1 = new Player(0, 8, 1, "pawn1");
        p2 = new Player(16, 8, 2, "pawn2");

        //create interactive button up
        up=new Button();
        change(up);
        up.setId("up");
        up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(playerPlaying==1){
                    makeMove(p1, 0);
                    addInteractiveButtons(p2);
                    playerPlaying=2;
                }else{
                    makeMove(p2, 0);
                    addInteractiveButtons(p1);
                    playerPlaying=1;
                }
            }
        });

        //create interactive button down
        down=new Button();
        change(down);
        down.setId("down");
        down.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(playerPlaying==1){
                    makeMove(p1, 1);
                    addInteractiveButtons(p2);
                    playerPlaying=2;
                }else{
                    makeMove(p2, 1);
                    addInteractiveButtons(p1);
                    playerPlaying=1;
                }
            }
        });

        //create interactive button left
        left=new Button();
        change(left);
        left.setId("left");
        left.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            if(playerPlaying==1){
                makeMove(p1, 2);
                addInteractiveButtons(p2);
                playerPlaying=2;
            }else{
                makeMove(p2, 2);
                addInteractiveButtons(p1);
                playerPlaying=1;
            }
            }
        });

        //create interactive button right
        right=new Button();
        change(right);
        right.setId("right");
        right.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            if(playerPlaying==1){
                makeMove(p1, 3);
                addInteractiveButtons(p2);
                playerPlaying=2;
            }else{
                makeMove(p2, 3);
                addInteractiveButtons(p1);
                playerPlaying=1;
            }
            }
        });

        BorderPane root = new BorderPane();
        root.setId("root");

        //create game board
        iBoard = new GridPane();
        iBoard.setId("iBoard");
        iBoard.setHgap(5);
        iBoard.setVgap(5);
        setIBoard();
        addInteractiveButtons(p1);//add first player
        Group group = new Group(iBoard);

        //reoraginize nodes position (vertical alignment)
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
                if(board.getBoard()[i][j]==Board.BoardState.BLOCK || board.getBoard()[i][j]==Board.BoardState.PLAYER){
                    iBoard.add(createBlock(), j, i);
                }else if(board.getBoard()[i][j]==Board.BoardState.WALL_BLOCK){
                    if(i%2!=0 && j%2==0){
                        iBoard.add(createHorizontalWall("#59D1A8"), j, i);
                    }else if(i%2==0 && j%2!=0){
                        iBoard.add(createVerticalWall("#59D1A8"), j, i);
                    }else {
                        iBoard.add(createWallConnection("#59D1A8"), j, i);
                    }
                }else if(board.getBoard()[i][j]==Board.BoardState.WALL){
                    if(i%2!=0 && j%2==0){
                        iBoard.add(createHorizontalWall("#E88E3A"), j, i);
                    }else if(i%2==0 && j%2!=0){
                        iBoard.add(createVerticalWall("#E88E3A"), j, i);
                    }else {
                        iBoard.add(createWallConnection("#E88E3A"), j, i);
                    }
                }
            }
        }
        addPawn(p1);
        addPawn(p2);
    }

    public void addPawn(Player p){
        ImageView image = new ImageView();
        image.setFitHeight(35);
        image.setFitWidth(35);
        image.setImage(p.getPawn().getImage());

        iBoard.add(image, p.getPawn().getColumn(), p.getPawn().getLine());
    }

    public void addInteractiveButtons(Player p){
        Pawn pawn = p.getPawn();

        if(pawn.getLine()-2>=0 && board.getBoard()[pawn.getLine()-1][pawn.getColumn()]!= Board.BoardState.WALL) {
            if(board.getBoard()[pawn.getLine()-2][pawn.getColumn()]!= Board.BoardState.PLAYER)
                iBoard.add(up, pawn.getColumn(), pawn.getLine() - 2);
            else if(pawn.getLine()-4>=0)
                iBoard.add(up, pawn.getColumn(), pawn.getLine() - 4);
        }
        if(pawn.getLine()+2<17 && board.getBoard()[pawn.getLine()+1][pawn.getColumn()]!= Board.BoardState.WALL) {
            if(board.getBoard()[pawn.getLine()+2][pawn.getColumn()]!= Board.BoardState.PLAYER)
                iBoard.add(down, pawn.getColumn(), pawn.getLine() + 2);
            else if(pawn.getLine()+4<17)
                iBoard.add(down, pawn.getColumn(), pawn.getLine() + 4);
        }
        if(pawn.getColumn()-2>=0 && board.getBoard()[pawn.getLine()][pawn.getColumn()-1]!= Board.BoardState.WALL) {
            if(board.getBoard()[pawn.getLine()][pawn.getColumn()-2]!= Board.BoardState.PLAYER)
                iBoard.add(left, pawn.getColumn() - 2, pawn.getLine());
            else if(pawn.getColumn()-4>=0)
                iBoard.add(left, pawn.getColumn() - 4, pawn.getLine());
        }
        if(pawn.getColumn()+2<17 && board.getBoard()[pawn.getLine()][pawn.getColumn()+1]!= Board.BoardState.WALL) {
            if(board.getBoard()[pawn.getLine()][pawn.getColumn()+2]!= Board.BoardState.PLAYER)
                iBoard.add(right, pawn.getColumn() + 2, pawn.getLine());
            else if(pawn.getColumn()+4<17)
                iBoard.add(right, pawn.getColumn() + 4, pawn.getLine());
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

    private Rectangle createHorizontalWall(String color) {
        Rectangle rectangle = new Rectangle(35, 5);
        rectangle.setStroke(Color.web(color));
        rectangle.setFill(Color.web(color));

        return rectangle;
    }

    private Rectangle createVerticalWall(String color) {
        Rectangle rectangle = new Rectangle(5, 35);
        rectangle.setStroke(Color.web(color));
        rectangle.setFill(Color.web(color));

        return rectangle;
    }

    private Rectangle createWallConnection(String color) {
        Rectangle rectangle = new Rectangle(5, 5);
        changeWallOnHoverUsingEvents(rectangle);
        rectangle.setStroke(Color.web(color));
        rectangle.setFill(Color.web(color));

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

    public void changeWallOnHoverUsingEvents(final Node node) {
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(playerPlaying==1){
                    board.createWall(p1.getWall()[0], horizontal_wall,GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
                    makeMove(p1, 4);
                    addInteractiveButtons(p2);
                    playerPlaying=2;
                }else{
                    board.createWall(p2.getWall()[0], horizontal_wall,GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
                    makeMove(p2, 4);
                    addInteractiveButtons(p1);
                    playerPlaying=1;
                }
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

        hWall = new Button();
        hWall.setId("hWall");
        hWall.setOpacity(0.5);
        changeBackgroundOnHoverUsingEvents(hWall);
        hWall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                horizontal_wall=true;
                hWall.setOpacity(0.5);
                vWall.setOpacity(1);
            }
        });

        vWall = new Button();
        vWall.setId("vWall");
        changeBackgroundOnHoverUsingEvents(vWall);
        vWall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                horizontal_wall=false;
                hWall.setOpacity(1);
                vWall.setOpacity(0.5);
            }
        });

        GridPane gamepvp_buttons = new GridPane();
        gamepvp_buttons.setHgap(20);
        gamepvp_buttons.setHgap(20);
        gamepvp_buttons.add(homebutton, 1, 1);
        gamepvp_buttons.add(hWall, 2, 1);
        gamepvp_buttons.add(vWall, 3, 1);
        gamepvp_buttons.setAlignment(Pos.CENTER);

        return gamepvp_buttons;
    }
}
