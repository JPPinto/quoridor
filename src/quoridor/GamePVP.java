package quoridor;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by João on 02/03/2015.
 */
public class GamePVP {

    private Scene scene;

    public GamePVP(final Stage primaryStage){

        primaryStage.setTitle("Quoridor");

        BorderPane root = new BorderPane();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 50, 50, 50));
        gridPane.setHgap(9); gridPane.setVgap(9);

        gridPane.setGridLinesVisible(true);
        //root.setCenter(border);

        root.setId("root");
        scene = new Scene(gridPane, 500, 500);
        scene.getStylesheets().add("css/gamefieldpvp.css");
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
