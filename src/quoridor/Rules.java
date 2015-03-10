package quoridor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import utilities.DecoratedText;

/**
 * Created by João on 02/03/2015.
 */
public class Rules {

    private Scene scene;

    public Rules(final Stage primaryStage){

        primaryStage.setTitle("Quoridor");

        BorderPane root = new BorderPane();

        VBox container =new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        container.setPadding(new Insets(20, 20, 20, 20));
        Text title = new Text("Rules");
        title.setId("title");


        //Adding rules text
        DecoratedText dtext=new DecoratedText(new Font("AngryBirds",20));
        dtext.setWrappingWidth(200);
        dtext.append("mmmmmmmmmmmmmmmmmmmmmmmmmmm dm mdshfui hiuhduifhouagui euhg uisdhuigudhguiue  eufh ueh uhuhdu  diusgds");
        dtext.setId("back");


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

        Button backbutton = new Button();
        backbutton.setId("backbutton_exited");
        changeBack(backbutton);
        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("backButton");
            }
        });

        Button frontbutton = new Button();
        frontbutton.setId("frontbutton_exited");
        changeFront(frontbutton);
        frontbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("frontButton");
            }
        });

        HBox rules_buttons = new HBox();
        rules_buttons.setSpacing(10);
        rules_buttons.getChildren().addAll(homebutton, backbutton, frontbutton);

        container.getChildren().addAll(title, dtext, rules_buttons);

        root.setCenter(container);
        root.setId("border");
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

}
