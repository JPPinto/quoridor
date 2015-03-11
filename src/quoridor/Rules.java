package quoridor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by João on 02/03/2015.
 */
public class Rules {

    private Scene scene;

    private ArrayList<String> rules =new ArrayList<String>();
    private int rules_count=0;
    private Text dtext;

    private Button frontbutton, backbutton;

    public Rules(final Stage primaryStage){

        primaryStage.setTitle("Quoridor");
        initRules();

        BorderPane root = new BorderPane();

        VBox container =new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        Text title = new Text("Rules");
        title.setId("title");


        //Adding rules text
        FlowPane rText = new FlowPane();
        rText.setId("flowpane");
        dtext=new Text(rules.get(rules_count));
        dtext.setWrappingWidth(400);
        dtext.setStyle("");
        dtext.setId("text");
        rText.getChildren().add(dtext);


        //creating buttons and adding them to Hbox layout
        Button homebutton = new Button();
        homebutton.setId("homebutton_exited");
        changeHome(homebutton);
        homebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Quoridor main_menu=new Quoridor();
                main_menu.QuoridorInit(primaryStage);
                primaryStage.setScene(main_menu.getScene());
                System.out.println("homeButton");
            }
        });

        backbutton = new Button();
        backbutton.setId("backbutton_exited");
        changeBack(backbutton);
        backbutton.setVisible(false);
        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rules_count=rules_count-1<0?7:rules_count-1;
                dtext.setText(rules.get(rules_count));
                if(rules_count==0){
                    backbutton.setVisible(false);
                }else{
                    frontbutton.setVisible(true);
                }
            }
        });

        frontbutton = new Button();
        frontbutton.setId("frontbutton_exited");
        changeFront(frontbutton);
        frontbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rules_count=rules_count+1>7?0:rules_count+1;
                dtext.setText(rules.get(rules_count));
                if(rules_count==7){
                    frontbutton.setVisible(false);
                }else{
                    backbutton.setVisible(true);
                }
            }
        });

        HBox rules_buttons = new HBox();
        rules_buttons.setSpacing(10);
        rules_buttons.getChildren().addAll(homebutton, backbutton, frontbutton);

        container.getChildren().addAll(title, rText, rules_buttons);

        root.setCenter(container);
        root.setId("root");
        scene = new Scene(root, 500, 500);
        scene.getStylesheets().add("css/rulesmenu.css");
    }

    public Scene getScene(){
        return scene;
    }

    public static void changeBack(final Node node) {
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

    public static void changeFront(final Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setId("frontbutton_hover");
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setId("frontbutton_exited");
            }
        });
    }

    public static void changeHome(final Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setId("homebutton_hover");
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setId("homebutton_exited");
            }
        });
    }

    public void initRules(){
        rules.add("CONTENTS:\n\n" +
                "   - one board with 81 squares\n" +
                "   - 20 fences\n" +
                "   - 2 pawns\n");
        rules.add("PURPOSE OF THE GAME\n\n" +
                "To be the first to reach the line opposite to one's base line.\n");
        rules.add("RULES FOR 2 PLAYERS\n\n" +
                "When the game starts the fences are placed in their storage area (10 for each player)." +
                "Each player places his pawn in the centre of his base line." +
                "A draw will determine who starts first.\n");
        rules.add("HOW TO PLAY:\n\n" +
                "Each player in turn, chooses to move his pawn or to put up one of his fences." +
                "When he has run out of fences, the player must move his pawn.\n");
        rules.add("PAWN MOVES\n\n" +
                "The pawns are moved one square at a time, horizontally or vertically, forwards or backwards." +
                "The pawns must get around the fences.\n");
        rules.add("POSITIONING OF THE FENCES\n\n" +
                "The fences must be placed between 2 sets of 2 squares." +
                "The fences can be used to facilitate the player’s progress or to impede that of the opponent, however, an access to the goal line must always be left open.\n");
        rules.add("FACE TO FACE\n\n" +
                "When two pawns face each other on neighboring squares which are not separated " +
                "by a fence, the player whose turn it is can jump the opponent’s pawn " +
                "(and place himself behind him), thus advancing an extra square. If there is a fence behind the said pawn, the player can place his pawn to the left or the right of the other pawn.\n");
        rules.add("END OF GAME\n\n" +
                "The first player who reaches one of the 9 squares opposite his base line is the winner.\n");
    }

}
