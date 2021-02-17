package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Trry extends Application {

    boolean b = false;

    @Override
    public void start(Stage primaryStage) {

        Button btn = new Button();
        btn.setMnemonicParsing(true);
        btn.setText("Hell_o");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                b = !b;
                btn.setMnemonicParsing(false);
                if (!b) {
                    btn.setText("Hell_o");
                    System.out.println("Hello");
                } else {
                    btn.setText("W_orld");
                    System.out.println("World");
                }
                btn.setMnemonicParsing(true);
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }


}
