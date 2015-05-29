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

    private Button pvp, pve,eve, exit, rules;

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
                GamePVP gamepvp=new GamePVP(primaryStage);
                primaryStage.setScene(gamepvp.getScene());
            }
        });

        //Creating Button pve
        pve = new Button("1PvCPU");
        changeBackgroundOnHoverUsingEvents(pve);
        pve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamePVE gamepve=new GamePVE(primaryStage);
                primaryStage.setScene(gamepve.getScene());
            }
        });

        //Creating Button eve
        eve = new Button("CPUvCPU");
        changeBackgroundOnHoverUsingEvents(eve);
        eve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("CPUvCPU");
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

        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(20);
        grid.add(pvp, 1, 1);
        grid.add(pve, 1, 2);
        grid.add(eve, 1, 3);
        grid.add(rules, 1, 4);
        grid.add(exit,1,5);
        grid.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setId("scene");
        root.setTop(flow);
        root.setCenter(grid);

        scene = new Scene(root, 600, 600);
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

    public Scene getScene(){
        return scene;
    }

}
