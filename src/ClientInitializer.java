import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ClientInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent path = FXMLLoader.load(this.getClass().getResource("lk/ijse/gdse/view/ClientForm.fxml"));
        Scene mainScene = new Scene(path);
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();
    }
}
