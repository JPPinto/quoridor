package quoridor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Created by João on 02/03/2015.
 */
public class Rules {

    private Scene scene;

    public Rules(Stage primaryStage){

        primaryStage.setTitle("Quoridor");

        BorderPane root = new BorderPane();

        VBox box =new VBox();
        box.setAlignment(Pos.CENTER);
        Text title = new Text("Rules");
        title.setId("title");
        Label text = new Label("mmmmmmmmmmmmmmmmmmmmmmmmmmm dm mdshfui hiuhduifhouagui euhg uisdhuigudhguiue  eufh ueh uhuhdu  diusgds");
        text.setId("text");
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setAlignment(Pos.CENTER_LEFT);
        box.getChildren().addAll(title, text);

        root.setCenter(box);
        root.setId("border");
        scene = new Scene(root, 500, 500);
        scene.getStylesheets().add("css/rulesmenu.css");
    }

    public Scene getScene(){
        return scene;
    }

}
