package quoridor;

import java.io.InputStream;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Quoridor extends Application {

    private Button pvp, pve, exit, rules;

    //Font
    public InputStream is;
    public Font font;

    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    public Quoridor() {}

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Quoridor");
        QuoridorInit(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void QuoridorInit(final Stage primaryStage){

        FlowPane flow =new FlowPane();
        flow.setPadding(new Insets(50,0,0,0));
        flow.setAlignment(Pos.CENTER);
        Text title = new Text("Quoridor");
        title.setId("title");
        flow.getChildren().add(title);

        //Creating Button pvp
        pvp = new Button("1Pv2P");
        changeBackgroundOnHoverUsingEvents(pvp);
        pvp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("1Pv2P");
            }
        });

        //Creating Button pve
        pve = new Button("1PvCPU");
        changeBackgroundOnHoverUsingEvents(pve);
        pve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("1PvCPU");
            }
        });

        //Creating Button exit
        exit = new Button("Exit");
        changeBackgroundOnHoverUsingEvents(exit);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        //Creating Button rules
        rules = new Button("Rules");
        changeBackgroundOnHoverUsingEvents(rules);
        rules.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Rules rules=new Rules(primaryStage);
                primaryStage.setScene(rules.getScene());
            }
        });

        BorderPane root = new BorderPane();
        root.setId("scene");
        VBox vbox = new VBox();
        vbox.setSpacing(15);
        vbox.setPadding(new Insets(30, 30, 30, 30));
        vbox.getChildren().addAll(pvp, pve, rules, exit);
        vbox.setAlignment(Pos.CENTER);
        root.setTop(flow);
        root.setCenter(vbox);
        //root.getChildren().addAll(title,vbox);

        scene = new Scene(root, 500, 500);
        scene.getStylesheets().add("css/mainmenu.css");
    }

    private void loadFont() {
        /*try {

         is = getClass().getResourceAsStream("/Font/angrybirds-regular.ttf");//AngryBirds
         font = Font.createFont(Font.TRUETYPE_FONT, is);
         } catch (IOException | FontFormatException ex) {
         Logger.getLogger(Quoridor.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    public static void changeBackgroundOnHoverUsingEvents(final Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setId("hoverB");
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setId("exitedB");
            }
        });
    }

    public Scene getScene(){
        return scene;
    }

}
