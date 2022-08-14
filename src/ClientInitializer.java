import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ClientInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("You are online");
        Parent path = FXMLLoader.load(this.getClass().getResource("lk/ijse/gdse/view/LoginForm.fxml"));
        Scene mainScene = new Scene(path);
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Login");
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("lk/ijse/gdse/assets/chat.png"))));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
