package quoridor;

import Logic.Board;
import Logic.Game;
import Logic.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import minimax.GameState;
import minimax.Minimax;
import minimax.Square;
import minimax.Wall;

/**
 * Created by João on 02/03/2015.
 */
public class GameEVE {

    private Game game;

    private Scene scene;
    private Stage stage;
    private Button hWall, vWall, but;
    private GridPane iBoard;
    private Label label;

    private boolean horizontal_wall=true;

    public GameEVE(final Stage primaryStage){

        primaryStage.setTitle("Quoridor");
        stage=primaryStage;
        game = new Game(2,2);

        BorderPane root = new BorderPane();
        root.setId("root");

        //create game board
        iBoard = new GridPane();
        iBoard.setId("iBoard");
        iBoard.setHgap(5);
        iBoard.setVgap(5);
        setIBoard();
        Group group = new Group(iBoard);

        //reoraginize nodes position (vertical alignment)
        VBox container =new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        HBox container2 =new HBox();
        container2.setAlignment(Pos.CENTER);
        container2.getChildren().addAll(group, drawButtons(primaryStage));
        container.getChildren().addAll(group, container2);
        root.setCenter(container);

        scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("css/gamefieldpvp.css");
    }

    public void setIBoard(){
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++){
                if(game.getBoard().getBoard()[i][j]== Board.BoardState.BLOCK || game.getBoard().getBoard()[i][j]==Board.BoardState.PLAYER){
                    iBoard.add(createBlock(), j, i);
                }else if(game.getBoard().getBoard()[i][j]==Board.BoardState.WALL_BLOCK){
                    if(i%2!=0 && j%2==0){
                        iBoard.add(createHorizontalWall("#59D1A8"), j, i);
                    }else if(i%2==0 && j%2!=0){
                        iBoard.add(createVerticalWall("#59D1A8"), j, i);
                    }else {
                        iBoard.add(createWallConnection("#59D1A8"), j, i);
                    }
                }else if(game.getBoard().getBoard()[i][j]==Board.BoardState.WALL){
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
        addPawn(game.getP1());
        addPawn(game.getP2());
    }

    public void addPawn(Player p){
        ImageView image = new ImageView();
        image.setFitHeight(35);
        image.setFitWidth(35);
        image.setImage(p.getPawn().getImage());

        iBoard.add(image, p.getPawn().getColumn(), p.getPawn().getLine());
    }

    public void makeMove(Minimax m){
        makeAIMove(m);
        game.verifyWinning(game.currentPlayerPlaying().getPawn());
        game.nextTurn();
        label.setText("       Player " + game.getCurrentPlayer() + "\n       Walls: " + game.currentPlayerPlaying().leftWallNum());
        game.nextPlayer();
    }

    public void makeAIMove(Minimax m){
        clearIBoard();
        GameState gs = new GameState(game);
        Object obj = m.fromString(m.getMove(gs));
        if(obj instanceof Wall){
            System.out.println("Wall: "+((Wall) obj).toString());
            System.out.println("Placing WALL on Row:  " + ((Wall) obj).getNorthWest().getRow() + " Colunm: " + ((Wall) obj).getNorthWest().getColumn());
            System.out.println("Placing WALL on Row:  " + (((Wall) obj).getNorthWest().getRow() * 2+1) + " Colunm: " + (((Wall) obj).getNorthWest().getColumn() * 2+1));
            game.createWall(((Wall) obj).getOrientation()== Logic.Wall.WDirection.HORIZONTAL?true:false, ((Wall) obj).getNorthWest().getRow() * 2+1, ((Wall) obj).getNorthWest().getColumn() * 2+1);
            //    game.getCurrentPlayer()==gs.addNumWalls2();
            //}
        }else{
            System.out.println("line = "+(((Square)obj).getRow())+"|column = "+(((Square)obj).getColumn()));
            game.getBoard().getBoard()[game.currentPlayerPlaying().getPawn().getLine()][game.currentPlayerPlaying().getPawn().getColumn()]= Board.BoardState.BLOCK;
            game.currentPlayerPlaying().getPawn().setLine(((Square) obj).getRow() * 2);
            game.currentPlayerPlaying().getPawn().setColumn(((Square)obj).getColumn()*2);
            game.getBoard().getBoard()[((Square)obj).getRow()*2][((Square)obj).getColumn()*2]= Board.BoardState.PLAYER;
        }
        game.getBoard().printMatrix();
        setIBoard();
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
                Quoridor main_menu = new Quoridor();
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
                horizontal_wall = true;
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
                horizontal_wall = false;
                hWall.setOpacity(1);
                vWall.setOpacity(0.5);
            }
        });

        but=new Button();
        change(but);
        but.setId("right");
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(game.getCurrentPlayer()==1){
                    makeMove(game.getM1());
                }else{
                    makeMove(game.getM2());
                }
            }
        });

        label = new Label("       Player " + game.getCurrentPlayer() + "\n       Walls: " + game.currentPlayerPlaying().leftWallNum());//info to player 1
        label.setContentDisplay(ContentDisplay.CENTER);

        GridPane gamepvp_buttons = new GridPane();
        gamepvp_buttons.setHgap(20);
        gamepvp_buttons.setHgap(20);
        gamepvp_buttons.add(homebutton, 1, 1);
        gamepvp_buttons.add(hWall, 2, 1);
        gamepvp_buttons.add(vWall, 3, 1);
        gamepvp_buttons.add(label, 4, 1);
        gamepvp_buttons.add(but, 5, 1);
        gamepvp_buttons.setAlignment(Pos.CENTER);

        return gamepvp_buttons;
    }
}
