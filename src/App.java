import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Login & Register Window");
        Parent root = FXMLLoader.load(getClass().getResource("Scene/Authenticate.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
